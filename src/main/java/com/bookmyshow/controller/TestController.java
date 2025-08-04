package com.bookmyshow.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String home() {
        return "Welcome to BookMyShow!";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello User!";
    }
}
