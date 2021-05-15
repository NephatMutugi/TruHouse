package com.nephat.truhouse.resetPassword;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.nephat.truhouse.R;
import com.nephat.truhouse.authentication.SignInActivity;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class PasswordActivity extends AppCompatActivity {

    private static final String TAG = "PasswordActivity";
    private static final String BASE_URL = "http://192.168.100.2/realEstate/changePassword.php";

    //Widgets
    private TextInputEditText mPassword, mConfirmPassword;
    private Button mBtnSavePass;

    private static String mEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        Intent intent = getIntent();
        mEmail = intent.getStringExtra("email");

        mPassword = findViewById(R.id.edit_new_pass);
        mConfirmPassword = findViewById(R.id.edit_new_confirm_pass);
        mBtnSavePass = findViewById(R.id.btnNewPass);

        mBtnSavePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewPass();
            }
        });

    }

    private void saveNewPass(){
        final String email, password, confirm_password;

        email = mEmail;
        password = String.valueOf(mPassword.getText());
        confirm_password = String.valueOf(mConfirmPassword.getText());

        if (TextUtils.isEmpty(password)){
            mPassword.setError("Field cannot be empty");
            return;
        } if (password.length()<4){
            mPassword.setError("Password should be at least 4 characters");
            return;
        } if (!TextUtils.equals(password, confirm_password)){
            mConfirmPassword.setError("Password do not match");
        } else {
            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
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
                            if (result.equals("Update Success")){
                                toastMessage("Updated successfully");

                                Intent intent = new Intent(PasswordActivity.this, SignInActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                toastMessage("Update failed");
                            }
                        }
                    }

                }
            });


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}