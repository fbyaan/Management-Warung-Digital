package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LaporanFrame extends JFrame {

    // Constructor LaporanFrame yang akan mengatur tampilan frame
    public LaporanFrame() {
        // Set judul frame
        setTitle("Laporan");
        // Menutup frame saat di-X (disposal frame ini saja, bukan aplikasi)
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Set ukuran frame
        setSize(500, 300);
        // Posisi frame di tengah layar
        setLocationRelativeTo(null);

        // Membuat panel dengan layout GridLayout (4 baris dan 1 kolom)
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10)); // 4 baris, 1 kolom, jarak 10 piksel antar komponen
        // Menambahkan padding sekitar panel
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Membuat tombol untuk laporan harian
        JButton harianButton = new JButton("Laporan Penjualan Harian");
        // Membuat tombol untuk laporan bulanan
        JButton bulananButton = new JButton("Laporan Penjualan Bulanan");
        // Membuat tombol untuk laporan produk terlaris
        JButton terlarisButton = new JButton("Laporan Produk Terlaris");
        // Membuat tombol untuk kembali ke menu utama
        JButton backButton = new JButton("Kembali ke Menu Utama");

        // Menambahkan ActionListener untuk tombol harianButton
        harianButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Menutup frame ini dan membuka LaporanHarianFrame
                dispose();
                new LaporanHarianFrame().setVisible(true);
            }
        });

        // Menambahkan ActionListener untuk tombol bulananButton
        bulananButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Menutup frame ini dan membuka LaporanBulananFrame
                dispose();
                new LaporanBulananFrame().setVisible(true);
            }
        });

        // Menambahkan ActionListener untuk tombol terlarisButton
        terlarisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Menutup frame ini dan membuka LaporanTerlarisFrame
                dispose();
                new LaporanTerlarisFrame().setVisible(true);
            }
        });

        // Menambahkan ActionListener untuk tombol backButton
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Menutup frame ini dan membuka MenuUtamaFrame
                dispose();
                new MenuUtamaFrame().setVisible(true);
            }
        });

        // Menambahkan tombol-tombol ke dalam panel
        panel.add(harianButton);
        panel.add(bulananButton);
        panel.add(terlarisButton);
        panel.add(backButton);

        // Menambahkan panel ke dalam frame
        add(panel);
    }
}
