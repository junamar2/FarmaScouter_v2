package com.al415885.farmascouter_v2.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.al415885.farmascouter_v2.AboutActivity;
import com.al415885.farmascouter_v2.DrugActivity;
import com.al415885.farmascouter_v2.MainActivity;
import com.al415885.farmascouter_v2.R;
import com.al415885.farmascouter_v2.db.OrmLiteHelper;
import com.al415885.farmascouter_v2.models.cima.secondlevel.MedSecond;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import org.openjena.atlas.iterator.Iter;

import java.util.Iterator;

public class SettingsFragment extends PreferenceFragmentCompat{

    // Class-specific variables
    private Preference about, database;

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
        database = getPreferenceManager().findPreference("keyDatabase");
        database.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                androidx.appcompat.app.AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
                adb.setView(R.layout.favourites_dialog);
                adb.setTitle(((MainActivity) requireActivity()).getResources()
                        .getString(R.string.titleDialogSettings));
                adb.setMessage(((MainActivity) requireActivity()).getResources()
                        .getString(R.string.dialogSettings));
                adb.setIcon(android.R.drawable.ic_dialog_alert);
                adb.setPositiveButton(((MainActivity) requireActivity()).getResources()
                        .getString(R.string.accept), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                RuntimeExceptionDao<MedSecond, Integer> dao =
                                        OrmLiteHelper.getInstance(getContext())
                                                .getRuntimeDao(MedSecond.class);
                                Iterator<MedSecond> iterator = dao.iterator();
                                while(iterator.hasNext())
                                    dao.delete(iterator.next());
                            }
                        }).start();
                    }
                });
                adb.setNegativeButton(((MainActivity) requireActivity()).getResources()
                        .getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                adb.show();
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
