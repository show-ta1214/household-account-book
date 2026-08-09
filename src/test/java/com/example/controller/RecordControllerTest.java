package com.example.controller;

import com.example.config.ApplicationBoot;
import com.example.entity.Expense;
import com.example.entity.Income;
import com.example.service.ExpenseDao;
import com.example.service.IncomeDao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    void incomeCanBeEditedAndDeleted() throws Exception {
        Income income = incomeDao.save(new Income(LocalDate.of(2026, 8, 8), "salary", 250000, "給与"));

        mockMvc.perform(get("/records/incomes/{id}/edit", income.getId()))
                .andExpect(status().isOk()).andExpect(view().name("income"))
                .andExpect(model().attribute("editMode", true))
                .andExpect(content().string(containsString("value=\"2026-08-08\"")))
                .andExpect(content().string(containsString("収入を編集")));

        mockMvc.perform(post("/records/incomes/{id}/edit", income.getId())
                        .param("incomeDate", "2026-08-09").param("category", "bonus")
                        .param("amount", "300000").param("memo", "更新後"))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/records"));

        Income updated = incomeDao.findById(income.getId()).orElseThrow();
        assertThat(updated.getCategory()).isEqualTo("bonus");
        assertThat(updated.getAmount()).isEqualTo(300000);
        assertThat(updated.getMemo()).isEqualTo("更新後");

        mockMvc.perform(post("/records/incomes/{id}/delete", income.getId()))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/records"));
        assertThat(incomeDao.findById(income.getId())).isEmpty();
    }

    @Test
    void expenseCanBeEditedAndDeleted() throws Exception {
        Expense expense = expenseDao.save(new Expense(LocalDate.of(2026, 8, 8), "food", 1200, "食費"));

        mockMvc.perform(get("/records/expenses/{id}/edit", expense.getId()))
                .andExpect(status().isOk()).andExpect(view().name("expenses"))
                .andExpect(model().attribute("editMode", true))
                .andExpect(content().string(containsString("value=\"2026-08-08\"")))
                .andExpect(content().string(containsString("支出を編集")));

        mockMvc.perform(post("/records/expenses/{id}/edit", expense.getId())
                        .param("expenseDate", "2026-08-09").param("category", "daily")
                        .param("amount", "2500").param("memo", "更新後"))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/records"));

        Expense updated = expenseDao.findById(expense.getId()).orElseThrow();
        assertThat(updated.getCategory()).isEqualTo("daily");
        assertThat(updated.getAmount()).isEqualTo(2500);

        mockMvc.perform(post("/records/expenses/{id}/delete", expense.getId()))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/records"));
        assertThat(expenseDao.findById(expense.getId())).isEmpty();
    }

    @Test
    void invalidEditReturnsEditFormWithoutUpdating() throws Exception {
        Income income = incomeDao.save(new Income(LocalDate.of(2026, 8, 8), "salary", 250000, null));

        mockMvc.perform(post("/records/incomes/{id}/edit", income.getId())
                        .param("incomeDate", "2099-01-01").param("category", "")
                        .param("amount", "0").param("memo", ""))
                .andExpect(status().isOk()).andExpect(view().name("income"))
                .andExpect(model().attribute("editMode", true));

        assertThat(incomeDao.findById(income.getId()).orElseThrow().getAmount()).isEqualTo(250000);
    }
}
