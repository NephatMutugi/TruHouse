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

public class VerifyAgentActivity extends AppCompatActivity {

    private static final String TAG = "VerifyAgentActivity";
    private String regNumber;

    //Widgets
    private TextView textWelcome;
    private TextInputEditText mPhone, mEmail, mLocality, mPassword, mConfirmPass;
    private Button mBtnVerify;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_agent);
        textWelcome = findViewById(R.id.displayRegNo);
        mPhone = findViewById(R.id.agent_phone);
        mEmail = findViewById(R.id.agent_email);
        mLocality = findViewById(R.id.agent_locality);
        mPassword = findViewById(R.id.agent_password);
        mConfirmPass = findViewById(R.id.agent_confirmPass);
        mBtnVerify = findViewById(R.id.btnVerify);

        hideSoftKeyboard();

        Intent intent = getIntent();
        regNumber = intent.getStringExtra("REG_NUMBER");
        textWelcome.setText(regNumber);

        mBtnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performVerification();
            }
        });



    }

    private void performVerification(){
        String reg_no, phone, email, locality, password, confirm_password;

        reg_no = regNumber;
        phone = String.valueOf(mPhone.getText());
        email = String.valueOf(mEmail.getText());
        locality = String.valueOf(mLocality.getText());
        password = String.valueOf(mPassword.getText());
        confirm_password = String.valueOf(mConfirmPass.getText());

        if (TextUtils.isEmpty(phone)){
            mPhone.setError("This field is mandatory");
            return;
        }
        if (TextUtils.isEmpty(email)){
            mEmail.setError("This field is mandatory");
            return;
        }
        if (TextUtils.isEmpty(locality)){
            mLocality.setError("This field is mandatory");
            return;
        }
        if (TextUtils.isEmpty(password)){
            mPassword.setError("Password is required");
            return;
        }
        if (password.length() < 4){
            mPassword.setError("Password should be 4 or more characters");
            return;
        } if (!TextUtils.equals(password, confirm_password)) {
            mConfirmPass.setError("Passwords do not match");
        } else {

            Call<ApiResponse> call = ApiClient.getApiClient().create(ApiInterface.class)
                    .performAgentUpdate(reg_no,phone,email,locality,password);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.code()==200){

                        if (response.body().getStatus().equals("ok")){
                            if (response.body().getResultCode() == 1){
                                toastMessage("Registered successfully");
                                Intent intent = new Intent(VerifyAgentActivity.this,LoginAsAgentActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                    }
                    else {
                        toastMessage("Something went wrong");
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {

                }
            });

        }


    }

    public void onBackPressed() {
        super.onBackPressed();
    }


    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}