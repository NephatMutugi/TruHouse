package com.nephat.truhouse;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class HelpActivity extends AppCompatActivity {

    private static final String TAG = "HelpActivity";

    private ImageView mImageSend;
    private TextInputEditText mUserName, mUserEmail;
    private EditText mMessage;

    private static String uName, uEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        hideSoftKeyboard();

        Intent intent = getIntent();
        uName = intent.getStringExtra("name");
        uEmail = intent.getStringExtra("email");


        mImageSend = findViewById(R.id.imageSend);
        mUserName = findViewById(R.id.help_username);
        mUserName.setText(uName);

        mUserEmail = findViewById(R.id.help_useremail);
        mUserEmail.setText(uEmail);
        mMessage = findViewById(R.id.editMessage);

        mImageSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
                mMessage.setText("");
            }
        });

    }

    private void sendMail(){
        Log.d(TAG, "sendMail: Sending Email");

        String name, email, body;
        name = String.valueOf(mUserName.getText());
        email = String.valueOf(mUserEmail.getText());
        body = String.valueOf(mMessage.getText());

        //Email destination
        String[] sendTo = {"nephproject080@gmail.com"};

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, sendTo);

        intent.putExtra(Intent.EXTRA_SUBJECT, "USER FEEDBACK");
        intent.putExtra(Intent.EXTRA_TEXT, "Personal information : \n Name: " +name + "\n Email: " + email + "\n \n \n" + body);
        startActivity(Intent.createChooser(intent, "Choose application to use"));
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    private void hideSoftKeyboard()
    {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}