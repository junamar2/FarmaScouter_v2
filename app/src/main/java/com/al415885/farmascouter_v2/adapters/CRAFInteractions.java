package com.al415885.farmascouter_v2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.al415885.farmascouter_v2.R;

import java.util.List;

/**
 * CustomRecyclerAdapter: Class for the adapter from the RecyclerView of FreezeValuesFragment.
 *
 * @author (junamar2@inf.upv.es)
 * @version (2020 - 2021)
 */
public class CRAFInteractions extends RecyclerView.Adapter<CRAFInteractions.ViewHolder> {

    /* The list of elements */
    private List<String> drugs, descriptions;
    private OnItemClickListener onItemClickListener;

    /** Constructor for the class */
    public CRAFInteractions(OnItemClickListener onItemClickListener,
                            List<String> drugs, List<String> descriptions){
        this.descriptions = descriptions;
        this.drugs = drugs;
        this.onItemClickListener = onItemClickListener;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_element_interactions_fragment, parent, false);
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
        holder.tvDrug.setText(drugs.get(position));
        holder.tvDescription.setText(descriptions.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(holder.getAdapterPosition());
            }
        });
    }

    /** Returns the total number of items in the data set held by the adapter
     * @return int
     */
    @Override
    public int getItemCount() {
        return this.drugs.size();
    }

    /**
     * ViewHolder: Class for the ViewHolder of the adapter.
     *
     * @author (junamar2@inf.upv.es)
     * @version (2020 - 2021)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{

        /* The elements of the holder */
        public TextView tvDrug, tvDescription;

        /** Constructor for the class */
        public ViewHolder(View view){
            super(view);
            this.tvDrug = (TextView) view.findViewById(R.id.tvDrug);
            this.tvDescription = (TextView) view.findViewById(R.id.tvDescription);
        }
    }
}