
package za.co.pantrybloom.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(
        tableName = "recipe_requirements",
        primaryKeys = {"recipeId", "ingredientKey"}
)
public class RecipeRequirement {

    public long recipeId;

    @NonNull
    public String ingredientKey;

    public double amount;
    public String unit;

    public RecipeRequirement(
            long recipeId,
            @NonNull String ingredientKey,
            double amount,
            String unit) {

        this.recipeId = recipeId;
        this.ingredientKey = ingredientKey;
        this.amount = amount;
        this.unit = unit;
    }
}
