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
import android.view.WindowManager;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private TextInputEditText mUserName, mUserEmail, mPassword, mConfirmPassword;
    private Button mRegisterBtn;
    private TextView mLinkLogin;
    private static String URL_REGISTER = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUserName = findViewById(R.id.register_username);
        mUserEmail = findViewById(R.id.register_email);
        mPassword = findViewById(R.id.register_pass);
        mConfirmPassword = findViewById(R.id.confirm_pass);

        mRegisterBtn = findViewById(R.id.register_btn);
        mLinkLogin = findViewById(R.id.login_link);

        hideSoftKeyboard();

        mLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Regist();
            }
        });

    }

    private void Regist(){
        final String username, userEmail, userPassword, confirmPassword;
        username = String.valueOf(mUserName.getText());
        userEmail = String.valueOf(mUserEmail.getText());
        userPassword = String.valueOf(mPassword.getText());
        confirmPassword = String.valueOf(mConfirmPassword.getText());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
                                toastMessage("Register success");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            toastMessage("Registration failed" +e.toString());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        toastMessage("Registration failed" +error.toString());

                    }
                })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("name", username);
                params.put("email", userEmail);
                params.put("password", userPassword);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);






        if (TextUtils.isEmpty(userEmail)){
            mUserEmail.setError("Email is required");
            return;
        } if (TextUtils.isEmpty(username)){
            mUserName.setError("User Name is required");
            return;
        } if (TextUtils.isEmpty(userPassword)){
            mPassword.setError("Password is required");
            return;
        } if (userPassword.length() < 6){
            mPassword.setError("Password should be 6 or more characters");
            return;
        } if (!TextUtils.equals(userPassword, confirmPassword)){
            mConfirmPassword.setError("Passwords do not match");
        } else {

            toastMessage("Result");
        }
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


/*           //Start ProgressBar first (Set visibility VISIBLE)
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[3];
                            field[0] = "user_email";
                            field[1] = "user_name";
                            field[2] = "password";
                            //Creating array for data
                            String[] data = new String[3];
                            data[0] = userEmail;
                            data[1] = username;
                            data[2] = userPassword;
                            PutData putData = new PutData("http://192.168.100.2/realEstate/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Sign Up Success")){

                                        toastMessage(result);

                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
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

                */