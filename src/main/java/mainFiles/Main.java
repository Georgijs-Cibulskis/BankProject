package mainFiles;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static HashSet<User> users = new HashSet<>();
    private static User currentUser = null;
    private static final String USER_FILE = "StoringFiles" + File.separator + "Users.txt";

    public static void main(String[] args) {
        try {
            loadUsersFromFile();

            while (true) {
                if (currentUser == null) {
                    showLoginMenu();
                } else {
                    showBankMenu();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            saveUsersToFile();
        }
    }

    private static void loadUsersFromFile() {
        File file = new File(USER_FILE);
        if (!file.exists()) {
            users = new HashSet<>();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            users = (HashSet<User>) ois.readObject();
            System.out.println("Loaded users from file.");
        } catch (Exception e) {
            System.out.println("Could not load users: " + e.getMessage());
            users = new HashSet<>();
        }
    }

    private static void saveUsersToFile() {
        try {
            File dir = new File("StoringFiles");
            if (!dir.exists()) {
                if (dir.mkdirs()) {
                    System.out.println("Directory created: " + dir.getAbsolutePath());
                } else {
                    System.out.println("Failed to create directory: " + dir.getAbsolutePath());
                }
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_FILE))) {
                oos.writeObject(users);
                System.out.println("Users saved.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void showLoginMenu() {
        System.out.println("\nWelcome to the Bank App!");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("Type 'exit' to quit");

        String input = scanner.nextLine();

        switch (input) {
            case "1":
                registerUser();
                break;
            case "2":
                loginUser();
                break;
            case "exit":
                System.out.println("Exiting program...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void registerUser() {
        System.out.print("Choose a username: ");
        String username = scanner.nextLine();
        if (findUserByUsername(username) != null) {
            System.out.println("Username already taken.");
            return;
        }
        System.out.print("Choose a password: ");
        String password = scanner.nextLine();
        users.add(new User(username, password));
        System.out.println("Registration successful. You can now log in.");
        saveUsersToFile();
    }

    private static void loginUser() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = findUserByUsername(username);
        if (user != null && user.checkPassword(password)) {
            currentUser = user;
            System.out.println("Login successful. Welcome, " + username + "!");
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private static User findUserByUsername(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) return u;
        }
        return null;
    }

    private static void showBankMenu() {
        System.out.println("\nChoose an action:");
        System.out.println("1. Create new account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. Print account info");
        System.out.println("6. List all accounts");
        System.out.println("7. Logout");
        System.out.println("Type 'exit' to quit");

        System.out.print("Choice: ");
        String input = scanner.nextLine();

        switch (input) {
            case "1": createAccountForCurrentUser(); break;
            case "2": depositToAccount(); break;
            case "3": withdrawFromAccount(); break;
            case "4": transferBetweenAccounts(); break;
            case "5": printAccountInfo(); break;
            case "6": listAllAccounts(); break;
            case "7": logout(); break;
            case "exit":
                System.out.println("Exiting program...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void createAccountForCurrentUser() {
        System.out.print("Enter account name (e.g. 'funding', 'saving'): ");
        String accountName = scanner.nextLine();

        System.out.print("Enter initial balance: ");
        try {
            double balance = scanner.nextDouble();
            scanner.nextLine(); // consume newline

            BankAccount newAccount = new BankAccount(balance, accountName);
            currentUser.addAccount(newAccount);
            System.out.println("Created account '" + accountName + "' with ID: " + newAccount.getId());
            saveUsersToFile();
        } catch (AmountException e) {
            System.out.println("Error: " + e.getMessage());
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input - not a number");
            scanner.nextLine();
        }
    }

    private static BankAccount findAccountInCurrentUser() {
        try {
            System.out.print("Enter account ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // consume newline
            BankAccount acc = currentUser.findAccountById(id);
            if (acc == null) {
                System.out.println("Account with ID " + id + " not found.");
            }
            return acc;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input - not a number");
            scanner.nextLine();
            return null;
        }
    }

    private static void depositToAccount() {
        BankAccount acc = findAccountInCurrentUser();
        if (acc != null) {
            try {
                System.out.print("Enter deposit amount: ");
                double dep = scanner.nextDouble();
                scanner.nextLine();
                acc.deposit(dep);
                System.out.println("Deposit successful.");
                saveUsersToFile();
            } catch (AmountException e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input - not a number");
                scanner.nextLine();
            }
        }
    }

    private static void withdrawFromAccount() {
        BankAccount acc = findAccountInCurrentUser();
        if (acc != null) {
            try {
                System.out.print("Enter withdrawal amount: ");
                double with = scanner.nextDouble();
                scanner.nextLine();
                acc.withdraw(with);
                System.out.println("Withdrawal successful.");
                saveUsersToFile();
            } catch (AmountException e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input - not a number");
                scanner.nextLine();
            }
        }
    }

    private static void transferBetweenAccounts() {
        System.out.print("Sender account - ");
        BankAccount sender = findAccountInCurrentUser();
        if (sender == null) return;

        System.out.print("Receiver account (enter account ID): ");
        int receiverId;
        try {
            receiverId = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input - not a number");
            scanner.nextLine();
            return;
        }

        // Search receiver account among all users:
        BankAccount receiver = null;
        outer:
        for (User u : users) {
            for (BankAccount a : u.getAccounts()) {
                if (a.getId() == receiverId) {
                    receiver = a;
                    break outer;
                }
            }
        }

        if (receiver == null) {
            System.out.println("Receiver account not found.");
            return;
        }

        try {
            System.out.print("Enter amount to transfer: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            sender.transferMoney(receiver, amount);
            System.out.println("Transfer successful.");
            saveUsersToFile();
        } catch (AmountException e) {
            System.out.println("Error: " + e.getMessage());
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input - not a number");
            scanner.nextLine();
        }
    }

    private static void printAccountInfo() {
        BankAccount acc = findAccountInCurrentUser();
        if (acc != null) {
            acc.printBalance();
        }
    }

    private static void listAllAccounts() {
        ArrayList<BankAccount> accs = currentUser.getAccounts();
        if (accs.isEmpty()) {
            System.out.println("No accounts found.");
        } else {
            for (BankAccount acc : accs) {
                System.out.println(acc);
            }
        }
    }

    private static void logout() {
        System.out.println("User " + currentUser.getUsername() + " logged out.");
        saveUsersToFile();
        currentUser = null;
    }
}