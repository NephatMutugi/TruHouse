package com.nephat.truhouse.models;

public class AvgRating {

    private String agent_reg;
    private String agent_id;
    private String average;

    public AvgRating() {
    }

    public AvgRating(String agent_reg, String agent_id, String average) {
        this.agent_reg = agent_reg;
        this.agent_id = agent_id;
        this.average = average;
    }

    public String getAgent_reg() {
        return agent_reg;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public String getAverage() {
        return average;
    }
}
