package com.sumit.notificationservice.util;

import com.sumit.notificationservice.model.Request;
import com.sumit.notificationservice.model.Response;

import java.util.UUID;

public class NotificationServiceHelper {

    public static synchronized String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static Response getResponse(Request msg, String result) {
        Response response = new Response();
        response.setUuid(NotificationServiceHelper.generateUUID());
        response.setFrom(msg.getFrom());
        response.setTo(msg.getTo());
        response.setResult(result);
        return response;
    }
}

