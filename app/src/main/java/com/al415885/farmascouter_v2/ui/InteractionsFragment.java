package com.al415885.farmascouter_v2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.al415885.farmascouter_v2.DrugActivity;
import com.al415885.farmascouter_v2.MainActivity;
import com.al415885.farmascouter_v2.R;
import com.al415885.farmascouter_v2.adapters.CRAFHomePS;
import com.al415885.farmascouter_v2.adapters.CRAFInteractions;
import com.al415885.farmascouter_v2.adapters.OnItemClickListener;
import com.al415885.farmascouter_v2.adapters.OnLongItemClickListener;
import com.al415885.farmascouter_v2.models.cima.secondlevel.MedSecond;
import com.al415885.farmascouter_v2.threads.Bio2RdfThread;
import com.al415885.farmascouter_v2.threads.UMLSThread;

import java.util.ArrayList;
import java.util.List;

public class InteractionsFragment extends Fragment {

    // UI elements
    private ImageButton imbSearch;
    private EditText etSearch;
    private RecyclerView rvInteractions;
    private List<String> rvListDrugs;
    private List<String> rvListDesc;
    private ProgressBar pbInteractions;
    private TextView tvPBInteractions, tvNoInteractions, tvShowing, tvDrugDesc, tvDescTitle;
    private CheckBox cbAutomatic, cbCustom;

    //Threads
    private Thread UIThread;
    private Bio2RdfThread bio2RdfThread;
    private UMLSThread umlsThread;

    // Class-specific variables
    private String search;
    private CRAFInteractions adapter;
    private List<String> listIds;
    private int threadType = 0;
    private int count;

    /* Constructor for creating again the view */
    public InteractionsFragment(){}

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
        View view = inflater.inflate(R.layout.fragment_interactions, container, false);

        // Find UI elements
        findUIElements(view);

        // Initialise variables
        initialiseVariables();

        // Set Up the Recycler View
        setUpRecyclerView();

        setNavigationDrawerCheckedItem();
        // Listeners
        setUpListeners(view);

