package com.example.controller;

import com.example.form.IncomeForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IncomeController {

    @GetMapping("/income")
    public String showIncomePage(Model model) {
        model.addAttribute("incomeForm", new IncomeForm());
        return "income";
    }

    @PostMapping("/income")
    public String validateIncome(
            @Valid @ModelAttribute("incomeForm") IncomeForm incomeForm,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "income";
        }

        return "redirect:/income/complete";
    }

    @GetMapping("/income/complete")
    public String showIncomeCompletePage() {
        return "income-complete";
    }
}
