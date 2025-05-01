package view;

import db.ProdukDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HapusProdukFrame extends JFrame {

    private JTextField idField;

    public HapusProdukFrame() {
        setTitle("Hapus Produk");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel idLabel = new JLabel("ID Produk yang akan dihapus:");
        idField = new JTextField();

        JButton hapusButton = new JButton("Hapus");
        JButton backButton = new JButton("Kembali");

        hapusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(idField.getText());

                    ProdukDAO produkDAO = new ProdukDAO();
                    produkDAO.hapusProduk(id);

                    JOptionPane.showMessageDialog(HapusProdukFrame.this, "Produk berhasil dihapus!");
                    dispose();
                    new ProdukFrame().setVisible(true);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(HapusProdukFrame.this, "ID Produk tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ProdukFrame().setVisible(true);
            }
        });

        panel.add(idLabel);
        panel.add(idField);
        panel.add(hapusButton);
        panel.add(backButton);

        add(panel);
    }
}