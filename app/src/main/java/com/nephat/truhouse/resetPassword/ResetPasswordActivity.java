package com.nephat.truhouse.resetPassword;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.nephat.truhouse.R;

public class ResetPasswordActivity extends AppCompatActivity {

    private TextInputEditText mEmail;
    private Button mBtnEmail;

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mEmail = findViewById(R.id.edit_reset_email);
        mBtnEmail = findViewById(R.id.btnVerifyEmail);

        email = String.valueOf(mEmail.getText());

        mBtnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void VerifyEmail(){

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}