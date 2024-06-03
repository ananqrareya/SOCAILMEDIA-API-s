package com.social.media.ExceptionHandler;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class ErrorDetails {
    private String status;
    private String message;
    private Date date;

    public ErrorDetails() {

    }

    public ErrorDetails(String status, String message, Date date) {
        this.status = status;
        this.message = message;
        this.date = date;
    }
}
