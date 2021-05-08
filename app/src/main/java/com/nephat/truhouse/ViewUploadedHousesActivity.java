package com.nephat.truhouse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nephat.truhouse.models.AgentHouse;
import com.nephat.truhouse.models.FetchAgentHouseResponse;
import com.nephat.truhouse.recyclerView.AgentHouseAdapter;
import com.nephat.truhouse.retrofitUtil.ApiClient;
import com.nephat.truhouse.retrofitUtil.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewUploadedHousesActivity extends AppCompatActivity {

    private static final String TAG = "ViewUploadedHousesActiv";

    RecyclerView recyclerView;
    List<AgentHouse> agentHouseList;
    private List<FetchAgentHouseResponse> agentHouseResponseList;
    private AgentHouseAdapter agentHouseAdapter;

    String name, id, regNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_uploaded_houses);

        Intent intent = getIntent();
        name = intent.getStringExtra("NAME");
        id = intent.getStringExtra("ID");
        regNo = intent.getStringExtra("REG");

        recyclerView = findViewById(R.id.myRecyclerView1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Retrofit
        Call<FetchAgentHouseResponse> call = ApiClient.getApiClient().create(ApiInterface.class).fetchAgentHouseInfo(regNo);
        call.enqueue(new Callback<FetchAgentHouseResponse>() {
            @Override
            public void onResponse(Call<FetchAgentHouseResponse> call, Response<FetchAgentHouseResponse> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: "+ response.body().getAgentHouseList().toString());
                    agentHouseList = response.body().getAgentHouseList();
                    agentHouseAdapter = new AgentHouseAdapter(agentHouseList, ViewUploadedHousesActivity.this);
                    recyclerView.setAdapter(agentHouseAdapter);

                }
            }

            @Override
            public void onFailure(Call<FetchAgentHouseResponse> call, Throwable t) {

            }
        });

    }
}