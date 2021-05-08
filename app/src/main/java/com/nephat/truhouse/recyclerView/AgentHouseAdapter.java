package com.nephat.truhouse.recyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nephat.truhouse.R;
import com.nephat.truhouse.models.AgentHouse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AgentHouseAdapter extends RecyclerView.Adapter<AgentHouseAdapter.AgentViewHolder> {

    List<AgentHouse> agentHouseList;
    Context context;

    public AgentHouseAdapter(List<AgentHouse> agentHouseList, Context context) {
        this.agentHouseList = agentHouseList;
        this.context = context;
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

    public class AgentViewHolder extends RecyclerView.ViewHolder {

        TextView mHouseTitle, mHouseType, mHouseLocation, mDelete;
        ImageView mDisplayHouse;

        public AgentViewHolder(@NonNull View itemView) {
            super(itemView);

            mHouseTitle = itemView.findViewById(R.id.textHouseTitle1);
            mHouseType = itemView.findViewById(R.id.textHouseType1);
            mHouseLocation = itemView.findViewById(R.id.textHouseLocation1);
            mDelete = itemView.findViewById(R.id.textDelete);
            mDisplayHouse = itemView.findViewById(R.id.displayImage1);
        }
    }
}
