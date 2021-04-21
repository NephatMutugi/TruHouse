package com.nephat.truhouse.authentication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.nephat.truhouse.MainActivity;
import com.nephat.truhouse.R;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "SignInActivity";
    private static final String URL_LOGIN = "http://192.168.100.2/realEstate/login.php";

    private Button mSignInBtn;
    private TextInputEditText mSignInEmail, mSignInPassword;
    private TextView mForgotPass, mLinkRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mSignInBtn = findViewById(R.id.sign_in_btn);
        mLinkRegister = findViewById(R.id.link_reg_text);
        mSignInEmail = findViewById(R.id.sign_in_email);
        mSignInPassword = findViewById(R.id.sign_in_pass);

        mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String loginEmail, loginPassword;
                loginEmail = String.valueOf(mSignInEmail);
                loginPassword = String.valueOf(mSignInPassword);

                if (!loginEmail.isEmpty() || !loginPassword.isEmpty()){

                  //  Login(loginEmail, loginPassword);

                } else {
                    mSignInEmail.setError("Please insert email");
                    mSignInPassword.setError("Please insert password");
                }

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