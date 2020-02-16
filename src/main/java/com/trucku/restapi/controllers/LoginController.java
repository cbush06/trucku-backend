package com.trucku.restapi.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @Value("${frontendUrl}")
    private String frontendUrl;

    @GetMapping("/login-form")
    public String login() {
        return "login";
    }

    @GetMapping("/success")
    public void success(HttpServletResponse response, HttpSession session) throws IOException {
        String url = frontendUrl + "?JSESSIONID=" + session.getId();
        response.sendRedirect(url);
    }
    
}