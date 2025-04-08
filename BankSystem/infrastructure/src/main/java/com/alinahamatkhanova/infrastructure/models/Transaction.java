package com.alinahamatkhanova.infrastructure.models;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    public Transaction() {}

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.date = LocalDateTime.now();
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