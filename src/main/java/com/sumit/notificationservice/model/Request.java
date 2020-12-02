package com.sumit.notificationservice.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Request {
    @SerializedName(value = "channel")
    private String channel;

    @SerializedName(value = "from")
    private String from;

    @SerializedName(value = "to")
    private String to;

    @SerializedName(value = "subject")
    private String subject;

    @SerializedName(value = "body")
    private String body;

    @Override
    public String toString() {
        return "Request{" +
                "channel='" + channel + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
