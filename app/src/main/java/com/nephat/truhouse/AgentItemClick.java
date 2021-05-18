package com.nephat.truhouse;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.nephat.truhouse.models.ApiResponse;
import com.nephat.truhouse.retrofitUtil.ApiClient;
import com.nephat.truhouse.retrofitUtil.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgentItemClick extends AppCompatActivity {

    private static final String TAG = "AgentItemClick";
    private final String BASE_URL = "http://192.168.100.2/realEstate/";

    //Widgets
    private EditText mHouseType, mHouseLocation, mHousePrice, mHouseContact, mHouseDescription;
    private TextView mHouseTitle;
    private Button mUpdateBtn;


    String hId, title, imagePath, imagePath2, imagePath3, mLocation, mPrice, mHouse_Type, mContact, mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_item_click);
        hideSoftKeyboard();

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        imagePath = intent.getStringExtra("image");
        imagePath2 = intent.getStringExtra("image2");
        imagePath3 = intent.getStringExtra("image3");
        mLocation = intent.getStringExtra("location");
        mPrice = intent.getStringExtra("price");
        mContact = intent.getStringExtra("contact");
        mHouse_Type = intent.getStringExtra("house_type");
        mDescription = intent.getStringExtra("description");
        hId = intent.getStringExtra("house_id");

        mHouseTitle = findViewById(R.id.agentHouseTitle);
        mHouseTitle.setText(title);

        mHouseType = findViewById(R.id.agentDispType);
        mHouseType.setText(mHouse_Type);

        mHouseLocation = findViewById(R.id.agentDispLocation);
        mHouseLocation.setText(mLocation);

        mHousePrice = findViewById(R.id.agentDispPrice);
        mHousePrice.setText(mPrice);

        mHouseContact = findViewById(R.id.agentDispContact);
        mHouseContact.setText(mContact);

        mHouseDescription = findViewById(R.id.agentDispDescription);
        mHouseDescription.setText(mDescription);

        mUpdateBtn = findViewById(R.id.updateBtn);


        ImageSlider imageSlider = findViewById(R.id.agentHouseImageSlider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(BASE_URL+imagePath, "Image 1", ScaleTypes.FIT));
        slideModels.add(new SlideModel(BASE_URL+imagePath2, "Image 2", ScaleTypes.FIT));
        slideModels.add(new SlideModel(BASE_URL+imagePath3, "Image 3", ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);


        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateHouseDetails();
            }
        });
    }


    private void updateHouseDetails(){
        String id, location, price, house_type, contact, description;
        id = hId;
        location = String.valueOf(mHouseLocation.getText());
        price = String.valueOf(mHousePrice.getText());
        house_type = String.valueOf(mHouseType.getText());
        contact = String.valueOf(mHouseContact.getText());
        description = String.valueOf(mHouseDescription.getText());

        if (TextUtils.isEmpty(location) || TextUtils.isEmpty(price) || TextUtils.isEmpty(house_type) || TextUtils.isEmpty(contact) || TextUtils.isEmpty(description)){
            toastMessage("Fields cannot be empty");
        } else {
            Call<ApiResponse> call = ApiClient.getApiClient().create(ApiInterface.class)
                    .performHouseUpdate(id, location, price, house_type, contact, description);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.code()==200){
                        if (response.body().getStatus().equals("ok")){
                            if (response.body().getResultCode() ==1){
                                toastMessage("Details updated successfully");
                            } else {
                                toastMessage("An error occurred");
                            }
                        } else {
                            toastMessage("Details not updated");
                        }
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {

                }
            });
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu item) {
        getMenuInflater().inflate(R.menu.menu_item, item);
        MenuItem menuItem = item.findItem(R.id.deleteItem);


        return super.onCreateOptionsMenu(item);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();
        if (itemId==R.id.deleteItem){
            toastMessage("Clicked");
            Call<ApiResponse> call = ApiClient.getApiClient().create(ApiInterface.class).performHouseDelete(hId);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.code()==200){
                        if (response.body().getResultCode()==1){
                            toastMessage("House successfully deleted");
                        }else {
                            toastMessage("Not deleted");
                        }
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {

                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openDialog(){

    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void hideSoftKeyboard()
    {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}