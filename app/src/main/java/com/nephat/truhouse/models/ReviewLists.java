package com.nephat.truhouse.models;

public class ReviewLists {

    private String review;
    private String rating_id;
    private String user_id;
    private String agent_reg;
    private String id;
    private String name;

    public ReviewLists() {

    }

    public ReviewLists(String review, String rating_id, String user_id, String agent_reg, String id, String name) {
        this.review = review;
        this.rating_id = rating_id;
        this.user_id = user_id;
        this.agent_reg = agent_reg;
        this.id = id;
        this.name = name;
    }

    public String getReview() {
        return review;
    }

    public String getRating_id() {
        return rating_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getAgent_reg() {
        return agent_reg;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
