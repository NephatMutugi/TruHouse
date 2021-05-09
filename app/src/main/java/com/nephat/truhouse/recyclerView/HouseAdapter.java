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
import com.nephat.truhouse.models.House;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.ViewHolder> implements Filterable {

    List<House> houseList;
    List<House> newHouseList;
    Context context;
    private RecyclerViewClickListener listener;

    public HouseAdapter(Context context, List<House> houseList, RecyclerViewClickListener listener) {
        this.context = context;
        this.houseList = houseList;
        this.listener = listener;
        newHouseList = new ArrayList<>(houseList);

        notifyDataSetChanged();
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
        House list = houseList.get(position);
        holder.mHouseType.setText(String.valueOf(list.getHouse_type()));
        holder.mHouseLocation.setText(String.valueOf(list.getLocation()));
        Picasso.get().load("http://192.168.100.2/realEstate/" +list.getImage_path())
                .into(holder.mDisplayHouse);

        //Picasso.get().load(houseList.get(position).getImageUrl()).into(holder.mDisplayHouse);
        //holder.mHouseType.setText(houseList.get(position).getHouseType());
        //holder.mHouseLocation.setText(houseList.get(position).getHouseLocation());

    }

    @Override
    public int getItemCount() {
        return houseList.size();
    }


    @Override
    public Filter getFilter() {
        return filterUser;
    }

    private Filter filterUser = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String searchText = constraint.toString().toLowerCase();
            List<House> tempList = new ArrayList<>();
            if (searchText.length()==0 || searchText.isEmpty()){
                tempList.addAll(newHouseList);
            } else {
                for (House item:newHouseList){
                    if (item.getLocation().toLowerCase().contains(searchText) ||
                            item.getHouse_type().toLowerCase().contains(searchText)){
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

            houseList.clear();
            houseList.addAll((Collection<? extends House>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView mHouseType, mHouseLocation, mMoreContact;
        ImageView mDisplayHouse;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mDisplayHouse = itemView.findViewById(R.id.displayImage);
            mHouseType = itemView.findViewById(R.id.textHouseType);
            mHouseLocation = itemView.findViewById(R.id.textHouseLocation);
            mMoreContact = itemView.findViewById(R.id.contactMore);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            listener.onClick(v, getAbsoluteAdapterPosition());

        }
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
}
