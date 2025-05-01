// package view;

// import javax.swing.*;
// import java.awt.*;

// public class SplashScreen extends JWindow {

//     private int duration; // Durasi waktu splash screen akan ditampilkan (dalam milidetik)

//     // Konstruktor yang menerima durasi tampilan splash screen
//     public SplashScreen(int d) {
//         duration = d;
//     }

//     // Metode untuk menampilkan splash screen
//     public void showSplash() {

//         // Panel konten splash screen
//         JPanel content = (JPanel) getContentPane();
//         content.setBackground(Color.WHITE); // Warna latar belakang putih

//         // Mengatur ukuran dan posisi jendela agar berada di tengah layar
//         int width = 450;
//         int height = 300;
//         Dimension screen = Toolkit.getDefaultToolkit().getScreenSize(); // Mendapatkan ukuran layar
//         int x = (screen.width - width) / 2;
//         int y = (screen.height - height) / 2;
//         setBounds(x, y, width, height); // Mengatur posisi dan ukuran jendela

        
//         // Membuat border dengan warna oranye
//         Color oraRed = new Color(255, 165, 0, 255);
//         content.setBorder(BorderFactory.createLineBorder(oraRed, 2));

//         // Menampilkan splash screen
//         setVisible(true);

//         // Menunggu selama durasi yang telah ditentukan
//         try {
//             Thread.sleep(duration);
//         } catch (Exception e) {
//             e.printStackTrace();
//         }

//         // Menutup splash screen
//         dispose();
//     }

//     // Metode untuk menampilkan splash screen lalu membuka LoginFrame
//     public void showSplashAndExit() {

//         // Tidak menampilkan splash screen, langsung membuka LoginFrame
//         SwingUtilities.invokeLater(() -> {
//             try {
//                 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Menggunakan tema sistem operasi
//             } catch (Exception e) {
//                 e.printStackTrace();
//             }
//             new LoginFrame().setVisible(true); // Membuka frame login
//         });
//     }

//     // Metode main untuk menjalankan aplikasi dan langsung membuka program utama
//     public static void main(String[] args) {
//         // Menjalankan GUI di dalam Event Dispatching Thread
//         SwingUtilities.invokeLater(new Runnable() {
//             public void run() {
//                 SplashScreen splash = new SplashScreen(0); 
//                 splash.showSplashAndExit(); // Tidak menampilkan splash screen, langsung membuka LoginFrame
//             }
//         });
//     }
// }
