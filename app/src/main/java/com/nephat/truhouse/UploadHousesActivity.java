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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.nephat.truhouse.models.ApiResponse;
import com.nephat.truhouse.retrofitUtil.ApiClient;
import com.nephat.truhouse.retrofitUtil.ApiInterface;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UploadHousesActivity extends AppCompatActivity {

    private static final String TAG = "UploadHousesActivity";
    private static final String BASE_URL = "http://192.168.100.2/realEstate/";
    private static final int MY_PERMISSIONS_REQUEST = 100;
    private final int PICK_IMAGE_FROM_GALLERY_REQUEST = 1;
    private final int PICK_MULTIPLE_REQUEST = 2;
    private String houseType, housePrice, houseLocation, houseContact, houseDescription, houseName;
    private ArrayList<Uri> imageUris;
    int position = 0;
    List<String> imagesEncodedList;

    private Bitmap bitmap, bitmap2, bitmap3;
    boolean check = true;
    String name, id, regNo;

    private TextView mAgentName;
    private TextInputEditText mType, mPrice, mLocation, mContact, mDescription, mHouseTitle;
    private EditText mImageName, mImageName2, mImageName3;
    private ImageView mHouseImage, mHouseImage2, mHouseImage3;
    private Button mSaveBtn, mSelectImageBtn;

    private Retrofit retrofit = ApiClient.getApiClient();
    private ApiInterface apiInterface = retrofit.create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_houses);

        //Name of agent
        mAgentName = findViewById(R.id.textName);

        //Image views
        mHouseImage = findViewById(R.id.houseImage);
        mHouseImage2 = findViewById(R.id.houseImage2);
        mHouseImage3 = findViewById(R.id.houseImage3);

        //Text input fields
        mHouseTitle = findViewById(R.id.editHouseName);
        mType = findViewById(R.id.editHouseType);
        mPrice = findViewById(R.id.editPrice);
        mLocation = findViewById(R.id.editLocation);
        mContact = findViewById(R.id.editContact);
        mDescription = findViewById(R.id.editDescription);

        //Edit text Image names
        mImageName = findViewById(R.id.imageTitle);
        mImageName2 = findViewById(R.id.imageTitle2);
        mImageName3 = findViewById(R.id.imageTitle3);

        mSaveBtn = findViewById(R.id.btnSubmit);
        mSelectImageBtn = findViewById(R.id.btnSelectImage);
        hideSoftKeyboard();

        Intent intent = getIntent();
        name = intent.getStringExtra("NAME");
        id = intent.getStringExtra("ID");
        regNo = intent.getStringExtra("REG");
        mAgentName.setText(name);

        imageUris = new ArrayList<>();



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
                pickImagesIntent();
            }
        });


        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
                Intent intent = new Intent(UploadHousesActivity.this, ChooseActionActivity.class);
                intent.putExtra("NAME", name);
                intent.putExtra("ID", id);
                intent.putExtra("REG", regNo);
                startActivity(intent);
                finish();
            }
        });

    }

    private void pickImagesIntent(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Images"), PICK_IMAGE_FROM_GALLERY_REQUEST);
    }

    private void uploadImage(){
        String image = imageToString();
        String image2 = imageToString2();
        String image3 = imageToString3();
        String agent_fk = regNo;

        String imageName, imageName2, imageName3;

        houseName = String.valueOf(mHouseTitle.getText());
        houseType = String.valueOf(mType.getText());
        housePrice = String.valueOf(mPrice.getText());
        houseLocation = String.valueOf(mLocation.getText());
        houseContact = String.valueOf(mContact.getText());
        houseDescription = String.valueOf(mDescription.getText());

        imageName = String.valueOf(mImageName.getText());
        imageName2 = String.valueOf(mImageName2.getText());
        imageName3 = String.valueOf(mImageName3.getText());



        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ApiResponse> call = apiInterface.uploadImage(houseName, houseLocation, housePrice,
                houseType, houseContact, houseDescription, image, image2, image3, agent_fk, imageName, imageName2, imageName3 );

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.code()==200){
                    if (response.body().getStatus().equals("ok")){
                        if (response.body().getResultCode()==1){
                            toastMessage("House was registered successfully");


                        } else {
                            toastMessage("Property already uploaded");
                        }

                    } else {
                        toastMessage("Something went wrong");
                    }
                } else{
                    toastMessage("Something went wrong...");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_FROM_GALLERY_REQUEST && resultCode == RESULT_OK && data != null ){

            if (data.getClipData() != null){
                for (int i = 0; i < 3; i++){
                    //Get image uri at specific index
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    imageUris.add(imageUri);
                }
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUris.get(0));
                    bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUris.get(1));
                    bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUris.get(2));

                    mHouseImage.setImageBitmap(bitmap);
                    mHouseImage2.setImageBitmap(bitmap2);
                    mHouseImage3.setImageBitmap(bitmap3);


                } catch (IOException e) {
                    e.printStackTrace();
                }

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
    private String imageToString2(){
        ByteArrayOutputStream byteArrayOutputStream;
        byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageByte = byteArrayOutputStream.toByteArray();
        String convertImage2 = Base64.encodeToString(imageByte, Base64.DEFAULT);

        return convertImage2;
    }
    private String imageToString3(){
        ByteArrayOutputStream byteArrayOutputStream;
        byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap3.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageByte = byteArrayOutputStream.toByteArray();
        String convertImage3 = Base64.encodeToString(imageByte, Base64.DEFAULT);

        return convertImage3;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
       /* Intent intent = new Intent(UploadHousesActivity.this, LoginAsAgentActivity.class);
        startActivity(intent);*/
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


    private void hideSoftKeyboard()
    {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}



