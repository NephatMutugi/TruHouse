 package com.nephat.truhouse.resetPassword;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.nephat.truhouse.R;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class AgentEnterEmailActivity extends AppCompatActivity {

    private static final String TAG = "AgentEnterEmailActivity";
    private static final String BASE_URL = "http://192.168.100.2/realEstate/agentResetPass.php";

    //Widgets
    private TextInputEditText mAgentEmail;
    private Button mAgentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_enter_email);

        mAgentEmail = findViewById(R.id.edit_agent_reset_email);
        mAgentBtn = findViewById(R.id.btnAgentVerifyEmail);

        mAgentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyAgentEmail();
            }
        });
    }

    private void verifyAgentEmail() {

        final String email;
        email = String.valueOf(mAgentEmail.getText());

        if (!email.isEmpty()){
            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    String[] field = new String[1];
                    field[0] = "email";

                    String[] data = new String[1];
                    data[0] = email;

                    PutData putData = new PutData(BASE_URL, "POST", field, data);
                    if (putData.startPut()){
                        if (putData.onComplete()){
                            String result = putData.getResult();

                            toastMessage("Verification code sent to email");
                            Intent intent = new Intent(AgentEnterEmailActivity.this, AgentCodeActivity.class);
                            intent.putExtra("user_email", email);
                            startActivity(intent);
                        } else {
                            toastMessage("Failed");
                        }
                    }
                }
            });

        } else {
            mAgentEmail.setError("Field cannot be empty");
            toastMessage("Email field cannot be empty");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}