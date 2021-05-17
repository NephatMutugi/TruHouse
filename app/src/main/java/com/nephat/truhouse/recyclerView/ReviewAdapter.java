package com.nephat.truhouse.recyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nephat.truhouse.R;
import com.nephat.truhouse.models.ReviewLists;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    List<ReviewLists> reviewLists;
    Context context;

    public ReviewAdapter(List<ReviewLists> reviewLists, Context context) {
        this.reviewLists = reviewLists;
        this.context = context;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.agent_rating, parent, false);

        return new ReviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder holder, int position) {
        //Bind the data
        ReviewLists lists = reviewLists.get(position);
        holder.mUserName.setText(String.valueOf(lists.getName()));
        holder.mReview.setText(String.valueOf(lists.getReview()));

    }

    @Override
    public int getItemCount() {
        return reviewLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mUserName, mReview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mUserName = itemView.findViewById(R.id.userName);
            mReview = itemView.findViewById(R.id.userReview);

        }
    }
}
