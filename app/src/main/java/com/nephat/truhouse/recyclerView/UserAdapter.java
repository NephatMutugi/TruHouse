package com.nephat.truhouse.recyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nephat.truhouse.R;
import com.nephat.truhouse.models.DataModel;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.AppViewHolder> {

    private LayoutInflater layoutInflater;
    private List<DataModel> list;
    private ItemClickListener clickListener;


    public UserAdapter(List<DataModel> list, ItemClickListener clickListener){

        this.list = list;
        this.clickListener = clickListener;
    }



    @NonNull
    @Override
    public UserAdapter.AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.recycler_row, parent, false);

        return new AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.AppViewHolder holder, int position) {
        holder.titleText.setText(list.get(position).getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class AppViewHolder extends RecyclerView.ViewHolder {

        TextView titleText;

        public AppViewHolder(@NonNull View itemView) {
            super(itemView);

            titleText = itemView.findViewById(R.id.titleTextView);
        }
    }

    public interface ItemClickListener{
        public void onItemClick(DataModel dataModel);
    }

}
