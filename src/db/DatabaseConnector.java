package db;

// Package 'db' digunakan untuk menyimpan kelas-kelas yang berhubungan dengan operasi database.

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// Mengimpor pustaka JDBC yang diperlukan untuk koneksi dan manipulasi database.

public class DatabaseConnector {
    // Kelas utama untuk menangani koneksi dan inisialisasi database.

    private static final String DB_URL = "jdbc:sqlite:warung_digital.db";
    // URL koneksi database SQLite. Database ini bernama 'warung_digital.db'.

    public static Connection connect() {
        // Metode untuk menghubungkan aplikasi ke database.
        try {
            Class.forName("org.sqlite.JDBC");
            // Memastikan driver SQLite sudah di-load.
            return DriverManager.getConnection(DB_URL);
            // Membuka koneksi ke database.
        } catch (ClassNotFoundException e) {
            // Jika driver SQLite tidak ditemukan.
            System.err.println("SQLite JDBC driver not found.");
            return null;
        } catch (SQLException e) {
            // Jika terjadi kesalahan saat menghubungkan ke database.
            System.err.println("Error connecting to the database: " + e.getMessage());
            return null;
        }
    }

    public static boolean createTables() {
        // Metode untuk membuat tabel dalam database jika belum ada.
        Connection conn = null;
        Statement stmt = null;
        boolean success = true; // Menandakan apakah pembuatan tabel berhasil.

        try {
            conn = connect();
            // Membuka koneksi ke database.
            if (conn == null) {
                // Jika koneksi gagal.
                System.err.println("Failed to connect to the database. Cannot create tables.");
                return false; // Kembalikan false karena koneksi gagal.
            }

            stmt = conn.createStatement();
            // Membuat objek statement untuk menjalankan perintah SQL.

            // Membuat tabel 'users'.
            String sqlUser = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT NOT NULL UNIQUE CHECK(LENGTH(username) <= 50)," +
                    "password TEXT NOT NULL" +
                    ");";
            stmt.execute(sqlUser);
            System.out.println("Table 'users' created or already exists.");

            // Membuat tabel 'produk'.
            String sqlProduk = "CREATE TABLE IF NOT EXISTS produk (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nama TEXT NOT NULL," +
                    "harga REAL NOT NULL," +
                    "stok INTEGER NOT NULL" +
                    ");";
            stmt.execute(sqlProduk);
            System.out.println("Table 'produk' created or already exists.");

            // Membuat tabel 'transaksi'.
            String sqlTransaksi = "CREATE TABLE IF NOT EXISTS transaksi (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "tanggal TEXT DEFAULT CURRENT_TIMESTAMP," +
                    "total_harga REAL NOT NULL" +
                    ");";
            stmt.execute(sqlTransaksi);
            System.out.println("Table 'transaksi' created or already exists.");

            // Membuat tabel 'transaksi_items'.
            String sqlTransaksiItems = "CREATE TABLE IF NOT EXISTS transaksi_items (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "transaksi_id INTEGER NOT NULL," +
                    "produk_id INTEGER NOT NULL," +
                    "quantity INTEGER NOT NULL," +
                    "FOREIGN KEY (transaksi_id) REFERENCES transaksi(id)," +
                    "FOREIGN KEY (produk_id) REFERENCES produk(id)" +
                    ");";
            stmt.execute(sqlTransaksiItems);
            System.out.println("Table 'transaksi_items' created or already exists.");

        } catch (SQLException e) {
            // Jika terjadi kesalahan saat membuat tabel.
            System.err.println("Error creating tables: " + e.getMessage());
            success = false; // Tandai bahwa pembuatan tabel gagal.
        } finally {
            try {
                if (stmt != null) stmt.close();
                // Menutup statement untuk membebaskan sumber daya.
            } catch (SQLException e) {
                System.err.println("Error closing statement: " + e.getMessage());
            }
            try {
                if (conn != null) conn.close();
                // Menutup koneksi ke database.
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
        return success; // Mengembalikan status keberhasilan pembuatan tabel.
    }

    public static void main(String[] args) {
        // Metode utama untuk menjalankan program.

        if (createTables()) {
            // Jika tabel berhasil dibuat atau sudah ada.
            System.out.println("Database tables setup completed successfully.");
        } else {
            // Jika pembuatan tabel gagal.
            System.err.println("Database tables setup failed.");
        }
    }
}
