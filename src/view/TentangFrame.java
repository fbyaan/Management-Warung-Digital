package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TentangFrame extends JFrame {

    public TentangFrame() {
        setTitle("Tentang Aplikasi");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel judulLabel = new JLabel("Sistem Manajemen Warung Digital");
        judulLabel.setHorizontalAlignment(SwingConstants.CENTER);
        judulLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JTextArea informasiArea = new JTextArea(
                "Aplikasi ini dibuat untuk membantu pengelolaan warung secara digital.\n\n" +
                        "Versi: 1.0\n" +
                        "Dibuat oleh: Fakhrudidn dan Fabian\n" +
                        "Tanggal: 14 Januari 2025"
        );
        informasiArea.setEditable(false);
        informasiArea.setLineWrap(true);
        informasiArea.setWrapStyleWord(true);

        JLabel kontakLabel = new JLabel("Kontak Dukungan Teknis: 081234567890");
        kontakLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton backButton = new JButton("Kembali ke Menu Utama");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuUtamaFrame().setVisible(true);
            }
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(backButton);

        panel.add(judulLabel, BorderLayout.NORTH);
        panel.add(new JScrollPane(informasiArea), BorderLayout.CENTER);  // Add scroll pane
        panel.add(kontakLabel, BorderLayout.SOUTH);
        add(panel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}