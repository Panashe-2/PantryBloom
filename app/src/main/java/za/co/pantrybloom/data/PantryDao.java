package za.co.pantrybloom.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface PantryDao {
    @Query("SELECT * FROM pantry ORDER BY name COLLATE NOCASE")
    List<PantryEntry> all();

    @Query("SELECT * FROM pantry WHERE id=:id LIMIT 1")
    PantryEntry find(long id);

    @Insert long add(PantryEntry entry);
    @Update void change(PantryEntry entry);
    @Delete void remove(PantryEntry entry);
}
