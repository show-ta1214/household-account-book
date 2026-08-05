package com.example.controller;

import com.example.dao.IncomeDao;
import com.example.dto.IncomeDto;
import com.example.entity.Income;
import com.example.form.IncomeForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class IncomeController {

    private final IncomeDao incomeDao;

    public IncomeController(IncomeDao incomeDao) {
        this.incomeDao = incomeDao;
    }

    @GetMapping("/income")
    public String showIncomePage(Model model) {
        model.addAttribute("incomeForm", new IncomeForm());
        return "income";
    }

    @PostMapping("/income")
    public String validateIncome(
            @Valid @ModelAttribute("incomeForm") IncomeForm incomeForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "income";
        }

        IncomeDto incomeDto = new IncomeDto(
                incomeForm.getIncomeDate(),
                incomeForm.getCategory(),
                incomeForm.getAmount(),
                incomeForm.getMemo());
        incomeDao.save(new Income(
                incomeForm.getIncomeDate(),
                incomeForm.getCategory(),
                incomeForm.getAmount(),
                incomeForm.getMemo()));
        redirectAttributes.addFlashAttribute("incomeDto", incomeDto);

        return "redirect:/income/complete";
    }

    @GetMapping("/income/complete")
    public String showIncomeCompletePage() {
        return "income-complete";
    }
}
