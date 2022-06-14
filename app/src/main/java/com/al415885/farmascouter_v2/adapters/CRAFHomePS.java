package com.al415885.farmascouter_v2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.al415885.farmascouter_v2.R;
import com.al415885.farmascouter_v2.models.cima.secondlevel.MedPSSecond;

import java.util.List;

/**
 * CustomRecyclerAdapter: Class for the adapter from the RecyclerView of FreezeValuesFragment.
 *
 * @author (junamar2@inf.upv.es)
 * @version (2020 - 2021)
 */
public class CRAFHomePS extends RecyclerView.Adapter<CRAFHomePS.ViewHolder> {

    /* The list of elements */
    private List<MedPSSecond> list;

    /** Constructor for the class */
    public CRAFHomePS(List<MedPSSecond> list){
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_element_home_fragment_ps, parent, false);
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
        holder.tvCn.setText(list.get(position).getCn());
        holder.tvName.setText(list.get(position).getNombre());
        holder.tvStartDate.setText(String.valueOf(list.get(position).getFini()));
        holder.tvEndDate.setText(String.valueOf(list.get(position).getFfin()));
        holder.tvTypeOf.setText(String.valueOf(list.get(position).getTipoProblemaSuministro()));
        holder.tvRemarks.setText(list.get(position).getObserv());
        holder.cbActive.setChecked(list.get(position).isActivo());
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
        public TextView tvCn, tvName, tvStartDate, tvEndDate, tvTypeOf, tvRemarks;
        public CheckBox cbActive;

        /** Constructor for the class */
        public ViewHolder(View view){
            super(view);
            this.tvCn = (TextView) view.findViewById(R.id.tvCnValue);
            this.tvName = (TextView) view.findViewById(R.id.tvNameValue);
            this.tvStartDate = (TextView) view.findViewById(R.id.tvStartDateValue);
            this.tvEndDate = (TextView) view.findViewById(R.id.tvEndDateValue);
            this.tvTypeOf = (TextView) view.findViewById(R.id.tvTypeOfValue);
            this.cbActive = (CheckBox) view.findViewById(R.id.cbActive);
            this.tvRemarks = (TextView) view.findViewById(R.id.tvRemarksValue);
        }
    }
}
