package com.nephat.truhouse.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nephat.truhouse.R;
import com.nephat.truhouse.models.DataModel;
import com.nephat.truhouse.recyclerView.UserAdapter;

import java.util.ArrayList;

public class FeedsFragment extends Fragment implements UserAdapter.ItemClickListener {

    private static final String TAG = "FeedsFragment";

    private ArrayList<DataModel> list = new ArrayList<>();


    public FeedsFragment() {
        // Required empty public constructor
    }

    public static FeedsFragment newInstance(){
        return new FeedsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_feeds, container, false);

        buildListData();
        initRecyclerView(view);

        return view;
    }

    private void initRecyclerView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
       // recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        UserAdapter adapter = new UserAdapter(list);
        recyclerView.setAdapter(adapter);
    }

    private void buildListData(){
        list.add(new DataModel("Bedsitter"));
        list.add(new DataModel("One Bedroom"));
        list.add(new DataModel("Apartment Studio"));
        list.add(new DataModel("Cottages"));
        list.add(new DataModel("Two Bedrooms"));
        list.add(new DataModel("Apartments for sale"));
        list.add(new DataModel("Last Semester"));
    }


    @Override
    public void onItemClick(DataModel dataModel) {

    }
}