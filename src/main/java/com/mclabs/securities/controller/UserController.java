package com.mclabs.securities.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/hello")
    public String helloUser(Map<String, Object> map) {
        map.put("hello", "user");
        return "hello world!!";
    }
}
