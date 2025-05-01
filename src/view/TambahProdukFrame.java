package view;

import db.ProdukDAO;
import model.Produk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TambahProdukFrame extends JFrame {

    // Deklarasi komponen-komponen untuk input produk
    private JTextField namaField;
    private JTextField hargaField;
    private JTextField stokField;

    // Konstruktor untuk menyiapkan tampilan JFrame
    public TambahProdukFrame() {
        // Set judul dan pengaturan dasar untuk JFrame
        setTitle("Tambah Produk Baru");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Menutup frame ketika jendela ditutup
        setSize(400, 300); // Ukuran jendela
        setLocationRelativeTo(null); // Menempatkan jendela di tengah layar

        // Membuat panel dengan layout GridLayout untuk tata letak form input
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10)); // GridLayout dengan 4 baris dan 2 kolom
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Memberikan margin pada panel

        // Membuat komponen-komponen GUI (label dan field untuk nama produk, harga, dan stok)
        JLabel namaLabel = new JLabel("Nama Produk:");
        namaField = new JTextField();
        JLabel hargaLabel = new JLabel("Harga:");
        hargaField = new JTextField();
        JLabel stokLabel = new JLabel("Stok:");
        stokField = new JTextField();

        // Membuat tombol untuk menambahkan produk dan kembali ke daftar produk
        JButton tambahButton = new JButton("Tambah");
        JButton backButton = new JButton("Kembali");

        // ActionListener untuk tombol "Tambah" yang menangani penambahan produk
        tambahButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mengambil nilai input dari pengguna untuk nama, harga, dan stok
                String nama = namaField.getText();
                try {
                    double harga = Double.parseDouble(hargaField.getText()); // Mengonversi harga menjadi angka desimal
                    int stok = Integer.parseInt(stokField.getText()); // Mengonversi stok menjadi angka bulat

                    // Membuat objek Produk dan mengisi data yang dimasukkan oleh pengguna
                    Produk produk = new Produk(); // Asumsi ada kelas Produk
                    produk.setNama(nama);
                    produk.setHarga(harga);
                    produk.setStok(stok);

                    // Menggunakan ProdukDAO untuk menyimpan produk baru ke dalam database
                    ProdukDAO produkDAO = new ProdukDAO();
                    produkDAO.tambahProduk(produk); // Asumsi ada metode tambahProduk()

                    // Memberi feedback kepada pengguna bahwa produk berhasil ditambahkan
                    JOptionPane.showMessageDialog(TambahProdukFrame.this, "Produk berhasil ditambahkan!");
                    dispose(); // Menutup frame Tambah Produk
                    new ProdukFrame().setVisible(true); // Menampilkan frame daftar produk
                } catch (NumberFormatException ex) {
                    // Menangani kasus input harga atau stok yang tidak valid (misalnya bukan angka)
                    JOptionPane.showMessageDialog(TambahProdukFrame.this, "Input harga atau stok tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // ActionListener untuk tombol "Kembali" yang mengarahkan kembali ke daftar produk
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Menutup frame Tambah Produk
                new ProdukFrame().setVisible(true); // Menampilkan frame daftar produk
            }
        });

        // Menambahkan komponen-komponen ke panel
        panel.add(namaLabel);
        panel.add(namaField);
        panel.add(hargaLabel);
        panel.add(hargaField);
        panel.add(stokLabel);
        panel.add(stokField);
        panel.add(tambahButton);
        panel.add(backButton);

        // Menambahkan panel ke frame utama
        add(panel);
    }
}
