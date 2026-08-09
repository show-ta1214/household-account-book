package com.example.service;

import com.example.entity.Expense;

import java.util.List;
import java.util.Optional;

public interface ExpenseDao {

    Expense save(Expense expense);

    List<Expense> findAll();

    Optional<Expense> findById(Long id);

    void deleteById(Long id);

    void deleteAll();
}
