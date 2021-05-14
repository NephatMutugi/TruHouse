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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nephat.truhouse.R;
import com.nephat.truhouse.RateAgentActivity;
import com.nephat.truhouse.models.AgentList;
import com.nephat.truhouse.models.ApiResponse;
import com.nephat.truhouse.models.FetchAgentListResponse;
import com.nephat.truhouse.recyclerView.AgentAdapter;
import com.nephat.truhouse.retrofitUtil.ApiClient;
import com.nephat.truhouse.retrofitUtil.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlertsFragment extends Fragment {

    private static final String TAG = "AlertsFragment";
    RecyclerView recyclerView;
    List<AgentList> agentLists;
    private AgentAdapter agentAdapter;
    private AgentAdapter.AgentRecyclerViewClickListener listener;

    private String username, userLocality;

    private static String name, id, reg_no, rating;
    int mPosition;


    public AlertsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =  inflater.inflate(R.layout.fragment_alerts, container, false);

        Log.d(TAG, "onCreateView: " +name + " " + id);

       return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "onViewCreated: " + name + " " +id );

        setOnClickListener();
        recyclerView = view.findViewById(R.id.myAgentRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Retrofit
        Call<FetchAgentListResponse> call = ApiClient.getApiClient().create(ApiInterface.class).fetchAgentsList();
        call.enqueue(new Callback<FetchAgentListResponse>() {
            @Override
            public void onResponse(Call<FetchAgentListResponse> call, Response<FetchAgentListResponse> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: " +response.body().getAgentList().toString());
                    agentLists = response.body().getAgentList();

                    agentAdapter = new AgentAdapter(agentLists, getActivity(), listener);
                    recyclerView.setAdapter(agentAdapter);
                }
            }

            @Override
            public void onFailure(Call<FetchAgentListResponse> call, Throwable t) {

            }
        });
    }


    public void setData(String id, String name){
        AlertsFragment.id = id;
        AlertsFragment.name = name;



        Log.d(TAG, "setData: " +name + " " + id);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (agentAdapter != null){
                    agentAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setOnClickListener(){
        listener = new AgentAdapter.AgentRecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {

                reg_no = agentLists.get(position).getReg_no();
                Log.d(TAG, "onClick: " + reg_no);

                Call<ApiResponse> call = ApiClient.getApiClient().create(ApiInterface.class).getAvgRating(reg_no);
                call.enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if (response.code() == 200){

                            if (response.body().getStatus().equals("ok")){

                                if (response.body().getResultCode() == 1){

                                    rating = response.body().getAverage();
                                    Log.d(TAG, "onResponse: " + rating);

                                } else {
                                    Log.d(TAG, "onResponse: " + "Error");
                                }
                            } else {
                                Log.d(TAG, "onResponse: " + "error");
                            }
                        } else {
                            Log.d(TAG, "onResponse: " + "Error...");
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {

                        Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
                    }
                });



                Intent intent = new Intent(getActivity(), RateAgentActivity.class);
                intent.putExtra("id", agentLists.get(position).getId());
                intent.putExtra("reg_no", agentLists.get(position).getReg_no());
                intent.putExtra("name", agentLists.get(position).getName());
                intent.putExtra("email", agentLists.get(position).getEmail());
                intent.putExtra("phone", agentLists.get(position).getPhone());
                intent.putExtra("locality", agentLists.get(position).getLocality());
                intent.putExtra("qualification", agentLists.get(position).getQualification());

                intent.putExtra("user_id", id);
                intent.putExtra("user_name", name);
                intent.putExtra("rating", rating);

                startActivity(intent);
            }
        };
    }

    private void getAgentRating(){

    }
}