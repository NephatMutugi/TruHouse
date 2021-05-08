package com.nephat.truhouse.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchAgentHouseResponse {

    @SerializedName("houses")
    List<AgentHouse> agentHouseList;
    String error;

    public FetchAgentHouseResponse(List<AgentHouse> agentHouseList, String error) {
        this.agentHouseList = agentHouseList;
        this.error = error;
    }

    public List<AgentHouse> getAgentHouseList() {
        return agentHouseList;
    }

    public void setAgentHouseList(List<AgentHouse> agentHouseList) {
        this.agentHouseList = agentHouseList;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
