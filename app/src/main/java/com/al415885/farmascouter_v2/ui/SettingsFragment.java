package com.al415885.farmascouter_v2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.al415885.farmascouter_v2.AboutActivity;
import com.al415885.farmascouter_v2.DrugActivity;
import com.al415885.farmascouter_v2.MainActivity;
import com.al415885.farmascouter_v2.R;

public class SettingsFragment extends PreferenceFragmentCompat{

    // Class-specific variables
    private Preference about;

    /** Constructor for the class */
    public SettingsFragment(){}

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preference_settings, rootKey);
        setNavigationDrawerCheckedItem();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        about =  getPreferenceManager().findPreference("keyAbout");
        about.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                Intent intent = new Intent(getContext(), AboutActivity.class);
                startActivity(intent);
                return true;
            }
        });
        return view;
    }

    private void setNavigationDrawerCheckedItem() {
        for (int i = 0; i < 5; i++) {
            MenuItem item = ((MainActivity) requireActivity()).getNavigationDrawer().getMenu().getItem(i);
            if(item.getItemId() == R.id.navigation_settings){
                item.setChecked(true);
            }
            else item.setChecked(false);
        }
    }
}
