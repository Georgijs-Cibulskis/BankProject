public class bankAccount {

    private static int nextId = 1;  // static counter to generate unique IDs

    private int id;
    private String name;
    private double balance;

    // Default constructor
    public bankAccount() {
        this.id = nextId++;
        this.name = "Unnamed";
        this.balance = 0;
    }

    // Constructor with balance and name
    public bankAccount(double balance, String name) {
        this.id = nextId++;
        setBalance(balance);
        this.name = name;
    }

    private void setBalance(double balance) {
        if (balance >= 0) {
            this.balance = balance;
        } else {
            throw new AmountException("Amount cannot be negative: " + balance);
        }
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        } else {
            throw new AmountException("Amount cannot be negative: " + amount);
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            this.balance -= amount;
        } else {
            throw new AmountException("Amount cannot be negative or insufficient balance");
        }
    }

    public void printBalance() {
        System.out.println("Account ID: " + this.id);
        System.out.println("Owner: " + this.name);
        System.out.println("Current balance: " + this.balance);
    }

    public void transferMoney(bankAccount receiver, double amount) {
        if (this.balance >= amount) {
            this.withdraw(amount);
            receiver.deposit(amount);
        } else {
            throw new AmountException("Not enough money on balance: " + this.balance);
        }
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    // Optional: toString override for simpler display
    @Override
    public String toString() {
        return "ID: " + id + ", Owner: " + name + ", Balance: " + balance;
    }
}
