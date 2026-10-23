package za.co.pantrybloom.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import za.co.pantrybloom.R;
import za.co.pantrybloom.data.PantryRepository;

public class RecipeCardAdapter extends RecyclerView.Adapter<RecipeCardAdapter.Card> {
    private final List<PantryRepository.RecipeCard> recipes=new ArrayList<>();
    private final Consumer<PantryRepository.RecipeCard> onClick;
    public RecipeCardAdapter(Consumer<PantryRepository.RecipeCard> onClick) { this.onClick=onClick; }

    public void submit(List<PantryRepository.RecipeCard> updated) {
        recipes.clear(); recipes.addAll(updated);notifyDataSetChanged();
    }

    @NonNull @Override public Card onCreateViewHolder(@NonNull ViewGroup parent,int type) {
        return new Card(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe,parent,false));
    }
    @Override public void onBindViewHolder(@NonNull Card card,int position) {
        PantryRepository.RecipeCard recipe=recipes.get(position);
        card.title.setText(recipe.recipe.title);
        card.subtitle.setText("All " + recipe.ingredientCount + " ingredients available");
        card.itemView.setOnClickListener(v -> onClick.accept(recipe));
    }
    @Override public int getItemCount() { return recipes.size(); }

    static class Card extends RecyclerView.ViewHolder {
        final TextView title,subtitle;
        Card(View view) {
            super(view);
            title=view.findViewById(R.id.recipe_title);
            subtitle=view.findViewById(R.id.recipe_subtitle);
        }
    }
}
