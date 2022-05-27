package com.al415885.farmascouter_v2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.al415885.farmascouter_v2.ui.ViewPagerFragment;

public class DrugActivity extends AppCompatActivity {

    // UI elements
    private Toolbar tbNavigationDrawer;

    /** Method that creates the main activity of the APP
     *
     * @param savedInstanceState: Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Put the layout
        setContentView(R.layout.activity_drug);

        // Find the UI elements
        findUIElements();

        // Load the corresponding Fragment into the view
        loadFragment();

        // Set up the Action Bar
        setUpActionBar();
    }

    /**
     * Method that find the elements of the UI
     */
    private void findUIElements(){
        this.tbNavigationDrawer = findViewById(R.id.tb_activity_drug);
    }

    /**
     * Method that loads the main Fragment
     */
    private void loadFragment(){
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fcv_activity_drug, ViewPagerFragment.class, null)
                .commit();
    }

    /**
     * Method that sets up the Action Bar
     */
    private void setUpActionBar(){
        setSupportActionBar(this.tbNavigationDrawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Drug");
    }

    /**
     * Method that performs the action when back is pressed
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /** Method that performs any final cleanup before an activity is destroyed */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
