package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IncomeController {

    @GetMapping("/income")
    public String showIncomePage() {
        return "income";
    }
}
