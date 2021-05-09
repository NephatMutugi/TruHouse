package com.nephat.truhouse;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

public class AgentItemClick extends AppCompatActivity {

    private static final String TAG = "AgentItemClick";
    private final String BASE_URL = "http://192.168.100.2/realEstate/";

    //Widgets
    private EditText mHouseType, mHouseLocation, mHousePrice, mHouseContact, mHouseDescription;
    private TextView mHouseTitle;


    String title, imagePath, imagePath2, imagePath3, location, price, houseType, contact, description;

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
        location = intent.getStringExtra("location");
        price = intent.getStringExtra("price");
        contact = intent.getStringExtra("contact");
        houseType = intent.getStringExtra("house_type");
        description = intent.getStringExtra("description");

        mHouseTitle = findViewById(R.id.agentHouseTitle);
        mHouseTitle.setText(title);
        mHouseType = findViewById(R.id.agentDispType);
        mHouseType.setText(houseType);

        ImageSlider imageSlider = findViewById(R.id.agentHouseImageSlider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(BASE_URL+imagePath, "Image 1", ScaleTypes.FIT));
        slideModels.add(new SlideModel(BASE_URL+imagePath2, "Image 2", ScaleTypes.FIT));
        slideModels.add(new SlideModel(BASE_URL+imagePath3, "Image 3", ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        MenuItem menuItem = menu.findItem(R.id.deleteItem);


        return super.onCreateOptionsMenu(menu);
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void hideSoftKeyboard()
    {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}