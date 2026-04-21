package com.alinahamatkhanova.services;
import com.alinahamatkhanova.utils.ClientUtil;
import org.springframework.stereotype.Service;

@Service
public class ProxyService {

    private final ClientUtil clientUtil;

    public ProxyService(ClientUtil clientUtil) {

        this.clientUtil = clientUtil;
    }

    public String getUsers(String gender, String hairColor) {
        StringBuilder path = new StringBuilder("/api/users");
        if (gender != null || hairColor != null) {
            path.append("?");
            if (gender != null) path.append("gender=").append(gender).append("&");
            if (hairColor != null) path.append("hairColor=").append(hairColor);
        }
        return clientUtil.get(path.toString()).getBody();
    }

    public String getUser(String username) {

        return clientUtil.get("/api/users/" + username).getBody();
    }

    public String getAllAccounts() {

        return clientUtil.get("/api/accounts").getBody();
    }

    public String getUserAccounts(String username) {
        return clientUtil.get("/api/accounts/user/" + username).getBody();
    }

    public String getAccountWithTransactions(String id) {

        return clientUtil.get("/api/accounts/" + id).getBody();
    }

    public void createUser(String jsonBody) {

        clientUtil.post("/api/users", jsonBody);
    }

    public String getUserInfo(String username) {

        return clientUtil.get("/api/users/" + username).getBody();
    }

    public String addFriend(String username, String friendLogin, String name, int age, String gender, String hairColor) {
        String jsonBody = String.format("{\"login\":\"%s\",\"name\":\"%s\",\"age\":%d,\"gender\":\"%s\",\"hairColor\":\"%s\"}", friendLogin, name, age, gender, hairColor);
        return clientUtil.post("/api/users/" + username + "/friends", jsonBody).getBody();
    }

    public String removeFriend(String username, String friendLogin) {
        return clientUtil.delete("/api/users/" + username + "/friends/" + friendLogin).getBody();
    }

    public String deposit(String accountId, double amount) {
        return clientUtil.postWithoutBody("/api/accounts/" + accountId + "/deposit?amount=" + amount).getBody();
    }

    public String withdraw(String accountId, double amount) {
        return clientUtil.postWithoutBody("/api/accounts/" + accountId + "/withdraw?amount=" + amount).getBody();
    }

    public String transfer(String fromAccountId, String toAccountId, double amount) {
        return clientUtil.postWithoutBody("/api/accounts/transfer?fromAccountId=" + fromAccountId + "&toAccountId=" + toAccountId + "&amount=" + amount).getBody();
    }
}