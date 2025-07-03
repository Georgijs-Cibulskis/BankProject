import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<bankAccount> accounts = new ArrayList<>();

        while (true) {
            System.out.println("\nChoose an action:");
            System.out.println("1. Create new account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Print account info");
            System.out.println("6. List all accounts");
            System.out.println("Type 'exit' to quit");

            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting program...");
                break;
            }

            switch (input) {
                case "1":
                    System.out.print("Enter owner name: ");
                    String name = scanner.nextLine();
                    try {
                        System.out.print("Enter initial balance: ");
                        double balance = scanner.nextDouble();
                        scanner.nextLine(); // consume newline
                        bankAccount newAccount = new bankAccount(balance, name);
                        accounts.add(newAccount);
                        System.out.println("Created account with ID: " + newAccount.getId());
                    } catch (AmountException e) {
                        System.out.println("Error: " + e.getMessage());
                        scanner.nextLine(); // clear bad input
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input - not a number");
                        scanner.nextLine();
                    }
                    break;

                case "2":
                    bankAccount accDep = findAccount(scanner, accounts);
                    if (accDep != null) {
                        try {
                            System.out.print("Enter deposit amount: ");
                            double dep = scanner.nextDouble();
                            scanner.nextLine();
                            accDep.deposit(dep);
                            System.out.println("Deposit successful.");
                        } catch (AmountException e) {
                            System.out.println("Error: " + e.getMessage());
                            scanner.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input - not a number");
                            scanner.nextLine();
                        }
                    }
                    break;

                case "3":
                    bankAccount accWith = findAccount(scanner, accounts);
                    if (accWith != null) {
                        try {
                            System.out.print("Enter withdrawal amount: ");
                            double with = scanner.nextDouble();
                            scanner.nextLine();
                            accWith.withdraw(with);
                            System.out.println("Withdrawal successful.");
                        } catch (AmountException e) {
                            System.out.println("Error: " + e.getMessage());
                            scanner.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input - not a number");
                            scanner.nextLine();
                        }
                    }
                    break;

                case "4":
                    System.out.println("Sender account:");
                    bankAccount sender = findAccount(scanner, accounts);
                    System.out.println("Receiver account:");
                    bankAccount receiver = findAccount(scanner, accounts);
                    if (sender != null && receiver != null) {
                        try {
                            System.out.print("Enter amount to transfer: ");
                            double amount = scanner.nextDouble();
                            scanner.nextLine();
                            sender.transferMoney(receiver, amount);
                            System.out.println("Transfer successful.");
                        } catch (AmountException e) {
                            System.out.println("Error: " + e.getMessage());
                            scanner.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input - not a number");
                            scanner.nextLine();
                        }
                    }
                    break;

                case "5":
                    bankAccount accPrint = findAccount(scanner, accounts);
                    if (accPrint != null) {
                        accPrint.printBalance();
                    }
                    break;

                case "6":
                    for (bankAccount acc : accounts) {
                        System.out.println(acc);
                    }
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    // Helper method to find account by ID
    private static bankAccount findAccount(Scanner scanner, ArrayList<bankAccount> accounts) {
        try {
            System.out.print("Enter account ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // consume newline
            for (bankAccount acc : accounts) {
                if (acc.getId() == id) {
                    return acc;
                }
            }
            System.out.println("Account with ID " + id + " not found.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input - not a number");
            scanner.nextLine();
        }
        return null;
    }
}
