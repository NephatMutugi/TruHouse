package com.nephat.truhouse.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nephat.truhouse.HouseDetailsActivity;
import com.nephat.truhouse.R;
import com.nephat.truhouse.models.FetchHousesResponse;
import com.nephat.truhouse.models.House;
import com.nephat.truhouse.recyclerView.HouseAdapter;
import com.nephat.truhouse.retrofitUtil.ApiClient;
import com.nephat.truhouse.retrofitUtil.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedsFragment extends Fragment {

    private static final String TAG = "FeedsFragment";

    RecyclerView recyclerView;
    List<House> houseList;
    private List<FetchHousesResponse> responseList;
    private HouseAdapter houseAdapter;
    private HouseAdapter.RecyclerViewClickListener listener;

    private String userName, userEmail;



    public FeedsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_feeds, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Intent intent = new Intent();

        userName = intent.getStringExtra("name");
        userEmail = intent.getStringExtra("email");



        setOnClickListener();
        recyclerView = view.findViewById(R.id.myRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        //retrofit

        Call<FetchHousesResponse> call = ApiClient.getApiClient().create(ApiInterface.class).fetchHouseInfo();

        call.enqueue(new Callback<FetchHousesResponse>() {
            @Override
            public void onResponse(Call<FetchHousesResponse> call, Response<FetchHousesResponse> response) {

                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: "+ response.body().getHouseList().toString());

                    houseList = response.body().getHouseList();

                    houseAdapter = new HouseAdapter(getActivity(), houseList, listener);
                    recyclerView.setAdapter(houseAdapter);

                    //recyclerView.setAdapter(new HouseAdapter(getContext(), houseList));
                }

            }

            @Override
            public void onFailure(Call<FetchHousesResponse> call, Throwable t) {

                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    private void setOnClickListener(){
        listener = new HouseAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getActivity(), HouseDetailsActivity.class);
                intent.putExtra("title", houseList.get(position).getTitle());
                intent.putExtra("image", houseList.get(position).getImage_path());
                intent.putExtra("image2", houseList.get(position).getImage_path2());
                intent.putExtra("image3", houseList.get(position).getImage_path3());
                intent.putExtra("location", houseList.get(position).getLocation());
                intent.putExtra("price", houseList.get(position).getPrice());
                intent.putExtra("contact", houseList.get(position).getContact());
                intent.putExtra("description", houseList.get(position).getDescription());
                intent.putExtra("house_type", houseList.get(position).getHouse_type());

                intent.putExtra("name", userName);
                intent.putExtra("email", userEmail);

                Log.d(TAG, "onClick: " + houseList);
                startActivity(intent);

            }
        };
    }

    private void setHouseInfo(){


    }

    private void toastMessage(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}






//private ArrayList<DataModel> list = new ArrayList<>();


/*

        Call<FetchHousesResponse> call = RetrofitClient.getInstance().getApi().fetchHouseInfo();
        call.enqueue(new Callback<FetchHousesResponse>() {
            @Override
            public void onResponse(Call<FetchHousesResponse> call, Response<FetchHousesResponse> response) {

            }

            @Override
            public void onFailure(Call<FetchHousesResponse> call, Throwable t) {
                toastMessage(t.getMessage());
            }
        });













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


        buildListData();
        initRecyclerView(view);
    */