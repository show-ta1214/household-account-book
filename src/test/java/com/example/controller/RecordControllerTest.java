package com.example.controller;

import com.example.config.ApplicationBoot;
import com.example.dao.ExpenseDao;
import com.example.dao.IncomeDao;
import com.example.entity.Expense;
import com.example.entity.Income;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = ApplicationBoot.class)
@AutoConfigureMockMvc
class RecordControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private IncomeDao incomeDao;
    @Autowired private ExpenseDao expenseDao;

    @BeforeEach
    void clearRecords() {
        incomeDao.deleteAll();
        expenseDao.deleteAll();
    }

    @Test
    void recordsDisplaysEmptyMessages() throws Exception {
        mockMvc.perform(get("/records"))
                .andExpect(status().isOk()).andExpect(view().name("records"))
                .andExpect(model().attribute("incomes", empty()))
                .andExpect(model().attribute("expenses", empty()))
                .andExpect(content().string(containsString("登録された収入はありません。")))
                .andExpect(content().string(containsString("登録された支出はありません。")));
    }

    @Test
    void recordsDisplaysSavedIncomeAndExpense() throws Exception {
        incomeDao.save(new Income(LocalDate.of(2026, 8, 8), "salary", 250000, "8月分給与"));
        expenseDao.save(new Expense(LocalDate.of(2026, 8, 9), "food", 1200, "スーパー"));

        mockMvc.perform(get("/records"))
                .andExpect(status().isOk()).andExpect(view().name("records"))
                .andExpect(content().string(containsString("2026-08-08")))
                .andExpect(content().string(containsString("給与")))
                .andExpect(content().string(containsString("250,000 円")))
                .andExpect(content().string(containsString("8月分給与")))
                .andExpect(content().string(containsString("2026-08-09")))
                .andExpect(content().string(containsString("食費")))
                .andExpect(content().string(containsString("1,200 円")))
                .andExpect(content().string(containsString("スーパー")));
    }
}
