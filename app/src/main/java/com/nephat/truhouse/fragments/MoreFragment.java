package com.nephat.truhouse.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.nephat.truhouse.HelpActivity;
import com.nephat.truhouse.R;
import com.nephat.truhouse.apputil.AppConfig;
import com.nephat.truhouse.authentication.AuthAgentsActivity;
import com.nephat.truhouse.authentication.SignInActivity;


public class MoreFragment extends Fragment {

    private static final String TAG = "MoreFragment";

    //Widgets
    private Button btnSignIn;
    private TextView mLogout, mUserName;
    private LinearLayout sendToHelp, authAgents, mLogOut;

    private AppConfig appConfig;

    private static  String name, email, id;

    public MoreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);
       // btnSignIn = view.findViewById(R.id.btn_sign_in);
        mLogout = view.findViewById(R.id.logout);
        sendToHelp = view.findViewById(R.id.linearLayoutPreQualified);
        authAgents = view.findViewById(R.id.linearLayoutMortgageCalc);
        mLogOut = view.findViewById(R.id.linearLayoutHelp);
        mUserName = view.findViewById(R.id.textUserName);
        mUserName.setText(name);

        appConfig = new AppConfig(getActivity().getApplicationContext());

        sendToHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        authAgents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AuthAgentsActivity.class);
                startActivity(intent);
            }
        });


        mLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appConfig.updateUserLoginStatus(false);
                Intent intent = new Intent(getActivity(), SignInActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void getUserData(String name, String id, String email){
        MoreFragment.name = name;
        MoreFragment.id = id;
        MoreFragment.email = email;
        Log.d(TAG, "setMyData: " +name + " " + id + email);

    }
}