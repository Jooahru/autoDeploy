package com.example.demo.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/log")
public class LogController {
    @Autowired
    Environment env;
    private static final Logger logger = LoggerFactory.getLogger(LogController.class);
    @GetMapping
    public String logRequest(@RequestParam String message) {
        logger.info("API RESPONSE FROM " + env.getProperty("HOSTNAME"));
        return "Logged message: " + message;
    }
}
