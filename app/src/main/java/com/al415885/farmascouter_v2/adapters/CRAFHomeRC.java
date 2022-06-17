package com.al415885.farmascouter_v2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.al415885.farmascouter_v2.R;
import com.al415885.farmascouter_v2.models.cima.secondlevel.MedRCSecond;

import java.sql.Date;
import java.util.List;

/**
 * CustomRecyclerAdapter: Class for the adapter from the RecyclerView of FreezeValuesFragment.
 *
 * @author (junamar2@inf.upv.es)
 * @version (2020 - 2021)
 */
public class CRAFHomeRC
        extends RecyclerView.Adapter<CRAFHomeRC.ViewHolder> {

    /* The list of elements */
    private List<MedRCSecond> list;

    /** Constructor for the class */
    public CRAFHomeRC(List<MedRCSecond> list){
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
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_element_home_fragment_rc, parent, false);
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
        holder.tvNRegisterRC.setText(list.get(position).getNregistro());
        holder.tvDate.setText(new Date(Long.parseLong(String.valueOf(list.get(position)
                .getFecha()))).toString());
        holder.tvChangeType.setText(String.valueOf(list.get(position).getTipoCambio()));
        List<String> changes = list.get(position).getCambio();
        String chText = "";
        for(int i = 0; i < changes.size(); i++){
            if(changes.size() - 1 == i)
                chText = chText + changes.get(i);
            else
                chText = chText + changes.get(i) + ", ";
        }
        if(chText.equals(""))
            chText = "Unknown";
        holder.tvChange.setText(chText);
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
        public TextView tvNRegisterRC, tvDate, tvChangeType, tvChange;

        /** Constructor for the class */
        public ViewHolder(View view){
            super(view);
            this.tvNRegisterRC = (TextView) view.findViewById(R.id.tvRelationTypeValue);
            this.tvDate = (TextView) view.findViewById(R.id.tvRelationNameValue);
            this.tvChangeType = (TextView) view.findViewById(R.id.tvChangeTypeValue);
            this.tvChange = (TextView) view.findViewById(R.id.tvChangeValue);
        }
    }
}
