package com.example.controller;

import com.example.config.ApplicationBoot;
import com.example.service.ExpenseDao;
import com.example.service.IncomeDao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = ApplicationBoot.class)
@AutoConfigureMockMvc
class RegistrationErrorHandlingTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IncomeDao incomeDao;

    @MockitoBean
    private ExpenseDao expenseDao;

    @Test
    void incomeInsertErrorRedirectsToRegistrationErrorPage() throws Exception {
        when(incomeDao.save(any())).thenThrow(new RuntimeException("test income insert error"));

        mockMvc.perform(post("/income")
                        .param("incomeDate", "2026-08-05")
                        .param("category", "salary")
                        .param("amount", "250000")
                        .param("memo", "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/registration-error"));
    }

    @Test
    void expenseInsertErrorRedirectsToRegistrationErrorPage() throws Exception {
        when(expenseDao.save(any())).thenThrow(new RuntimeException("test expense insert error"));

        mockMvc.perform(post("/expenses")
                        .param("expenseDate", "2026-08-05")
                        .param("category", "food")
                        .param("amount", "1200")
                        .param("memo", "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/registration-error"));
    }

    @Test
    void registrationErrorDisplaysErrorPage() throws Exception {
        mockMvc.perform(get("/registration-error"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration-error"))
                .andExpect(content().string(containsString("データを登録できませんでした")))
                .andExpect(content().string(containsString("href=\"/\"")));
    }
}
