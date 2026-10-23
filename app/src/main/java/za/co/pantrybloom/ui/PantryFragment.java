package za.co.pantrybloom.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import za.co.pantrybloom.R;
import za.co.pantrybloom.data.PantryEntry;
import za.co.pantrybloom.data.PantryRepository;
import za.co.pantrybloom.ui.adapters.PantryCardAdapter;

public class PantryFragment extends Fragment {
    private PantryCardAdapter adapter;
    private TextView empty, count;

    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater,
                                               @Nullable ViewGroup parent,@Nullable Bundle state) {
        View view=inflater.inflate(R.layout.fragment_pantry,parent,false);
        RecyclerView list=view.findViewById(R.id.pantry_list);
        list.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter=new PantryCardAdapter(entry -> {
            Intent edit=new Intent(requireContext(),IngredientEditorActivity.class);
            edit.putExtra(IngredientEditorActivity.EXTRA_ID,entry.id);
            startActivity(edit);
        });
        list.setAdapter(adapter);
        empty=view.findViewById(R.id.pantry_empty);
        count=view.findViewById(R.id.pantry_count);
        view.findViewById(R.id.add_ingredient).setOnClickListener(v ->
                startActivity(new Intent(requireContext(),IngredientEditorActivity.class)));
        return view;
    }

    @Override public void onResume() {
        super.onResume();
        PantryRepository.get(requireContext()).pantry(this::present);
    }

    private void present(List<PantryEntry> items) {
        if (!isAdded() || getView() == null) return;
        boolean alerts=requireContext().getSharedPreferences("bloom_prefs",0)
                .getBoolean("expiry_alerts",true);
        adapter.submit(items,alerts);
        empty.setVisibility(items.isEmpty()?View.VISIBLE:View.GONE);
        count.setText("MY INGREDIENTS  ·  " + items.size());
    }
}
