package at.htl.entity;


import at.htl.Classes.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountTest {

    @Test
    public void testGetEmail() {
        // Arrange
        String email = "test@example.com";
        Account account = new Account();
        account.setEmail(email);

        // Act
        String actualEmail = account.getEmail();

        // Assert
        Assertions.assertEquals(email, actualEmail);
    }

    @Test
    public void testGetResetPassword() {
        // Arrange
        String resetPassword = "ABC123";
        Account account = new Account();
        account.setResetPassword(resetPassword);

        // Act
        String actualResetPassword = account.getResetPassword();

        // Assert
        Assertions.assertEquals(resetPassword, actualResetPassword);
    }

    @Test
    public void testGetNewPassword() {
        // Arrange
        String newPassword = "newPassword123";
        Account account = new Account();
        account.setNewPassword(newPassword);

        // Act
        String actualNewPassword = account.getNewPassword();

        // Assert
        Assertions.assertEquals(newPassword, actualNewPassword);
    }

    @Test
    public void testSetEmail() {
        // Arrange
        String email = "test@example.com";
        Account account = new Account();

        // Act
        account.setEmail(email);

        // Assert
        Assertions.assertEquals(email, account.getEmail());
    }

    @Test
    public void testSetResetPassword() {
        // Arrange
        String resetPassword = "ABC123";
        Account account = new Account();

        // Act
        account.setResetPassword(resetPassword);

        // Assert
        Assertions.assertEquals(resetPassword, account.getResetPassword());
    }

    @Test
    public void testSetNewPassword() {
        // Arrange
        String newPassword = "newPassword123";
        Account account = new Account();

        // Act
        account.setNewPassword(newPassword);

        // Assert
        Assertions.assertEquals(newPassword, account.getNewPassword());
    }

    @Test
    public void testEquals_SameObject() {
        // Arrange
        Account account = new Account();

        // Act & Assert
        Assertions.assertEquals(account, account);
    }

    @Test
    public void testEquals_NullObject() {
        // Arrange
        Account account = new Account();

        // Act & Assert
        Assertions.assertNotEquals(account, null);
    }
}
