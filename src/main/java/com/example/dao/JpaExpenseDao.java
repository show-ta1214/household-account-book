package com.example.dao;

import com.example.entity.Expense;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class JpaExpenseDao implements ExpenseDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Expense save(Expense expense) {
        entityManager.persist(expense);
        return expense;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Expense> findAll() {
        return entityManager.createQuery("SELECT e FROM Expense e ORDER BY e.id", Expense.class)
                .getResultList();
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM Expense").executeUpdate();
    }
}
