package za.co.pantrybloom.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pantry")
public class PantryEntry {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;
    public double amount;
    public String unit;
    public String expiry; // ISO yyyy-MM-dd or empty string

    public PantryEntry(String name, double amount, String unit, String expiry) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.expiry = expiry;
    }
}
