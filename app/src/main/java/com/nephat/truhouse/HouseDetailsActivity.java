package com.nephat.truhouse;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.textfield.TextInputEditText;
import com.nephat.truhouse.models.FetchHousesResponse;
import com.nephat.truhouse.models.House;
import com.nephat.truhouse.retrofitUtil.ApiClient;
import com.nephat.truhouse.retrofitUtil.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class HouseDetailsActivity extends AppCompatActivity {

    private static final String TAG = "HouseDetailsActivity";
    private final String BASE_URL = "http://192.168.100.2/realEstate/";

    List<House> houseList;

    String title, imagePath, imagePath2, imagePath3, location, price, houseType, contact, description;
    String name, email;

    //Widgets
    private TextView mDispType, mDispLocation, mDispPrice, mDispContact, mDispDescription;
    private TextInputEditText mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_details);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        imagePath = intent.getStringExtra("image");
        imagePath2 = intent.getStringExtra("image2");
        imagePath3 = intent.getStringExtra("image3");
        location = intent.getStringExtra("location");
        price = intent.getStringExtra("price");
        contact = intent.getStringExtra("contact");
        houseType = intent.getStringExtra("house_type");
        description = intent.getStringExtra("description");

        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");

        //Widgets
        mDispType = findViewById(R.id.dispType);
        mDispType.setText(houseType);

        mDispLocation = findViewById(R.id.dispLocation);
        mDispLocation.setText(location);

        mDispPrice = findViewById(R.id.dispPrice);
        mDispPrice.setText(price);

        mDispContact = findViewById(R.id.dispContact);
        mDispContact.setText(contact);

        mDispDescription = findViewById(R.id.dispDescription);
        mDispDescription.setText(description);

        //mName = findViewById(R.id.user_name);
       // mName.setText(name);



        ImageSlider imageSlider = findViewById(R.id.houseImageSlider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("http://192.168.100.2/realEstate/" +imagePath, "Image 1", ScaleTypes.FIT));
        slideModels.add(new SlideModel("http://192.168.100.2/realEstate/" +imagePath2,"Image 2", ScaleTypes.FIT));
        slideModels.add(new SlideModel("http://192.168.100.2/realEstate/" +imagePath3,"Image 3", ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);


        Call<FetchHousesResponse> call = ApiClient.getApiClient().create(ApiInterface.class).fetchHouseInfo();
    }
}