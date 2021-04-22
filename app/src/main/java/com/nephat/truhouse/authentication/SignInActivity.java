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

    private Button mSignInBtn;
    private TextInputEditText mSignInEmail, mSignInPassword;
    private TextView mForgotPass, mLinkRegister;

    private boolean isRememberUserLogin = false;
    private AppConfig appConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mSignInEmail = findViewById(R.id.sign_in_email);
        mSignInPassword = findViewById(R.id.sign_in_pass);
        mSignInBtn = findViewById(R.id.sign_in_btn);
        mLinkRegister = findViewById(R.id.link_reg_text);

        appConfig = new AppConfig(this);
        if (appConfig.isUserLogin()){
            String name = appConfig.getNameOfUser();
        }


        mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
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


        String loginEmail, loginPassword;
        loginEmail = String.valueOf(mSignInEmail.getText());
        loginPassword = String.valueOf(mSignInPassword.getText());

        if (!loginEmail.isEmpty() || !loginPassword.isEmpty()){

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



















/*
    private void Login(final String email, final String password){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d(TAG, "onResponse: " +jsonObject.toString());

                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String name = object.getString("name").trim();
                                    String email = object.getString("email").trim();

                                    toastMessage("Success Login. \nYour Name :"
                                            +name + "Your Email :" +email);

                                }
                            }

                        } catch (JSONException e){
                            e.printStackTrace();
                            toastMessage("Error "+ e.toString());


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        toastMessage("Error "+ error.toString());
                    }
                })

        {
            @Override
            protected Map<String, String> getParams()  throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
              //  params.put("Content-Type", "application/json");
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
*/







/*

                if (!loginEmail.equals("") && !loginPassword.equals("")){

                    //Start ProgressBar first (Set visibility VISIBLE)
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "user_email";
                            field[1] = "password";

                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = loginEmail;
                            data[1] = loginPassword;

                            PutData putData = new PutData("http://192.168.100.2/realEstate/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Login Success")){
                                        toastMessage(result);
                                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {

                                        toastMessage(result);
                                    }

                                    Log.i("PutData", result);
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });

                } else {
                    toastMessage("All fields are required");
                }

 */