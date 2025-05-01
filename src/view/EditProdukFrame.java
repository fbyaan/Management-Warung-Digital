// Package untuk kelas tampilan (view)
package view;

// Import library yang dibutuhkan
import db.ProdukDAO; // Kelas untuk operasi database terkait produk
import model.Produk; // Model yang merepresentasikan data produk

// Import komponen GUI
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Kelas untuk frame pengeditan produk
public class EditProdukFrame extends JFrame {

    // Komponen input untuk data produk
    private JTextField idField;    // Input ID produk
    private JTextField namaField; // Input nama produk
    private JTextField hargaField; // Input harga produk
    private JTextField stokField;  // Input stok produk

    // Konstruktor untuk inisialisasi frame
    public EditProdukFrame() {
        // Menentukan judul dan properti dasar frame
        setTitle("Edit Detail Produk"); // Judul frame
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Tutup frame saat jendela ditutup
        setSize(400, 350); // Ukuran frame
        setLocationRelativeTo(null); // Tempatkan frame di tengah layar

        // Panel utama dengan tata letak GridLayout untuk input
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10)); // 5 baris, 2 kolom, jarak antar elemen 10 px
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding 20 px di setiap sisi

        // Label dan input field untuk masing-masing data produk
        JLabel idLabel = new JLabel("ID Produk:"); // Label untuk ID produk
        idField = new JTextField(); // Input untuk ID produk
        JLabel namaLabel = new JLabel("Nama Produk:"); // Label untuk nama produk
        namaField = new JTextField(); // Input untuk nama produk
        JLabel hargaLabel = new JLabel("Harga:"); // Label untuk harga produk
        hargaField = new JTextField(); // Input untuk harga produk
        JLabel stokLabel = new JLabel("Stok:"); // Label untuk stok produk
        stokField = new JTextField(); // Input untuk stok produk

        // Tombol untuk aksi edit dan kembali
        JButton editButton = new JButton("Edit"); // Tombol untuk menyimpan perubahan
        JButton backButton = new JButton("Kembali"); // Tombol untuk kembali ke menu sebelumnya

        // Listener untuk tombol "Edit"
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Parsing data input
                    int id = Integer.parseInt(idField.getText()); // Konversi ID ke integer
                    String nama = namaField.getText(); // Ambil nama produk
                    double harga = Double.parseDouble(hargaField.getText()); // Konversi harga ke double
                    int stok = Integer.parseInt(stokField.getText()); // Konversi stok ke integer

                    // Membuat objek Produk untuk menyimpan data baru
                    Produk produk = new Produk();
                    produk.setId(id); // Set ID produk
                    produk.setNama(nama); // Set nama produk
                    produk.setHarga(harga); // Set harga produk
                    produk.setStok(stok); // Set stok produk

                    // Menggunakan DAO untuk menyimpan perubahan ke database
                    ProdukDAO produkDAO = new ProdukDAO();
                    produkDAO.editProduk(produk); // Update data produk di database

                    // Menampilkan pesan sukses
                    JOptionPane.showMessageDialog(EditProdukFrame.this, "Produk berhasil diupdate!");
                    dispose(); // Tutup frame saat ini
                    new ProdukFrame().setVisible(true); // Tampilkan frame produk

                } catch (NumberFormatException ex) { // Tangani kesalahan input tidak valid
                    JOptionPane.showMessageDialog(EditProdukFrame.this, 
                        "Input tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Listener untuk tombol "Kembali"
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Tutup frame saat ini
                new ProdukFrame().setVisible(true); // Tampilkan frame produk
            }
        });

        // Menambahkan komponen ke panel
        panel.add(idLabel); // Tambahkan label ID produk
        panel.add(idField); // Tambahkan input ID produk
        panel.add(namaLabel); // Tambahkan label nama produk
        panel.add(namaField); // Tambahkan input nama produk
        panel.add(hargaLabel); // Tambahkan label harga produk
        panel.add(hargaField); // Tambahkan input harga produk
        panel.add(stokLabel); // Tambahkan label stok produk
        panel.add(stokField); // Tambahkan input stok produk
        panel.add(editButton); // Tambahkan tombol Edit
        panel.add(backButton); // Tambahkan tombol Kembali

        // Menambahkan panel utama ke frame
        add(panel);
    }
}
