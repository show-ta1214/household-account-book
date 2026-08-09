package com.example.controller;

import com.example.dao.ExpenseDao;
import com.example.dao.IncomeDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecordController {
    private final IncomeDao incomeDao;
    private final ExpenseDao expenseDao;

    public RecordController(IncomeDao incomeDao, ExpenseDao expenseDao) {
        this.incomeDao = incomeDao;
        this.expenseDao = expenseDao;
    }

    @GetMapping("/records")
    public String showRecords(Model model) {
        model.addAttribute("incomes", incomeDao.findAll());
        model.addAttribute("expenses", expenseDao.findAll());
        return "records";
    }
}
