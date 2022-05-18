package com.al415885.farmascouter_v2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.al415885.farmascouter_v2.R;
import com.al415885.farmascouter_v2.results.ResultsMed;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * CustomRecyclerAdapter: Class for the adapter from the RecyclerView of FreezeValuesFragment.
 *
 * @author (junamar2@inf.upv.es)
 * @version (2020 - 2021)
 */
public class CustomRecyclerAdapterMedFrag extends RecyclerView.Adapter<CustomRecyclerAdapterMedFrag.ViewHolder> {

    /* The list of elements */
    private List<ResultsMed> list;
    private OnItemClickListener onItemClickListener;
    private OnLongItemClickListener onLongItemClickListener;

    /** Constructor for the class */
    public CustomRecyclerAdapterMedFrag(OnItemClickListener onItemClickListener,
                                        OnLongItemClickListener onLongItemClickListener,
                                        List<ResultsMed> list){
        this.list = list;
        this.onItemClickListener = onItemClickListener;
        this.onLongItemClickListener = onLongItemClickListener;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_element_drug_fragment, parent, false);
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
        holder.tvName.setText(list.get(position).getNombre());
        if(list.get(position).getFotos() != null && list.get(position).getFotos().size() > 1)
        Picasso.get().load(list.get(position).getFotos().get(0).getUrl())
                .fit().into(holder.img1);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(holder.getAdapterPosition());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onLongItemClickListener.onLongClick(holder.getAdapterPosition());
                return true;
            }
        });
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
        public TextView tvnRegister, tvName, tvLab;
        public ImageView img1;

        /** Constructor for the class */
        public ViewHolder(View view){
            super(view);
            this.tvName = (TextView) view.findViewById(R.id.tvNameMedValue);
            this.img1 = (ImageView) view.findViewById(R.id.img1);
        }
    }
}