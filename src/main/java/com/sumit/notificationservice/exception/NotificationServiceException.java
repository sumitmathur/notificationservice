package com.sumit.notificationservice.exception;

public class NotificationServiceException extends Exception {

    private static final long serialVersionUID = 6842792062562380911L;
    private NotificationError notificationError;

    public NotificationServiceException(NotificationError error) {
        this(error, (Throwable) null);
    }

    public NotificationServiceException(NotificationError error, Throwable cause) {
        super(error.getError(), cause);
        this.notificationError = error;
    }
}
