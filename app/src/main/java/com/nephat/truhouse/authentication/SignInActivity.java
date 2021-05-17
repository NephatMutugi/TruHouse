package com.nephat.truhouse.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.nephat.truhouse.MainActivity;
import com.nephat.truhouse.R;
import com.nephat.truhouse.apputil.AppConfig;
import com.nephat.truhouse.fragments.AlertsFragment;
import com.nephat.truhouse.fragments.FeedsFragment;
import com.nephat.truhouse.fragments.MoreFragment;
import com.nephat.truhouse.models.ApiResponse;
import com.nephat.truhouse.resetPassword.ResetPasswordActivity;
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

    private String userEmail;
    private String userName, id;
    String loginEmail, loginPassword;

    FeedsFragment feedsFragment = new FeedsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        hideSoftKeyboard();

        mSignInEmail = findViewById(R.id.sign_in_email);
        mSignInPassword = findViewById(R.id.sign_in_pass);
        mSignInBtn = findViewById(R.id.sign_in_btn);
        mAgentLogin = findViewById(R.id.btnEnterAsAgent);
        mLinkRegister = findViewById(R.id.link_reg_text);
        mForgotPass = findViewById(R.id.forgot_pass_text);

        appConfig = new AppConfig(this);
        if (appConfig.isUserLogin()){
            String name = appConfig.getNameOfUser();
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            intent.putExtra("name", userName);
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

        mForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, ResetPasswordActivity.class);
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

                                userName = response.body().getName();
                                id = response.body().getId();
                                userEmail = response.body().getEmail();

                                String myName, myID, myEmail;
                                myName = userName;
                                myID = id;
                                myEmail = userEmail;
                                Log.d(TAG, "onResponse: " + myName + myID);

                                /*Bundle b2 = new Bundle();
                                b2.putString("uName", myName);
                                b2.putString("uID", myID);
                                FeedsFragment feedsFragment1 = new FeedsFragment();
                                feedsFragment1.setArguments(b2);*/
                                FeedsFragment feedsFragment1 = new FeedsFragment();
                                feedsFragment1.setMyData(myName, myID, myEmail);

                                AlertsFragment alertsFragment = new AlertsFragment();
                                alertsFragment.setData(myID, myName);

                                MoreFragment moreFragment = new MoreFragment();
                                moreFragment.getUserData(myName, myID, myEmail);

                                Log.d(TAG, "onResponse: " +userName+" " + "id:" + id + " email: " +myEmail);


                                if (isRememberUserLogin){
                                    appConfig.updateUserLoginStatus(true);
                                    appConfig.saveNameOfUser(userName);
                                }

                                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                intent.putExtra("name", myName);
                                intent.putExtra("id", myID);
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

    private void hideSoftKeyboard()
    {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
