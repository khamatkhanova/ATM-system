package com.alinahamatkhanova.services;
import org.springframework.security.core.Authentication;
import java.util.Map;

public interface IClientService {
    String getClientInfo(Authentication authentication, Map<String, String> headers);
    String getClientAccounts(Authentication authentication, Map<String, String> headers);
    String getAccountById(String id, Map<String, String> headers);
    String addFriend(Authentication authentication, String login, String name, int age, String gender, String hairColor);
    String removeFriend(Authentication authentication, String friendLogin, Map<String, String> headers);
    String deposit(String accountId, double amount, Map<String, String> headers);
    String withdraw(String accountId, double amount, Map<String, String> headers);
    String transfer(String fromAccountId, String toAccountId, double amount, Map<String, String> headers);
}