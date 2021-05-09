package com.nephat.truhouse.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.nephat.truhouse.R;
import com.nephat.truhouse.models.ApiResponse;
import com.nephat.truhouse.retrofitUtil.ApiClient;
import com.nephat.truhouse.retrofitUtil.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private TextInputEditText mUserName, mUserEmail, mPassword, mConfirmPassword;
    private Button mRegisterBtn;
    private TextView mLinkLogin;
    final static String URL_REGISTER = "http://192.168.100.2/realEstate/register.php";

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

                hideSoftKeyboard();

               performRegister();
            }
        });

    }


    private void performRegister(){
        String username, userEmail, userPassword, confirmPassword;
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
        } if (userPassword.length() < 4){
            mPassword.setError("Password should be 4 or more characters");
            return;
        } if (!TextUtils.equals(userPassword, confirmPassword)) {
            mConfirmPassword.setError("Passwords do not match");
        } else {

            Call<ApiResponse> call = ApiClient.getApiClient().create(ApiInterface.class).performUserRegister(username, userEmail, userPassword);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.code() == 200){

                        if (response.body().getStatus().equals("ok")){

                            if (response.body().getResultCode() == 1){

                                toastMessage("Registration was successful");
                                onBackPressed();
                                finish();

                            } else {

                                toastMessage("User already exists");

                            }


                        } else {

                            displayUserInfo("Something went wrong...");
                        }


                    } else{

                        displayUserInfo("Something went wrong...");

                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {

                }
            });

        }
    }

    private void displayUserInfo(String message){
      toastMessage(message);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

