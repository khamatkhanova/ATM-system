package com.alinahamatkhanova.services;
import com.alinahamatkhanova.models.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class AdminService implements IAdminService {

    private final AuthService authService;
    private final ProxyService proxyService;
    private final PasswordEncoder encoder;

    public AdminService(AuthService authService, ProxyService proxyService, PasswordEncoder encoder) {
        this.authService = authService;
        this.proxyService = proxyService;
        this.encoder = encoder;
    }

    @Override
    public void createClient(Map<String, String> headers, Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        String name = body.get("name");
        String gender = body.get("gender");
        String hairColor = body.get("hairColor");
        String ageStr = body.get("age");

        if (username == null || password == null || name == null || gender == null || hairColor == null || ageStr == null || authService.exists(username)) {
            throw new IllegalArgumentException("invalid client data or user already exists");
        }

        int age = Integer.parseInt(ageStr);
        authService.addUser(username, password, Role.ROLE_CLIENT, encoder);

        String jsonBody = String.format("{\"login\":\"%s\",\"name\":\"%s\",\"age\":%d,\"gender\":\"%s\",\"hairColor\":\"%s\"}", username, name, age, gender, hairColor);
        proxyService.post("/api/users", jsonBody, Map.of("Content-Type", "application/json"));
    }

    @Override
    public void createAdmin(String username, String password) {
        if (authService.exists(username)) {
            throw new IllegalArgumentException("Admin already exists");
        }
        authService.addUser(username, password, Role.ROLE_ADMIN, encoder);
    }

    @Override
    public String getAllUsers(Map<String, String> headers, String gender, String hairColor) {
        StringBuilder path = new StringBuilder("/api/users");
        if (gender != null || hairColor != null) { path.append("?"); if (gender != null) path.append("gender=").append(gender).append("&"); if (hairColor != null) path.append("hairColor=").append(hairColor);}
        return proxyService.get(path.toString(), headers).getBody();
    }

    @Override
    public String getUserByUsername(String username, Map<String, String> headers) {
        return proxyService.get("/api/users/" + username, headers).getBody();
    }

    @Override
    public String getAllAccounts(Map<String, String> headers) {
        return proxyService.get("/api/accounts", headers).getBody();
    }

    @Override
    public String getAccountsByUser(String username, Map<String, String> headers) {
        return proxyService.get("/api/accounts/user/" + username, headers).getBody();
    }

    @Override
    public String getAccountWithTransactions(String id, Map<String, String> headers) {
        return proxyService.get("/api/accounts/" + id, headers).getBody();
    }
}