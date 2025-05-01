// Import library yang diperlukan untuk GUI, database, dan model
package view;

import db.ProdukDAO; // Untuk berkomunikasi dengan database terkait data produk
import db.TransaksiDAO; // Untuk berkomunikasi dengan database terkait transaksi
import model.Produk; // Model produk untuk merepresentasikan data produk
import model.Transaksi; // Model transaksi untuk merepresentasikan data transaksi
import model.TransaksiItem; // Model transaksi item untuk menyimpan detail transaksi

import javax.swing.*; // Library untuk membuat GUI
import javax.swing.table.DefaultTableModel; // Library untuk model tabel
import java.awt.*; // Library untuk layout GUI
import java.awt.event.ActionEvent; // Library untuk aksi pada tombol
import java.awt.event.ActionListener; // Library untuk mendengarkan aksi tombol
import java.util.ArrayList; // Library untuk daftar dinamis
import java.util.List; // Library untuk tipe daftar

// Kelas utama untuk frame "Buat Transaksi Baru"
public class BuatTransaksiFrame extends JFrame {

    // Komponen GUI dan variabel yang diperlukan
    private JTextField idProdukField; // Input untuk ID produk
    private JTextField qtyField; // Input untuk kuantitas produk
    private JTable transaksiTable; // Tabel untuk menampilkan detail transaksi
    private DefaultTableModel tableModel; // Model tabel untuk manipulasi data
    private JLabel totalLabel; // Label untuk menampilkan total harga
    private double totalHarga = 0.0; // Variabel untuk menyimpan total harga
    private List<TransaksiItem> transaksiItems = new ArrayList<>(); // Daftar item transaksi

    // Konstruktor untuk inisialisasi frame
    public BuatTransaksiFrame() {
        setTitle("Buat Transaksi Baru"); // Judul frame
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Tutup frame saat klik 'X'
        setSize(800, 500); // Ukuran frame
        setLocationRelativeTo(null); // Tempatkan frame di tengah layar

        // Panel utama yang akan menampung semua komponen
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding di sekitar panel

        // Panel atas untuk input produk dan kuantitas
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // Grid 2x2
        JLabel idProdukLabel = new JLabel("ID Produk:"); // Label untuk ID Produk
        idProdukField = new JTextField(); // Input untuk ID Produk
        JLabel qtyLabel = new JLabel("Quantity:"); // Label untuk kuantitas
        qtyField = new JTextField(); // Input untuk kuantitas
        inputPanel.add(idProdukLabel); // Tambahkan label ID Produk ke panel
        inputPanel.add(idProdukField); // Tambahkan input ID Produk ke panel
        inputPanel.add(qtyLabel); // Tambahkan label kuantitas ke panel
        inputPanel.add(qtyField); // Tambahkan input kuantitas ke panel

        // Panel tengah untuk menampilkan tabel transaksi
        String[] columnNames = {"ID Produk", "Nama Produk", "Harga", "Quantity", "Subtotal"}; // Nama kolom tabel
        tableModel = new DefaultTableModel(columnNames, 0); // Buat model tabel dengan kolom
        transaksiTable = new JTable(tableModel); // Buat tabel dengan model tabel
        JScrollPane scrollPane = new JScrollPane(transaksiTable); // Tambahkan scrollbar pada tabel

        // Panel bawah untuk tombol aksi dan total harga
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Panel dengan layout flow
        totalLabel = new JLabel("Total: Rp 0.0"); // Label total harga
        JButton tambahButton = new JButton("Tambah Item"); // Tombol untuk menambah item ke tabel
        JButton simpanButton = new JButton("Simpan Transaksi"); // Tombol untuk menyimpan transaksi
        JButton backButton = new JButton("Kembali"); // Tombol untuk kembali ke halaman sebelumnya

        // Aksi untuk tombol "Tambah Item"
        tambahButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int idProduk = Integer.parseInt(idProdukField.getText()); // Ambil ID produk dari input
                    int qty = Integer.parseInt(qtyField.getText()); // Ambil kuantitas dari input

                    ProdukDAO produkDAO = new ProdukDAO(); // Buat objek DAO untuk produk
                    Produk produk = produkDAO.getProdukById(idProduk); // Cari produk berdasarkan ID

                    if (produk == null) { // Jika produk tidak ditemukan
                        JOptionPane.showMessageDialog(BuatTransaksiFrame.this, "Produk dengan ID tersebut tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (qty > produk.getStok()) { // Jika kuantitas melebihi stok yang tersedia
                        JOptionPane.showMessageDialog(BuatTransaksiFrame.this, "Stok tidak mencukupi! Stok tersedia: " + produk.getStok(), "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Hitung subtotal (harga * kuantitas)
                    double subtotal = produk.getHarga() * qty;
                    totalHarga += subtotal; // Tambahkan subtotal ke total harga
                    totalLabel.setText("Total: Rp " + totalHarga); // Perbarui label total harga

                    // Tambahkan data produk ke tabel
                    Object[] rowData = {produk.getId(), produk.getNama(), produk.getHarga(), qty, subtotal};
                    tableModel.addRow(rowData);

                    // Buat objek TransaksiItem dan tambahkan ke daftar transaksiItems
                    TransaksiItem item = new TransaksiItem(produk, qty);
                    transaksiItems.add(item);

                    // Kurangi stok produk secara sementara (untuk transaksi)
                    produk.setStok(produk.getStok() - qty);

                } catch (NumberFormatException ex) { // Tangani kesalahan input
                    JOptionPane.showMessageDialog(BuatTransaksiFrame.this, "Input tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Aksi untuk tombol "Simpan Transaksi"
        simpanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Transaksi transaksi = new Transaksi(); // Buat objek transaksi
                transaksi.setTotalHarga(totalHarga); // Set total harga transaksi

                TransaksiDAO transaksiDAO = new TransaksiDAO(); // Buat objek DAO untuk transaksi
                int idTransaksi = transaksiDAO.simpanTransaksi(transaksi); // Simpan transaksi dan dapatkan ID-nya

                if (idTransaksi > 0) { // Jika transaksi berhasil disimpan
                    transaksiDAO.simpanTransaksiItems(idTransaksi, transaksiItems); // Simpan detail transaksi

                    // Kurangi stok produk secara permanen pada database
                    ProdukDAO produkDAO = new ProdukDAO();
                    for (TransaksiItem item : transaksiItems) {
                        produkDAO.kurangiStok(item.getProduk().getId(), item.getQty());
                    }

                    JOptionPane.showMessageDialog(BuatTransaksiFrame.this, "Transaksi berhasil disimpan!");
                    dispose(); // Tutup frame saat ini
                    new TransaksiFrame().setVisible(true); // Tampilkan frame transaksi
                } else { // Jika gagal menyimpan transaksi
                    JOptionPane.showMessageDialog(BuatTransaksiFrame.this, "Gagal menyimpan transaksi!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Aksi untuk tombol "Kembali"
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Tutup frame saat ini
                new TransaksiFrame().setVisible(true); // Tampilkan frame transaksi
            }
        });

        // Tambahkan tombol ke panel bawah
        bottomPanel.add(totalLabel);
        bottomPanel.add(tambahButton);
        bottomPanel.add(simpanButton);
        bottomPanel.add(backButton);

        // Tambahkan semua panel ke panel utama
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Tambahkan panel utama ke frame
        add(mainPanel);
    }
}
