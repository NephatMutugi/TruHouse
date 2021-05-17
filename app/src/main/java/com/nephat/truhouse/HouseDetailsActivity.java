package com.nephat.truhouse;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
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
    private EditText mUserName, mUserEmail, mUserPhone, mUserRequest;
    private Button mBtnRequestInfo;

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

        name = intent.getStringExtra("NAME");
        email = intent.getStringExtra("EMAIL");
        Log.d(TAG, "onCreate: " + name + email);

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

        mUserName = findViewById(R.id.yourName);
        mUserName.setText(name);

        mUserPhone = findViewById(R.id.yourPhone);
        mUserEmail = findViewById(R.id.yourEmail);
        mUserEmail.setText(email);

        mUserRequest = findViewById(R.id.yourRequest);
        mBtnRequestInfo = findViewById(R.id.btnRequestInfo);

        mUserRequest.setText(R.string.request1);
        mUserRequest.append(location);
        mUserRequest.append(" it is a ");
        mUserRequest.append(houseType);
        mUserRequest.append(" named ");
        mUserRequest.append(title);

        Log.d(TAG, "onCreate: " +email);




        ImageSlider imageSlider = findViewById(R.id.houseImageSlider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("http://192.168.100.2/realEstate/" +imagePath, "Image 1", ScaleTypes.FIT));
        slideModels.add(new SlideModel("http://192.168.100.2/realEstate/" +imagePath2,"Image 2", ScaleTypes.FIT));
        slideModels.add(new SlideModel("http://192.168.100.2/realEstate/" +imagePath3,"Image 3", ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);


        Call<FetchHousesResponse> call = ApiClient.getApiClient().create(ApiInterface.class).fetchHouseInfo();

        mBtnRequestInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });

    }

    private void sendMail(){
        Log.d(TAG, "sendMail: Sending email");
        //Get text from the text views
        String name, email, phone, body;
        name = String.valueOf(mUserName.getText());
        email = String.valueOf(mUserEmail.getText());
        phone = String.valueOf(mUserPhone.getText());
        body = String.valueOf(mUserRequest.getText());

        //Destination for the email
        String [] sendTo = {"nephproject080@gmail.com"};

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, sendTo);

        intent.putExtra(Intent.EXTRA_SUBJECT, "REQUEST INFORMATION ABOUT A HOUSE");
        intent.putExtra(Intent.EXTRA_TEXT, "Personal information : \n Name: " +name + "\n Phone: " +phone
        + "\n Email: " + email + "\n " + body);

        startActivity(Intent.createChooser(intent, "Choose application to use"));


    }

}