package com.nephat.truhouse;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RateAgentActivity extends AppCompatActivity {

    private static final String TAG = "RateAgentActivity";
    //Widgets
    private TextView mName, mEmail, mPhone, mLocality, mQualification;

    private String name, email, phone, locality, qualification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_agent);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        phone = intent.getStringExtra("phone");
        locality = intent.getStringExtra("locality");
        qualification = intent.getStringExtra("qualification");

        //Initialize widgets
        mName = findViewById(R.id.textAgentName);
        mEmail = findViewById(R.id.textAgentEmail);
        mPhone = findViewById(R.id.textAgentPhone);
        mLocality = findViewById(R.id.textLocalityAgent);
        mQualification = findViewById(R.id.textAgentQualification);


    }
}