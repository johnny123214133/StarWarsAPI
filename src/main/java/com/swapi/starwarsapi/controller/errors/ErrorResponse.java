package com.swapi.starwarsapi.controller.errors;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private List<String> messages;

    public ErrorResponse(HttpStatus status) {
        this();
        this.status = status.value();
        this.error = status.getReasonPhrase();
    }

    public ErrorResponse(HttpStatus status, List<String> messages) {
        this();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.messages = messages;
    }

    public ErrorResponse(){
        timestamp = LocalDateTime.now();
    }
    public ErrorResponse(HttpStatus status, LocalDateTime timestamp, List<String> messages) {
        this();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.timestamp = timestamp;
        this.messages = messages;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}