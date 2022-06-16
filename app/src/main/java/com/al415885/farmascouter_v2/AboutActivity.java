package com.al415885.farmascouter_v2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * Class for the About Activity
 */
public class AboutActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_about);

        // Find UI elements
        findUIElements();

        // Set up the Action Bar
        setUpActionBar();
    }

    /**
     * Method that find the elements of the UI
     */
    private void findUIElements(){
        this.tbNavigationDrawer = findViewById(R.id.tb_activity_about);
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
