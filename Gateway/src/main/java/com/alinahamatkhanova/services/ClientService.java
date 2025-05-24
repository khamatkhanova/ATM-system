package com.alinahamatkhanova.services;
import com.alinahamatkhanova.dto.AddFriendRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ClientService implements IClientService {

    private final ProxyService proxyService;

    public ClientService(ProxyService proxyService) {
        this.proxyService = proxyService;
    }

    private String getUsername(Authentication authentication) {
        return authentication.getName();
    }

    @Override
    public String getClientInfo(Authentication authentication) {
        return proxyService.getUserInfo(getUsername(authentication));
    }

    @Override
    public String getClientAccounts(Authentication authentication) {
        return proxyService.getUserAccounts(getUsername(authentication));
    }

    @Override
    public String getAccountById(String id) {
        return proxyService.getAccountWithTransactions(id);
    }

    @Override
    public String addFriend(Authentication authentication, AddFriendRequest request) {
        return proxyService.addFriend(getUsername(authentication), request.getLogin(), request.getName(), request.getAge(), request.getGender(), request.getHairColor()
        );
    }

    @Override
    public String removeFriend(Authentication authentication, String friendLogin) {
        return proxyService.removeFriend(getUsername(authentication), friendLogin);
    }

    @Override
    public String deposit(String accountId, double amount) {
        return proxyService.deposit(accountId, amount);
    }

    @Override
    public String withdraw(String accountId, double amount) {
        return proxyService.withdraw(accountId, amount);
    }

    @Override
    public String transfer(String fromAccountId, String toAccountId, double amount) {
        return proxyService.transfer(fromAccountId, toAccountId, amount);
    }
}