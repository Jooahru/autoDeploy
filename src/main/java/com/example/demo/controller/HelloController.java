package com.example.demo.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

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
        return "HelloWorld version4444 from ======" + env.getProperty("HOSTNAME");
    }
    @Autowired
    private RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @GetMapping("/greeting")
    public String greeting() {
        return "Hello from ServiceA";
    }

    @GetMapping("/forcha")
    public String getDeviceInfo(@RequestHeader(value = "User-Agent") String userAgent, HttpServletRequest request) {
        // 클라이언트 IP 주소
        String clientIpAddress = request.getRemoteAddr();

        // User-Agent 정보 출력
        String deviceInfo = "User-Agent: " + userAgent + "\n";
        deviceInfo += "Client IP Address: " + clientIpAddress;

        // 추가적인 로깅 (로그 파일이나 콘솔에 출력)
        logger.info("Device Info: " + deviceInfo);

        // 필요한 경우, 분석하여 특정 정보를 추출할 수 있습니다.

        return "안녕 여연아";
    }
}
