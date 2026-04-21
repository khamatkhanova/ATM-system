package com.alinahamatkhanova.services;
import com.alinahamatkhanova.dto.CreateClientRequest;

public interface IAdminService {
    void createClient(CreateClientRequest request);
    void createAdmin(String username, String password);
    String getAllUsers(String gender, String hairColor);
    String getUserByUsername(String username);
    String getAllAccounts();
    String getAccountsByUser(String username);
    String getAccountWithTransactions(String id);
}