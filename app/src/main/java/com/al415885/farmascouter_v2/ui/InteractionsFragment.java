package com.al415885.farmascouter_v2.ui;

import android.os.Bundle;
import android.provider.Contacts;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.al415885.farmascouter_v2.MainActivity;
import com.al415885.farmascouter_v2.R;
import com.al415885.farmascouter_v2.threads.Bio2RdfThread;
import com.al415885.farmascouter_v2.threads.UMLSThread;

import java.util.List;

public class InteractionsFragment extends Fragment {

    // UI elements
    private ImageButton imbSearch;
    private EditText etSearch;
    private ListView lvInteractions;
    private ProgressBar pbInteractions;
    private TextView tvPBInteractions, tvNoInteractions, tvShowing;

    //Threads
    private Thread UIThread;
    private Bio2RdfThread bio2RdfThread;
    private UMLSThread umlsThread;

    // Class-specific variables
    private String search;
    private ArrayAdapter<String> adapter;
    private List<String> listIds;

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

        setNavigationDrawerCheckedItem();
        // Listeners
        this.imbSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the keyboard
                if(view != null){
                    ((MainActivity) requireActivity()).hideSoftKeyboard();
                }
                if(adapter != null && !adapter.isEmpty())
                    adapter.clear();
                search = etSearch.getText().toString();
                UIThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        UIThreadAction();
                    }
                });
                bio2RdfThread = new Bio2RdfThread(getContext(), UIThread);
                umlsThread = new UMLSThread(1, getContext(), null, search, bio2RdfThread);
                umlsThread.start();
                tvNoInteractions.setVisibility(View.INVISIBLE);
                pbInteractions.setVisibility(View.VISIBLE);
                tvPBInteractions.setVisibility(View.VISIBLE);
                pbInteractions.animate();
            }
        });

        this.lvInteractions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String drugbankui = listIds.get(position);
                UIThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        UIThreadAction();
                    }
                });
                bio2RdfThread = new Bio2RdfThread(getContext(), UIThread);
                bio2RdfThread.setDRUGBANKui(drugbankui);
                bio2RdfThread.start();
                pbInteractions.setVisibility(View.VISIBLE);
                tvPBInteractions.setVisibility(View.VISIBLE);
            }
        });

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
        this.lvInteractions = view.findViewById(R.id.lvInteractions);
        this.pbInteractions = view.findViewById(R.id.pbInteractions);
        this.tvPBInteractions = view.findViewById(R.id.tvPBInteractions);
        this.tvNoInteractions = view.findViewById(R.id.tvNoInteractions);
        this.tvShowing = view.findViewById(R.id.tvShowing);
    }

    private void setNavigationDrawerCheckedItem() {
        for (int i = 0; i < 5; i++) {
            MenuItem item = ((MainActivity) requireActivity()).getNavigationDrawer().getMenu().getItem(i);
            if(item.getItemId() == R.id.navigation_interactions){
                item.setChecked(true);
            }
            else item.setChecked(false);
        }
    }

    private void UIThreadAction(){
        List<String> listInteractions = bio2RdfThread.getInteractions();
        listIds = bio2RdfThread.getIds();
        if(isVisible()) {
            if(listInteractions.isEmpty()){
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pbInteractions.setVisibility(View.INVISIBLE);
                        tvPBInteractions.setVisibility(View.INVISIBLE);
                        tvNoInteractions.setVisibility(View.VISIBLE);
                    }
                });
            }
            else {
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pbInteractions.setVisibility(View.INVISIBLE);
                        tvPBInteractions.setVisibility(View.INVISIBLE);
                        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listInteractions);
                        lvInteractions.setAdapter(adapter);
                        tvShowing.setText(getResources().getString(R.string.showing) + " " + bio2RdfThread.getTitle());
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }
    }
}
