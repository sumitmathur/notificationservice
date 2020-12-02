package com.sumit.notificationservice.model;

public enum ChannelType {
    email, slack;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}

