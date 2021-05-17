package com.nephat.truhouse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nephat.truhouse.models.ApiResponse;
import com.nephat.truhouse.models.FetchAgentReviews;
import com.nephat.truhouse.models.ReviewLists;
import com.nephat.truhouse.recyclerView.ReviewAdapter;
import com.nephat.truhouse.retrofitUtil.ApiClient;
import com.nephat.truhouse.retrofitUtil.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateAgentActivity extends AppCompatActivity {

    private static final String TAG = "RateAgentActivity";
    //Widgets
    private TextView mName, mEmail, mPhone, mLocality, mQualification, mAgentRating, mUserRating;
    private RatingBar mRatingBar;
    private EditText mEditReview;
    private Button mBtnSubmitReview;

    RecyclerView recyclerView;
    List<ReviewLists> reviewLists;
    private ReviewAdapter reviewAdapter;


    private String id, reg_no, name, email, phone, locality, qualification, userId, userName, rating;
    String temp;
    private static String avgRating;
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

        userId = intent.getStringExtra("user_id");
        userName = intent.getStringExtra("user_name");
        avgRating = intent.getStringExtra("rating");

        //getAgentRating();

        Log.d(TAG, "onCreate: " + userId + " " + userName + " " + id + " " + reg_no);

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

        recyclerView = findViewById(R.id.ratingRecycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(RateAgentActivity.this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);

        showReviews();

        if (avgRating != null){
            mAgentRating.append(avgRating);
        } /*else {
            mAgentRating.append(avgRating);
        }*/

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
                rateAgent();
            }
        });
    }

    private void getAgentRating(){

        Call<ApiResponse> call = ApiClient.getApiClient().create(ApiInterface.class).getAvgRating(reg_no);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                if (response.code() == 200){

                    if (response.body().getResultCode() == 1){
                        avgRating = response.body().getAverage();
                        Log.d(TAG, "onResponse: " +avgRating );
                    }
                }

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });
    }


    private void showReviews(){
        //Retrofit
        Call<FetchAgentReviews> call = ApiClient.getApiClient().create(ApiInterface.class).fetchReviews(reg_no);
        call.enqueue(new Callback<FetchAgentReviews>() {
            @Override
            public void onResponse(Call<FetchAgentReviews> call, Response<FetchAgentReviews> response) {
                if (response.isSuccessful()){
                    reviewLists = response.body().getReviewLists();
                    reviewAdapter = new ReviewAdapter(reviewLists, RateAgentActivity.this);
                    recyclerView.setAdapter(reviewAdapter);
                }
            }

            @Override
            public void onFailure(Call<FetchAgentReviews> call, Throwable t) {

            }
        });

    }


    private void rateAgent(){

        String user_id, agent_id, agent_reg, rating, review;
        review = String.valueOf(mEditReview.getText());

        user_id = userId;
        agent_id = id;
        rating = temp;
        agent_reg = reg_no;

        Call<ApiResponse> call = ApiClient.getApiClient().create(ApiInterface.class).performAgentRating(user_id, agent_id, agent_reg, rating, review);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.code() == 200){
                    if (response.body().getStatus().equals("ok")){
                        if (response.body().getResultCode() == 1){
                            toastMessage("User rated successfully");
                        } else {
                            toastMessage("You've already rated this agent");
                        }
                    } else {
                        toastMessage("An error occurred");
                    }
                } else {
                    toastMessage("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });

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