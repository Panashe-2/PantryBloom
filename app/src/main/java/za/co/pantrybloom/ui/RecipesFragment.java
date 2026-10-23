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
import za.co.pantrybloom.data.PantryRepository;
import za.co.pantrybloom.ui.adapters.RecipeCardAdapter;

public class RecipesFragment extends Fragment {
    private RecipeCardAdapter adapter;
    private TextView empty, count;

    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater,
                                               @Nullable ViewGroup parent,@Nullable Bundle state) {
        View view=inflater.inflate(R.layout.fragment_recipes,parent,false);
        RecyclerView list=view.findViewById(R.id.recipes_list);
        list.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter=new RecipeCardAdapter(card -> {
            Intent intent=new Intent(requireContext(),RecipeDetailActivity.class);
            intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_ID,card.recipe.id);
            startActivity(intent);
        });
        list.setAdapter(adapter);
        empty=view.findViewById(R.id.recipes_empty);
        count=view.findViewById(R.id.recipes_count);
        return view;
    }

    @Override public void onResume() {
        super.onResume();
        PantryRepository.get(requireContext()).suggestions(this::present);
    }

    private void present(List<PantryRepository.RecipeCard> items) {
        if (!isAdded() || getView()==null) return;
        adapter.submit(items);
        empty.setVisibility(items.isEmpty()?View.VISIBLE:View.GONE);
        count.setText("MATCHING RECIPES  ·  " + items.size());
    }
}
