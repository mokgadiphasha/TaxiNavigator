package com.taxiapi.Exception;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ExceptionUtil {


    public <T> Map<String,Object> errorBody(HttpStatus status, T error){
        Map<String,Object> body = new TreeMap<>();

        body.put("Status: ",status);
        body.put("Message: ",error);

        return body;
    }
}
