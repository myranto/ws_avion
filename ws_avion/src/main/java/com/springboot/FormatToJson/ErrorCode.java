package com.springboot.FormatToJson;

public class ErrorCode {
    String message;
    int status;


    public ErrorCode(String cd, int i) {
        message=cd;
        status=i;
    }
}
