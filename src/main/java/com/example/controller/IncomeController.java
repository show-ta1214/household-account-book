package com.example.controller;

import com.example.dto.IncomeDto;
import com.example.entity.Income;
import com.example.form.IncomeForm;
import com.example.service.IncomeDao;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class IncomeController {

    private static final Logger logger = LoggerFactory.getLogger(IncomeController.class);

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
        try {
            incomeDao.save(new Income(
                    incomeForm.getIncomeDate(),
                    incomeForm.getCategory(),
                    incomeForm.getAmount(),
                    incomeForm.getMemo()));
        } catch (RuntimeException e) {
            logger.error("収入データの登録中にエラーが発生しました。", e);
            return "redirect:/registration-error";
        }
        redirectAttributes.addFlashAttribute("incomeDto", incomeDto);

        return "redirect:/income/complete";
    }

    @GetMapping("/income/complete")
    public String showIncomeCompletePage() {
        return "income-complete";
    }
}
