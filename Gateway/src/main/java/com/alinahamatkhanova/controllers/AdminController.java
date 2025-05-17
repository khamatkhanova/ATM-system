package com.alinahamatkhanova.controllers;
import com.alinahamatkhanova.services.IAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final IAdminService adminService;

    public AdminController(IAdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/create-client")
    public ResponseEntity<Void> createClient(@RequestHeader Map<String, String> headers, @RequestBody Map<String, String> body) {
        adminService.createClient(headers, body);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/create-admin")
    public ResponseEntity<String> createAdmin(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
        adminService.createAdmin(username, password);
        return ResponseEntity.ok("admin created");
    }

    @GetMapping("/users")
    public ResponseEntity<String> getAllUsers(@RequestHeader Map<String, String> headers, @RequestParam(required = false, name = "gender") String gender, @RequestParam(required = false, name = "hairColor") String hairColor) {
        return ResponseEntity.ok(adminService.getAllUsers(headers, gender, hairColor));
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<String> getUserByUsername(@PathVariable(name = "username") String username, @RequestHeader Map<String, String> headers) {
        return ResponseEntity.ok(adminService.getUserByUsername(username, headers));
    }

    @GetMapping("/accounts")
    public ResponseEntity<String> getAllAccounts(@RequestHeader Map<String, String> headers) {
        return ResponseEntity.ok(adminService.getAllAccounts(headers));
    }

    @GetMapping("/accounts/user/{username}")
    public ResponseEntity<String> getAccountsByUser(@PathVariable(name = "username") String username, @RequestHeader Map<String, String> headers) {
        return ResponseEntity.ok(adminService.getAccountsByUser(username, headers));
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<String> getAccountWithTransactions(@PathVariable(name = "id") String id, @RequestHeader Map<String, String> headers) {
        return ResponseEntity.ok(adminService.getAccountWithTransactions(id, headers));
    }
}