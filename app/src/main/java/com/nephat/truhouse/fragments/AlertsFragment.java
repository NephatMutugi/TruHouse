package com.nephat.truhouse.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nephat.truhouse.R;
import com.nephat.truhouse.models.AgentList;
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
    private String username, userLocality;


    public AlertsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alerts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
                    agentAdapter = new AgentAdapter(agentLists, getActivity());
                    recyclerView.setAdapter(agentAdapter);
                }
            }

            @Override
            public void onFailure(Call<FetchAgentListResponse> call, Throwable t) {

            }
        });
    }
}