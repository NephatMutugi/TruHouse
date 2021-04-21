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

    public String getStatus() {
        return status;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getName() {
        return name;
    }
}
