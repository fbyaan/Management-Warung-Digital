package db;

import model.Produk;
import model.Transaksi;
import model.TransaksiItem; // Import TransaksiItem dari package model

import java.sql.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransaksiDAO {

    public int simpanTransaksi(Transaksi transaksi) {
        String sql = "INSERT INTO transaksi (tanggal, total_harga) VALUES (DATETIME('now', 'localtime'), ?)";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setDouble(1, transaksi.getTotalHarga());
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1); // Mengembalikan ID transaksi yang baru saja dibuat
            } else {
                return -1; // Gagal mendapatkan ID
            }
        } catch (SQLException e) {
            System.err.println("Error saving transaction: " + e.getMessage());
            return -1;
        }
    }

    public void simpanTransaksiItems(int idTransaksi, List<TransaksiItem> transaksiItems) {
        String sql = "INSERT INTO transaksi_items (transaksi_id, produk_id, quantity) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (TransaksiItem item : transaksiItems) {
                pstmt.setInt(1, idTransaksi);
                pstmt.setInt(2, item.getProduk().getId());
                pstmt.setInt(3, item.getQty());
                pstmt.executeUpdate(); // Eksekusi query untuk setiap item transaksi
            }

        } catch (SQLException e) {
            System.err.println("Error saving transaction items: " + e.getMessage());
        }
    }

    public List<Transaksi> getAllTransaksi() {
        List<Transaksi> transaksiList = new ArrayList<>();
        String sql = "SELECT * FROM transaksi";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Transaksi transaksi = new Transaksi();
                transaksi.setId(rs.getInt("id"));
                transaksi.setTanggal(LocalDateTime.parse(rs.getString("tanggal"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                transaksi.setTotalHarga(rs.getDouble("total_harga"));
                transaksiList.add(transaksi);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all transactions: " + e.getMessage());
        }
        return transaksiList;
    }

    public Transaksi getTransaksiById(int id) {
        String sql = "SELECT * FROM transaksi WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Transaksi transaksi = new Transaksi();
                transaksi.setId(rs.getInt("id"));
                transaksi.setTanggal(LocalDateTime.parse(rs.getString("tanggal"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                transaksi.setTotalHarga(rs.getDouble("total_harga"));
                return transaksi;
            }
        } catch (SQLException e) {
            System.err.println("Error getting transaction by id: " + e.getMessage());
        }
        return null;
    }

    public String generateStruk(int idTransaksi) {
        StringBuilder struk = new StringBuilder();
        String sql = "SELECT t.id AS transaksi_id, t.tanggal, t.total_harga, " +
                     "ti.quantity, p.nama AS nama_produk, p.harga AS harga_produk " +
                     "FROM transaksi t " +
                     "JOIN transaksi_items ti ON t.id = ti.transaksi_id " +
                     "JOIN produk p ON ti.produk_id = p.id " +
                     "WHERE t.id = ?";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idTransaksi);
            ResultSet rs = pstmt.executeQuery();

            struk.append("================== Warung Digital ==================\n");
            boolean first = true;
            double totalHarga = 0.0;
            while (rs.next()) {
                if (first) {
                    struk.append("ID Transaksi: ").append(rs.getInt("transaksi_id")).append("\n");
                    struk.append("Tanggal: ").append(rs.getString("tanggal")).append("\n");
                    first = false;
                }
                int quantity = rs.getInt("quantity");
                double hargaProduk = rs.getDouble("harga_produk");
                double subtotal = quantity * hargaProduk;
                totalHarga += subtotal;

                struk.append(rs.getString("nama_produk")).append(" x ").append(quantity);
                struk.append(" @ Rp ").append(hargaProduk).append(" = Rp ").append(subtotal).append("\n");

            }
            struk.append("--------------------------------------------------\n");
            struk.append("Total: Rp ").append(totalHarga).append("\n");
            struk.append("==================== Terima Kasih ====================\n");
            struk.append("Kata kata hari ini pak sujuuuuuuuuddd....\n");
            struk.append("yang sudah selesai bo.. boleh pulang\n"); 
        } catch (SQLException e) {
            System.err.println("Error generating struk: " + e.getMessage());
            return "Gagal mencetak struk.";
        }
        return struk.toString();
    }

    public String generateLaporanHarian(LocalDate tanggal) {
        StringBuilder laporan = new StringBuilder();
        String sql = "SELECT SUM(total_harga) AS total_penjualan " +
                     "FROM transaksi " +
                     "WHERE DATE(tanggal) = ?";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tanggal.toString());
            ResultSet rs = pstmt.executeQuery();

            laporan.append("================ Laporan Penjualan Harian =================\n");
            laporan.append("Tanggal: ").append(tanggal.format(DateTimeFormatter.ISO_LOCAL_DATE)).append("\n");

            if (rs.next()) {
                laporan.append("Total Penjualan: Rp ").append(rs.getDouble("total_penjualan")).append("\n");
            } else {
                laporan.append("Tidak ada penjualan pada tanggal ini.\n");
            }
            laporan.append("=========================================================\n");

        } catch (SQLException e) {
            System.err.println("Error generating daily report: " + e.getMessage());
            return "Gagal membuat laporan harian.";
        }
        return laporan.toString();
    }

    public String generateLaporanBulanan(YearMonth bulanTahun) {
        StringBuilder laporan = new StringBuilder();
        String sql = "SELECT SUM(total_harga) AS total_penjualan " +
                     "FROM transaksi " +
                     "WHERE STRFTIME('%Y-%m', tanggal) = ?";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bulanTahun.format(DateTimeFormatter.ofPattern("yyyy-MM")));
            ResultSet rs = pstmt.executeQuery();

            laporan.append("================ Laporan Penjualan Bulanan =================\n");
            laporan.append("Bulan Tahun: ").append(bulanTahun.format(DateTimeFormatter.ofPattern("yyyy-MM"))).append("\n");

            if (rs.next()) {
                laporan.append("Total Penjualan: Rp ").append(rs.getDouble("total_penjualan")).append("\n");
            } else {
                laporan.append("Tidak ada penjualan pada bulan ini.\n");
            }
            laporan.append("==========================================================\n");

        } catch (SQLException e) {
            System.err.println("Error generating monthly report: " + e.getMessage());
            return "Gagal membuat laporan bulanan.";
        }
        return laporan.toString();
    }

    public String generateLaporanTerlaris() {
        StringBuilder laporan = new StringBuilder();
        String sql = "SELECT p.nama, SUM(ti.quantity) AS total_terjual " +
                     "FROM transaksi_items ti " +
                     "JOIN produk p ON ti.produk_id = p.id " +
                     "GROUP BY p.nama " +
                     "ORDER BY total_terjual DESC " +
                     "LIMIT 5";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            laporan.append("================ Laporan Produk Terlaris ==================\n");
            laporan.append("Top 5 Produk:\n");

            int i = 1;
            while (rs.next()) {
                laporan.append(i++).append(". ").append(rs.getString("nama")).append(" - Total Terjual: ").append(rs.getInt("total_terjual")).append("\n");
            }
            laporan.append("==========================================================\n");

        } catch (SQLException e) {
            System.err.println("Error generating best selling report: " + e.getMessage());
            return "Gagal membuat laporan produk terlaris.";
        }
        return laporan.toString();
    }
}