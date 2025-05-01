package db;

import model.Produk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdukDAO {

    public void tambahProduk(Produk produk) {
        String sql = "INSERT INTO produk (nama, harga, stok) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, produk.getNama());
            pstmt.setDouble(2, produk.getHarga());
            pstmt.setInt(3, produk.getStok());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding product: " + e.getMessage());
        }
    }

    public void editProduk(Produk produk) {
        String sql = "UPDATE produk SET nama = ?, harga = ?, stok = ? WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, produk.getNama());
            pstmt.setDouble(2, produk.getHarga());
            pstmt.setInt(3, produk.getStok());
            pstmt.setInt(4, produk.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error editing product: " + e.getMessage());
        }
    }

    public void hapusProduk(int id) {
        String sql = "DELETE FROM produk WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting product: " + e.getMessage());
        }
    }

    public Produk getProdukById(int id) {
        String sql = "SELECT * FROM produk WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Produk produk = new Produk();
                produk.setId(rs.getInt("id"));
                produk.setNama(rs.getString("nama"));
                produk.setHarga(rs.getDouble("harga"));
                produk.setStok(rs.getInt("stok"));
                return produk;
            }
        } catch (SQLException e) {
            System.err.println("Error getting product by id: " + e.getMessage());
        }
        return null;
    }

    public List<Produk> getAllProduk() {
        List<Produk> produkList = new ArrayList<>();
        String sql = "SELECT * FROM produk";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Produk produk = new Produk();
                produk.setId(rs.getInt("id"));
                produk.setNama(rs.getString("nama"));
                produk.setHarga(rs.getDouble("harga"));
                produk.setStok(rs.getInt("stok"));
                produkList.add(produk);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all products: " + e.getMessage());
        }
        return produkList;
    }

    public boolean kurangiStok(int idProduk, int jumlah) {
        String sql = "UPDATE produk SET stok = stok - ? WHERE id = ? AND stok >= ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, jumlah);
            pstmt.setInt(2, idProduk);
            pstmt.setInt(3, jumlah); // Pastikan stok cukup untuk dikurangi
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0; // Berhasil jika ada baris yang diperbarui
        } catch (SQLException e) {
            System.err.println("Error reducing stock: " + e.getMessage());
            return false; // Gagal mengurangi stok
        }
    }
    
}