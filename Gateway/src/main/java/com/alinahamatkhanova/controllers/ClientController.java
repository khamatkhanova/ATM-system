package com.alinahamatkhanova.controllers;
import com.alinahamatkhanova.services.IClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/client")
@PreAuthorize("hasRole('CLIENT')")
public class ClientController {

    private final IClientService clientService;

    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/me")
    public ResponseEntity<String> getClientInfo(Authentication authentication, @RequestHeader Map<String, String> headers) {
        return ResponseEntity.ok(clientService.getClientInfo(authentication, headers));
    }

    @GetMapping("/accounts")
    public ResponseEntity<String> getClientAccounts(Authentication authentication, @RequestHeader Map<String, String> headers) {
        return ResponseEntity.ok(clientService.getClientAccounts(authentication, headers));
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<String> getAccountById(@PathVariable(name = "id") String id, @RequestHeader Map<String, String> headers) {
        return ResponseEntity.ok(clientService.getAccountById(id, headers));
    }

    @PostMapping("/friends")
    public ResponseEntity<String> addFriend(Authentication authentication, @RequestParam(name = "login") String login, @RequestParam(name = "name") String name, @RequestParam(name = "age") int age, @RequestParam(name = "gender") String gender, @RequestParam(name = "hairColor") String hairColor) {
        return ResponseEntity.ok(clientService.addFriend(authentication, login, name, age, gender, hairColor));
    }

    @DeleteMapping("/friends/{friendLogin}")
    public ResponseEntity<String> removeFriend(Authentication authentication,
                                               @PathVariable(name = "friendLogin") String friendLogin, @RequestHeader Map<String, String> headers) {
        return ResponseEntity.ok(clientService.removeFriend(authentication, friendLogin, headers));
    }

    @PostMapping("/accounts/{id}/deposit")
    public ResponseEntity<String> deposit(@PathVariable(name = "id") String id, @RequestParam(name = "amount") double amount, @RequestHeader Map<String, String> headers) {
        return ResponseEntity.ok(clientService.deposit(id, amount, headers));
    }

    @PostMapping("/accounts/{id}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable(name = "id") String id, @RequestParam(name = "amount") double amount, @RequestHeader Map<String, String> headers) {
        return ResponseEntity.ok(clientService.withdraw(id, amount, headers));
    }

    @PostMapping("/accounts/transfer")
    public ResponseEntity<String> transfer(@RequestParam(name = "fromAccountId") String fromAccountId, @RequestParam(name = "toAccountId") String toAccountId, @RequestParam(name = "amount") double amount, @RequestHeader Map<String, String> headers) {
        return ResponseEntity.ok(clientService.transfer(fromAccountId, toAccountId, amount, headers));
    }
}