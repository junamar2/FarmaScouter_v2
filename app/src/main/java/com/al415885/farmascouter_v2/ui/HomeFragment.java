package com.al415885.farmascouter_v2.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.al415885.farmascouter_v2.results.ResultsMedPSuministro;
import com.al415885.farmascouter_v2.threads.CIMAThread;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    // UI elements
    private RecyclerView rvHomeFragment;
    private List<ResultsMedPSuministro> rvList;
    private CustomRecyclerAdapterHomeFrag adapter;

    // Threads
    private CIMAThread cimaThread;
    private Thread UIThread;

    /* Constructor for creating again the view */
    public HomeFragment(){}

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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Find UI elements
        this.rvHomeFragment = view.findViewById(R.id.rvHomeFragment);

        // Initialise variables
        this.rvList = new ArrayList<>();
        this.adapter = new CustomRecyclerAdapterHomeFrag(this.rvList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        this.rvHomeFragment.setAdapter(this.adapter);
        this.rvHomeFragment.setLayoutManager(manager);
        setNavigationDrawerCheckedItem();
        this.UIThread = new Thread(new Runnable() {
            @Override
            public void run() {
                rvList.addAll(cimaThread.getRvListPSum());
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

        this.cimaThread = new CIMAThread(0, getContext(), this.UIThread);
        ((MainActivity) requireActivity()).setCimaThread(this.cimaThread);
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

    private void setNavigationDrawerCheckedItem() {
        for (int i = 0; i < 3; i++) {
            MenuItem item = ((MainActivity) requireActivity()).getNavigationDrawer().getMenu().getItem(i);
            if(item.getItemId() == R.id.navigation_home){
                item.setChecked(true);
            }
            else item.setChecked(false);
        }
    }
}
