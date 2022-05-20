package com.al415885.farmascouter_v2.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.al415885.farmascouter_v2.DrugActivity;
import com.al415885.farmascouter_v2.MainActivity;
import com.al415885.farmascouter_v2.R;
import com.al415885.farmascouter_v2.results.ResultsMed;
import com.al415885.farmascouter_v2.utils.Foto;
import com.squareup.picasso.Picasso;

public class DrugFragment extends Fragment {

    // UI elements
    private ImageView imgPack, imgDrug;

    /* Constructor for creating again the view */
    public DrugFragment(){}

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
        View view = inflater.inflate(R.layout.fragment_drug, container, false);
        // Find UI elements
        ResultsMed a = (ResultsMed) ((DrugActivity) getActivity()).getIntent().
                getSerializableExtra("Drug");
        this.imgPack = view.findViewById(R.id.imgPack);
        this.imgDrug = view.findViewById(R.id.imgDrug);
        Foto b = a.getFotos().get(0);
        String url = b.getUrl();
        ((DrugActivity) requireActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Picasso.get().load(a.getFotos().get(0).getUrl()).
                        fit().into(imgPack);
                Picasso.get().load(a.getFotos().get(1).getUrl())
                        .fit().into(imgDrug);
            }
        });
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
