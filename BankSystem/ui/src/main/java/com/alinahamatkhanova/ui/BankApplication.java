package com.alinahamatkhanova.ui;
import com.alinahamatkhanova.infrastructure.repositories.AccountRepository;
import com.alinahamatkhanova.infrastructure.repositories.UserRepository;
import com.alinahamatkhanova.infrastructure.models.Account;
import com.alinahamatkhanova.infrastructure.models.Gender;
import com.alinahamatkhanova.infrastructure.models.HairColor;
import com.alinahamatkhanova.infrastructure.models.User;
import com.alinahamatkhanova.bl.services.AccountService;
import com.alinahamatkhanova.bl.services.UserService;
import java.util.Scanner;

public class BankApplication {

    private static final UserRepository userRepository = new UserRepository();
    private static final AccountRepository accountRepository = new AccountRepository();
    private static final UserService userService = new UserService(userRepository);
    private static final AccountService accountService = new AccountService(accountRepository);

    public static void main(String[] args) {
        System.out.println("Welcome to the Bank Application");

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Enter a command (createUser, createAccount, deposit, withdraw, transfer, addFriend, removeFriend, quit):");
            String command = scanner.nextLine();

            switch (command) {
                case "createUser" -> {
                    System.out.println("Enter login, name, age, gender, hairColor:");
                    createUser(scanner.nextLine(), scanner.nextLine(),
                            Integer.parseInt(scanner.nextLine()),
                            Gender.valueOf(scanner.nextLine().toUpperCase()),
                            HairColor.valueOf(scanner.nextLine().toUpperCase()));
                }
                case "createAccount" -> {
                    System.out.println("Enter login:");
                    createAccount(scanner.nextLine());
                }
                case "deposit" -> {
                    System.out.println("Enter account ID and amount:");
                    deposit(scanner.nextLine(), Double.parseDouble(scanner.nextLine()));
                }
                case "withdraw" -> {
                    System.out.println("Enter account ID and amount:");
                    withdraw(scanner.nextLine(), Double.parseDouble(scanner.nextLine()));
                }
                case "transfer" -> {
                    System.out.println("Enter fromAccount ID, toAccount ID, amount:");
                    transfer(scanner.nextLine(), scanner.nextLine(), Double.parseDouble(scanner.nextLine()));
                }
                case "addFriend" -> {
                    System.out.println("Enter user login:");
                    String login = scanner.nextLine();
                    System.out.println("Enter friend's login, name, age, gender, hairColor:");
                    User friend = new User(scanner.nextLine(), scanner.nextLine(),
                            Integer.parseInt(scanner.nextLine()),
                            Gender.valueOf(scanner.nextLine().toUpperCase()),
                            HairColor.valueOf(scanner.nextLine().toUpperCase()));
                    addFriend(login, friend);
                }
                case "removeFriend" -> {
                    System.out.println("Enter user login:");
                    String login = scanner.nextLine();
                    System.out.println("Enter friend's login, name, age, gender, hairColor:");
                    User friend = new User(scanner.nextLine(), scanner.nextLine(),
                            Integer.parseInt(scanner.nextLine()),
                            Gender.valueOf(scanner.nextLine().toUpperCase()),
                            HairColor.valueOf(scanner.nextLine().toUpperCase()));
                    removeFriend(login, friend);
                }
                case "quit" -> running = false;
                default -> System.out.println("Unknown command");
            }
        }
        scanner.close();
    }

    public static void createUser(String login, String name, int age, Gender gender, HairColor hairColor) {
        if (userService.exists(login)) {
            System.out.println("User with login " + login + " already exists");
            return;
        }
        userService.createUser(login, name, age, gender, hairColor);
        System.out.println("User " + name + " created successfully.");
    }

    public static void createAccount(String login) {
        if (!checkUserExists(login)) return;

        Account account = new Account("ACC" + System.currentTimeMillis(), login);
        accountService.saveAccount(account);
        System.out.println("Account created successfully");
    }

    public static void deposit(String accountId, double amount) {
        Account account = findAccountOrNotify(accountId);
        if (account == null) return;

        if (accountService.deposit(account, amount)) {
            System.out.println("Deposit of " + amount + " completed");
        } else {
            System.out.println("Deposit failed");
        }
    }

    public static void withdraw(String accountId, double amount) {
        Account account = findAccountOrNotify(accountId);
        if (account == null) return;

        if (accountService.withdraw(account, amount)) {
            System.out.println("Withdrawal of " + amount + " completed");
        } else {
            System.out.println("Insufficient balance or invalid amount");
        }
    }

    public static void transfer(String fromAccountId, String toAccountId, double amount) {
        Account fromAccount = findAccountOrNotify(fromAccountId);
        Account toAccount = findAccountOrNotify(toAccountId);
        if (fromAccount == null || toAccount == null) return;

        User sender = userService.getUser(fromAccount.getUserLogin());
        User receiver = userService.getUser(toAccount.getUserLogin());

        if (sender == null || receiver == null) {
            System.out.println("User not found");
            return;
        }

        if (accountService.transfer(fromAccount, toAccount, amount, sender, receiver)) {
            System.out.println("Transfer of " + amount + " completed");
        } else {
            System.out.println("Transfer failed");
        }
    }

    public static void addFriend(String login, User friend) {
        if (!checkUserExists(login)) return;

        userService.addFriend(login, friend);
        System.out.println("Friend added");
    }

    public static void removeFriend(String login, User friend) {
        if (!checkUserExists(login)) return;

        userService.removeFriend(login, friend);
        System.out.println("Friend removed");
    }

    private static boolean checkUserExists(String login) {
        if (userService.getUser(login) == null) {
            System.out.println("User not found");
            return false;
        }
        return true;
    }

    private static Account findAccountOrNotify(String accountId) {
        Account account = accountService.getAccountById(accountId);
        if (account == null) {
            System.out.println("Account not found");
        }
        return account;
    }
}