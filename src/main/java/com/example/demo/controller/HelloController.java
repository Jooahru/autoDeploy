package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {
    @Autowired
    Environment env;

    @GetMapping("/status")
    public String status() {
        return "Status - returned by Pod - " + env.getProperty("HOSTNAME");
    }

    @GetMapping("/hello")
    public String helloWorld() {
        return "HelloWorld version1 from" + env.getProperty("HOSTNAME");
    }
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/greeting")
    public String greeting() {
        return "Hello from ServiceA";
    }
}
