package com.nephat.truhouse;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.nephat.truhouse.retrofitUtil.ApiClient;
import com.nephat.truhouse.retrofitUtil.ApiInterface;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadHousesActivity extends AppCompatActivity {

    private static final String TAG = "UploadHousesActivity";

    private static final String BASE_URL = "http://192.168.100.2/realEstate/";

    private static final int MY_PERMISSIONS_REQUEST = 100;
    private int PICK_IMAGE_FROM_GALLERY_REQUEST = 1;

    String filepath = "";
    String filepath1 = "";
    String filepath2 = "";
    String filepath3 = "";

    private EditText mType, mPrice, mLocation, mContact, mDescription;
    private ImageView mHouseImage;
    private Button mSaveBtn;


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
        mSaveBtn = findViewById(R.id.btnSubmit);


        if (ContextCompat.checkSelfPermission(UploadHousesActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(UploadHousesActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST);

        }

        mHouseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(intent, "Select Picture"),
                        PICK_IMAGE_FROM_GALLERY_REQUEST
                );
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_FROM_GALLERY_REQUEST &&
                requestCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            uploadFile(uri);
        }

    }

    private void uploadFile(Uri fileUri) {

        RequestBody houseType = RequestBody.create(MultipartBody.FORM, mType.getText().toString());
        RequestBody housePrice = RequestBody.create(MultipartBody.FORM, mPrice.getText().toString());
        RequestBody houseLocation = RequestBody.create(MultipartBody.FORM, mLocation.getText().toString());
        RequestBody houseContact = RequestBody.create(MultipartBody.FORM, mContact.getText().toString());
        RequestBody houseDescription = RequestBody.create(MultipartBody.FORM, mDescription.getText().toString());



        //Create Retrofit Instance
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        //Get client & call object for the request
        ApiClient apiClient = retrofit.create(ApiClient.class);

        //Execute the request
        Call<ResponseBody> call = null;
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                toastMessage("Yeah");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                toastMessage("nooo");
            }
        });
    }

    private void uploadImage() {
        File file = new File(filepath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part parts = MultipartBody.Part.createFormData("newimage", file.getName(), requestBody);

        RequestBody someData = RequestBody.create(MediaType.parse("text/plain"), "This is a new image");

    }

    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}



/*
    private void uploadMultipleImages() {
        MultipartBody.Part image = prepareImagePart(filepath, "image");
        MultipartBody.Part image1 = prepareImagePart(filepath1, "image1");
        MultipartBody.Part image2 = prepareImagePart(filepath2, "image2");
        MultipartBody.Part image3 = prepareImagePart(filepath3, "image3");
    }

    private MultipartBody.Part prepareImagePart(String path, String partName){
        File file = new File(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse(getContentResolver().getType(Uri.fromFile(file))), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestBody);

    }

    private void uploadImage(){

    }

 */
