package com.sumit.notificationservice.model;

import com.google.gson.annotations.SerializedName;

public class BatchResponse extends Response{

    @SerializedName(value = "responses")
    Response[] responses;

    /**
     * @return the responses
     */
    public Response[] getResponses() {
        return responses;
    }

    /**
     * @param responses responses to set
     */
    public void setResponses(Response[] responses) {
        this.responses = responses;
    }

}