package com.nephat.truhouse.resetPassword;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.nephat.truhouse.R;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class ResetPasswordActivity extends AppCompatActivity {

    private static final String TAG = "ResetPasswordActivity";
    private static final String BASE_URL = "http://192.168.100.2/realEstate/resetPassword.php";

    private TextInputEditText mEmail;
    private Button mBtnEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mEmail = findViewById(R.id.edit_reset_email);
        mBtnEmail = findViewById(R.id.btnVerifyEmail);


        mBtnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyEmail();
            }
        });

    }

    private void verifyEmail(){

        final String email;
        email = String.valueOf(mEmail.getText());

        if (!email.isEmpty()){
            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    String[] field = new String[1];
                    field[0] = "email";

                    String[] data = new String[1];
                    data[0] = email;

                    PutData putData = new PutData(BASE_URL, "POST", field, data);
                    if (putData.startPut()){
                        if (putData.onComplete()){
                            String result = putData.getResult();

                            toastMessage("Verification code sent to email");
                            Intent intent = new Intent(ResetPasswordActivity.this, CodeActivity.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
                        } else {
                            toastMessage("Failed");
                        }
                    }
                }
            });

        } else {
            mEmail.setError("Field cannot be empty");
            toastMessage("Email field cannot be empty");
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}