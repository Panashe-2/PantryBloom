package za.co.pantrybloom.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipes")
public class RecipeEntry {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String title;
    public String directions;

    public RecipeEntry(String title, String directions) {
        this.title = title;
        this.directions = directions;
    }
}
