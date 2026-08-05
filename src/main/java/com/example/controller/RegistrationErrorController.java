package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistrationErrorController {

    @GetMapping("/registration-error")
    public String showRegistrationErrorPage() {
        return "registration-error";
    }
}
