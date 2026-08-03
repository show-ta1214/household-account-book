package com.example.controller;

import com.example.config.ApplicationBoot;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}
