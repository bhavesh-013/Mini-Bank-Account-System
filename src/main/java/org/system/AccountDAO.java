package org.system;
import java.sql.*;

public class AccountDAO{
//case 1 -->
    public void createAccount(Account account) {
        String sql = """
                INSERT INTO accounts(name, email, balance)
                VALUES (?, ?, ?)
                """;
        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, account.getName());
            ps.setString(2, account.getEmail());
            ps.setDouble(3, account.getBalance());

            ps.executeUpdate();

            System.out.println("Account created successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//case 2 -->
    public static void getAccount(int id){
        String sql = "SELECT * FROM accounts WHERE id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println("\n===== ACCOUNT DETAILS =====");

                System.out.println("ID      : " + rs.getInt("id"));
                System.out.println("Name    : " + rs.getString("name"));
                System.out.println("Email   : " + rs.getString("email"));
                System.out.println("Balance : ₹" + rs.getDouble("balance"));

            } else {
                System.out.println("Account not found!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//case 3 -->
    public void deposit(int accountId, double amount) {

        String Updatesql = """
        UPDATE accounts SET balance = balance + ? WHERE id = ?
        """;
        String transactionSql = """
            INSERT INTO transactions(account_id, type, amount)
            VALUES (?, 'DEPOSIT', ?)
            """;
        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(Updatesql);
                PreparedStatement transactionPs =
                        con.prepareStatement(transactionSql)
        ) {

            ps.setDouble(1, amount);
            ps.setInt(2, accountId);

            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("Account not found!");
            }
            transactionPs.setInt(1, accountId);
            transactionPs.setDouble(2, amount);

            transactionPs.executeUpdate();
            System.out.println("Amount deposited successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//case 4 -->
public void withdraw(int accountId, double amount) {

    String selectSql =
            "SELECT balance FROM accounts WHERE id = ?";

    String updateSql = """
            UPDATE accounts
            SET balance = balance - ?
            WHERE id = ?
            """;

    String transactionSql = """
            INSERT INTO transactions(account_id, type, amount)
            VALUES (?, 'WITHDRAW', ?)
            """;

    try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement selectPs =
                    connection.prepareStatement(selectSql)
    ) {

        // 1. Get current balance
        selectPs.setInt(1, accountId);

        ResultSet rs = selectPs.executeQuery();

        // 2. Check account
        if (!rs.next()) {
            System.out.println("Account not found!");
            return;
        }

        double balance = rs.getDouble("balance");

        // 3. Check sufficient balance
        if (amount > balance) {
            System.out.println("Insufficient balance!");
            return;
        }

        // 4. Update balance
        try (
                PreparedStatement updatePs =
                        connection.prepareStatement(updateSql);
                PreparedStatement transactionPs =
                        connection.prepareStatement(transactionSql)
        ) {

            updatePs.setDouble(1, amount);
            updatePs.setInt(2, accountId);

            int rows = updatePs.executeUpdate();

            if (rows == 0) {
                System.out.println("Withdrawal failed!");
                return;
            }

            // 5. Save transaction
            transactionPs.setInt(1, accountId);
            transactionPs.setDouble(2, amount);

            transactionPs.executeUpdate();

            System.out.println("Amount withdrawn successfully!");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
//case 5 -->
    public void getTransactionHistory(int accountId) {
        String sql = """
                        SELECT id, type, amount, created_at 
                        FROM transactions WHERE account_id = ? 
                                          ORDER BY created_at DESC
                        """;
        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            System.out.println("\n===== TRANSACTION HISTORY =====");
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println(
                        "ID: " + rs.getInt("id")
                                + " | Type: " + rs.getString("type")
                                + " | Amount: ₹" + rs.getDouble("amount")
                                + " | Date: " + rs.getTimestamp("created_at")
                );
            }
            if (!found) {
                System.out.println("No transactions found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//case 6 -->
    public void deleteAccount(int accountId) {

        String deleteTransactions =
                "DELETE FROM transactions WHERE account_id = ?";

        String deleteAccount =
                "DELETE FROM accounts WHERE id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement transactionPs =
                        connection.prepareStatement(deleteTransactions);
                PreparedStatement accountPs =
                        connection.prepareStatement(deleteAccount)
        ) {

            // 1. Delete transaction history
            transactionPs.setInt(1, accountId);
            transactionPs.executeUpdate();

            // 2. Delete account
            accountPs.setInt(1, accountId);

            int rows = accountPs.executeUpdate();

            if (rows > 0) {
                System.out.println("Account deleted successfully!");
            } else {
                System.out.println("Account not found!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//case 7 -->

}
