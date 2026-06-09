package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DatabaseConnection {
    private static Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/hospital_management?useSSL=false&serverTimezone=Asia/Jakarta";
    private static final String USERNAME = "hospital";
    private static final String PASSWORD = "";
    
    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Perbaiki: ini driver yang benar untuk MySQL 8+
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Koneksi database berhasil!");
                
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, 
                    "Driver MySQL tidak ditemukan!\n" +
                    "Pastikan mysql-connector-java.jar sudah ditambahkan ke Libraries\n\n" +
                    "Error: " + e.getMessage(), 
                    "Database Error", 
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, 
                    "Gagal koneksi ke database!\n" +
                    "Pastikan MySQL server sedang berjalan (XAMPP/MAMP)\n\n" +
                    "Error: " + e.getMessage(), 
                    "Database Error", 
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
        return connection;
    }
    
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Koneksi database ditutup!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    // Method untuk testing koneksi
    public static void testConnection() {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("✅ Database terhubung!");
            JOptionPane.showMessageDialog(null, 
                "✅ Database berhasil terhubung!\n" +
                "Server: localhost\n" +
                "Database: hospital_management", 
                "Sukses", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("❌ Gagal koneksi database!");
        }
    }
    
    public static void main(String[] args) {
        testConnection();
    }
}
