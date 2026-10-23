package za.co.pantrybloom.data;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import za.co.pantrybloom.logic.StrictRecipeMatcher;

/** All Room operations run on a background executor; view callbacks run on the UI thread. */
public final class PantryRepository {
    private static volatile PantryRepository INSTANCE;
    private final BloomDatabase database;
    private final ExecutorService worker=Executors.newSingleThreadExecutor();
    private final Handler main=new Handler(Looper.getMainLooper());

    public static class RecipeCard {
        public final RecipeEntry recipe;
        public final int ingredientCount;
        RecipeCard(RecipeEntry recipe, int ingredientCount) {
            this.recipe=recipe; this.ingredientCount=ingredientCount;
        }
    }

    public static class RecipeDetail {
        public final RecipeEntry recipe;
        public final List<RecipeRequirement> required;
        RecipeDetail(RecipeEntry recipe, List<RecipeRequirement> required) {
            this.recipe=recipe; this.required=required;
        }
    }

    private PantryRepository(Context context) {
        database=BloomDatabase.get(context);
        worker.execute(() -> RecipeSeed.populate(database.recipes()));
    }

    public static PantryRepository get(Context context) {
        if (INSTANCE == null) {
            synchronized (PantryRepository.class) {
                if (INSTANCE == null) INSTANCE=new PantryRepository(context.getApplicationContext());
            }
        }
        return INSTANCE;
    }

    private <T> void deliver(T value, Consumer<T> callback) {
        main.post(() -> callback.accept(value));
    }

    public void pantry(Consumer<List<PantryEntry>> callback) {
        worker.execute(() -> deliver(database.pantry().all(),callback));
    }

    public void entry(long id,Consumer<PantryEntry> callback) {
        worker.execute(() -> deliver(database.pantry().find(id),callback));
    }

    public void save(PantryEntry item,Consumer<Boolean> callback) {
        worker.execute(() -> {
            try {
                if (item.id == 0) database.pantry().add(item);
                else database.pantry().change(item);
                deliver(true,callback);
            } catch (RuntimeException ex) { deliver(false,callback); }
        });
    }

    public void delete(PantryEntry item,Consumer<Boolean> callback) {
        worker.execute(() -> {
            try {
                database.pantry().remove(item);
                deliver(true,callback);
            } catch (RuntimeException ex) { deliver(false,callback); }
        });
    }

    public void suggestions(Consumer<List<RecipeCard>> callback) {
        worker.execute(() -> {
            List<StrictRecipeMatcher.Amount> stock=new ArrayList<>();
            for (PantryEntry item:database.pantry().all()) {
                stock.add(new StrictRecipeMatcher.Amount(item.name,item.amount,item.unit));
            }
            List<RecipeCard> result=new ArrayList<>();
            for (RecipeEntry recipe:database.recipes().all()) {
                List<StrictRecipeMatcher.Amount> needs=new ArrayList<>();
                for (RecipeRequirement req:database.recipes().requirements(recipe.id)) {
                    needs.add(new StrictRecipeMatcher.Amount(req.ingredientKey,req.amount,req.unit));
                }
                if (StrictRecipeMatcher.canMake(stock, needs)) result.add(new RecipeCard(recipe, needs.size()));
            }
            deliver(result,callback);
        });
    }

    public void recipeDetail(long id,Consumer<RecipeDetail> callback) {
        worker.execute(() -> deliver(new RecipeDetail(database.recipes().find(id),
                database.recipes().requirements(id)),callback));
    }
}
