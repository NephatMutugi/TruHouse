package com.nephat.truhouse.recyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nephat.truhouse.R;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.AppViewHolder> {

    String[] langData = {};
    private LayoutInflater layoutInflater;

    public UserAdapter(String[] _data){
        langData = _data;
    }



    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.house_item, parent, false);

        return new AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        String title = langData[position];
        holder.title.setText(title);
    }

    @Override
    public int getItemCount() {
        return langData.length;
    }

    public class AppViewHolder extends RecyclerView.ViewHolder {

        ImageView home_icon;
        TextView title;

        public AppViewHolder(@NonNull View itemView) {
            super(itemView);

            home_icon = itemView.findViewById(R.id.imgIcon);
            title = itemView.findViewById(R.id.name_title);

        }
    }
}
