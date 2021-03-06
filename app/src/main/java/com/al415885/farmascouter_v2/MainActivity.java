package com.al415885.farmascouter_v2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import com.al415885.farmascouter_v2.threads.CIMAThread;
import com.al415885.farmascouter_v2.ui.DrugsFragment;
import com.al415885.farmascouter_v2.ui.FavouritesFragment;
import com.al415885.farmascouter_v2.ui.InteractionsFragment;
import com.al415885.farmascouter_v2.ui.SettingsFragment;
import com.al415885.farmascouter_v2.ui.VPHomeFragment;
import com.google.android.material.navigation.NavigationView;

/**
 * Class for the Main activity
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AdapterView.OnItemSelectedListener {

    // UI elements
    private Toolbar tbNavigationDrawer;
    private DrawerLayout dlNavigationDrawer;
    private NavigationView nvNavigationDrawer;


    // Threads
    private CIMAThread cimaThreadPSUM, cimaThreadRC, cimaThreadDR;

    // Class-specific variables
    private SharedPreferences prefs;

    /** Method that creates the main activity of the APP
     *
     * @param savedInstanceState: Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Put the layout
        setContentView(R.layout.activity_main);

        // Initialise variables
        initialiseVariables();

        // Show initial dialog (if needed)
        if(this.prefs.getString("keyName", "").equals(""))
            showInitialDialog();

        // Find the UI elements
        findUIElements();

        // Load the corresponding Fragment into the view
        loadFragment();

        // Set up the Action Bar
        setUpActionBar();
    }

    /**
     * Method that opens the Navigation Drawer if the item of the Action Bar is pressed
     * @param item: MenuItem
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Hide the keyboard
            hideSoftKeyboard();
            // Open the Drawer
            this.dlNavigationDrawer.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method that closes de Navigation Drawer if back is pressed
     */
    @Override
    public void onBackPressed() {
        if (this.dlNavigationDrawer.isDrawerOpen(GravityCompat.START)) {
            this.dlNavigationDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Method that manages the layouts to be loaded depending of the selected item
     * @param item: MenuItem
     * @return boolean
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Hide the keyboard
        hideSoftKeyboard();
        // Stops any active Thread
        stopThreads();
        // If an item is selected, close drawer
        this.dlNavigationDrawer.closeDrawer(GravityCompat.START);
        // Depending on the item selected, lead the class
        Class<? extends Fragment> fragmentClass = null;
        switch (item.getItemId()){
            case R.id.navigation_home:
                fragmentClass = VPHomeFragment.class;
                getSupportActionBar().setTitle(getResources().getString(R.string.home));
                break;
            case R.id.navigation_drugs:
                fragmentClass = DrugsFragment.class;
                getSupportActionBar().setTitle(getResources().getString(R.string.searchDrugs));
                break;
            case R.id.navigation_interactions:
                fragmentClass = InteractionsFragment.class;
                getSupportActionBar().setTitle(getResources().getString(R.string.interactionCh));
                break;
            case R.id.navigation_favourites:
                fragmentClass = FavouritesFragment.class;
                getSupportActionBar().setTitle(getResources().getString(R.string.favourites));
                break;
            case R.id.navigation_settings:
                fragmentClass = SettingsFragment.class;
                getSupportActionBar().setTitle(getResources().getString(R.string.settings));
                break;
            default:
                break;
        }
        // If the item is checked, stay. If not, load
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

    /**
     * Method that returns the Navigation Drawer
     * @return NavigationView
     */
    public NavigationView getNavigationDrawer() {
        return this.nvNavigationDrawer;
    }

    /**
     * Method that returns the preferences of the APP
     */
    public SharedPreferences getPrefs(){
        return this.prefs;
    }

    /**
     * Method that sets the CIMA Thread PSUM into the Main Activity
     * @param cimaThread: CIMAThread
     */
    public void setCimaThreadPSUM(CIMAThread cimaThread){
        this.cimaThreadPSUM = cimaThread;
    }

    /**
     * Method that sets the CIMA Thread RC into the Main Activity
     * @param cimaThread: CIMAThread
     */
    public void setCimaThreadRC(CIMAThread cimaThread){
        this.cimaThreadRC = cimaThread;
    }

    /**
     * Method that sets the CIMA Thread DR into the Main Activity
     * @param cimaThread: CIMAThread
     */
    public void setCimaThreadDR(CIMAThread cimaThread){
        this.cimaThreadDR = cimaThread;
    }

    /**
     * Method that hides the Keyboard
     */
    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText()){
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Method that interrupts any active Thread
     */
    private void stopThreads(){
        if(this.cimaThreadRC!= null && this.cimaThreadRC.isAlive())
            this.cimaThreadRC.interrupt();
        else if(this.cimaThreadPSUM!= null && this.cimaThreadPSUM.isAlive())
            this.cimaThreadPSUM.interrupt();
        else if(this.cimaThreadDR!= null && this.cimaThreadDR.isAlive())
            this.cimaThreadDR.interrupt();
    }

    /**
     * Method that shows a initial dialog in order to create a profile
     */
    private void showInitialDialog(){
        ViewDialog dialog = new ViewDialog();
        dialog.showDialog(this);
    }

    /**
     * Method that find the elements of the UI
     */
    private void findUIElements(){
        this.tbNavigationDrawer = findViewById(R.id.tb_navigation_drawer);
        this.dlNavigationDrawer = findViewById(R.id.nd_layout);
        this.nvNavigationDrawer = findViewById(R.id.nv_navigation_drawer);
    }

    /**
     * Method that initialise the class-specific variables
     */
    private void initialiseVariables(){
        this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
        this.cimaThreadDR = null;
        this.cimaThreadRC = null;
        this.cimaThreadPSUM = null;
    }

    /**
     * Method that loads the main Fragment
     */
    private void loadFragment(){
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fcv_navigation_drawer, VPHomeFragment.class, null)
                .commit();
    }

    /**
     * Method that sets up the Action Bar
     */
    private void setUpActionBar(){
        setSupportActionBar(this.tbNavigationDrawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        // Attach a listener to the Navigation Drawer
        this.nvNavigationDrawer.setNavigationItemSelectedListener(this);
    }

    /** Method that performs any final cleanup before an activity is destroyed */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}