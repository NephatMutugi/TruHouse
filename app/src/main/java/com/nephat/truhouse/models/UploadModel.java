package com.nephat.truhouse.models;

import com.google.gson.annotations.SerializedName;

public class UploadModel {

    @SerializedName("title")
    private String Title;

    @SerializedName("location")
    private String Location;

    @SerializedName("price")
    private String Price;

    @SerializedName("house_type")
    private String House_type;

    @SerializedName("contact")
    private String Contact;

    @SerializedName("description")
    private String Description;

    @SerializedName("image")
    private String Image;

    @SerializedName("response")
    private String Response;

    public String getResponse() {
        return Response;
    }



}
