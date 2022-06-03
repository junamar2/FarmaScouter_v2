package com.al415885.farmascouter_v2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.al415885.farmascouter_v2.R;
import com.al415885.farmascouter_v2.mappings.SNOMEDCTAtomRelations;
import com.al415885.farmascouter_v2.results.ResultsMed;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * CustomRecyclerAdapter: Class for the adapter from the RecyclerView of FreezeValuesFragment.
 *
 * @author (junamar2@inf.upv.es)
 * @version (2020 - 2021)
 */
public class CustomRecyclerAdapterDrugUMLSFrag extends RecyclerView.Adapter<CustomRecyclerAdapterDrugUMLSFrag.ViewHolder> {

    /* The list of elements */
    private List<SNOMEDCTAtomRelations> list;
    private OnItemClickListener onItemClickListener;
    private OnLongItemClickListener onLongItemClickListener;

    /** Constructor for the class */
    public CustomRecyclerAdapterDrugUMLSFrag(List<SNOMEDCTAtomRelations> list){
        this.list = list;
    }

    /** Method called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to
     * represent an item
      * @param parent ViewGroup: The ViewGroup into which the new View will be added after it is
     *                bound to an adapter position
     * @param viewType int: The view type of the new View
     * @return CustomRecyclerAdapter.ViewHolder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_element_drug_umls_fragment, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    /** Method called by RecyclerView to display the data at the specified position
     * @param holder CustomRecyclerAdapter.ViewHolder: The ViewHolder which should be updated to
     *               represent the contents of the item at the given position in the data set
     * @param position int: The position of the item within the adapter's data set
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvName.setText(list.get(position).getRelatedIdName());
        holder.tvRelationType.setText(list.get(position).getAdditionalRelationLabel());
    }

    /** Returns the total number of items in the data set held by the adapter
     * @return int
     */
    @Override
    public int getItemCount() {
        return this.list.size();
    }

    /**
     * ViewHolder: Class for the ViewHolder of the adapter.
     *
     * @author (junamar2@inf.upv.es)
     * @version (2020 - 2021)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{

        /* The elements of the holder */
        public TextView tvName, tvRelationType;

        /** Constructor for the class */
        public ViewHolder(View view){
            super(view);
            this.tvName = (TextView) view.findViewById(R.id.tvRelationNameValue);
            this.tvRelationType = (TextView) view.findViewById(R.id.tvRelationTypeValue);
        }
    }
}