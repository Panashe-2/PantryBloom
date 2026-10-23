package za.co.pantrybloom.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities={PantryEntry.class, RecipeEntry.class, RecipeRequirement.class}, version=1, exportSchema=false)
public abstract class BloomDatabase extends RoomDatabase {
    public abstract PantryDao pantry();
    public abstract RecipeDao recipes();

    private static volatile BloomDatabase INSTANCE;

    public static BloomDatabase get(Context context) {
        if (INSTANCE == null) {
            synchronized (BloomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BloomDatabase.class, "pantry_bloom.db").build();
                }
            }
        }
        return INSTANCE;
    }
}
