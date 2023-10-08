package com.booklog.global.exception;

import lombok.Data;

@Data
public class BookLogApiResponse {

    private String timestamp;
    private int status;
    private String error;
    private String path;
    private String reason;

    public BookLogApiResponse(String timestamp, int status, String error, String path,
        String reason) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.path = path;
        this.reason = reason;
    }
}
