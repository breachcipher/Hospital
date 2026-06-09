package dao;

import database.DatabaseConnection;
import models.Ruangan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class RuanganDAO {
    
    public boolean insert(Ruangan ruangan) {
        String sql = "INSERT INTO ruangan (kd_ruangan, nama_ruangan, jenis_ruangan, kapasitas, tersedia, harga_per_hari) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, ruangan.getKdRuangan());
            pstmt.setString(2, ruangan.getNamaRuangan());
            pstmt.setString(3, ruangan.getJenisRuangan());
            pstmt.setInt(4, ruangan.getKapasitas());
            pstmt.setString(5, ruangan.getTersedia());
            pstmt.setDouble(6, ruangan.getHargaPerHari());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        }
    }
    
    public List<Ruangan> getAll() {
        List<Ruangan> list = new ArrayList<>();
        String sql = "SELECT * FROM ruangan ORDER BY kd_ruangan";
        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Ruangan r = new Ruangan();
                r.setId(rs.getInt("id"));
                r.setKdRuangan(rs.getString("kd_ruangan"));
                r.setNamaRuangan(rs.getString("nama_ruangan"));
                r.setJenisRuangan(rs.getString("jenis_ruangan"));
                r.setKapasitas(rs.getInt("kapasitas"));
                r.setTersedia(rs.getString("tersedia"));
                r.setHargaPerHari(rs.getDouble("harga_per_hari"));
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean update(Ruangan ruangan) {
        String sql = "UPDATE ruangan SET nama_ruangan=?, jenis_ruangan=?, kapasitas=?, tersedia=?, harga_per_hari=? WHERE kd_ruangan=?";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, ruangan.getNamaRuangan());
            pstmt.setString(2, ruangan.getJenisRuangan());
            pstmt.setInt(3, ruangan.getKapasitas());
            pstmt.setString(4, ruangan.getTersedia());
            pstmt.setDouble(5, ruangan.getHargaPerHari());
            pstmt.setString(6, ruangan.getKdRuangan());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        }
    }
    
    public boolean delete(String kdRuangan) {
        String sql = "DELETE FROM ruangan WHERE kd_ruangan=?";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, kdRuangan);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        }
    }
    
    public int getCount() {
        String sql = "SELECT COUNT(*) FROM ruangan";
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
        return String.format("RGN%03d", count + 1);
    }
    
    public List<String> getKodeList() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT kd_ruangan, nama_ruangan FROM ruangan WHERE tersedia='Ya' ORDER BY kd_ruangan";
        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(rs.getString("kd_ruangan") + " - " + rs.getString("nama_ruangan"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public String getNamaByKode(String kdRuangan) {
        String sql = "SELECT nama_ruangan FROM ruangan WHERE kd_ruangan=?";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, kdRuangan);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getString("nama_ruangan");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}