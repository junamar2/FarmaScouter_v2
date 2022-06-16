package com.al415885.farmascouter_v2.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.al415885.farmascouter_v2.DrugActivity;
import com.al415885.farmascouter_v2.MainActivity;
import com.al415885.farmascouter_v2.R;
import com.al415885.farmascouter_v2.adapters.CRAFDrugs;
import com.al415885.farmascouter_v2.adapters.OnItemClickListener;
import com.al415885.farmascouter_v2.adapters.OnLongItemClickListener;
import com.al415885.farmascouter_v2.db.OrmLiteHelper;
import com.al415885.farmascouter_v2.models.cima.secondlevel.MedSecond;
import com.al415885.farmascouter_v2.threads.CIMAThread;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for the Drug Fragment
 */
public class DrugsFragment extends Fragment {


    // UI elements
    private RecyclerView rvDrugsFragment;
    private List<MedSecond> rvList;
    private CRAFDrugs adapter;
    private ImageButton imbSearch;
    private EditText etSearch;
    private View view;
    private ProgressBar pbDrugs;
    private TextView tvPBDrugs, tvNoResults;

    // Threads
    private CIMAThread cimaThread;
    private Thread UIThread;

    // Class-specific variables
    private String search;

    /* Constructor for creating again the view */
    public DrugsFragment(){}

    /** Method called to have the fragment instantiate its user interface view
     * @param inflater LayoutInflater: Object used to inflate any views in the fragment
     * @param container ViewGroup: Parent view that the fragment's UI should be attached to
     * @param savedInstanceState Bundle: Saved state for re-constructing the fragment
     * @return View*/
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        /* Creates the view */
        this.view = inflater.inflate(R.layout.fragment_drugs, container, false);

        // Find UI elements
        findUIElements();

        // Set Up the Recycler View
        setUpRecyclerView();

        // Set up the listeners
        setUpListeners();

        setNavigationDrawerCheckedItem();

        return view;
    }

    private void setNavigationDrawerCheckedItem() {
        for (int i = 0; i < 5; i++) {
            MenuItem item = ((MainActivity) requireActivity()).getNavigationDrawer().getMenu().getItem(i);
            if(item.getItemId() == R.id.navigation_drugs){
                item.setChecked(true);
            }
            else item.setChecked(false);
        }
    }

    public void setUpRecyclerView(){
        this.rvList = new ArrayList<>();
        this.adapter = new CRAFDrugs(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                MedSecond med = (MedSecond) rvList.get(position);
                Intent intent = new Intent(getContext(), DrugActivity.class);
                intent.putExtra("Drug", med);
                intent.putExtra("Search", search);
                startActivity(intent);
            }
        }, new OnLongItemClickListener() {
            @Override
            public void onLongClick(int position) {
                MedSecond drug = (MedSecond) rvList.get(position);
                showAddFavouritesDialog(drug);
            }
        }, this.rvList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        this.rvDrugsFragment.setAdapter(this.adapter);
        this.rvDrugsFragment.setLayoutManager(manager);
    }

    /** Method that shows a dialog in order to add the drug to favourites */
    private void showAddFavouritesDialog(MedSecond drug){
        drug.setSearch(search);
        AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
        adb.setView(R.layout.favourites_dialog);
        adb.setTitle(((MainActivity) requireActivity()).getResources()
                .getString(R.string.titleDialogDrugs));
        adb.setMessage(((MainActivity) requireActivity()).getResources()
                .getString(R.string.dialogDrugsDescription));
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
                        dao.createOrUpdate(drug);
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
    }

    /**
     * Method that find the elements of the UI
     */
    private void findUIElements(){
        this.rvDrugsFragment = this.view.findViewById(R.id.rvDrugFragment);
        this.imbSearch = this.view.findViewById(R.id.imbSearch);
        this.etSearch = this.view.findViewById(R.id.etSearch);
        this.pbDrugs = this.view.findViewById(R.id.pbDrugs);
        this.tvPBDrugs = this.view.findViewById(R.id.tvPBDrugs);
        this.tvNoResults = this.view.findViewById(R.id.tvNoResults);
    }

    /**
     * Method that sets up the needed listeners
     */
    private void setUpListeners(){
        this.imbSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the keyboard
                if(view != null){
                    ((MainActivity) requireActivity()).hideSoftKeyboard();
                }
                search = etSearch.getText().toString();
                setUpRecyclerView();
                UIThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        rvList.addAll(cimaThread.getRvListMed());
                        if(isVisible()) {
                            if(rvList.isEmpty()){
                                requireActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tvNoResults.setVisibility(View.VISIBLE);
                                        pbDrugs.setVisibility(View.INVISIBLE);
                                        tvPBDrugs.setVisibility(View.INVISIBLE);
                                    }
                                });
                            }
                            else {
                                requireActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.notifyDataSetChanged();
                                        pbDrugs.setVisibility(View.INVISIBLE);
                                        tvPBDrugs.setVisibility(View.INVISIBLE);
                                    }
                                });
                            }
                        }
                    }
                });
                cimaThread = new CIMAThread(2, getContext(), UIThread);
                cimaThread.setSearchMedName(search.trim().toLowerCase());
                ((MainActivity) requireActivity()).setCimaThreadDR(cimaThread);
                cimaThread.start();
                pbDrugs.setVisibility(View.VISIBLE);
                tvPBDrugs.setVisibility(View.VISIBLE);
                pbDrugs.animate();
            }
        });
    }
}

