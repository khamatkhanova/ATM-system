package com.alinahamatkhanova.controllers;
import com.alinahamatkhanova.dto.AddFriendRequest;
import com.alinahamatkhanova.services.IClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
@PreAuthorize("hasRole('CLIENT')")
public class ClientController {

    private final IClientService clientService;

    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/me")
    public ResponseEntity<String> getClientInfo(Authentication authentication) {
        return ResponseEntity.ok(clientService.getClientInfo(authentication));
    }

    @GetMapping("/accounts")
    public ResponseEntity<String> getClientAccounts(Authentication authentication) {
        return ResponseEntity.ok(clientService.getClientAccounts(authentication));
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<String> getAccountById(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok(clientService.getAccountById(id));
    }

    @PostMapping("/friends")
    public ResponseEntity<String> addFriend(Authentication authentication, @RequestBody AddFriendRequest request) {
        return ResponseEntity.ok(clientService.addFriend(authentication, request));
    }

    @DeleteMapping("/friends/{friendLogin}")
    public ResponseEntity<String> removeFriend(Authentication authentication, @PathVariable(name = "friendLogin") String friendLogin) {
        return ResponseEntity.ok(clientService.removeFriend(authentication, friendLogin));
    }

    @PostMapping("/accounts/{id}/deposit")
    public ResponseEntity<String> deposit(@PathVariable(name = "id") String id, @RequestParam(name = "amount") double amount) {
        return ResponseEntity.ok(clientService.deposit(id, amount));
    }

    @PostMapping("/accounts/{id}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable(name = "id") String id, @RequestParam(name = "amount") double amount) {
        return ResponseEntity.ok(clientService.withdraw(id, amount));
    }

    @PostMapping("/accounts/transfer")
    public ResponseEntity<String> transfer(@RequestParam(name = "fromAccountId") String fromAccountId, @RequestParam(name = "toAccountId") String toAccountId, @RequestParam(name = "amount") double amount) {
        return ResponseEntity.ok(clientService.transfer(fromAccountId, toAccountId, amount));
    }
}