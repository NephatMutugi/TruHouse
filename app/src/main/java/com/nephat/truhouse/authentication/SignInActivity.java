package com.nephat.truhouse.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.nephat.truhouse.MainActivity;
import com.nephat.truhouse.R;

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

        mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

                // FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                // fragmentTransaction.replace(R.id.moreContainer, new MoreFragment()).commit();
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
}