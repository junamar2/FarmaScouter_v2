package com.al415885.farmascouter_v2.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.al415885.farmascouter_v2.DrugActivity;
import com.al415885.farmascouter_v2.R;
import com.al415885.farmascouter_v2.adapters.CustomRecyclerAdapterDrugUMLSFrag;
import com.al415885.farmascouter_v2.adapters.CustomRecyclerAdapterHomeFrag;
import com.al415885.farmascouter_v2.adapters.CustomRecyclerAdapterMedFrag;
import com.al415885.farmascouter_v2.mappings.SNOMEDCTAtomRelations;
import com.al415885.farmascouter_v2.results.ResultsMed;
import com.al415885.farmascouter_v2.results.ResultsMedPSuministro;
import com.al415885.farmascouter_v2.threads.UMLSThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DrugUMLSFragment extends Fragment {

    // UI elements
    private TextView tvDefinition, tvRelations, tvDefinitionTitle, tvNoInfo;
    private RecyclerView rvRelations;
    private List<SNOMEDCTAtomRelations> rvList;
    private CustomRecyclerAdapterDrugUMLSFrag adapter;

    // Threads
    private UMLSThread umlsThread;

    // Information about the drug
    private String definition;
    private List<SNOMEDCTAtomRelations> relations;

    /* Constructor for creating again the view */
    public DrugUMLSFragment(){ }

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
        View view = inflater.inflate(R.layout.fragment_drug_umls, container, false);
        // Find UI elements
        findUIElements(view);
        // Initialise variables
        initialiseVariables();
        // Set Up Recycler View
        setUpRecyclerView();
        // Return the view
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvNoInfo.setVisibility(View.VISIBLE);
            }
        });
        Thread UIThread = new Thread(new Runnable() {
            @Override
            public void run() {
                definition = umlsThread.getDefinition();
                rvList.addAll(umlsThread.getRelations());
                if(isVisible()) {
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvNoInfo.setVisibility(View.INVISIBLE);
                            tvRelations.setVisibility(View.VISIBLE);
                            tvDefinitionTitle.setVisibility(View.VISIBLE);
                            tvDefinition.setText(definition);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
        String search = (String) ((DrugActivity) requireActivity()).getIntent().getSerializableExtra("Search");
        this.umlsThread = new UMLSThread(0, getContext(), UIThread, search);
        this.umlsThread.start();
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
        this.tvDefinition = view.findViewById(R.id.tvDefinitionValue);
        this.rvRelations = view.findViewById(R.id.rvRelations);
        this.tvDefinitionTitle = view.findViewById(R.id.tvDefinition);
        this.tvNoInfo = view.findViewById(R.id.tvNothingToShow);
        this.tvRelations = view.findViewById(R.id.tvRelations);
    }

    /**
     * Method that initialise the class-specific variables
     */
    private void initialiseVariables(){
        this.relations = new ArrayList<>();
        this.rvList = new ArrayList<>();
        this.adapter = new CustomRecyclerAdapterDrugUMLSFrag(this.rvList);
    }

    /**
     * Method that sets up tje Recycler View
     */
    private void setUpRecyclerView(){
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        this.rvRelations.setAdapter(this.adapter);
        this.rvRelations.setLayoutManager(manager);
    }
}
