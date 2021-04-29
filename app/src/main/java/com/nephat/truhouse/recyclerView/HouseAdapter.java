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
import com.nephat.truhouse.models.House;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.ViewHolder> {

    List<House> houseList;
    Context context;

    public HouseAdapter(Context context, List<House> houseList) {
        this.context = context;
        this.houseList = houseList;
    }


    @NonNull
    @Override
    public HouseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.houses_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HouseAdapter.ViewHolder holder, int position) {
        //Bind the data

        holder.mHouseType.setText(houseList.get(position).getHouseType());
        holder.mHouseLocation.setText(houseList.get(position).getHouseLocation());
        Picasso.get().load(houseList.get(position).getImageUrl()).into(holder.mDisplayHouse);
    }

    @Override
    public int getItemCount() {
        return houseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView mHouseType, mHouseLocation, mMoreContact;
        ImageView mDisplayHouse;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mDisplayHouse = itemView.findViewById(R.id.displayImage);
            mHouseType = itemView.findViewById(R.id.textHouseType);
            mHouseLocation = itemView.findViewById(R.id.textHouseLocation);
            mMoreContact = itemView.findViewById(R.id.contactMore);
        }
    }
}
