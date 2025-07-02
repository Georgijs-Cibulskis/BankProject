public class bankAccount {

    double balance;

    public bankAccount() {
        this.balance = 0;
    }

    public bankAccount(double balance) {
        setBalance(balance);
    }

    private void setBalance(double balance) {
        if (balance >= 0) {
            this.balance = balance;
        } else {
            throw new NegativeAmountException("Amount cannot be negative: " + balance);
        }
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        } else {
            throw new NegativeAmountException("Amount cannot be negative: " + amount);
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            this.balance -= amount;
        } else {
            System.out.println("Not enough money on your balance: " + this.balance);
        }
    }

    public void printBalance() {
        System.out.println("Current balance is: " + this.balance);
    }

    public void transferMoney(bankAccount receiver, double amount) {
        if (this.balance >= amount) {
            this.withdraw(amount);
            receiver.deposit(amount);
        } else {
            System.out.println("Not enough money on your balance: " + this.balance);
        }
    }




}
