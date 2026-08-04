package com.example.dto;

import java.time.LocalDate;

public class IncomeDto {

    private final LocalDate incomeDate;
    private final String category;
    private final Integer amount;
    private final String memo;

    public IncomeDto(LocalDate incomeDate, String category, Integer amount, String memo) {
        this.incomeDate = incomeDate;
        this.category = category;
        this.amount = amount;
        this.memo = memo;
    }

    public LocalDate getIncomeDate() {
        return incomeDate;
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
