package com.trucku.restapi.restcontrollers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CsrfController {

    @GetMapping("/api/csrf")
    public Map<String, String> csrf(HttpSession session) {
        CsrfToken userToken = (CsrfToken) session.getAttribute(HttpSessionCsrfTokenRepository.class.getName() + ".CSRF_TOKEN");
        Map<String, String> csrfMap = new HashMap<>();
        csrfMap.put("header", userToken.getHeaderName());
        csrfMap.put("token", userToken.getToken());
        return Collections.unmodifiableMap(csrfMap);
    }
}