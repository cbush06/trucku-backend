package com.trucku.restapi.restcontrollers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/api/user")
    public Authentication user(Authentication auth) {
        return auth;
    }
}