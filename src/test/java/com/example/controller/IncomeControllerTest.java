package com.example.controller;

import com.example.config.ApplicationBoot;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = ApplicationBoot.class)
@AutoConfigureMockMvc
class IncomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void incomeDisplaysIncomeEntryPage() throws Exception {
        mockMvc.perform(get("/income"))
                .andExpect(status().isOk())
                .andExpect(view().name("income"))
                .andExpect(content().string(containsString("収入入力")))
                .andExpect(content().string(containsString("href=\"/\"")));
    }

    @Test
    void invalidIncomeDisplaysValidationMessages() throws Exception {
        mockMvc.perform(post("/income")
                        .param("incomeDate", "2099-01-01")
                        .param("category", "")
                        .param("amount", "0")
                        .param("memo", "a".repeat(201)))
                .andExpect(status().isOk())
                .andExpect(view().name("income"))
                .andExpect(content().string(containsString("収入日は今日以前の日付を入力してください。")))
                .andExpect(content().string(containsString("カテゴリーを選択してください。")))
                .andExpect(content().string(containsString("金額は1円以上で入力してください。")))
                .andExpect(content().string(containsString("メモは200文字以内で入力してください。")));
    }

    @Test
    void validIncomeDisplaysSuccessMessage() throws Exception {
        mockMvc.perform(post("/income")
                        .param("incomeDate", "2026-08-04")
                        .param("category", "salary")
                        .param("amount", "250000")
                        .param("memo", "8月分給与"))
                .andExpect(status().isOk())
                .andExpect(view().name("income"))
                .andExpect(content().string(containsString("入力内容に問題はありません。")));
    }
}
