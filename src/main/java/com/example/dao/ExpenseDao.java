package com.example.dao;

import com.example.entity.Expense;

import java.util.List;

public interface ExpenseDao {

    Expense save(Expense expense);

    List<Expense> findAll();

    void deleteAll();
}
