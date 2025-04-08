package com.alinahamatkhanova.infrastructure.models;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "balance", nullable = false)
    private double balance;

    @Column(name = "user_login", nullable = false)
    private String userLogin;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "account_id")
    private List<Transaction> transactionHistory = new ArrayList<>();

    public Account() {}

    public Account(String id, String userLogin) {
        this.id = id;
        this.userLogin = userLogin;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add(new Transaction("Deposit", amount));
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdraw", amount));
            return true;
        }
        return false;
    }

    public boolean transfer(Account toAccount, double amount, double fee) {
        if (this.withdraw(amount + fee)) {
            toAccount.deposit(amount);
            transactionHistory.add(new Transaction("Transfer to " + toAccount.getId(), amount + fee));
            return true;
        }
        return false;
    }
}