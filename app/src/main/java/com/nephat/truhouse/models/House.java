package com.nephat.truhouse.models;

public class House {

    private String image_path;
    private String location;
    private String house_type;


    public House() {
    }

    public House(String image_path, String location, String house_type) {
        this.image_path = image_path;
        this.location = location;
        this.house_type = house_type;
    }

    public String getImage_path() {
        return image_path;
    }

    public String getLocation() {
        return location;
    }

    public String getHouse_type() {
        return house_type;
    }
}
