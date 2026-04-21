package com.alinahamatkhanova.ui.controllers;
import com.alinahamatkhanova.bl.services.AccountService;
import com.alinahamatkhanova.infrastructure.models.Account;
import com.alinahamatkhanova.ui.dto.TransactionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/operations")
public class TransactionController {

    private final AccountService accountService;

    public TransactionController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "получить операции по типу и accountId")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "операции найдены"), @ApiResponse(responseCode = "404", description = "аккаунт не найден")})
    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getOperationsByFilter(@RequestParam(name = "accountId") String accountId, @RequestParam(name = "type", required = false) String type) {

        Account account = accountService.getAccountById(accountId);
        if (account == null) return ResponseEntity.notFound().build();

        List<TransactionDTO> operations = account.getTransactionHistory().stream().filter(t -> type == null || t.getType().equalsIgnoreCase(type)).map(TransactionDTO::new).toList();

        return ResponseEntity.ok(operations);
    }
}