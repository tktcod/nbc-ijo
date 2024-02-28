package com.spring.nbcijo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/auth/login-page")
    public String loginPage() {
        return "index";
    }
}
