package com.example.service;

import com.example.entity.Expense;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class JpaExpenseDao implements ExpenseDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Expense save(Expense expense) {
        if (expense.getId() == null) {
            entityManager.persist(expense);
            return expense;
        }
        return entityManager.merge(expense);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Expense> findAll() {
        return entityManager.createQuery("SELECT e FROM Expense e ORDER BY e.id", Expense.class)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Expense> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Expense.class, id));
    }

    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(entityManager::remove);
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM Expense").executeUpdate();
    }
}
