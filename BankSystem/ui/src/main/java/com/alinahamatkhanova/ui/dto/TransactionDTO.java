package com.alinahamatkhanova.ui.dto;
import com.alinahamatkhanova.infrastructure.models.Transaction;
import java.time.LocalDateTime;

public class TransactionDTO {
    private String type;
    private double amount;
    private LocalDateTime date;

    public TransactionDTO(){}
    public TransactionDTO(Transaction transaction) {
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
        this.date = transaction.getDate();
    }

    public String getType() {
        return type;
    }
    public double getAmount() {
        return amount;
    }
    public LocalDateTime getDate() {
        return date;
    }
}