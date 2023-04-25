package com.example.demo2.excceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message, Integer id) {
        super(message + id);
    }
}