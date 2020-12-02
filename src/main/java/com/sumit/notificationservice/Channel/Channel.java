package com.sumit.notificationservice.Channel;

import com.sumit.notificationservice.exception.NotificationServiceException;
import com.sumit.notificationservice.model.Request;

public interface Channel {
    public String notify(Request msg) throws NotificationServiceException;

    public String channelName();
}
