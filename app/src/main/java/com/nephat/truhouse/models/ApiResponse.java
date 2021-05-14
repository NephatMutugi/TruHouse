package com.nephat.truhouse.models;

import com.google.gson.annotations.SerializedName;

/**
 * This class handles response from the server*/
public class ApiResponse {

    //Receives status of the request
    @SerializedName("status")
    private String status;

    @SerializedName("result_code")
    private int resultCode;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private String id;

    @SerializedName("reg_no")
    private String reg_no;

    @SerializedName("email")
    private String email;

    @SerializedName("average")
    private String average;

    public String getAverage() {
        return average;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getName() {
        return name;
    }

    public String getId(){
        return id;
    }

    public String getReg_no(){
        return reg_no;
    }
}
