package com.al415885.farmascouter_v2.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.al415885.farmascouter_v2.MainActivity;
import com.al415885.farmascouter_v2.R;
import com.al415885.farmascouter_v2.adapters.CustomRecyclerAdapterHomeFrag;
import com.al415885.farmascouter_v2.adapters.CustomRecyclerAdapterHomeFragRCambios;
import com.al415885.farmascouter_v2.results.ResultsMedPSuministro;
import com.al415885.farmascouter_v2.results.ResultsRCambios;
import com.al415885.farmascouter_v2.threads.CIMAThread;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentRCambios extends Fragment {

    // UI elements
    private RecyclerView rvHomeFragmentRC;
    private List<ResultsRCambios> rvList;
    private CustomRecyclerAdapterHomeFragRCambios adapter;

    // Threads
    private CIMAThread cimaThread;
    private Thread UIThread;

    /* Constructor for creating again the view */
    public HomeFragmentRCambios(){}

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
        View view = inflater.inflate(R.layout.fragment_home_rcambios, container, false);

        // Find UI elements
        this.rvHomeFragmentRC = view.findViewById(R.id.rvHomeFragmentRC);

        // Initialise variables
        this.rvList = new ArrayList<>();
        this.adapter = new CustomRecyclerAdapterHomeFragRCambios(this.rvList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        this.rvHomeFragmentRC.setAdapter(this.adapter);
        this.rvHomeFragmentRC.setLayoutManager(manager);

        this.UIThread = new Thread(new Runnable() {
            @Override
            public void run() {
                rvList.addAll(cimaThread.getRvListRCambios());
                if(isVisible()) {
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });

        this.cimaThread = new CIMAThread(1, getContext(), this.UIThread);
        ((MainActivity) requireActivity()).setCimaThreadRC(this.cimaThread);
        this.cimaThread.start();

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
}
