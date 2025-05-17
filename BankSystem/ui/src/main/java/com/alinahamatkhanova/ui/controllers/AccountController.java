package com.alinahamatkhanova.ui.controllers;
import com.alinahamatkhanova.bl.services.AccountService;
import com.alinahamatkhanova.bl.services.UserService;
import com.alinahamatkhanova.infrastructure.models.Account;
import com.alinahamatkhanova.infrastructure.models.User;
import com.alinahamatkhanova.ui.dto.AccountDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private final UserService userService;

    public AccountController(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @Operation(summary = "cоздать аккаунт для пользователя")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "аккаунт создан"), @ApiResponse(responseCode = "404", description = "пользователь не найден")})
    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestParam(name = "userLogin") String userLogin) {
        if (!userService.exists(userLogin)) {
            return ResponseEntity.notFound().build();
        }
        Account account = new Account("ACC" + System.currentTimeMillis(), userLogin);
        accountService.saveAccount(account);
        return ResponseEntity.status(201).body(new AccountDTO(account));
    }

    @Operation(summary = "пополнить счёт")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "счёт пополнен"), @ApiResponse(responseCode = "404", description = "аккаунт не найден")})
    @PostMapping("/{id}/deposit")
    public ResponseEntity<Void> deposit(@PathVariable(name = "id") String id, @RequestParam(name = "amount") double amount) {
        Account account = accountService.getAccountById(id);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }
        accountService.deposit(account, amount);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "снять деньги со счёта")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "снятие успешно"), @ApiResponse(responseCode = "404", description = "аккаунт не найден")})
    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Void> withdraw(@PathVariable(name = "id") String id, @RequestParam(name = "amount") double amount) {
        Account account = accountService.getAccountById(id);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }
        accountService.withdraw(account, amount);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "перевести деньги с одного счёта на другой")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "перевод выполнен"), @ApiResponse(responseCode = "404", description = "один из аккаунтов не найден")})
    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@RequestParam(name = "fromAccountId") String fromAccountId, @RequestParam(name = "toAccountId") String toAccountId, @RequestParam(name = "amount") double amount) {

        Account from = accountService.getAccountById(fromAccountId);
        Account to = accountService.getAccountById(toAccountId);

        if (from == null || to == null) {
            return ResponseEntity.notFound().build();
        }

        User sender = userService.getUser(from.getUserLogin());
        User receiver = userService.getUser(to.getUserLogin());
        accountService.transfer(from, to, amount, sender, receiver);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "получить аккаунт по ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "аккаунт найден"), @ApiResponse(responseCode = "404", description = "аккаунт не найден")})
    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable(name = "id") String id) {
        Account account = accountService.getAccountById(id);
        return account != null ? ResponseEntity.ok(new AccountDTO(account)) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "получить все аккаунты пользователя")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "аккаунты найдены"), @ApiResponse(responseCode = "404", description = "пользователь не найден")})
    @GetMapping("/user/{userLogin}")
    public ResponseEntity<List<AccountDTO>> getAccountsByUser(@PathVariable(name = "userLogin") String userLogin) {
        if (!userService.exists(userLogin)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(accountService.getAccountsByUser(userLogin).stream().map(AccountDTO::new).toList());
    }

    @Operation(summary = "получить все аккаунты")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "список аккаунтов возвращён"), @ApiResponse(responseCode = "404", description = "аккаунты не найдены")})
    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<AccountDTO> accounts = accountService.getAllAccounts().stream().map(AccountDTO::new).toList();
        if (accounts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(accounts);
    }
}