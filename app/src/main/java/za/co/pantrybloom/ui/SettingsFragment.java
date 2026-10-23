package za.co.pantrybloom.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import za.co.pantrybloom.R;

public class SettingsFragment extends Fragment {
    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater,
                                               @Nullable ViewGroup parent,@Nullable Bundle state) {
        View root=inflater.inflate(R.layout.fragment_settings,parent,false);
        Switch expiry=root.findViewById(R.id.expiry_switch);
        boolean stored=requireContext().getSharedPreferences("bloom_prefs",0)
                .getBoolean("expiry_alerts",true);
        expiry.setChecked(stored);
        expiry.setOnCheckedChangeListener((button,checked) ->
                requireContext().getSharedPreferences("bloom_prefs",0).edit()
                        .putBoolean("expiry_alerts",checked).apply());
        return root;
    }
}
