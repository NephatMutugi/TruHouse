package com.nephat.truhouse.recyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nephat.truhouse.R;
import com.nephat.truhouse.models.AgentHouse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AgentHouseAdapter extends RecyclerView.Adapter<AgentHouseAdapter.AgentViewHolder> implements Filterable {

    List<AgentHouse> agentHouseList;
    List<AgentHouse> filterList;
    Context context;

    private AgentRecyclerViewClickListener clickListener;


    public AgentHouseAdapter(List<AgentHouse> agentHouseList, Context context, AgentRecyclerViewClickListener clickListener) {
        this.agentHouseList = agentHouseList;
        this.context = context;
        this.clickListener = clickListener;
        filterList = new ArrayList<>(agentHouseList);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AgentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.house_list, parent, false);

        return new AgentHouseAdapter.AgentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgentHouseAdapter.AgentViewHolder holder, int position) {

        //Bind the data
        AgentHouse list = agentHouseList.get(position);
        holder.mHouseTitle.setText(String.valueOf(list.getTitle()));
        holder.mHouseType.setText(String.valueOf(list.getHouse_type()));
        holder.mHouseLocation.setText(String.valueOf(list.getLocation()));
        Picasso.get().load("http://192.168.100.2/realEstate/" +list.getImage_path())
                .into(holder.mDisplayHouse);

    }


    @Override
    public int getItemCount() {
        return agentHouseList.size();
    }


    @Override
    public Filter getFilter() {
        return filterUser;
    }

    private Filter filterUser = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String searchText = constraint.toString().toLowerCase();
            List<AgentHouse> tempList = new ArrayList<>();
            if (searchText.length()==0 || searchText.isEmpty()){
                tempList.addAll(filterList);
            } else {
                for (AgentHouse item:filterList){
                    if (item.getLocation().toLowerCase().contains(searchText) ||
                    item.getHouse_type().toLowerCase().contains(searchText) ||
                            item.getTitle().toLowerCase().contains(searchText)){
                        tempList.add(item);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = tempList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {

            agentHouseList.clear();
            agentHouseList.addAll((Collection<? extends AgentHouse>) filterResults.values);
            notifyDataSetChanged();
        }
    };




    public class AgentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mHouseTitle, mHouseType, mHouseLocation, mDelete;
        ImageView mDisplayHouse;

        public AgentViewHolder(@NonNull View itemView) {
            super(itemView);

            mHouseTitle = itemView.findViewById(R.id.textHouseTitle1);
            mHouseType = itemView.findViewById(R.id.textHouseType1);
            mHouseLocation = itemView.findViewById(R.id.textHouseLocation1);
           // mDelete = itemView.findViewById(R.id.textDelete);
            mDisplayHouse = itemView.findViewById(R.id.displayImage1);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAbsoluteAdapterPosition());
        }

        //clickListener.onClick(v, getAbsoluteAdapterPosition());

    }

    public interface AgentRecyclerViewClickListener {
        void onClick(View v, int position);
    }
}
