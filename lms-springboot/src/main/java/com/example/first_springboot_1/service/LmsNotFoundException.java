package com.example.first_springboot_1.service;

public class LmsNotFoundException extends RuntimeException {
    public LmsNotFoundException(String message) { super(message); }
}
