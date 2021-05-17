package com.nephat.truhouse.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchAgentReviews {

    @SerializedName("reviews")
    List<ReviewLists> reviewLists;
    String error;

    public FetchAgentReviews(List<ReviewLists> reviewLists, String error) {
        this.reviewLists = reviewLists;
        this.error = error;
    }

    public List<ReviewLists> getReviewLists() {
        return reviewLists;
    }

    public void setReviewLists(List<ReviewLists> reviewLists) {
        this.reviewLists = reviewLists;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
