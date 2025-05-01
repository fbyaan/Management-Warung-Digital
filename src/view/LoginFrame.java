// Package view untuk tampilan GUI
package view;

import db.UserDAO; // Import UserDAO untuk operasi login di database

import javax.swing.*; // Library untuk membuat GUI
import java.awt.*; // Library untuk layout GUI
import java.awt.event.ActionEvent; // Library untuk aksi pada tombol
import java.awt.event.ActionListener; // Library untuk mendengarkan aksi tombol
import java.awt.event.KeyAdapter; // Library untuk menangani event keyboard
import java.awt.event.KeyEvent; // Library untuk event key

// Kelas utama untuk frame login
public class LoginFrame extends JFrame {
    // Komponen GUI untuk input dan tombol
    private JTextField usernameField; // Input teks untuk username
    private JPasswordField passwordField; // Input teks untuk password

    // Konstruktor untuk inisialisasi frame login
    public LoginFrame() {
        setTitle("Login - Warung Digital"); // Judul frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Tutup aplikasi saat frame ditutup
        setSize(450, 350); // Ukuran frame
        setLocationRelativeTo(null); // Tempatkan frame di tengah layar

        // Panel utama dengan layout BoxLayout untuk tampilan modern
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30)); // Tambahkan margin di sekeliling panel
        panel.setBackground(Color.decode("#f0f8ff")); // Warna latar belakang yang lembut

        // Tambahkan header untuk judul aplikasi
        JLabel titleLabel = new JLabel("Warung Digital");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26)); // Font besar untuk judul
        titleLabel.setForeground(Color.decode("#4682b4")); // Warna teks biru
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Pusatkan label

        // Spasi antara elemen
        panel.add(Box.createVerticalStrut(20));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(30));

        // Panel untuk form input username dan password
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(2, 2, 10, 10)); // Grid 2x2 untuk input
        formPanel.setBackground(Color.decode("#f0f8ff"));

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField = new JTextField();
        usernameField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        usernameField.setPreferredSize(new Dimension(200, 30)); // Atur ukuran field input teks

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        passwordField.setPreferredSize(new Dimension(200, 30)); // Atur ukuran field input teks

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        panel.add(formPanel);
        panel.add(Box.createVerticalStrut(20)); // Spasi antara form dan tombol

        // Tombol login dan register
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        // Desain tombol
        loginButton.setBackground(Color.decode("#4682b4"));
        loginButton.setForeground(Color.black);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setFocusPainted(false);

        registerButton.setBackground(Color.decode("#87cefa"));
        registerButton.setForeground(Color.BLACK);
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setFocusPainted(false);

        // Panel untuk tombol
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(Color.decode("#f0f8ff"));
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        panel.add(buttonPanel);

        // Aksi saat tombol login ditekan
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText(); // Ambil teks username dari input
                String password = new String(passwordField.getPassword()); // Ambil password sebagai string

                UserDAO userDAO = new UserDAO(); // Buat objek UserDAO untuk operasi database
                if (userDAO.login(username, password)) { // Panggil metode login pada UserDAO
                    JOptionPane.showMessageDialog(LoginFrame.this, "Login Berhasil!");

                    // Inisialisasi database pengguna jika belum ada
                    userDAO.initializeUserDatabase(username);

                    dispose(); // Tutup frame login
                    new MenuUtamaFrame().setVisible(true); // Tampilkan frame menu utama
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Login Gagal. Periksa username dan password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Aksi untuk tombol register
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Tutup frame login
                new RegisterFrame().setVisible(true); // Tampilkan frame registrasi
            }
        });

        // Tambahkan fitur untuk login menggunakan tombol Enter
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginButton.doClick(); // Panggil aksi tombol login
                }
            }
        });

        // Tambahkan panel utama ke frame
        add(panel);
    }
}
