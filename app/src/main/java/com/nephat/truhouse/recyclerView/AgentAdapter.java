package com.nephat.truhouse.recyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nephat.truhouse.R;
import com.nephat.truhouse.models.AgentList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AgentAdapter extends RecyclerView.Adapter<AgentAdapter.ViewHolder> implements Filterable {

    List<AgentList> agentList;
    List<AgentList> newAgentList;
    Context context;
    private AgentRecyclerViewClickListener listener;



    public AgentAdapter(List<AgentList> agentList, Context context, AgentRecyclerViewClickListener listener) {
        this.agentList = agentList;
        this.context = context;
        this.listener = listener;
        newAgentList = new ArrayList<>(agentList);

        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public AgentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.agent_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgentAdapter.ViewHolder holder, int position) {
        //Bind the data
        AgentList list = agentList.get(position);
        holder.agentName.setText(String.valueOf(list.getName()));
        holder.agentLocality.setText(String.valueOf(list.getLocality()));
    }

    @Override
    public int getItemCount() {
        return agentList.size();
    }

    @Override
    public Filter getFilter() {
        return filterAgent;
    }


    private Filter filterAgent = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String searchAgent = constraint.toString().toLowerCase();
            List<AgentList> tempLists = new ArrayList<>();

            if (searchAgent.length()==0 || searchAgent.isEmpty()){
                tempLists.addAll(newAgentList);
            } else {
                for (AgentList item:newAgentList){
                    if (item.getName().toLowerCase().contains(searchAgent) ||
                    item.getLocality().toLowerCase().contains(searchAgent)){
                        tempLists.add(item);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = tempLists;


            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {
            agentList.clear();
            agentList.addAll((Collection<? extends AgentList>) filterResults.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView agentName, agentLocality;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            agentName = itemView.findViewById(R.id.textNameAgent);
            agentLocality = itemView.findViewById(R.id.textLocalityAgent);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAbsoluteAdapterPosition());
        }
    }

    public interface AgentRecyclerViewClickListener{
        void onClick(View v, int position);
    }
}
