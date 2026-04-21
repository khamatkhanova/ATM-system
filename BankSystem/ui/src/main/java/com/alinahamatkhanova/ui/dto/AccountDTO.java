package com.alinahamatkhanova.ui.dto;
import com.alinahamatkhanova.infrastructure.models.Account;

public class AccountDTO {
    private String id;
    private double balance;
    private String userLogin;

    public AccountDTO(){}
    public AccountDTO(Account account) {
        this.id = account.getId();
        this.balance = account.getBalance();
        this.userLogin = account.getUserLogin();
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
}