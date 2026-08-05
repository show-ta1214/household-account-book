package com.example.controller;

import com.example.dao.ExpenseDao;
import com.example.dto.ExpenseDto;
import com.example.entity.Expense;
import com.example.form.ExpenseForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ExpenseController {

    private final ExpenseDao expenseDao;

    public ExpenseController(ExpenseDao expenseDao) {
        this.expenseDao = expenseDao;
    }

    @GetMapping("/expenses")
    public String showExpensePage(Model model) {
        model.addAttribute("expenseForm", new ExpenseForm());
        return "expenses";
    }

    @PostMapping("/expenses")
    public String validateExpense(
            @Valid @ModelAttribute("expenseForm") ExpenseForm expenseForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "expenses";
        }

        ExpenseDto expenseDto = new ExpenseDto(
                expenseForm.getExpenseDate(),
                expenseForm.getCategory(),
                expenseForm.getAmount(),
                expenseForm.getMemo());
        expenseDao.save(new Expense(
                expenseForm.getExpenseDate(),
                expenseForm.getCategory(),
                expenseForm.getAmount(),
                expenseForm.getMemo()));
        redirectAttributes.addFlashAttribute("expenseDto", expenseDto);

        return "redirect:/expenses/complete";
    }

    @GetMapping("/expenses/complete")
    public String showExpenseCompletePage() {
        return "expense-complete";
    }
}
