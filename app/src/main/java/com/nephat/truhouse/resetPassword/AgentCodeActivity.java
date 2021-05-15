package com.nephat.truhouse.resetPassword;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.nephat.truhouse.R;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class AgentCodeActivity extends AppCompatActivity {

    private static final String TAG = "AgentCodeActivity";
    private static final String BASE_URL = "http://192.168.100.2/realEstate/agentVerifyCode.php";


    //Widgets
    private TextInputEditText mEmail, mCode;
    private Button mBtnVer;

    private static String agentEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_code);

        Intent intent = getIntent();
        agentEmail = intent.getStringExtra("user_email");

        mEmail = findViewById(R.id.edit_agent_ver_email);
        mCode = findViewById(R.id.edit_agent_ver_code);
        mBtnVer = findViewById(R.id.btn_ver_agent);

        mEmail.setText(agentEmail);

        mBtnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCode();
            }
        });

    }

    private void verifyCode(){
        final String email, verification_code;
        email = String.valueOf(mEmail.getText());
        verification_code = String.valueOf(mCode.getText());
        Log.d(TAG, "verifyCode: " +verification_code);

        if (!email.isEmpty() && !verification_code.isEmpty()){
            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    String[] field = new String[2];
                    field[0] = "email";
                    field[1] = "verification_code";

                    String[] data = new String[2];
                    data[0] = email;
                    data[1] = verification_code;

                    PutData putData = new PutData(BASE_URL, "POST", field, data);
                    if (putData.startPut()){
                        if (putData.onComplete()){
                            String result = putData.getResult();
                            Log.d(TAG, "run: " + result);
                            if (result.equals("Success")){
                                Intent intent = new Intent(AgentCodeActivity.this, AgentPasswordActivity.class);
                                intent.putExtra("email", email);
                                startActivity(intent);
                            } else {
                                toastMessage("Not Successful");
                            }
                        }
                    } else {
                        toastMessage("Missing details");
                    }
                }
            });
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}