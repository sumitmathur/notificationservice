package com.sumit.notificationservice.model;

import com.google.gson.annotations.SerializedName;

public class BatchRequest extends Request{

    @SerializedName(value = "requests")
    Request[] requests;

    /**
     * @return the requests
     */
    public Request[] getRequests() {
        return requests;
    }

    /**
     * @param requests requests to set
     */
    public void setRequests(Request[] requests) {
        this.requests = requests;
    }

}
