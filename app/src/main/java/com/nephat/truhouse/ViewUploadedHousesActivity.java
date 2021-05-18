package com.nephat.truhouse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
    private AgentHouseAdapter.AgentRecyclerViewClickListener listener;

    private TextView mAgentName;

    String name, id, regNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_uploaded_houses);

        Intent intent = getIntent();
        name = intent.getStringExtra("NAME");
        id = intent.getStringExtra("ID");
        regNo = intent.getStringExtra("REG");


        setOnClickListener();
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
                    agentHouseAdapter = new AgentHouseAdapter(agentHouseList, ViewUploadedHousesActivity.this, listener);
                    recyclerView.setAdapter(agentHouseAdapter);

                }
            }

            @Override
            public void onFailure(Call<FetchAgentHouseResponse> call, Throwable t) {

            }
        });

    }

    private void setOnClickListener(){
        listener = new AgentHouseAdapter.AgentRecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), AgentItemClick.class);
                intent.putExtra("title", agentHouseList.get(position).getTitle());
                intent.putExtra("image", agentHouseList.get(position).getImage_path());
                intent.putExtra("image2", agentHouseList.get(position).getImage_path2());
                intent.putExtra("image3", agentHouseList.get(position).getImage_path3());
                intent.putExtra("location", agentHouseList.get(position).getLocation());
                intent.putExtra("price", agentHouseList.get(position).getPrice());
                intent.putExtra("contact", agentHouseList.get(position).getContact());
                intent.putExtra("description", agentHouseList.get(position).getDescription());
                intent.putExtra("house_type", agentHouseList.get(position).getHouse_type());
                intent.putExtra("house_id", agentHouseList.get(position).getId());

                startActivity(intent);
            }
        };


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                agentHouseAdapter.getFilter().filter(s.toString());
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}