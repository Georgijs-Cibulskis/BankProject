package mainFiles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BankAccountTest {
    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount(100.0, "Savings");
    }

    @Test
    void testAccountCreation() {
        assertEquals("Savings", account.getName());
        assertEquals(100.0, account.getBalance(), 0.001);
        assertTrue(account.getId() > 0);
    }

    @Test
    void testDepositPositiveAmount() {
        account.deposit(50.0);
        assertEquals(150.0, account.getBalance(), 0.001);
    }

    @Test
    void testDepositNegativeAmount() {
        AmountException exception = assertThrows(AmountException.class, () -> account.deposit(-10.0));
        assertEquals("Amount cannot be negative: -10.0", exception.getMessage());
        assertEquals(100.0, account.getBalance(), 0.001); // Balance unchanged
    }

    @Test
    void testWithdrawValidAmount() {
        account.withdraw(30.0);
        assertEquals(70.0, account.getBalance(), 0.001);
    }

    @Test
    void testWithdrawNegativeAmount() {
        AmountException exception = assertThrows(AmountException.class, () -> account.withdraw(-10.0));
        assertEquals("Amount cannot be negative or insufficient balance", exception.getMessage());
        assertEquals(100.0, account.getBalance(), 0.001);
    }

    @Test
    void testWithdrawInsufficientBalance() {
        AmountException exception = assertThrows(AmountException.class, () -> account.withdraw(150.0));
        assertEquals("Amount cannot be negative or insufficient balance", exception.getMessage());
        assertEquals(100.0, account.getBalance(), 0.001);
    }

    @Test
    void testTransferMoney() {
        BankAccount receiver = new BankAccount(50.0, "Checking");
        account.transferMoney(receiver, 30.0);
        assertEquals(70.0, account.getBalance(), 0.001);
        assertEquals(80.0, receiver.getBalance(), 0.001);
    }

    @Test
    void testTransferInsufficientFunds() {
        BankAccount receiver = new BankAccount(50.0, "Checking");
        AmountException exception = assertThrows(AmountException.class, () -> account.transferMoney(receiver, 150.0));
        assertEquals("Not enough money on balance: 100.0", exception.getMessage());
        assertEquals(100.0, account.getBalance(), 0.001);
        assertEquals(50.0, receiver.getBalance(), 0.001);
    }

    @Test
    void testToString() {
        String expected = "ID: " + account.getId() + ", Owner: Savings, Balance: 100.0";
        assertEquals(expected, account.toString());
    }
}