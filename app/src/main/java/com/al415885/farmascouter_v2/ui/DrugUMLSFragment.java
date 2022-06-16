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

import com.al415885.farmascouter_v2.DrugActivity;
import com.al415885.farmascouter_v2.R;
import com.al415885.farmascouter_v2.threads.UMLSThread;

import java.util.ArrayList;
import java.util.List;

public class DrugUMLSFragment extends Fragment {

    // UI elements
    private TextView tvDefinition, tvIsa, tvDefinitionTitle, tvNoInfo, tvCause;
    private ListView lvCauses, lvIsa;

    // Threads
    private UMLSThread umlsThread;

    // Information about the drug
    private String definition;
    private List<String> listCause;
    private List<String> listIsa;

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

        // Set the TextView visible
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvNoInfo.setVisibility(View.VISIBLE);
            }
        });

        // Create the UI thread and start UMLS Thread
        createUIThread();

        // Return the view
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
        this.tvDefinitionTitle = view.findViewById(R.id.tvDefinition);
        this.tvNoInfo = view.findViewById(R.id.tvNothingToShow);
        this.tvIsa = view.findViewById(R.id.tvIsa);
        this.tvCause = view.findViewById(R.id.tvCause);
        this.lvCauses = view.findViewById(R.id.lvCauses);
        this.lvIsa = view.findViewById(R.id.lvIsa);
    }

    /**
     * Method that initialise the class-specific variables
     */
    private void initialiseVariables(){
        this.listIsa = new ArrayList<>();
        this.listCause = new ArrayList<>();
    }

    /**
     * Method that creates the UI Thread and starts the UMLS Thread
     */
    private void createUIThread(){
        Thread UIThread = new Thread(new Runnable() {
            @Override
            public void run() {
                definition = umlsThread.getDefinition();
                listCause = umlsThread.getListCause();
                listIsa = umlsThread.getListIsa();
                if(isVisible()) {
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayAdapter<String> adapterCause = new ArrayAdapter<>(getContext(),
                                    android.R.layout.simple_list_item_1, listCause);
                            lvCauses.setAdapter(adapterCause);
                            adapterCause.notifyDataSetChanged();
                            ArrayAdapter<String> adapterIsa = new ArrayAdapter<>(getContext(),
                                    android.R.layout.simple_list_item_1, listIsa);
                            lvIsa.setAdapter(adapterIsa);
                            adapterIsa.notifyDataSetChanged();
                            tvNoInfo.setVisibility(View.INVISIBLE);
                            tvIsa.setVisibility(View.VISIBLE);
                            tvCause.setVisibility(View.VISIBLE);
                            tvDefinitionTitle.setVisibility(View.VISIBLE);
                            tvDefinition.setText(definition);
                        }
                    });
                }
            }
        });
        String search = (String) ((DrugActivity) requireActivity()).getIntent()
                .getSerializableExtra("Search");
        this.umlsThread = new UMLSThread(0, getContext(), UIThread, search,
                null);
        this.umlsThread.start();
    }
}
