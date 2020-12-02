package com.sumit.notificationservice.Channel;

import com.sumit.notificationservice.exception.NotificationError;
import com.sumit.notificationservice.exception.NotificationServiceException;
import com.sumit.notificationservice.model.ChannelType;
import com.sumit.notificationservice.model.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.sumit.notificationservice.constants.NotificationServiceConstants.*;

@Component
public class EmailChannel implements Channel {
    private static final Logger LOG = LogManager.getLogger(EmailChannel.class);

    @Override
    public String channelName() {
        return ChannelType.email.toString();
    }

    @Override
    public String notify(Request msg) throws NotificationServiceException {
        try {
            // Email implementation using springboot starter. https://www.tutorialspoint.com/spring_boot/spring_boot_sending_email.htm
            LOG.info("Email sent to={}, from{}, subject={}, body={}", msg.getTo(), msg.getFrom(), msg.getSubject(), msg.getBody());
            return "success";
        } catch (Exception ex) {
            LOG.error(ex);
            ex.printStackTrace();
            if (ex instanceof NotificationServiceException) {
                throw (NotificationServiceException) ex;
            } else {
                NotificationError notificationError = new NotificationError();
                notificationError.setErrorDescription(ex.getMessage());
                notificationError.setRetryAble(true);
              if (ex instanceof IOException) {
                    notificationError.setError(IO_EXCEPTION);
                } else {
                    notificationError.setError(REQUEST_FAILED);
                }
                throw new NotificationServiceException(notificationError, ex);
            }
        } finally {
            LOG.info("Closing connections");
        }
    }
}
