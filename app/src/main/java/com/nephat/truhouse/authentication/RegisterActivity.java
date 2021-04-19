package com.nephat.truhouse.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.nephat.truhouse.MainActivity;
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

        hideSoftKeyboard();

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

                final String username, userEmail, userPassword, confirmPassword;
                username = String.valueOf(mUserName.getText());
                userEmail = String.valueOf(mUserEmail.getText());
                userPassword = String.valueOf(mPassword.getText());
                confirmPassword = String.valueOf(mConfirmPassword.getText());

                if (TextUtils.isEmpty(userEmail)){
                    mUserEmail.setError("Email is required");
                    return;
                } if (TextUtils.isEmpty(username)){
                    mUserName.setError("User Name is required");
                    return;
                } if (TextUtils.isEmpty(userPassword)){
                    mPassword.setError("Password is required");
                    return;
                } if (userPassword.length() < 6){
                    mPassword.setError("Password should be 6 or more characters");
                    return;
                } if (!TextUtils.equals(userPassword, confirmPassword)){
                    mConfirmPassword.setError("Passwords do not match");
                } else {
                    //Start ProgressBar first (Set visibility VISIBLE)
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[3];
                            field[0] = "user_email";
                            field[1] = "user_name";
                            field[2] = "password";
                            //Creating array for data
                            String[] data = new String[3];
                            data[0] = userEmail;
                            data[1] = username;
                            data[2] = userPassword;
                            PutData putData = new PutData("http://192.168.100.2/realEstate/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Sign Up Success")){

                                        toastMessage(result);

                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
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
                }

            }
        });

    }

    private void toastMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    private void hideSoftKeyboard()
    {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}