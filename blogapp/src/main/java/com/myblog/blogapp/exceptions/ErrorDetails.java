package com.myblog.blogapp.exceptions;

import java.util.Date;

public class ErrorDetails {

    private Date timestamp;
    private String message;

    private String details;

    public ErrorDetails(Date timestamp,String message,String details)
    {
        this.details=details;
        this.message=message;
        this.timestamp=timestamp;
    }

    public Date getTimestamp() {
        return timestamp;
    }
    public String getMessage() {
        return message;
    }
    public String getDetails() {
        return details;
    }

}
