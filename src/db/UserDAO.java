package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;

public class UserDAO {

    public boolean register(String username, String password) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();

            // Inisialisasi database khusus untuk pengguna baru
            initializeUserDatabase(username);
            return true;
        } catch (SQLException e) {
            System.err.println("Error during registration: " + e.getMessage());
            return false;
        }
    }

    public boolean login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Returns true if a match is found
        } catch (SQLException e) {
            System.err.println("Error during login: " + e.getMessage());
            return false;
        }
    }

    public void initializeUserDatabase(String username) {
        String userDbPath = "databases/" + username + ".db"; // Lokasi database untuk setiap pengguna
        File dbFile = new File(userDbPath);

        // Periksa apakah database pengguna sudah ada
        if (!dbFile.exists()) {
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + userDbPath)) {
                if (conn != null) {
                    // Inisialisasi tabel untuk pengguna baru
                    String sql = "CREATE TABLE IF NOT EXISTS example_data (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "name TEXT," +
                            "value TEXT)";
                    Statement stmt = conn.createStatement();
                    stmt.execute(sql);

                    System.out.println("Database untuk pengguna " + username + " berhasil dibuat di " + userDbPath);
                }
            } catch (SQLException e) {
                System.err.println("Error during database initialization for user " + username + ": " + e.getMessage());
            }
        } else {
            System.out.println("Database untuk pengguna " + username + " sudah ada.");
        }
    }
}
