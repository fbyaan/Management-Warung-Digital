package view;

import db.TransaksiDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class LaporanBulananFrame extends JFrame {

    private JTextArea laporanArea;
    private JTextField bulanTahunField;

    public LaporanBulananFrame() {
        setTitle("Laporan Penjualan Bulanan");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel bulanTahunLabel = new JLabel("Bulan Tahun (YYYY-MM):");
        bulanTahunField = new JTextField(10);
        JButton generateButton = new JButton("Generate Laporan");
        inputPanel.add(bulanTahunLabel);
        inputPanel.add(bulanTahunField);
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
                    YearMonth bulanTahun = YearMonth.parse(bulanTahunField.getText(), DateTimeFormatter.ofPattern("yyyy-MM"));

                    TransaksiDAO transaksiDAO = new TransaksiDAO();
                    String laporan = transaksiDAO.generateLaporanBulanan(bulanTahun);
                    laporanArea.setText(laporan);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(LaporanBulananFrame.this, "Format bulan tahun tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);

        add(panel);
    }
}