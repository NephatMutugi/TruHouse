package com.nephat.truhouse.recyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nephat.truhouse.R;
import com.nephat.truhouse.models.AgentList;

import java.util.List;

public class AgentAdapter extends RecyclerView.Adapter<AgentAdapter.ViewHolder> {

    List<AgentList> agentList;
    Context context;

    public AgentAdapter(List<AgentList> agentList, Context context) {
        this.agentList = agentList;
        this.context = context;

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

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView agentName, agentLocality;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            agentName = itemView.findViewById(R.id.textNameAgent);
            agentLocality = itemView.findViewById(R.id.textLocalityAgent);

        }
    }
}
