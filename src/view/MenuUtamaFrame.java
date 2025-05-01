// Package view untuk kelas tampilan GUI
package view;

import javax.swing.*; // Import library untuk GUI
import java.awt.*; // Import library untuk layout
import java.awt.event.ActionEvent; // Import library untuk event tombol
import java.awt.event.ActionListener; // Import library untuk mendengarkan aksi tombol

// Kelas utama untuk frame menu utama
public class MenuUtamaFrame extends JFrame {

    // Konstruktor untuk inisialisasi frame menu utama
    public MenuUtamaFrame() {
        setTitle("Warung Digital - Menu Utama"); // Judul frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Tutup aplikasi saat frame ditutup
        setSize(600, 400); // Ukuran frame
        setLocationRelativeTo(null); // Tempatkan frame di tengah layar

        // Panel utama untuk menempatkan tombol-tombol
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10)); // 6 baris, 1 kolom, jarak antar elemen 10 piksel
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margin 20 piksel di sekitar panel

        // Tombol untuk setiap fitur yang tersedia
        JButton produkButton = new JButton("Manajemen Produk"); // Tombol untuk membuka manajemen produk
        JButton transaksiButton = new JButton("Manajemen Transaksi"); // Tombol untuk membuka manajemen transaksi
        JButton laporanButton = new JButton("Laporan"); // Tombol untuk membuka laporan
        JButton tentangButton = new JButton("Tentang Aplikasi"); // Tombol untuk membuka informasi tentang aplikasi
        JButton logoutButton = new JButton("Logout"); // Tombol untuk logout ke halaman login
        JButton keluarButton = new JButton("Keluar"); // Tombol untuk keluar dari aplikasi

        // Aksi ketika tombol "Manajemen Produk" ditekan
        produkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Tutup frame menu utama
                new ProdukFrame().setVisible(true); // Buka frame manajemen produk
            }
        });

        // Aksi ketika tombol "Manajemen Transaksi" ditekan
        transaksiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Tutup frame menu utama
                new TransaksiFrame().setVisible(true); // Buka frame manajemen transaksi
            }
        });

        // Aksi ketika tombol "Laporan" ditekan
        laporanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Tutup frame menu utama
                new LaporanFrame().setVisible(true); // Buka frame laporan
            }
        });

        // Aksi ketika tombol "Tentang Aplikasi" ditekan
        tentangButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Tutup frame menu utama
                new TentangFrame().setVisible(true); // Buka frame informasi tentang aplikasi
            }
        });

        // Aksi ketika tombol "Logout" ditekan
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Tutup frame menu utama
                new LoginFrame().setVisible(true); // Kembali ke halaman login
            }
        });

        // Aksi ketika tombol "Keluar" ditekan
        keluarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Keluar dari aplikasi
            }
        });

        // Tambahkan semua tombol ke panel
        panel.add(produkButton); // Tambahkan tombol "Manajemen Produk"
        panel.add(transaksiButton); // Tambahkan tombol "Manajemen Transaksi"
        panel.add(laporanButton); // Tambahkan tombol "Laporan"
        panel.add(tentangButton); // Tambahkan tombol "Tentang Aplikasi"
        panel.add(logoutButton); // Tambahkan tombol "Logout"
        panel.add(keluarButton); // Tambahkan tombol "Keluar"

        // Tambahkan panel ke frame
        add(panel);
    }
}
