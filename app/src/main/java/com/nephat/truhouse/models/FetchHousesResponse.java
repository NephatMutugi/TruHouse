package com.nephat.truhouse.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchHousesResponse {

    @SerializedName("houses")
    List<House> houseList;
    String error;

    public FetchHousesResponse(List<House> houseList, String error) {
        this.houseList = houseList;
        this.error = error;
    }

    public List<House> getHouseList() {
        return houseList;
    }

    public void setHouseList(List<House> houseList) {
        this.houseList = houseList;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}