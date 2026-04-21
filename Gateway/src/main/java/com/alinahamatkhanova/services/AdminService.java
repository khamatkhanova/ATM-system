package com.alinahamatkhanova.services;
import com.alinahamatkhanova.dto.CreateClientRequest;
import com.alinahamatkhanova.models.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public void createClient(CreateClientRequest request) {
        if (authService.exists(request.getUsername())) {
            throw new IllegalArgumentException("invalid client data or user already exists");
        }

        authService.addUser(request.getUsername(), request.getPassword(), Role.ROLE_CLIENT, encoder);

        String jsonBody = String.format("{\"login\":\"%s\",\"name\":\"%s\",\"age\":%d,\"gender\":\"%s\",\"hairColor\":\"%s\"}", request.getUsername(), request.getName(), request.getAge(), request.getGender(), request.getHairColor());
        proxyService.createUser(jsonBody);
    }

    @Override
    public void createAdmin(String username, String password) {
        if (authService.exists(username)) {
            throw new IllegalArgumentException("admin already exists");
        }
        authService.addUser(username, password, Role.ROLE_ADMIN, encoder);
    }

    @Override
    public String getAllUsers(String gender, String hairColor) {
        return proxyService.getUsers(gender, hairColor);
    }

    @Override
    public String getUserByUsername(String username) {
        return proxyService.getUser(username);
    }

    @Override
    public String getAllAccounts() {
        return proxyService.getAllAccounts();
    }

    @Override
    public String getAccountsByUser(String username) {
        return proxyService.getUserAccounts(username);
    }

    @Override
    public String getAccountWithTransactions(String id) {
        return proxyService.getAccountWithTransactions(id);
    }
}