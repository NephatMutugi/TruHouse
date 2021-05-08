package com.nephat.truhouse.models;

public class AgentHouse {

    private String image_path;
    private String title;
    private String location;
    private String house_type;

    public AgentHouse() {

    }

    public AgentHouse(String image_path, String title, String location, String house_type) {
        this.image_path = image_path;
        this.title = title;
        this.location = location;
        this.house_type = house_type;
    }

    public String getImage_path() {
        return image_path;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getHouse_type() {
        return house_type;
    }
}
