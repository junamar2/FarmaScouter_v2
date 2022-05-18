package com.al415885.farmascouter_v2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.al415885.farmascouter_v2.results.ResultsMed;

public class DrugActivity extends AppCompatActivity {

    private Toolbar tbNavigationDrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.tbNavigationDrawer = findViewById(R.id.tb_navigation_drawer);
        setSupportActionBar(this.tbNavigationDrawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getActionBar().setTitle("Drug");
        ResultsMed a = (ResultsMed) getIntent().getSerializableExtra("Drug");
    }

    /** Method that performs any final cleanup before an activity is destroyed */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
