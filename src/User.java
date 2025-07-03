import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User implements Serializable {
    private String username;
    private String passwordHash;  // store hash instead of plain password
    private ArrayList<BankAccount> accounts;
    private static final long serialVersionUID = 1L;

    public User(String username, String password) {
        this.username = username;
        this.passwordHash = hashPassword(password);
        this.accounts = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    // Check password by comparing hashes
    public boolean checkPassword(String pwd) {
        String hashedInput = hashPassword(pwd);
        return this.passwordHash.equals(hashedInput);
    }

    public void addAccount(BankAccount acc) {
        accounts.add(acc);
    }

    public ArrayList<BankAccount> getAccounts() {
        return accounts;
    }

    public BankAccount findAccountById(int id) {
        for (BankAccount acc : accounts) {
            if (acc.getId() == id) return acc;
        }
        return null;
    }

    private static String hashPassword(String password) {
        try {
            // 1. Create a MessageDigest instance for SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // 2. Convert the password string to bytes and hash it
            byte[] hashedBytes = md.digest(password.getBytes());

            // 3. Convert the hashed bytes to a readable hex string
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                // Convert each byte to a 2-digit hex string and append
                sb.append(String.format("%02x", b));
            }

            // 4. Return the full hex string (the hash)
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // This happens if SHA-256 algorithm isn't available (very unlikely)
            throw new RuntimeException("SHA-256 algorithm not found.");
        }
    }
}
