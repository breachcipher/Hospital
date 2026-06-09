package dao;

import database.DatabaseConnection;
import models.Perawat;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class PerawatDAO {
    
    public boolean insert(Perawat perawat) {
        String sql = "INSERT INTO perawat (kd_perawat, nama_perawat, spesialis, jk, no_telp, alamat) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, perawat.getKdPerawat());
            pstmt.setString(2, perawat.getNamaPerawat());
            pstmt.setString(3, perawat.getSpesialis());
            pstmt.setString(4, perawat.getJk());
            pstmt.setString(5, perawat.getNoTelp());
            pstmt.setString(6, perawat.getAlamat());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        }
    }
    
    public List<Perawat> getAll() {
        List<Perawat> list = new ArrayList<>();
        String sql = "SELECT * FROM perawat ORDER BY kd_perawat";
        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Perawat p = new Perawat();
                p.setId(rs.getInt("id"));
                p.setKdPerawat(rs.getString("kd_perawat"));
                p.setNamaPerawat(rs.getString("nama_perawat"));
                p.setSpesialis(rs.getString("spesialis"));
                p.setJk(rs.getString("jk"));
                p.setNoTelp(rs.getString("no_telp"));
                p.setAlamat(rs.getString("alamat"));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean update(Perawat perawat) {
        String sql = "UPDATE perawat SET nama_perawat=?, spesialis=?, jk=?, no_telp=?, alamat=? WHERE kd_perawat=?";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, perawat.getNamaPerawat());
            pstmt.setString(2, perawat.getSpesialis());
            pstmt.setString(3, perawat.getJk());
            pstmt.setString(4, perawat.getNoTelp());
            pstmt.setString(5, perawat.getAlamat());
            pstmt.setString(6, perawat.getKdPerawat());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        }
    }
    
    public boolean delete(String kdPerawat) {
        String sql = "DELETE FROM perawat WHERE kd_perawat=?";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, kdPerawat);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        }
    }
    
    public int getCount() {
        String sql = "SELECT COUNT(*) FROM perawat";
        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public String generateKode() {
        int count = getCount();
        return String.format("PER%03d", count + 1);
    }
    
    public List<String> getKodeList() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT kd_perawat FROM perawat ORDER BY kd_perawat";
        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(rs.getString("kd_perawat"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}