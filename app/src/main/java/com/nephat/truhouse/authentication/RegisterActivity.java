package com.nephat.truhouse.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.nephat.truhouse.R;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private TextInputEditText mUserName, mUserEmail, mPassword, mConfirmPassword;
    private Button mRegisterBtn;
    private TextView mLinkLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUserName = findViewById(R.id.register_username);
        mUserEmail = findViewById(R.id.register_email);
        mPassword = findViewById(R.id.register_pass);
        mConfirmPassword = findViewById(R.id.confirm_pass);

        mRegisterBtn = findViewById(R.id.register_btn);
        mLinkLogin = findViewById(R.id.login_link);

        mLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username, userEmail, userPassword, confirmPassword;
                username = String.valueOf(mUserName.getText());
                userEmail = String.valueOf(mUserEmail.getText());
                userPassword = String.valueOf(mPassword.getText());
                confirmPassword = String.valueOf(mConfirmPassword.getText());

                //Start ProgressBar first (Set visibility VISIBLE)
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[3];
                        field[0] = "user_name";
                        field[1] = "user_email";
                        field[2] = "password";
                        //Creating array for data
                        String[] data = new String[3];
                        data[0] = "data-1";
                        data[1] = "data-2";
                        PutData putData = new PutData("https://projects.vishnusivadas.com/AdvancedHttpURLConnection/putDataTest.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                //End ProgressBar (Set visibility to GONE)
                                Log.i("PutData", result);
                            }
                        }
                        //End Write and Read data with URL
                    }
                });
            }
        });

    }
}