        return view;
    }

    /** Method called to do initial creation of a fragment
     * @param savedInstanceState Bundle: Saved state for re-constructing the fragment */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     * Method that find the elements of the UI
     */
    private void findUIElements(View view){
        this.imbSearch = view.findViewById(R.id.imbSearch);
        this.etSearch = view.findViewById(R.id.etSearch);
        this.rvInteractions = view.findViewById(R.id.rvInteractions);
        this.pbInteractions = view.findViewById(R.id.pbInteractions);
        this.tvPBInteractions = view.findViewById(R.id.tvPBInteractions);
        this.tvNoInteractions = view.findViewById(R.id.tvNoInteractions);
        this.tvShowing = view.findViewById(R.id.tvShowing);
        this.tvDrugDesc = view.findViewById(R.id.tvDrugDesc);
        this.tvDescTitle = view.findViewById(R.id.tvDescTitle);
        this.cbAutomatic = view.findViewById(R.id.cbAutomatic);
        this.cbCustom = view.findViewById(R.id.cbCustom);
    }

    /**
     * Method that initialise the class-specific variables
     */
    private void initialiseVariables(){
        this.etSearch.setHint("Search drugs by name");
        this.listIds = new ArrayList<>();
        this.rvListDrugs = new ArrayList<>();
        this.rvListDesc = new ArrayList<>();
        if(this.cbAutomatic.isChecked()) {
            this.cbCustom.setChecked(false);
            this.threadType = 0;
        }
        else {
            this.cbAutomatic.setChecked(false);
            this.threadType = 1;
        }
        this.count = -1;
    }

    /**
     * Method that checks the navigation drawer item
     */
    private void setNavigationDrawerCheckedItem() {
        for (int i = 0; i < 5; i++) {
            MenuItem item = ((MainActivity) requireActivity()).getNavigationDrawer().getMenu().getItem(i);
            if(item.getItemId() == R.id.navigation_interactions){
                item.setChecked(true);
            }
            else item.setChecked(false);
        }
    }

    /**
     * Method that specifies the action done by the thread
     */
    private void UIThreadAction(){
        if(this.threadType == 0) {
            rvListDrugs.addAll(bio2RdfThread.getInteractionsDrug());
            rvListDesc.addAll(bio2RdfThread.getInteractionsDesc());
            listIds.addAll(bio2RdfThread.getIds());
            if (isVisible()) {
                if (rvListDrugs.isEmpty()) {
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pbInteractions.setVisibility(View.INVISIBLE);
                            tvPBInteractions.setVisibility(View.INVISIBLE);
                            tvNoInteractions.setVisibility(View.VISIBLE);
                        }
                    });
                } else {
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pbInteractions.setVisibility(View.INVISIBLE);
                            tvPBInteractions.setVisibility(View.INVISIBLE);
                            tvShowing.setVisibility(View.VISIBLE);
                            tvDescTitle.setVisibility(View.VISIBLE);
                            tvDrugDesc.setVisibility(View.VISIBLE);
                            tvShowing.setText(getResources().getString(R.string.showing) + " "
                                    + bio2RdfThread.getTitle());
                            tvDescTitle.setText(getResources().getString(R.string.descriptionDrug) + " "
                                    + bio2RdfThread.getTitle());
                            tvDrugDesc.setText(bio2RdfThread.getDescription());
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        }
        else{
            if(!bio2RdfThread.isCustomRes()){
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pbInteractions.setVisibility(View.INVISIBLE);
                        tvPBInteractions.setVisibility(View.INVISIBLE);
                        tvNoInteractions.setVisibility(View.VISIBLE);
                    }
                });
            }
            else{
                rvListDrugs.addAll(bio2RdfThread.getInteractionsDrug());
                rvListDesc.addAll(bio2RdfThread.getInteractionsDesc());
                listIds.addAll(bio2RdfThread.getIds());
                if (isVisible()) {
                    if (rvListDrugs.isEmpty()) {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pbInteractions.setVisibility(View.INVISIBLE);
                                tvPBInteractions.setVisibility(View.INVISIBLE);
                                tvNoInteractions.setVisibility(View.VISIBLE);
                            }
                        });
                    } else {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pbInteractions.setVisibility(View.INVISIBLE);
                                tvPBInteractions.setVisibility(View.INVISIBLE);
                                tvShowing.setVisibility(View.VISIBLE);
                                tvShowing.setText("Showing interactions");
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }

        }
    }

    /**
     * Method that sets uo the Recycler View
     */
    private void setUpRecyclerView(){
        this.adapter = new CRAFInteractions(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                String drugbankui = listIds.get(position);
                UIThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        UIThreadAction();
                    }
                });
                etSearch.setText("");
                cbAutomatic.setChecked(true);
                cbCustom.setChecked(false);
                threadType = 0;
                bio2RdfThread = new Bio2RdfThread(getContext(), UIThread, threadType);
                bio2RdfThread.setDRUGBANKui(drugbankui);
                bio2RdfThread.start();
                pbInteractions.setVisibility(View.VISIBLE);
                tvPBInteractions.setVisibility(View.VISIBLE);
                if(rvListDesc != null && !rvListDesc.isEmpty()) {
                    rvListDrugs.clear();
                    rvListDesc.clear();
                    listIds.clear();
                }
            }
        }, this.rvListDrugs, this.rvListDesc);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        this.rvInteractions.setAdapter(this.adapter);
        this.rvInteractions.setLayoutManager(manager);
    }

    /**
     * Method that sets up the needed listeners
     * @param view: the View
     */
    private void setUpListeners(View view){
        this.imbSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the keyboard
                if(view != null){
                    ((MainActivity) requireActivity()).hideSoftKeyboard();
                }
                if(rvListDesc != null && !rvListDesc.isEmpty()) {
                    rvListDrugs.clear();
                    rvListDesc.clear();
                    listIds.clear();
                    adapter.notifyDataSetChanged();
                }
                tvShowing.setVisibility(View.INVISIBLE);
                tvDescTitle.setVisibility(View.INVISIBLE);
                tvDrugDesc.setVisibility(View.INVISIBLE);
                String [] splits;
                if(cbAutomatic.isChecked()) {
                    cbCustom.setChecked(false);
                    threadType = 0;
                    search = etSearch.getText().toString();
                }
                else {
                    cbAutomatic.setChecked(false);
                    threadType = 1;
                    splits = etSearch.getText().toString().split(",");
                    search = splits[0];
                }
                UIThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        UIThreadAction();
                    }
                });
                bio2RdfThread = new Bio2RdfThread(getContext(), UIThread, threadType);
                if(threadType == 1) {
                    count++;
                    bio2RdfThread.addCount(count);
                }
                umlsThread = new UMLSThread(1, getContext(), null, search, bio2RdfThread);
                umlsThread.start();
                tvNoInteractions.setVisibility(View.INVISIBLE);
                pbInteractions.setVisibility(View.VISIBLE);
                tvPBInteractions.setVisibility(View.VISIBLE);
                pbInteractions.animate();
                etSearch.setText("");
            }
        });

        this.cbAutomatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setHint("Search drugs by name");
                cbCustom.setChecked(false);
            }
        });

        this.cbCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setHint("Name two drugs coma separated");
                cbAutomatic.setChecked(false);
            }
        });
    }
}
