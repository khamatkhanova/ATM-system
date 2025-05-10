package com.alinahamatkhanova.services;
import com.alinahamatkhanova.models.AuthUser;
import com.alinahamatkhanova.models.Role;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService implements UserDetailsService {

    private final Map<String, AuthUser> users = new ConcurrentHashMap<>();
    private final PasswordEncoder encoder;

    public AuthService(PasswordEncoder encoder) {
        this.encoder = encoder;
        users.put("admin", new AuthUser("admin", encoder.encode("admin"), Role.ROLE_ADMIN));
        users.put("client", new AuthUser("client", encoder.encode("client"), Role.ROLE_CLIENT));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser user = users.get(username);
        if (user == null) {
            throw new UsernameNotFoundException("user not found: " + username);
        }
        return user;
    }

    public boolean exists(String username) {
        return users.containsKey(username);
    }

    public void addUser(String username, String rawPassword, Role role, PasswordEncoder encoder) {
        users.put(username, new AuthUser(username, encoder.encode(rawPassword), role));
    }
}