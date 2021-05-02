package com.nephat.truhouse.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.nephat.truhouse.R;

public class LoginAsAgentActivity extends AppCompatActivity {

    private static final String TAG = "LoginAsAgentActivity";

    private boolean isRememberAgentLogin = false;

    //Widgets
    private TextInputEditText mRegistrationNo, mPassword;
    private Button mLoginBtn;
    private TextView mLinkLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_as_agent);

        mRegistrationNo = findViewById(R.id.sign_in_reg_no);
        mPassword = findViewById(R.id.sign_in_agent_pass);
        mLoginBtn = findViewById(R.id.sign_in_agent_btn);
        mLinkLogin = findViewById(R.id.link_to_login);

        mLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginAsAgentActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void performAgentLogin(){

    }

    public void checkBoxAgentClicked(View view){

        isRememberAgentLogin = ((CheckBox) view).isChecked();
    }
}