package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProdukFrame extends JFrame {

    public ProdukFrame() {
        setTitle("Manajemen Produk");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton tambahButton = new JButton("Tambah Produk Baru");
        JButton hapusButton = new JButton("Hapus Produk");
        JButton lihatButton = new JButton("Lihat Daftar Produk");
        JButton editButton = new JButton("Edit Detail Produk");
        JButton backButton = new JButton("Kembali ke Menu Utama");

        tambahButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TambahProdukFrame().setVisible(true);
            }
        });

        hapusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new HapusProdukFrame().setVisible(true);
            }
        });

        lihatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LihatProdukFrame().setVisible(true);
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new EditProdukFrame().setVisible(true);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuUtamaFrame().setVisible(true);
            }
        });

        panel.add(tambahButton);
        panel.add(hapusButton);
        panel.add(lihatButton);
        panel.add(editButton);
        panel.add(backButton);

        add(panel);
    }
}