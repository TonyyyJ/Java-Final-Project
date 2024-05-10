import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    // Please modify this path to your local path where the EmailChecker.db from our zip submission is 
    private static final String URL = "jdbc:sqlite:C:\\Users\\tonyy\\Desktop\\Java-Final-Project\\final\\DB\\EmailChecker.db";


    // Connect to the SQLite database
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // Create tables if they do not exist
    public static void createTables() {
        String sql = "CREATE TABLE IF NOT EXISTS email (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "subject TEXT," +
                "sender TEXT NOT NULL," +
                "content TEXT," +
                "is_spam TEXT)";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tables created successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void recreateTable() {
        String dropTableSql = "DROP TABLE IF EXISTS email";
        String createTableSql = "CREATE TABLE IF NOT EXISTS email (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "subject TEXT," +
                "sender TEXT NOT NULL," +
                "content TEXT," +
                "is_spam TEXT)";
    
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(dropTableSql);
            stmt.execute(createTableSql);
            System.out.println("Table recreated successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Insert a new email into the database
    public static void insertEmail( String subject, String sender, String content, String isSpam) {
        String sql = "INSERT INTO email(subject, sender, content, is_spam) VALUES(?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, subject);
            pstmt.setString(2, sender);
            pstmt.setString(3, content);
            pstmt.setString(4, isSpam);
            pstmt.executeUpdate();
            System.out.println("Email inserted successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Email> fetchEmails() {
        List<Email> emails = new ArrayList<>();
        String sql = "SELECT * FROM email";
        try (Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                emails.add(new Email(rs.getInt("id"), rs.getString("subject"), rs.getString("sender"), rs.getString("content"), rs.getString("is_spam")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return emails;
    }

    public static List<Email> fetchEmailsBySpamStatus(String isSpam) {
        List<Email> emails = new ArrayList<>();
        String sql = "SELECT * FROM email WHERE is_spam = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, isSpam);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                emails.add(new Email(
                    rs.getInt("id"),
                    rs.getString("subject"),
                    rs.getString("sender"),
                    rs.getString("content"),
                    rs.getString("is_spam")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return emails;
    }

    // // Main method to create table and insert example data
    // public static void main(String[] args) {
    //     createTables(); // Create the database table
    //     // Insert example data
    // }
}
