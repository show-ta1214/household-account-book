package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "incomes")
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate incomeDate;

    @Column(nullable = false, length = 50)
    private String category;

    @Column(nullable = false)
    private Integer amount;

    @Column(length = 200)
    private String memo;

    protected Income() {
    }

    public Income(LocalDate incomeDate, String category, Integer amount, String memo) {
        this.incomeDate = incomeDate;
        this.category = category;
        this.amount = amount;
        this.memo = memo;
    }

    public Long getId() {
        return id;
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

    public void update(LocalDate incomeDate, String category, Integer amount, String memo) {
        this.incomeDate = incomeDate;
        this.category = category;
        this.amount = amount;
        this.memo = memo;
    }
}
