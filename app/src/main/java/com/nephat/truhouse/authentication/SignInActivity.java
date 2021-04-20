package com.nephat.truhouse.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.nephat.truhouse.MainActivity;
import com.nephat.truhouse.R;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "SignInActivity";

    private Button mSignInBtn;
    private TextInputEditText mSignInEmail, mSignInPassword;
    private TextView mForgotPass, mLinkRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mSignInBtn = findViewById(R.id.sign_in_btn);
        mLinkRegister = findViewById(R.id.link_reg_text);
        mSignInEmail = findViewById(R.id.sign_in_email);
        mSignInPassword = findViewById(R.id.sign_in_pass);

        mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String loginEmail, loginPassword;
                loginEmail = String.valueOf(mSignInEmail);
                loginPassword = String.valueOf(mSignInPassword);

                if (!loginEmail.equals("") && !loginPassword.equals("")){

                    //Start ProgressBar first (Set visibility VISIBLE)
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "user_email";
                            field[1] = "password";

                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = loginEmail;
                            data[1] = loginPassword;

                            PutData putData = new PutData("http://192.168.100.2/realEstate/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Login Success")){
                                        toastMessage(result);
                                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {

                                        toastMessage(result);
                                    }

                                    Log.i("PutData", result);
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });

                } else {
                    toastMessage("All fields are required");
                }

            }
        });

        mLinkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        }

    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}