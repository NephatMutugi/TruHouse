package com.nephat.truhouse.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchAgentListResponse {

    @SerializedName("agents")
    List<AgentList> agentList;
    String error;

    public FetchAgentListResponse(List<AgentList> agentList, String error) {
        this.agentList = agentList;
        this.error = error;
    }

    public List<AgentList> getAgentList() {
        return agentList;
    }

    public void setAgentList(List<AgentList> agentList) {
        this.agentList = agentList;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
