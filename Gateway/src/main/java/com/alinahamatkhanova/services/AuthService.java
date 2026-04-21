package com.alinahamatkhanova.services;
import com.alinahamatkhanova.models.AuthUser;
import com.alinahamatkhanova.models.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService implements IAuthService, UserDetailsService {

    private final Map<String, AuthUser> users = new ConcurrentHashMap<>();

    public AuthService(PasswordEncoder encoder) {
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

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
    }
}