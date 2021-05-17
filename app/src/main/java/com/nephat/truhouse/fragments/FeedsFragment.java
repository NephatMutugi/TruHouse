package com.nephat.truhouse.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
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


    private String mName, mID;
    private String name, id, email;


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
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);

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
                }

            }

            @Override
            public void onFailure(Call<FetchHousesResponse> call, Throwable t) {

                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    public void setMyData(String name, String id, String email){
        this.name = name;
        this.id = id;
        this.email = email;
        Log.d(TAG, "setMyData: " +name + " " + id + email);

        String myName, myId;
        myName = name;
        myId = id;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        //Inflate menu
        inflater.inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Location/House Type");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (houseAdapter != null){
                    houseAdapter.getFilter().filter(newText);
                }

                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
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


                Log.d(TAG, "onClick: " + name + " " + id);
                intent.putExtra("NAME", name);
                intent.putExtra("EMAIL", email);



                Log.d(TAG, "onClick: " + houseList);
                startActivity(intent);

            }
        };
    }



    private void toastMessage(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}


