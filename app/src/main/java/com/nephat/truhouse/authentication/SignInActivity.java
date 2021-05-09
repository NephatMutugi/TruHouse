package com.nephat.truhouse.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.nephat.truhouse.MainActivity;
import com.nephat.truhouse.R;
import com.nephat.truhouse.apputil.AppConfig;
import com.nephat.truhouse.models.ApiResponse;
import com.nephat.truhouse.retrofitUtil.ApiClient;
import com.nephat.truhouse.retrofitUtil.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {


    private static final String TAG = "SignInActivity";
    private static final String URL_LOGIN = "http://192.168.100.2/realEstate/login.php";

    private Button mSignInBtn, mAgentLogin;
    private TextInputEditText mSignInEmail, mSignInPassword;
    private TextView mForgotPass, mLinkRegister;

    private boolean isRememberUserLogin = false;
    private AppConfig appConfig;

    private String userName, userEmail;
    String loginEmail, loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mSignInEmail = findViewById(R.id.sign_in_email);
        mSignInPassword = findViewById(R.id.sign_in_pass);
        mSignInBtn = findViewById(R.id.sign_in_btn);
        mAgentLogin = findViewById(R.id.btnEnterAsAgent);
        mLinkRegister = findViewById(R.id.link_reg_text);

        appConfig = new AppConfig(this);
        if (appConfig.isUserLogin()){
            String name = appConfig.getNameOfUser();
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("email", loginEmail);
            startActivity(intent);
            finish();
        }


        mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                performLogin();

            }
        });

        mAgentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, LoginAsAgentActivity.class);
                startActivity(intent);
                finish();
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

    private void performLogin(){


        loginEmail = String.valueOf(mSignInEmail.getText());
        loginPassword = String.valueOf(mSignInPassword.getText());

        if (!loginEmail.isEmpty() && !loginPassword.isEmpty()){

            Call<ApiResponse> call = ApiClient.getApiClient().create(ApiInterface.class).performUserLogin(loginEmail, loginPassword);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.code() == 200){

                        if (response.body().getStatus().equals("ok")){

                            if (response.body().getResultCode() == 1){

                                String name = response.body().getName();

                                if (isRememberUserLogin){
                                    appConfig.updateUserLoginStatus(true);
                                    appConfig.saveNameOfUser(name);
                                }

                                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();



                            } else {
                                toastMessage("Login Failed");
                            }


                        } else{

                            toastMessage("Something went wrong...");
                        }

                    } else {
                        toastMessage("Something went wrong...");
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {

                }
            });


        } else {
            toastMessage("Fields cannot be empty");
            mSignInEmail.setError("Please insert email");
            mSignInPassword.setError("Please insert password");
        }
    }


    public void checkBoxClicked(View view){

        isRememberUserLogin = ((CheckBox) view).isChecked();
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}
