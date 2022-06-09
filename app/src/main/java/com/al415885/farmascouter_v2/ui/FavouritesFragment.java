package com.al415885.farmascouter_v2.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FavouritesFragment extends Fragment {

    // UI elements
    private RecyclerView rvFavouritesFragment;
    private List<MedSecond> rvList;
    private CRAFDrugs adapter;

    /* Constructor for creating again the view */
    public FavouritesFragment(){}

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
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        // Find UI elements
        this.rvFavouritesFragment = view.findViewById(R.id.rvFavouritesFragment);
        // Initialise variables
        setUpRecyclerView();
        setNavigationDrawerCheckedItem();
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
        for (int i = 0; i < 4; i++) {
            MenuItem item = ((MainActivity) requireActivity()).getNavigationDrawer().getMenu().getItem(i);
            if(item.getItemId() == R.id.navigation_favourites){
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
                intent.putExtra("Search", med.getSearch());
                startActivity(intent);
            }
        }, new OnLongItemClickListener() {
            @Override
            public void onLongClick(int position) {
                androidx.appcompat.app.AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
                adb.setView(R.layout.favourites_dialog);
                adb.setTitle(((MainActivity) requireActivity()).getResources()
                        .getString(R.string.titleDialogFavourites));
                adb.setMessage(((MainActivity) requireActivity()).getResources()
                        .getString(R.string.dialogFavouritesDescription));
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
                                dao.delete(rvList.get(position));
                                rvList.remove(position);
                                ((MainActivity) requireActivity()).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.notifyDataSetChanged();
                                    }
                                });
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
        }, this.rvList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        this.rvFavouritesFragment.setAdapter(this.adapter);
        this.rvFavouritesFragment.setLayoutManager(manager);
        new Thread(new Runnable() {
            @Override
            public void run() {
                RuntimeExceptionDao<MedSecond, Integer> dao =
                        OrmLiteHelper.getInstance(getContext())
                                .getRuntimeDao(MedSecond.class);
                Iterator<MedSecond> iterator = dao.iterator();
                while (iterator.hasNext()){
                    MedSecond drug = iterator.next();
                    rvList.add(drug);
                }
                dao.closeLastIterator();
                if(isVisible()) {
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        }).start();
    }
}
