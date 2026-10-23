package za.co.pantrybloom.ui;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import za.co.pantrybloom.R;

/** One host Activity with three top-level Fragments; detail/editor use explicit Intents. */
public class MainActivity extends AppCompatActivity {
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button pantry=findViewById(R.id.nav_pantry);
        Button recipes=findViewById(R.id.nav_recipes);
        Button settings=findViewById(R.id.nav_settings);
        pantry.setOnClickListener(v -> show(new PantryFragment()));
        recipes.setOnClickListener(v -> show(new RecipesFragment()));
        settings.setOnClickListener(v -> show(new SettingsFragment()));
        if (savedInstanceState == null) show(new PantryFragment());
    }
    private void show(Fragment screen) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, screen).commit();
    }
}
