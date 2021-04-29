package com.nephat.truhouse.models;

public class House {

    private String imageUrl;
    private String houseType;
    private String houseLocation;

    public House() {
    }

    public House(String imageUrl, String houseType, String houseLocation) {
        this.imageUrl = imageUrl;
        this.houseType = houseType;
        this.houseLocation = houseLocation;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getHouseLocation() {
        return houseLocation;
    }

    public void setHouseLocation(String houseLocation) {
        this.houseLocation = houseLocation;
    }
}
