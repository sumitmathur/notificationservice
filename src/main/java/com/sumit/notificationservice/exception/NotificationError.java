package com.sumit.notificationservice.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationError {

    private int httpStatus = 0;
    private String error;
    private String errorDescription;
    private boolean retryAble = false;
    public NotificationError() {
    }
}
