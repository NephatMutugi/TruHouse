package com.nephat.truhouse.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.nephat.truhouse.R;
import com.nephat.truhouse.apputil.AppConfig;
import com.nephat.truhouse.authentication.SignInActivity;


public class MoreFragment extends Fragment {

    private static final String TAG = "MoreFragment";

    //Widgets
    private Button btnSignIn;
    private TextView mLogout;

    private AppConfig appConfig;

    public MoreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        btnSignIn = view.findViewById(R.id.btn_sign_in);
        mLogout = view.findViewById(R.id.logout);
        appConfig = new AppConfig(getActivity().getApplicationContext());

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appConfig.updateUserLoginStatus(false);
                Intent intent = new Intent(getActivity(), SignInActivity.class);
                startActivity(intent);
            }
        });


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignInActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}