package com.al415885.farmascouter_v2.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.al415885.farmascouter_v2.DrugActivity;
import com.al415885.farmascouter_v2.MainActivity;
import com.al415885.farmascouter_v2.R;
import com.al415885.farmascouter_v2.results.ResultsMed;
import com.al415885.farmascouter_v2.utils.Documento;
import com.al415885.farmascouter_v2.utils.Foto;
import com.al415885.farmascouter_v2.utils.ViaAdministracion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DrugFragment extends Fragment {

    // Class-specific variables
    private ResultsMed drug;
    // UI elements
    private ImageView imgPack, imgDrug;
    private TextView tvDrugName, tvLab, tvNRegister, tvVTM, tvPrescription, tvDosage, tvDosageForm,
                        tvSimpleDosage, tvWays, tvDocument, tvState, tvNonReplaceable;
    private CheckBox cbCommercialised, cbPrescription, cbGeneric, cbDriving, cbTriangle, cbOrphan,
                        cbBiosimilar, cbPSum, cbNotes, cbEMA, cbMaterialsInf;
    private ListView lvWays;

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
        findUIElements(view);

        // Get the clicked drug
        getClickedDrug();

        // Update UI
        updateUI();

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
        this.imgPack = view.findViewById(R.id.imgPack);
        this.imgDrug = view.findViewById(R.id.imgDrug);
        this.tvDrugName = view.findViewById(R.id.tvDrugNameValue);
        this.tvLab = view.findViewById(R.id.tvLabValue);
        this.tvNRegister = view.findViewById(R.id.tvNRegisterValue);
        this.tvVTM = view.findViewById(R.id.tvVTMValue);
        this.tvPrescription = view.findViewById(R.id.tvPrescriptionValue);
        this.tvDosage = view.findViewById(R.id.tvDosageValue);
        this.tvDosageForm = view.findViewById(R.id.tvDosageFormValue);
        this.tvSimpleDosage = view.findViewById(R.id.tvSimpleDosageValue);
        this.tvWays = view.findViewById(R.id.tvWaysValue);
        this.tvDocument = view.findViewById(R.id.tvDocumentValue);
        this.tvState = view.findViewById(R.id.tvStateValue);
        this.tvNonReplaceable = view.findViewById(R.id.tvNonReplaceableValue);
        this.cbCommercialised = view.findViewById(R.id.cbCommercialised);
        this.cbPrescription = view.findViewById(R.id.cbPrescription);
        this.cbGeneric = view.findViewById(R.id.cbGeneric);
        this.cbDriving = view.findViewById(R.id.cbDriving);
        this.cbTriangle = view.findViewById(R.id.cbTriangle);
        this.cbOrphan = view.findViewById(R.id.cbOrphan);
        this.cbBiosimilar = view.findViewById(R.id.cbBiosimilar);
        this.cbPSum = view.findViewById(R.id.cbPSum);
        this.cbNotes = view.findViewById(R.id.cbNotes);
        this.cbEMA = view.findViewById(R.id.cbEMA);
        this.cbMaterialsInf = view.findViewById(R.id.cbMaterialsInf);
    }

    /**
     * Method that gets the drug that was clicked
     */
    private void getClickedDrug(){
        this.drug = (ResultsMed) ((DrugActivity) getActivity()).getIntent().
                getSerializableExtra("Drug");
    }

    /**
     * Method that updates the UI
     */
    private void updateUI(){
        ((DrugActivity) requireActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Picasso.get().load(drug.getFotos().get(0).getUrl()).
                        fit().into(imgPack);
                Picasso.get().load(drug.getFotos().get(1).getUrl())
                        .fit().into(imgDrug);
                tvDrugName.setText(drug.getNombre());
                tvLab.setText(drug.getLabtitular());
                tvNRegister.setText(drug.getNregistro());
                tvVTM.setText(drug.getVTM().getNombre());
                tvPrescription.setText(drug.getCpresc());
                tvDosage.setText(drug.getDosis());
                tvDosageForm.setText(drug.getFormaFarmaceutica().getNombre());
                tvSimpleDosage.setText(drug.getFormaFarmaceuticaSimplificada().getNombre());
                String textWays = "";
                if(drug.getViasAdministracion() != null) {
                    for (int i = 0; i < drug.getViasAdministracion().size(); i++)
                        textWays = drug.getViasAdministracion().get(i).getNombre() + " ";
                }
                else textWays = "N/A";
                tvWays.setText(textWays);
                tvDocument.setText(drug.getDocs().get(0).getUrl());
                tvState.setText(String.valueOf(drug.getEstado().getAut()));
                String textNonReplaceable = "";
                if(drug.getNoSustituible() != null) {
                    for (int i = 0; i < drug.getNoSustituible().size(); i++)
                        textNonReplaceable = drug.getNoSustituible().get(i).getNombre() + " ";
                }
                else textNonReplaceable = "N/A";
                tvNonReplaceable.setText(textNonReplaceable);
                tvDocument.setText(drug.getDocs().get(0).getUrl());
                cbCommercialised.setChecked(drug.isComerc());
                cbPrescription.setChecked(drug.isReceta());
                cbGeneric.setChecked(drug.isGenerico());
                cbDriving.setChecked(drug.isConduc());
                cbTriangle.setChecked(drug.isTriangulo());
                cbOrphan.setChecked(drug.isHuerfano());
                cbBiosimilar.setChecked(drug.isBiosimilar());
                cbPSum.setChecked(drug.isPsum());
                cbNotes.setChecked(drug.isNotas());
                cbEMA.setChecked(drug.isEma());
                cbMaterialsInf.setChecked(drug.isMaterialesInf());
            }
        });
    }
}
