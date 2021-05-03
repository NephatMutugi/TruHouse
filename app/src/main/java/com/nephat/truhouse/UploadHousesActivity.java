package com.nephat.truhouse;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.nephat.truhouse.models.UploadModel;
import com.nephat.truhouse.retrofitUtil.ApiClient;
import com.nephat.truhouse.retrofitUtil.ApiInterface;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UploadHousesActivity extends AppCompatActivity {

    private static final String TAG = "UploadHousesActivity";
    private static final String BASE_URL = "http://192.168.100.2/realEstate/";
    private static final int MY_PERMISSIONS_REQUEST = 100;
    private int PICK_IMAGE_FROM_GALLERY_REQUEST = 1;
    private String houseType, housePrice, houseLocation, houseContact, houseDescription, houseName;

    private Bitmap bitmap;
    boolean check = true;

    private EditText mType, mPrice, mLocation, mContact, mDescription, mImageName;
    private ImageView mHouseImage;
    private Button mSaveBtn, mSelectImageBtn;

    private Retrofit retrofit = ApiClient.getApiClient();
    private ApiInterface apiInterface = retrofit.create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_houses);

        mHouseImage = findViewById(R.id.houseImage);
        mType = findViewById(R.id.editHouseType);
        mPrice = findViewById(R.id.editPrice);
        mLocation = findViewById(R.id.editLocation);
        mContact = findViewById(R.id.editContact);
        mDescription = findViewById(R.id.editDescription);
        mImageName = findViewById(R.id.imageTitle);
        mSaveBtn = findViewById(R.id.btnSubmit);
        mSelectImageBtn = findViewById(R.id.btnSelectImage);
        hideSoftKeyboard();


        if (ContextCompat.checkSelfPermission(UploadHousesActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(UploadHousesActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST);

        }

        mSelectImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(
                        intent, "Select Image From Gallery"),
                        PICK_IMAGE_FROM_GALLERY_REQUEST);
            }
        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadImage();
            }
        });

    }

    private void uploadImage(){
        String image = imageToString();
        houseName = String.valueOf(mImageName.getText());
        houseType = String.valueOf(mType.getText());
        housePrice = String.valueOf(mPrice.getText());
        houseLocation = String.valueOf(mLocation.getText());
        houseContact = String.valueOf(mContact.getText());
        houseDescription = String.valueOf(mDescription.getText());

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<UploadModel> call = apiInterface.uploadImage(houseName, houseLocation, housePrice,
                houseType, houseContact, houseDescription, image );

        call.enqueue(new Callback<UploadModel>() {
            @Override
            public void onResponse(Call<UploadModel> call, Response<UploadModel> response) {
                UploadModel model = response.body();
                if (model != null) {
                    toastMessage(model.getResponse());
                } else {
                    toastMessage("Image not uploaded");
                }
                //mHouseImage.setVisibility(View.VISIBLE);
               mImageName.setText("");
            }

            @Override
            public void onFailure(Call<UploadModel> call, Throwable t) {

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_FROM_GALLERY_REQUEST && resultCode == RESULT_OK && data != null ){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                mHouseImage.setImageBitmap(bitmap);
                mHouseImage.setVisibility(View.VISIBLE);
            } catch (IOException e){
                e.printStackTrace();
            }
        }

    }


    private String imageToString(){
        ByteArrayOutputStream byteArrayOutputStream;
        byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageByte = byteArrayOutputStream.toByteArray();
        String convertImage = Base64.encodeToString(imageByte, Base64.DEFAULT);

        return convertImage;
    }




    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


    private void hideSoftKeyboard()
    {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}
