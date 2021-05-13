package com.nephat.truhouse.models;

public class AgentList {

    private String id;
    private String reg_no;
    private String name;
    private String address;
    private String qualification;
    private String phone;
    private String email;
    private String locality;

    public AgentList() {

    }

    public AgentList(String id, String reg_no, String name, String address, String qualification, String phone, String email, String locality) {
        this.id = id;
        this.reg_no = reg_no;
        this.name = name;
        this.address = address;
        this.qualification = qualification;
        this.phone = phone;
        this.email = email;
        this.locality = locality;
    }

    public String getId() {
        return id;
    }

    public String getReg_no() {
        return reg_no;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getQualification() {
        return qualification;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getLocality() {
        return locality;
    }
}
