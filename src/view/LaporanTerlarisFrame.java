package view;

import db.TransaksiDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LaporanTerlarisFrame extends JFrame {

    private JTextArea laporanArea;

    public LaporanTerlarisFrame() {
        setTitle("Laporan Produk Terlaris");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        laporanArea = new JTextArea();
        laporanArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(laporanArea);

        JButton generateButton = new JButton("Generate Laporan");
        JButton backButton = new JButton("Kembali");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(generateButton);
        buttonPanel.add(backButton);

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TransaksiDAO transaksiDAO = new TransaksiDAO();
                String laporan = transaksiDAO.generateLaporanTerlaris();
                laporanArea.setText(laporan);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LaporanFrame().setVisible(true);
            }
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }
}