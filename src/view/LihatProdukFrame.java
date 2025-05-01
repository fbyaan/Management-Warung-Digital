package view;

import db.ProdukDAO;
import model.Produk;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class LihatProdukFrame extends JFrame {

    private JTable produkTable;
    private DefaultTableModel tableModel;

    public LihatProdukFrame() {
        setTitle("Daftar Produk");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Kolom tabel
        String[] columnNames = {"ID", "Nama Produk", "Harga", "Stok"};
        tableModel = new DefaultTableModel(columnNames, 0);
        produkTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(produkTable);

        JButton backButton = new JButton("Kembali");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ProdukFrame().setVisible(true);
            }
        });

        // Load data produk dari database
        loadProdukData();

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);

        add(panel);
    }

    private void loadProdukData() {
        try {
            ProdukDAO produkDAO = new ProdukDAO();
            List<Produk> produkList = produkDAO.getAllProduk(); // Ambil daftar produk dari DAO

            // Bersihkan tabel sebelum menambahkan data baru
            tableModel.setRowCount(0);

            for (Produk produk : produkList) {
                Object[] rowData = {produk.getId(), produk.getNama(), produk.getHarga(), produk.getStok()};
                tableModel.addRow(rowData);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data produk: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void kurangiStok(int idProduk, int jumlah) {
        try {
            ProdukDAO produkDAO = new ProdukDAO();
            boolean berhasil = produkDAO.kurangiStok(idProduk, jumlah);
            if (berhasil) {
                JOptionPane.showMessageDialog(this, "Stok berhasil dikurangi!");
                loadProdukData(); // Refresh tabel
            } else {
                JOptionPane.showMessageDialog(this, "Gagal mengurangi stok. Periksa data produk.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saat mengurangi stok: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
