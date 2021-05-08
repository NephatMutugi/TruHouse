package com.nephat.truhouse.models;

public class House {

    private String id;
    private String title;
    private String image_path;
    private String image_path2;
    private String image_path3;
    private String location;
    private String price;
    private String contact;
    private String description;
    private String house_type;


    public House() {
    }

    public House(String id, String title, String image_path, String image_path2, String image_path3, String location, String price, String contact, String description, String house_type) {
        this.id = id;
        this.title = title;
        this.image_path = image_path;
        this.image_path2 = image_path2;
        this.image_path3 = image_path3;
        this.location = location;
        this.price = price;
        this.contact = contact;
        this.description = description;
        this.house_type = house_type;
    }

    public String getId() {
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getImage_path() {
        return image_path;
    }

    public String getImage_path2() {
        return image_path2;
    }

    public String getImage_path3() {
        return image_path3;
    }

    public String getLocation() {
        return location;
    }

    public String getPrice() {
        return price;
    }

    public String getContact() {
        return contact;
    }

    public String getDescription() {
        return description;
    }


    public String getHouse_type() {
        return house_type;
    }
}
