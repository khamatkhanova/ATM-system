import com.alinahamatkhanova.infrastructure.models.Account;
import com.alinahamatkhanova.bl.services.AccountService;
import com.alinahamatkhanova.infrastructure.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class Tests {
    private AccountService accountService;
    private AccountRepository accountRepository;
    private Account fromAccount;
    private Account toAccount;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        accountService = new AccountService(accountRepository);
        fromAccount = new Account("ACC123", "user1");
        toAccount = new Account("ACC456", "user2");
        fromAccount.deposit(1000.0);
    }

    @Test
    void withdraw_ShouldReduceBalance_WhenEnoughFunds() {
        when(accountRepository.findById("ACC123")).thenReturn(fromAccount);

        boolean result = accountService.withdraw(fromAccount, 200.0);

        assertTrue(result);
        assertEquals(800.0, fromAccount.getBalance());
        verify(accountRepository).save(fromAccount);
    }

    @Test
    void withdraw_ShouldFail_WhenNotEnoughFunds() {
        when(accountRepository.findById("ACC123")).thenReturn(fromAccount);

        boolean result = accountService.withdraw(fromAccount, 2000.0);

        assertFalse(result);
        assertEquals(1000.0, fromAccount.getBalance());
        verify(accountRepository, never()).save(any());
    }

    @Test
    void deposit_ShouldIncreaseBalance() {
        when(accountRepository.findById("ACC123")).thenReturn(fromAccount);

        boolean result = accountService.deposit(fromAccount, 500.0);

        assertTrue(result);
        assertEquals(1500.0, fromAccount.getBalance());
        verify(accountRepository).save(fromAccount);
    }
}