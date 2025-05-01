package view;

import db.TransaksiDAO;
import model.Transaksi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RiwayatTransaksiFrame extends JFrame {

    private JTable transaksiTable;
    private DefaultTableModel tableModel;

    public RiwayatTransaksiFrame() {
        setTitle("Riwayat Transaksi");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] columnNames = {"ID Transaksi", "Tanggal", "Total Harga"};
        tableModel = new DefaultTableModel(columnNames, 0);
        transaksiTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(transaksiTable);

        JButton backButton = new JButton("Kembali");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TransaksiFrame().setVisible(true);
            }
        });

        loadTransaksiData();

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);

        add(panel);
    }

    private void loadTransaksiData() {
        TransaksiDAO transaksiDAO = new TransaksiDAO();
        List<Transaksi> transaksiList = transaksiDAO.getAllTransaksi();

        tableModel.setRowCount(0);

        for (Transaksi transaksi : transaksiList) {
            Object[] rowData = {transaksi.getId(), transaksi.getTanggal(), transaksi.getTotalHarga()};
            tableModel.addRow(rowData);
        }
    }
}