package com.example.service;

import com.example.entity.Income;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class JpaIncomeDao implements IncomeDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Income save(Income income) {
        if (income.getId() == null) {
            entityManager.persist(income);
            return income;
        }
        return entityManager.merge(income);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Income> findAll() {
        return entityManager.createQuery("SELECT i FROM Income i ORDER BY i.id", Income.class)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Income> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Income.class, id));
    }

    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(entityManager::remove);
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM Income").executeUpdate();
    }
}
