package view;

import db.TransaksiDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LaporanHarianFrame extends JFrame {

    private JTextArea laporanArea;
    private JTextField tanggalField;

    public LaporanHarianFrame() {
        setTitle("Laporan Penjualan Harian");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel tanggalLabel = new JLabel("Tanggal (YYYY-MM-DD):");
        tanggalField = new JTextField(10);
        JButton generateButton = new JButton("Generate Laporan");
        inputPanel.add(tanggalLabel);
        inputPanel.add(tanggalField);
        inputPanel.add(generateButton);

        laporanArea = new JTextArea();
        laporanArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(laporanArea);

        JButton backButton = new JButton("Kembali");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LaporanFrame().setVisible(true);
            }
        });

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LocalDate tanggal = LocalDate.parse(tanggalField.getText(), DateTimeFormatter.ISO_LOCAL_DATE);

                    TransaksiDAO transaksiDAO = new TransaksiDAO();
                    String laporan = transaksiDAO.generateLaporanHarian(tanggal);
                    laporanArea.setText(laporan);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(LaporanHarianFrame.this, "Format tanggal tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);

        add(panel);
    }
}