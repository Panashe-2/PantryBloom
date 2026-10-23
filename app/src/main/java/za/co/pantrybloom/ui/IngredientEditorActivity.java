package za.co.pantrybloom.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import za.co.pantrybloom.R;
import za.co.pantrybloom.data.PantryEntry;
import za.co.pantrybloom.data.PantryRepository;

public class IngredientEditorActivity extends AppCompatActivity {
    public static final String EXTRA_ID="entry_id";
    private static final String[] UNITS={"pcs","g","kg","ml","L"};
    private EditText name, amount, expiry;
    private Spinner unit;
    private Button save, delete;
    private PantryEntry current;
    private PantryRepository repo;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_ingredient_editor);
        repo=PantryRepository.get(this);
        name=findViewById(R.id.name_input);
        amount=findViewById(R.id.amount_input);
        expiry=findViewById(R.id.expiry_input);
        unit=findViewById(R.id.unit_input);
        save=findViewById(R.id.save_button);
        delete=findViewById(R.id.delete_button);
        ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,UNITS);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unit.setAdapter(spinnerAdapter);
        findViewById(R.id.cancel_button).setOnClickListener(v -> finish());
        save.setOnClickListener(v -> saveEntry());
        delete.setOnClickListener(v -> askDelete());

        long id=getIntent().getLongExtra(EXTRA_ID,0);
        if (id > 0) {
            save.setEnabled(false);
            repo.entry(id,item -> {
                if (isFinishing() || isDestroyed()) return;
                if (item == null) { toast("Ingredient not found"); finish(); return; }
                current=item;
                ((TextView)findViewById(R.id.edit_title)).setText("Edit Ingredient");
                name.setText(item.name);
                amount.setText(format(item.amount));
                expiry.setText(item.expiry);
                for (int i=0;i<UNITS.length;i++) if (UNITS[i].equals(item.unit)) unit.setSelection(i);
                delete.setVisibility(View.VISIBLE);
                save.setEnabled(true);
            });
        }
    }

    private void saveEntry() {
        String label=name.getText().toString().trim();
        String quantity=amount.getText().toString().trim();
        String date=expiry.getText().toString().trim();
        if (label.isEmpty()) { name.setError("Name is required"); name.requestFocus(); return; }
        if (label.length()>70) { name.setError("Use 70 characters or fewer"); return; }
        double value;
        try { value=Double.parseDouble(quantity); }
        catch (NumberFormatException ex) { amount.setError("Enter a number"); return; }
        if (!Double.isFinite(value) || value <= 0 || value > 1000000) {
            amount.setError("Amount must be greater than 0 and no more than 1,000,000"); return;
        }
        if (!date.isEmpty() && !validDate(date)) {
            expiry.setError("Enter a real date as YYYY-MM-DD"); return;
        }
        PantryEntry entry=current==null ? new PantryEntry(label,value,UNITS[unit.getSelectedItemPosition()],date)
                :current;
        entry.name=label;
        entry.amount=value;
        entry.unit=UNITS[unit.getSelectedItemPosition()];
        entry.expiry=date;
        save.setEnabled(false);
        repo.save(entry,ok -> {
            if (isFinishing() || isDestroyed()) return;
            if (ok) finish();
            else { save.setEnabled(true); toast("Could not save ingredient"); }
        });
    }

    private boolean validDate(String iso) {
        if (!iso.matches("\\d{4}-\\d{2}-\\d{2}")) return false;
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd",Locale.ROOT);
        formatter.setLenient(false);
        try { return formatter.parse(iso)!=null; }
        catch (ParseException ex) { return false; }
    }

    private void askDelete() {
        if (current == null) return;
        new AlertDialog.Builder(this).setTitle("Delete ingredient?")
                .setMessage("Remove " + current.name + " from your pantry?")
                .setNegativeButton("Cancel",(dialog,which)->dialog.dismiss())
                .setPositiveButton("Delete",(dialog,which)->repo.delete(current,ok -> {
                    if (isFinishing() || isDestroyed()) return;
                    if (ok) finish(); else toast("Could not delete ingredient");
                })).show();
    }

    private void toast(String message) { Toast.makeText(this,message,Toast.LENGTH_SHORT).show(); }
    private static String format(double n) {
        return n == Math.floor(n) ? String.format(Locale.ROOT,"%.0f",n)
                : String.format(Locale.ROOT,"%.3f",n).replaceAll("0+$", "").replaceAll("\\.$", "");
    }
}
