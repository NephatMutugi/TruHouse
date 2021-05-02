package com.nephat.truhouse.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.nephat.truhouse.R;

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



    }

    private void performVerification(){
        String reg_no, phone, email, locality, password, confirm_password;

        reg_no = regNumber;
        phone = String.valueOf(mPhone.getText());
        email = String.valueOf(mEmail.getText());
        locality = String.valueOf(mLocality.getText());
        password = String.valueOf(mPassword.getText());
        confirm_password = String.valueOf(mConfirmPass.getText());

    }







    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}