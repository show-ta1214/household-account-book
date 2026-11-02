package com.example.controller;

import com.example.entity.Expense;
import com.example.entity.Income;
import com.example.form.ExpenseForm;
import com.example.form.IncomeForm;
import com.example.service.ExpenseDao;
import com.example.service.IncomeDao;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.YearMonth;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RecordController {
    private final IncomeDao incomeDao;
    private final ExpenseDao expenseDao;

    public RecordController(IncomeDao incomeDao, ExpenseDao expenseDao) {
        this.incomeDao = incomeDao;
        this.expenseDao = expenseDao;
    }

    @GetMapping("/records")
    public String showRecords(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM") YearMonth month,
                              Model model) {
        YearMonth selectedMonth = month == null ? YearMonth.now() : month;
        var incomes = incomeDao.findAll();
        var expenses = expenseDao.findAll();
        long incomeTotal = incomes.stream()
                .filter(income -> YearMonth.from(income.getIncomeDate()).equals(selectedMonth))
                .mapToLong(Income::getAmount).sum();
        long expenseTotal = expenses.stream()
                .filter(expense -> YearMonth.from(expense.getExpenseDate()).equals(selectedMonth))
                .mapToLong(Expense::getAmount).sum();
        long total = incomeTotal + expenseTotal;
        model.addAttribute("incomes", incomes);
        model.addAttribute("expenses", expenses);
        model.addAttribute("selectedMonth", selectedMonth.toString());
        model.addAttribute("incomeTotal", incomeTotal);
        model.addAttribute("expenseTotal", expenseTotal);
        model.addAttribute("hasMonthlyData", total > 0);
        model.addAttribute("incomeAngle", total == 0 ? 0 : incomeTotal * 360.0 / total);
        return "records";
    }

    @GetMapping("/records/incomes/{id}/edit")
    public String showIncomeEdit(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return incomeDao.findById(id).map(income -> {
            IncomeForm form = new IncomeForm();
            form.setIncomeDate(income.getIncomeDate());
            form.setCategory(income.getCategory());
            form.setAmount(income.getAmount());
            form.setMemo(income.getMemo());
            model.addAttribute("incomeForm", form);
            model.addAttribute("recordId", id);
            model.addAttribute("editMode", true);
            return "income";
        }).orElseGet(() -> redirectWithMissingMessage(redirectAttributes));
    }

    @PostMapping("/records/incomes/{id}/edit")
    public String updateIncome(@PathVariable Long id,
                               @Valid @ModelAttribute("incomeForm") IncomeForm form,
                               BindingResult bindingResult, Model model,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("recordId", id);
            model.addAttribute("editMode", true);
            return "income";
        }
        Income income = incomeDao.findById(id).orElse(null);
        if (income == null) return redirectWithMissingMessage(redirectAttributes);
        income.update(form.getIncomeDate(), form.getCategory(), form.getAmount(), form.getMemo());
        incomeDao.save(income);
        redirectAttributes.addFlashAttribute("successMessage", "収入を更新しました。");
        return "redirect:/records";
    }

    @PostMapping("/records/incomes/{id}/delete")
    public String deleteIncome(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (incomeDao.findById(id).isEmpty()) return redirectWithMissingMessage(redirectAttributes);
        incomeDao.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "収入を削除しました。");
        return "redirect:/records";
    }

    @GetMapping("/records/expenses/{id}/edit")
    public String showExpenseEdit(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return expenseDao.findById(id).map(expense -> {
            ExpenseForm form = new ExpenseForm();
            form.setExpenseDate(expense.getExpenseDate());
            form.setCategory(expense.getCategory());
            form.setAmount(expense.getAmount());
            form.setMemo(expense.getMemo());
            model.addAttribute("expenseForm", form);
            model.addAttribute("recordId", id);
            model.addAttribute("editMode", true);
            return "expenses";
        }).orElseGet(() -> redirectWithMissingMessage(redirectAttributes));
    }

    @PostMapping("/records/expenses/{id}/edit")
    public String updateExpense(@PathVariable Long id,
                                @Valid @ModelAttribute("expenseForm") ExpenseForm form,
                                BindingResult bindingResult, Model model,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("recordId", id);
            model.addAttribute("editMode", true);
            return "expenses";
        }
        Expense expense = expenseDao.findById(id).orElse(null);
        if (expense == null) return redirectWithMissingMessage(redirectAttributes);
        expense.update(form.getExpenseDate(), form.getCategory(), form.getAmount(), form.getMemo());
        expenseDao.save(expense);
        redirectAttributes.addFlashAttribute("successMessage", "支出を更新しました。");
        return "redirect:/records";
    }

    @PostMapping("/records/expenses/{id}/delete")
    public String deleteExpense(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (expenseDao.findById(id).isEmpty()) return redirectWithMissingMessage(redirectAttributes);
        expenseDao.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "支出を削除しました。");
        return "redirect:/records";
    }

    private String redirectWithMissingMessage(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", "対象の登録データが見つかりません。");
        return "redirect:/records";
    }
}
