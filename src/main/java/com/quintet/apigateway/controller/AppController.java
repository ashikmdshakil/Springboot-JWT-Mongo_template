package com.quintet.apigateway.controller;

import com.quintet.apigateway.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class AppController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("authenticateUser")
    public HttpServletResponse authenticateUser(@RequestParam("role") String role, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return authenticationService.authenticateUser(request, response);
    }

    @GetMapping("hello")
    public String sayHello(){
        return "Hello";
    }

    @GetMapping("userSaysHello")
    public String userSaysHello(){
        return "Wow...it is working";
    }
}
