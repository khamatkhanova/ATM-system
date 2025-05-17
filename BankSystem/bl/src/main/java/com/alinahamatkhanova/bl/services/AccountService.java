package com.alinahamatkhanova.bl.services;
import com.alinahamatkhanova.infrastructure.models.Account;
import com.alinahamatkhanova.infrastructure.models.User;
import com.alinahamatkhanova.infrastructure.repositories.AccountRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public boolean deposit(Account account, double amount) {
        boolean result = account.deposit(amount);
        if (result) {
            accountRepository.save(account);
        }
        return result;
    }

    public boolean withdraw(Account account, double amount) {
        boolean result = account.withdraw(amount);
        if (result) {
            accountRepository.save(account);
        }
        return result;
    }

    public boolean transfer(Account fromAccount, Account toAccount, double amount, User sender, User receiver) {
        double fee = calculateFee(sender, receiver, amount);
        boolean result = fromAccount.transfer(toAccount, amount, fee);
        if (result) {
            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);
        }
        return result;
    }

    private double calculateFee(User sender, User receiver, double amount) {
        if (sender.equals(receiver)) return 0;
        return sender.getFriends().contains(receiver) ? amount * 0.03 : amount * 0.10;
    }

    public Account getAccountById(String id) {
        return accountRepository.findById(id).orElse(null);
    }

    public List<Account> getAccountsByUser(String userLogin) {
        return accountRepository.findByUserLogin(userLogin);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public void saveAccount(Account account) {
        accountRepository.save(account);
    }
}