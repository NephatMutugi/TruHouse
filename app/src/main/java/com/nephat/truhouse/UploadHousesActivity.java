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








    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                mHouseImage.setImageBitmap(bitmap);
            } catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    private void uploadImage() {
        ByteArrayOutputStream byteArrayOutputStream;
        byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        final String convertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
        class AsyncTaskUploadClass extends AsyncTask<Void, Void, String>{
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                toastMessage("Image is uploading");
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                toastMessage("Image has been uploaded successfully");
                mHouseImage.setImageResource(android.R.color.transparent);
            }

            @Override
            protected String doInBackground(Void... voids) {
                ImageProcessClass imageProcessClass = new ImageProcessClass();
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("location", houseLocation);
                hashMap.put("price", housePrice);
                hashMap.put("house_type", houseType);
                hashMap.put("contact", houseContact);
                hashMap.put("description", houseDescription);
                hashMap.put("image_path", convertImage);

                String finalData = imageProcessClass.ImageHttpRequest(BASE_URL, hashMap);

                return finalData;
            }
        }
        AsyncTaskUploadClass asyncTaskUploadClass = new AsyncTaskUploadClass();
        asyncTaskUploadClass.execute();
    }


    public class ImageProcessClass{
        public String ImageHttpRequest(String requestURL, HashMap<String, String> PData){
            StringBuilder stringBuilder = new StringBuilder();

            try {
                URL url;
                HttpURLConnection httpURLConnection;
                OutputStream outputStream;
                BufferedWriter bufferedWriter;
                BufferedReader bufferedReader;
                int RC;
                url = new URL(requestURL);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(19000);
                httpURLConnection.setConnectTimeout(19000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                outputStream = httpURLConnection.getOutputStream();

                bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(outputStream, "UTF-8"));

                bufferedWriter.write(bufferedWriterDataFN(PData));
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                RC = httpURLConnection.getResponseCode();
                if (RC == HttpURLConnection.HTTP_OK){
                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    stringBuilder = new StringBuilder();
                    String responseCode2;
                    while ((responseCode2 = bufferedReader.readLine()) != null){
                        stringBuilder.append(responseCode2);
                    }

                }

            } catch (Exception e){
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> hashMap) throws UnsupportedEncodingException{
            StringBuilder stringBuilder;
            stringBuilder = new StringBuilder();
            for (Map.Entry<String, String> KEY :hashMap.entrySet()){
                if (check){
                    check = false;
                } else {
                    stringBuilder.append("&");
                }
                stringBuilder.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));
                stringBuilder.append("=");
                stringBuilder.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilder.toString();
        }
    }

    */

    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


    private void hideSoftKeyboard()
    {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}








    /*

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

    */

















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
