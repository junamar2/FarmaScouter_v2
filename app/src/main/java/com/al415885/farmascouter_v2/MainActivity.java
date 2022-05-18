package com.al415885.farmascouter_v2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.al415885.farmascouter_v2.threads.CIMAThread;
import com.al415885.farmascouter_v2.ui.DrugsFragment;
import com.al415885.farmascouter_v2.ui.FavouritesFragment;
import com.al415885.farmascouter_v2.ui.HomeFragment;
import com.al415885.farmascouter_v2.ui.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final MenuItem lastItem = null;
    private Toolbar tbNavigationDrawer;
    private DrawerLayout dlNavigationDrawer;
    private NavigationView nvNavigationDrawer;
    private CIMAThread cimaThread = null;
    private Fragment saveFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.tbNavigationDrawer = findViewById(R.id.tb_navigation_drawer);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fcv_navigation_drawer, HomeFragment.class, null)
                .commit();
        setSupportActionBar(this.tbNavigationDrawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        this.dlNavigationDrawer = findViewById(R.id.nd_layout);
        this.nvNavigationDrawer = findViewById(R.id.nv_navigation_drawer);
        this.nvNavigationDrawer.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.dlNavigationDrawer.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (this.dlNavigationDrawer.isDrawerOpen(GravityCompat.START)) {
            this.dlNavigationDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        this.cimaThread.interrupt();
        this.dlNavigationDrawer.closeDrawer(GravityCompat.START);
        Class<? extends Fragment> fragmentClass = null;
        switch (item.getItemId()){
            case R.id.navigation_home:
                fragmentClass = HomeFragment.class;
                getSupportActionBar().setTitle("Home");
                break;
            case R.id.navigation_drugs:
                fragmentClass = DrugsFragment.class;
                getSupportActionBar().setTitle("Search Drugs");
                break;
            case R.id.navigation_favourites:
                fragmentClass = FavouritesFragment.class;
                getSupportActionBar().setTitle("Favourites Drugs");
                break;
            case R.id.navigation_settings:
                fragmentClass = SettingsFragment.class;
                getSupportActionBar().setTitle("Settings");
                break;
            default:
                break;
        }
        if (fragmentClass != null && !item.isChecked()) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fcv_navigation_drawer, fragmentClass, null)
                    .commit();
            return true;
        }
        return false;
    }

    public void openNavigationDrawer() {
        this.dlNavigationDrawer.openDrawer(GravityCompat.START);
    }

    public NavigationView getNavigationDrawer() {
        return this.nvNavigationDrawer;
    }

    public void setCimaThread(CIMAThread cimaThread){
        this.cimaThread = cimaThread;
    }

    /** Method that performs any final cleanup before an activity is destroyed */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}