package com.nephat.truhouse;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.nephat.truhouse.retrofitUtil.ApiClient;
import com.nephat.truhouse.retrofitUtil.ApiInterface;

import retrofit2.Retrofit;

public class UploadHousesActivity extends AppCompatActivity {

    String filepath = "";
    String filepath1 = "";
    String filepath2 = "";
    String filepath3 = "";


    private Retrofit retrofit = ApiClient.getApiClient();
    private ApiInterface apiInterface = retrofit.create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_houses);




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
}