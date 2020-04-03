package com.example.demo;

public class RRException extends Exception {

    public RRException(String msg) {
        super(msg);
    }
    public RRException(String msg,Exception e) {
        super(msg,e);
    }
}
