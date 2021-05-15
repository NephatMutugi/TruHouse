package com.nephat.truhouse.resetPassword;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.nephat.truhouse.R;
import com.nephat.truhouse.authentication.LoginAsAgentActivity;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class AgentPasswordActivity extends AppCompatActivity {

    private static final String TAG = "AgentPasswordActivity";
    private static final String BASE_URL = "http://192.168.100.2/realEstate/changeAgentPass.php";

    //Widgets
    private TextInputEditText mPassword, mConfirmPass;

    private static String agentEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_password);

        Intent intent = getIntent();
        agentEmail = intent.getStringExtra("email");

        mPassword = findViewById(R.id.edit_agent_new_pass);
        mConfirmPass = findViewById(R.id.edit_agent_new_confirm_pass);
        Button mBtnNewPass = findViewById(R.id.btnAgentNewPass);

        mBtnNewPass.setOnClickListener(v -> changePass());

    }

    private void changePass(){
        final String email, password, confirm_password;
        email = agentEmail;
        password = String.valueOf(mPassword.getText());
        confirm_password = String.valueOf(mConfirmPass.getText());

        if (TextUtils.isEmpty(password)){
            mPassword.setError("Field cannot be empty");
            return;
        } if (password.length()<4){
            mPassword.setError("Password should be at least 4 characters");
            return;
        } if (!TextUtils.equals(password, confirm_password)){
            mConfirmPass.setError("Password do not match");
        } else {
            Handler handler = new Handler();
            handler.post(() -> {
                String[] field = new String[2];
                field[0] = "email";
                field[1] = "password";

                String[] data = new String[2];
                data[0] = email;
                data[1] = password;

                PutData putData = new PutData(BASE_URL, "POST", field, data);
                if (putData.startPut()){
                    if (putData.onComplete()){
                        String result = putData.getResult();

                        Log.d(TAG, "changePass: " +result);
                        if (result.equals("Update Success")){
                            toastMessage("Updated successfully");

                            Intent intent = new Intent(AgentPasswordActivity.this, LoginAsAgentActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            toastMessage("Update failed");
                        }
                    }
                }

            });


        }

    }

    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}