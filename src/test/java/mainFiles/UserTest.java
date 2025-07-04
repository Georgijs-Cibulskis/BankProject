package mainFiles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("testUser", "password123");
    }

    @Test
    void testUserCreation() {
        assertEquals("testUser", user.getUsername());
        assertTrue(user.checkPassword("password123"));
        assertFalse(user.checkPassword("wrongPassword"));
    }

    @Test
    void testPasswordHashConsistency() {
        User anotherUser = new User("testUser", "password123");
        assertTrue(user.checkPassword("password123"));
        assertEquals(user.getUsername(), anotherUser.getUsername());
        // Since password is hashed, same password should produce same hash
        assertTrue(anotherUser.checkPassword("password123"));
    }

    @Test
    void testAddAndFindAccount() {
        BankAccount account = new BankAccount(100.0, "Savings");
        user.addAccount(account);
        ArrayList<BankAccount> accounts = user.getAccounts();
        assertEquals(1, accounts.size());
        assertEquals(account, user.findAccountById(account.getId()));
        assertNull(user.findAccountById(999)); // Non-existent ID
    }

    @Test
    void testUserEquality() {
        User user1 = new User("testUser", "password123");
        User user2 = new User("testUser", "differentPassword");
        assertEquals(user1, user2); // Equality based on username
        assertEquals(user1.hashCode(), user2.hashCode()); // HashSet compatibility
    }
}