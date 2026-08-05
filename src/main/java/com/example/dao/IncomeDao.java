package com.example.dao;

import com.example.entity.Income;

import java.util.List;

public interface IncomeDao {

    Income save(Income income);

    List<Income> findAll();

    void deleteAll();
}
