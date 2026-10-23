package za.co.pantrybloom.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface RecipeDao {
    @Query("SELECT COUNT(*) FROM recipes") int count();
    @Query("SELECT * FROM recipes ORDER BY title") List<RecipeEntry> all();
    @Query("SELECT * FROM recipes WHERE id=:id LIMIT 1") RecipeEntry find(long id);
    @Query("SELECT * FROM recipe_requirements WHERE recipeId=:id ORDER BY ingredientKey")
    List<RecipeRequirement> requirements(long id);
    @Insert long insertRecipe(RecipeEntry recipe);
    @Insert void insertRequirement(RecipeRequirement requirement);
}
