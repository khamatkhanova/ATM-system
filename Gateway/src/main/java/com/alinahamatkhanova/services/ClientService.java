package com.alinahamatkhanova.services;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import java.util.Map;

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
    public String getClientInfo(Authentication authentication, Map<String, String> headers) {
        return proxyService.get("/api/users/" + getUsername(authentication), headers).getBody();
    }

    @Override
    public String getClientAccounts(Authentication authentication, Map<String, String> headers) {
        return proxyService.get("/api/accounts/user/" + getUsername(authentication), headers).getBody();
    }

    @Override
    public String getAccountById(String id, Map<String, String> headers) {
        return proxyService.get("/api/accounts/" + id, headers).getBody();
    }

    @Override
    public String addFriend(Authentication authentication, String login, String name, int age, String gender, String hairColor) {
        String jsonBody = String.format("{\"login\":\"%s\",\"name\":\"%s\",\"age\":%d,\"gender\":\"%s\",\"hairColor\":\"%s\"}",
                login, name, age, gender, hairColor);
        Map<String, String> headers = Map.of("Content-Type", "application/json");
        return proxyService.post("/api/users/" + getUsername(authentication) + "/friends", jsonBody, headers).getBody();
    }

    @Override
    public String removeFriend(Authentication authentication, String friendLogin, Map<String, String> headers) {
        return proxyService.delete("/api/users/" + getUsername(authentication) + "/friends/" + friendLogin, headers).getBody();
    }

    @Override
    public String deposit(String accountId, double amount, Map<String, String> headers) {
        return proxyService.postWithoutBody("/api/accounts/" + accountId + "/deposit?amount=" + amount, headers).getBody();
    }

    @Override
    public String withdraw(String accountId, double amount, Map<String, String> headers) {
        return proxyService.postWithoutBody("/api/accounts/" + accountId + "/withdraw?amount=" + amount, headers).getBody();
    }

    @Override
    public String transfer(String fromAccountId, String toAccountId, double amount, Map<String, String> headers) {
        return proxyService.postWithoutBody("/api/accounts/transfer?fromAccountId=" + fromAccountId + "&toAccountId=" + toAccountId + "&amount=" + amount, headers).getBody();
    }
}
