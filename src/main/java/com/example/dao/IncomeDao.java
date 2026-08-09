package com.example.dao;

import com.example.entity.Income;

import java.util.List;
import java.util.Optional;

public interface IncomeDao {

    Income save(Income income);

    List<Income> findAll();

    Optional<Income> findById(Long id);

    void deleteById(Long id);

    void deleteAll();
}
