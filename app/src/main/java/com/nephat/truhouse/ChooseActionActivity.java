package com.nephat.truhouse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChooseActionActivity extends AppCompatActivity {

    private TextView mToViewHouse, mToUploadHouse, mAgentName;
    String name, id, regNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_action);

        mToViewHouse = findViewById(R.id.textToViewHouse);
        mToUploadHouse = findViewById(R.id.textToUploads);
        mAgentName = findViewById(R.id.textViewName);

        Intent intent = getIntent();
        name = intent.getStringExtra("NAME");
        id = intent.getStringExtra("ID");
        regNo = intent.getStringExtra("REG");
        mAgentName.setText(name);

        mToViewHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActionActivity.this, ViewUploadedHousesActivity.class);
                intent.putExtra("NAME", name);
                intent.putExtra("ID", id);
                intent.putExtra("REG", regNo);
                startActivity(intent);
            }
        });

        mToUploadHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActionActivity.this, UploadHousesActivity.class);
                intent.putExtra("NAME", name);
                intent.putExtra("ID", id);
                intent.putExtra("REG", regNo);
                startActivity(intent);
            }
        });
    }
}