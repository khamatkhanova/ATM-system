package com.alinahamatkhanova.services;
import java.util.Map;

public interface IAdminService {
    void createClient(Map<String, String> headers, Map<String, String> body);
    void createAdmin(String username, String password);
    String getAllUsers(Map<String, String> headers, String gender, String hairColor);
    String getUserByUsername(String username, Map<String, String> headers);
    String getAllAccounts(Map<String, String> headers);
    String getAccountsByUser(String username, Map<String, String> headers);
    String getAccountWithTransactions(String id, Map<String, String> headers);
}