import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:spam_checker.db";
    private static final String CREATE_SPAM_EMAILS_TABLE = "CREATE TABLE IF NOT EXISTS spam_emails (email_id INTEGER PRIMARY KEY AUTOINCREMENT, subject TEXT, sender TEXT, date TEXT)";

    private Connection connection;

    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            createTable(CREATE_SPAM_EMAILS_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable(String createTableSQL) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
        }
    }

    public void insertSpamEmail(String subject, String sender, String date) {
        String insertSQL = "INSERT INTO spam_emails (subject, sender, date) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, subject);
            preparedStatement.setString(2, sender);
            preparedStatement.setString(3, date);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getSpamEmails() throws SQLException {
        String selectSQL = "SELECT * FROM spam_emails";

        try (Statement statement = connection.createStatement()) {
            return statement.executeQuery(selectSQL);
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}