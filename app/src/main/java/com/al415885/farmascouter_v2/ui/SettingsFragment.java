package com.al415885.farmascouter_v2.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;

import com.al415885.farmascouter_v2.MainActivity;
import com.al415885.farmascouter_v2.R;

public class SettingsFragment extends PreferenceFragmentCompat{
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setNavigationDrawerCheckedItem();
    }

    private void setNavigationDrawerCheckedItem() {
        for (int i = 0; i < 4; i++) {
            MenuItem item = ((MainActivity) requireActivity()).getNavigationDrawer().getMenu().getItem(i);
            if(item.getItemId() == R.id.navigation_settings){
                item.setChecked(true);
            }
            else item.setChecked(false);
        }
    }
}
