package com.example.controller;

import com.example.form.ExpenseForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ExpenseController {

    @GetMapping("/expenses")
    public String showExpensePage(Model model) {
        model.addAttribute("expenseForm", new ExpenseForm());
        return "expenses";
    }

    @PostMapping("/expenses")
    public String validateExpense(
            @Valid @ModelAttribute("expenseForm") ExpenseForm expenseForm,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "expenses";
        }

        return "redirect:/expenses/complete";
    }

    @GetMapping("/expenses/complete")
    public String showExpenseCompletePage() {
        return "expense-complete";
    }
}
