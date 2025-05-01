package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransaksiFrame extends JFrame {

    public TransaksiFrame() {
        setTitle("Manajemen Transaksi");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton buatButton = new JButton("Buat Transaksi Baru");
        JButton riwayatButton = new JButton("Lihat Riwayat Transaksi");
        JButton cetakButton = new JButton("Cetak Struk Transaksi");
        JButton backButton = new JButton("Kembali ke Menu Utama");

        buatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new BuatTransaksiFrame().setVisible(true);
            }
        });

        riwayatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RiwayatTransaksiFrame().setVisible(true);
            }
        });

        cetakButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CetakStrukFrame().setVisible(true);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuUtamaFrame().setVisible(true);
            }
        });

        panel.add(buatButton);
        panel.add(riwayatButton);
        panel.add(cetakButton);
        panel.add(backButton);

        add(panel);
    }
}