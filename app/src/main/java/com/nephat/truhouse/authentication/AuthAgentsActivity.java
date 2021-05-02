package com.nephat.truhouse.authentication;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.nephat.truhouse.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class AuthAgentsActivity extends AppCompatActivity {

    private static final String TAG = "AuthAgentsActivity";

    private EditText mResults;
    private TextView  mRegNo;
    private Button mBtnSelectImage, mBtnVerifyAgent;
    private ImageView mLicenceImage;

    private static final int MY_PERMISSIONS_REQUEST = 100;
    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 400;
    private static final int PICK_IMAGE_FROM_GALLERY_REQUEST = 1000;
    private static final int PICK_IMAGE_FROM_CAMERA_REQUEST = 1001;

    private String regNumber;

    String[] cameraPermission;
    String[] storagePermission;

    Uri image_uri;

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_agents);

        mRegNo = findViewById(R.id.textRegNumber);
        mBtnSelectImage = findViewById(R.id.buttonSelectLicence);
        mBtnVerifyAgent = findViewById(R.id.buttonVerify);
        mLicenceImage = findViewById(R.id.licenceImageView);

        hideSoftKeyboard();

        //Camera permission
        cameraPermission = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        //Storage permission
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};


        mBtnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageImportDialog();
            }
        });

        mBtnVerifyAgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AuthAgentsActivity.this, VerifyAgentActivity.class);
                intent.putExtra("REG_NUMBER", regNumber);
                startActivity(intent);
            }
        });
    }

    private void verifyRegistrationNumber(){
        String registrationNo;
        registrationNo = String.valueOf(mRegNo);
        if (!registrationNo.isEmpty()){

        }

        Intent intent = new Intent(AuthAgentsActivity.this, VerifyAgentActivity.class);
        intent.putExtra("REG_NUMBER", regNumber);
        startActivity(intent);

    }



    private void showImageImportDialog(){
        //Items to display on dialog
        String[] items = {" Camera", " Gallery"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Select Image");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    //Camera Option clicked
                    if (!checkCameraPermission()){
                        //Camera permission not allowed
                        requestCameraPermission();
                    } else {
                        //Permission allowed, take picture
                        pickCamera();
                    }
                }

                if (which == 1){
                    //Gallery option clicked
                    if (!checkStoragePermission()){
                        //Storage permission not allowed
                        requestStoragePermission();
                    } else {
                        //Permission allowed, take picture
                        pickGallery();
                    }
                }
            }
        });

        dialog.create().show();
    }

    private void pickGallery() {
        //Intent to pick image from gallery
        Intent intent = new Intent(Intent.ACTION_PICK);
        //Set intent type to images
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_FROM_GALLERY_REQUEST);

    }

    private void pickCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "NewPic");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image to be scanned");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, PICK_IMAGE_FROM_CAMERA_REQUEST);

    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE);
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        return result;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        /**Check camera permissions and return the result
         * To get high quality images, we have to save image to external storage first
         * before inserting it on the image view hence the storage permissions*/

        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;

        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        return result && result1;
    }

    //Handle permission results
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case CAMERA_REQUEST_CODE:
                if (grantResults.length>0){
                    boolean cameraAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;

                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && writeStorageAccepted){
                        pickCamera();
                    } else {
                        toastMessage("Permission denied");
                    }
                }
                break;

            case STORAGE_REQUEST_CODE:
                if (grantResults.length>0){

                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (writeStorageAccepted){
                        pickGallery();
                    } else {
                        toastMessage("Permission denied");
                    }
                }
                break;
        }


    }

    //Handle image result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            //Get image from gallery
            if (requestCode == PICK_IMAGE_FROM_GALLERY_REQUEST){
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);

            }
            //Get image from camera
            if (requestCode == PICK_IMAGE_FROM_CAMERA_REQUEST){
                CropImage.activity(image_uri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
            }
        }
        //Get Cropped Image
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                //Set Image to image view
                mLicenceImage.setImageURI(resultUri);

                //Get drawable bitmap for text recognition
                BitmapDrawable bitmapDrawable = (BitmapDrawable)mLicenceImage.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                TextRecognizer recognizer = new TextRecognizer.Builder(getApplicationContext()).build();

                if (!recognizer.isOperational()){
                    toastMessage("Error");
                }
                else {
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> items = recognizer.detect(frame);
                    StringBuilder stringBuilder = new StringBuilder();
                    //Get all the text from string builder until there is no more text
                    /*for (int i=0; i<items.size(); i++){
                        TextBlock myItem = items.valueAt(i);
                        stringBuilder.append(myItem.getValue());
                        stringBuilder.append("/n");

                    } */
                    int i = items.size();
                    TextBlock myItem = items.valueAt(4);
                    stringBuilder.append(myItem.getValue());
                    String toText = stringBuilder.toString();
                    String number  = toText.replaceAll("[^0-9]", "");
                    regNumber = number;

                    //Set Text to edit text
                    //mRegNo.setText(stringBuilder.toString());
                     mRegNo.setText(number);
                    Log.d(TAG, "onActivityResult: " + number);
                }
            }

            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
                toastMessage(error.getMessage());
            }
        }

    }

    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}