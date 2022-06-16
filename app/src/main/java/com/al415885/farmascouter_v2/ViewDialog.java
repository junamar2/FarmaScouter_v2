package com.al415885.farmascouter_v2;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

/**
 * Class for the initial View Dialog
 */
public class ViewDialog {

    // UI elements
    private EditText etName;
    private Spinner spinnerRole;
    private FrameLayout frmAccept, frmCancel;

    // Class-specific variables
    private Activity activity;
    private Dialog dialog;
    private SharedPreferences prefs;
    private int role;

    /* Class constructor */
    public ViewDialog(){}

    /**
     * Method for showing the dialog
     * @param activity: Activity
     */
    public void showDialog(Activity activity) {

        // Initialise variables
        initialiseVariables(activity);

        // Build the dialog
        buildDialog();

        // Find UI elements
        findUIElements();

        // Set up the listeners
        setUpListeners();

        // Show the dialog
        dialog.show();
    }

    /**
     * Method that builds the dialog
     */
    private void buildDialog(){
        this.dialog = new Dialog(this.activity);
        this.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.dialog.setCancelable(false);
        this.dialog.setContentView(R.layout.dialog_create_profile);
        this.dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    /**
     * Method that initialises the class-specific variables
     */
    private void initialiseVariables(Activity activity){
        this.activity = activity;
        this.prefs = ((MainActivity) this.activity).getPrefs();
    }

    /**
     * Method that find the elements of the UI
     */
    private void findUIElements(){
        this.etName = this.dialog.findViewById(R.id.etName);
        this.spinnerRole = this.dialog.findViewById(R.id.spinnerRole);
        this.frmAccept = this.dialog.findViewById(R.id.frmAccept);
        this.frmCancel = this.dialog.findViewById(R.id.frmCancel);
    }

    /**
     * Method that sets up the needed listeners
     */
    private void setUpListeners(){
        this.spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                role = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        this.frmCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        this.frmAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("keyName", etName.getText().toString());
                editor.putString("keyRole", ((MainActivity) activity).getResources()
                        .getStringArray(R.array.optionsRole)[role]);
                editor.commit();
                dialog.cancel();
            }
        });
    }
}
