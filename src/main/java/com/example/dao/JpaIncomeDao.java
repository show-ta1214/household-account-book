package com.example.dao;

import com.example.entity.Income;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class JpaIncomeDao implements IncomeDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Income save(Income income) {
        entityManager.persist(income);
        return income;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Income> findAll() {
        return entityManager.createQuery("SELECT i FROM Income i ORDER BY i.id", Income.class)
                .getResultList();
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM Income").executeUpdate();
    }
}
