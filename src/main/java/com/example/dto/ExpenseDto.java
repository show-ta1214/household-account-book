package com.example.dto;

import java.time.LocalDate;

public class ExpenseDto {

    private final LocalDate expenseDate;
    private final String category;
    private final Integer amount;
    private final String memo;

    public ExpenseDto(LocalDate expenseDate, String category, Integer amount, String memo) {
        this.expenseDate = expenseDate;
        this.category = category;
        this.amount = amount;
        this.memo = memo;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public String getCategory() {
        return category;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getMemo() {
        return memo;
    }
}
