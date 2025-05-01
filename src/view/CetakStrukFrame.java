// Package untuk GUI yang digunakan dalam sistem
package view;

// Import library yang diperlukan untuk DAO (Data Access Object) dan model
import db.TransaksiDAO; // Untuk akses database terkait transaksi
import model.Transaksi; // Model transaksi untuk representasi data transaksi

// Import library GUI
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Kelas untuk frame cetak struk transaksi
public class CetakStrukFrame extends JFrame {

    // Komponen GUI
    private JTextField idTransaksiField; // Input untuk ID transaksi
    private JTextArea strukArea; // Area teks untuk menampilkan struk

    // Konstruktor untuk inisialisasi frame
    public CetakStrukFrame() {
        setTitle("Cetak Struk Transaksi"); // Judul frame
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Tutup frame saat klik tombol 'X'
        setSize(500, 400); // Ukuran frame
        setLocationRelativeTo(null); // Tempatkan frame di tengah layar

        // Panel utama dengan layout BorderLayout
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding untuk jarak antar komponen

        // Panel input di bagian atas untuk memasukkan ID transaksi
        JPanel inputPanel = new JPanel(new FlowLayout()); // Menggunakan FlowLayout untuk tata letak horizontal
        JLabel idTransaksiLabel = new JLabel("ID Transaksi:"); // Label untuk ID transaksi
        idTransaksiField = new JTextField(10); // Input untuk ID transaksi (panjang kolom 10 karakter)
        JButton cetakButton = new JButton("Cetak Struk"); // Tombol untuk mencetak struk
        inputPanel.add(idTransaksiLabel); // Tambahkan label ke panel input
        inputPanel.add(idTransaksiField); // Tambahkan input field ke panel input
        inputPanel.add(cetakButton); // Tambahkan tombol cetak ke panel input

        // Area teks untuk menampilkan struk transaksi
        strukArea = new JTextArea(); 
        strukArea.setEditable(false); // Area teks tidak dapat diubah oleh pengguna
        JScrollPane scrollPane = new JScrollPane(strukArea); // Scroll pane untuk area teks agar bisa di-scroll

        // Tombol kembali untuk kembali ke halaman sebelumnya
        JButton backButton = new JButton("Kembali");
        backButton.addActionListener(new ActionListener() { // Tambahkan aksi untuk tombol kembali
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Tutup frame saat ini
                new TransaksiFrame().setVisible(true); // Tampilkan frame transaksi
            }
        });

        // Aksi untuk tombol "Cetak Struk"
        cetakButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Ambil ID transaksi dari input
                    int idTransaksi = Integer.parseInt(idTransaksiField.getText());

                    // Buat objek TransaksiDAO untuk mengakses database
                    TransaksiDAO transaksiDAO = new TransaksiDAO();
                    // Cari data transaksi berdasarkan ID
                    Transaksi transaksi = transaksiDAO.getTransaksiById(idTransaksi);

                    if (transaksi == null) { // Jika transaksi tidak ditemukan
                        JOptionPane.showMessageDialog(CetakStrukFrame.this, 
                            "Transaksi dengan ID tersebut tidak ditemukan!", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Generate struk berdasarkan ID transaksi
                    String struk = transaksiDAO.generateStruk(idTransaksi);
                    strukArea.setText(struk); // Tampilkan struk di area teks

                } catch (NumberFormatException ex) { // Jika input ID transaksi tidak valid
                    JOptionPane.showMessageDialog(CetakStrukFrame.this, 
                        "ID Transaksi tidak valid!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Tambahkan panel input, area teks, dan tombol kembali ke panel utama
        panel.add(inputPanel, BorderLayout.NORTH); // Panel input di atas
        panel.add(scrollPane, BorderLayout.CENTER); // Scroll pane di tengah
        panel.add(backButton, BorderLayout.SOUTH); // Tombol kembali di bawah

        // Tambahkan panel utama ke frame
        add(panel);
    }
}
