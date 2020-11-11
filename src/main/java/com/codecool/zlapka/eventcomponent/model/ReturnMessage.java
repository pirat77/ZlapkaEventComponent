package com.codecool.zlapka.eventcomponent.model;

import java.util.Date;

public class ReturnMessage {

    public final Date timestamp;
    public final int status;
    public final String error;
    public final String message;
    public final String path;

    public ReturnMessage(Date timestamp, int status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}

