import java.util.Scanner;

public class Main {
    public static void main(String args[]) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter 1st bank account balance: ");
        double balance = scanner.nextDouble();
        bankAccount bankAccount1 = new bankAccount(balance);

        System.out.println("Enter 2nd bank account balance: ");
        balance = scanner.nextDouble();
        bankAccount bankAccount2 = new bankAccount(balance);

        System.out.println("Enter deposit for the 1st account: ");
        double deposOrWith = scanner.nextDouble();
        bankAccount1.deposit(deposOrWith);
        bankAccount1.printBalance();

        System.out.println("Enter withdrawal for the 1st account: ");
        deposOrWith = scanner.nextDouble();
        bankAccount1.withdraw(deposOrWith);
        bankAccount1.printBalance();

        System.out.println("Enter transfer amount from the 1st account to the 2nd one: ");
        double transfer = scanner.nextDouble();
        bankAccount1.transferMoney(bankAccount2, transfer);
        bankAccount1.printBalance();
        bankAccount2.printBalance();

    }
}
