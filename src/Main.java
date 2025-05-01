

import javax.swing.*;

import view.LoginFrame;

public class Main {
    public static void main(String[] args) {
        // Menjalankan GUI di dalam Event Dispatching Thread
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Menggunakan tema sistem operasi
            } catch (Exception e) {
                e.printStackTrace();
            }
            new LoginFrame().setVisible(true); // Membuka frame login
        });
    }
}
