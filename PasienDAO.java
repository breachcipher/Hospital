package dao;

import database.DatabaseConnection;
import models.Pasien;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class PasienDAO {
    
    public boolean insert(Pasien pasien) {
        String sql = "INSERT INTO pasien (kd_pasien, nama_pasien, tempat_lahir, tgl_lahir, jk, alamat, no_telp) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, pasien.getKdPasien());
            pstmt.setString(2, pasien.getNamaPasien());
            pstmt.setString(3, pasien.getTempatLahir());
            pstmt.setDate(4, new java.sql.Date(pasien.getTglLahir().getTime()));
            pstmt.setString(5, pasien.getJk());
            pstmt.setString(6, pasien.getAlamat());
            pstmt.setString(7, pasien.getNoTelp());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        }
    }
    
    public List<Pasien> getAll() {
        List<Pasien> list = new ArrayList<>();
        String sql = "SELECT * FROM pasien ORDER BY kd_pasien";
        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Pasien p = new Pasien();
                p.setId(rs.getInt("id"));
                p.setKdPasien(rs.getString("kd_pasien"));
                p.setNamaPasien(rs.getString("nama_pasien"));
                p.setTempatLahir(rs.getString("tempat_lahir"));
                p.setTglLahir(rs.getDate("tgl_lahir"));
                p.setJk(rs.getString("jk"));
                p.setAlamat(rs.getString("alamat"));
                p.setNoTelp(rs.getString("no_telp"));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean update(Pasien pasien) {
        String sql = "UPDATE pasien SET nama_pasien=?, tempat_lahir=?, tgl_lahir=?, jk=?, alamat=?, no_telp=? WHERE kd_pasien=?";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, pasien.getNamaPasien());
            pstmt.setString(2, pasien.getTempatLahir());
            pstmt.setDate(3, new java.sql.Date(pasien.getTglLahir().getTime()));
            pstmt.setString(4, pasien.getJk());
            pstmt.setString(5, pasien.getAlamat());
            pstmt.setString(6, pasien.getNoTelp());
            pstmt.setString(7, pasien.getKdPasien());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        }
    }
    
    public boolean delete(String kdPasien) {
        String sql = "DELETE FROM pasien WHERE kd_pasien=?";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, kdPasien);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        }
    }
    
    public int getCount() {
        String sql = "SELECT COUNT(*) FROM pasien";
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
        return String.format("PSN%03d", count + 1);
    }
    
    public List<String> getKodeList() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT kd_pasien, nama_pasien FROM pasien ORDER BY kd_pasien";
        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(rs.getString("kd_pasien") + " - " + rs.getString("nama_pasien"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public String getNamaByKode(String kdPasien) {
        String sql = "SELECT nama_pasien FROM pasien WHERE kd_pasien=?";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, kdPasien);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getString("nama_pasien");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}