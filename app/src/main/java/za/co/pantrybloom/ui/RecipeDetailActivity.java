package za.co.pantrybloom.ui;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;
import za.co.pantrybloom.R;
import za.co.pantrybloom.data.PantryRepository;
import za.co.pantrybloom.data.RecipeRequirement;

public class RecipeDetailActivity extends AppCompatActivity {
    public static final String EXTRA_RECIPE_ID="recipe_id";

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_recipe_detail);
        findViewById(R.id.detail_back).setOnClickListener(v -> finish());
        long id=getIntent().getLongExtra(EXTRA_RECIPE_ID,-1);
        if (id<=0) { finish();return; }
        PantryRepository.get(this).recipeDetail(id,detail -> {
            if (isFinishing() || isDestroyed()) return;
            if (detail.recipe==null) { finish();return; }
            ((TextView)findViewById(R.id.detail_title)).setText(detail.recipe.title);
            ((TextView)findViewById(R.id.detail_method)).setText(detail.recipe.directions);
            StringBuilder needs=new StringBuilder();
            for (RecipeRequirement item:detail.required) {
                needs.append("• ").append(String.format(Locale.ROOT,"%s %s ",
                        item.amount==Math.floor(item.amount)?String.format(Locale.ROOT,"%.0f",item.amount)
                                :String.format(Locale.ROOT,"%.3f",item.amount), item.unit))
                        .append(item.ingredientKey).append("\n");
            }
            ((TextView)findViewById(R.id.detail_ingredients)).setText(needs.toString().trim());
        });
    }
}
