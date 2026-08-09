package com.example.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class IncomeForm {

    @NotNull(message = "収入日を入力してください。")
    @PastOrPresent(message = "収入日は今日以前の日付を入力してください。")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate incomeDate;

    @NotBlank(message = "カテゴリーを選択してください。")
    private String category;

    @NotNull(message = "金額を入力してください。")
    @Positive(message = "金額は1円以上で入力してください。")
    @Max(value = 999999999, message = "金額は999,999,999円以下で入力してください。")
    private Integer amount;

    @Size(max = 200, message = "メモは200文字以内で入力してください。")
    private String memo;

    public LocalDate getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(LocalDate incomeDate) {
        this.incomeDate = incomeDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
