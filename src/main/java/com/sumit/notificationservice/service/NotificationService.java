package com.sumit.notificationservice.service;

import com.sumit.notificationservice.Channel.ChannelResolver;
import com.sumit.notificationservice.exception.NotificationServiceException;
import com.sumit.notificationservice.model.Request;
import com.sumit.notificationservice.model.Response;
import com.sumit.notificationservice.util.NotificationServiceHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class NotificationService {

    private static final Logger LOG = LogManager.getLogger(NotificationService.class);

    @Autowired
    ChannelResolver resolver;

    public NotificationService(ChannelResolver resolver) {
        this.resolver = resolver;
    }

    @Async("asyncConfiguration")
    public CompletableFuture<ResponseEntity<Response>> notifyAsync(String channelType, Request msg) throws NotificationServiceException {
        String result = resolver.getChannelByName(channelType).notify(msg);
        LOG.info("Message sent = " + msg.toString());
        Response response = NotificationServiceHelper.getResponse(msg, result);
        return CompletableFuture.completedFuture(new ResponseEntity(response, HttpStatus.OK));
    }

}