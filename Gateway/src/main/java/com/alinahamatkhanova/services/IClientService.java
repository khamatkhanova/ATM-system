package com.alinahamatkhanova.services;
import com.alinahamatkhanova.dto.AddFriendRequest;
import org.springframework.security.core.Authentication;

public interface IClientService {
    String getClientInfo(Authentication authentication);
    String getClientAccounts(Authentication authentication);
    String getAccountById(String id);
    String addFriend(Authentication authentication, AddFriendRequest request);
    String removeFriend(Authentication authentication, String friendLogin);
    String deposit(String accountId, double amount);
    String withdraw(String accountId, double amount);
    String transfer(String fromAccountId, String toAccountId, double amount);
}