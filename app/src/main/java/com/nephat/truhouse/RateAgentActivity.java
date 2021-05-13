package com.nephat.truhouse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RateAgentActivity extends AppCompatActivity {

    private static final String TAG = "RateAgentActivity";
    //Widgets
    private TextView mName, mEmail, mPhone, mLocality, mQualification, mAgentRating, mUserRating;
    private RatingBar mRatingBar;
    private EditText mEditReview;
    private Button mBtnSubmitReview;

    private String id, reg_no, name, email, phone, locality, qualification;
    String temp;
    Float rateValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_agent);
        hideSoftKeyboard();

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        reg_no = intent.getStringExtra("reg_no");
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        phone = intent.getStringExtra("phone");
        locality = intent.getStringExtra("locality");
        qualification = intent.getStringExtra("qualification");

        //Initialize widgets
        mName = findViewById(R.id.textAgentName);
        mEmail = findViewById(R.id.textAgentEmail);
        mPhone = findViewById(R.id.textAgentPhone);
        mLocality = findViewById(R.id.textAgentLocality);
        mQualification = findViewById(R.id.textAgentQualification);
        mRatingBar = findViewById(R.id.ratingBar);
        mEditReview = findViewById(R.id.editReview);
        mBtnSubmitReview = findViewById(R.id.btnSubmitReview);
        mAgentRating = findViewById(R.id.textAgentRating);
        mUserRating = findViewById(R.id.textUserRating);

        mAgentRating.append("4.5");

        mName.append(name);
        mEmail.append(email);
        mPhone.append(phone);
        mLocality.append(locality);
        mQualification.append(qualification);


        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rateValue = ratingBar.getRating();
                temp = rateValue.toString();
                mUserRating.setText(temp);
            }
        });


        mBtnSubmitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    private void hideSoftKeyboard()
    {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}