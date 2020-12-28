package banking;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountRepository {

    public AccountRepository() {
        createCardTable();
    }

    private void createCardTable() {
        try(Connection con = SQLiteJDBC.getDataSource().getConnection()) {
            try(Statement statement = con.createStatement()) {
                statement.executeUpdate(
                        "CREATE table IF NOT EXISTS card(" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "number TEXT," +
                                "pin TEXT," +
                                "balance INTEGER DEFAULT 0)"
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAccount(Account acc) {
        try(Connection con = SQLiteJDBC.getDataSource().getConnection()) {
            try(Statement statement = con.createStatement()) {
                statement.executeUpdate(
                        "INSERT INTO card (number, pin, balance) VALUES " +
                                "('" + acc.getCardNumber() + "', '" + acc.getPIN() + "', " + acc.getBalance() + ")"
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addIncome(String cardNumber, BigDecimal balance) {
        try(Connection con = SQLiteJDBC.getDataSource().getConnection()) {
            try(Statement statement = con.createStatement()) {
                statement.executeUpdate(
                        "UPDATE card SET balance = balance + " + balance.intValue() +
                                " WHERE number = '" + cardNumber + "'"
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Account findByCardNumber(String cardNumber) {
        try(Connection con = SQLiteJDBC.getDataSource().getConnection(); Statement statement = con.createStatement(); ) {
            try(ResultSet accs = statement.executeQuery(
                    "SELECT number, pin FROM card " +
                            "WHERE number = '" + cardNumber + "'"
            )) {
                return new Account(cardNumber, accs.getString("pin"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean exists(String cardNumber) {
        try(Connection con = SQLiteJDBC.getDataSource().getConnection()) {
            try(Statement statement = con.createStatement()) {
                try(ResultSet accs = statement.executeQuery(
                        "SELECT COUNT(*) as \"cnt\" FROM card " +
                                "WHERE number = '" + cardNumber + "'"
                )) {
                    return accs.getInt("cnt") > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void deleteAccount(String cardNumber) {
        try(Connection con = SQLiteJDBC.getDataSource().getConnection()) {
            try(Statement statement = con.createStatement()) {
                statement.executeUpdate(
                        "DELETE FROM card WHERE number = '" + cardNumber + "'"
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void transfer(String cardNumberFrom, String cardNumberTo, BigDecimal amount) {
        try(Connection con = SQLiteJDBC.getDataSource().getConnection()) {
            try(Statement statement = con.createStatement()) {
                statement.executeUpdate(
                        "UPDATE card SET balance = balance - " + amount.intValue() +
                                " WHERE number = '" + cardNumberFrom + "'"
                );
                statement.executeUpdate(
                        "UPDATE card SET balance = balance + " + amount.intValue() +
                                " WHERE number = '" + cardNumberTo + "'"
                );
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
