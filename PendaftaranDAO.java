package dao;

import database.DatabaseConnection;
import models.Pendaftaran;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class PendaftaranDAO {
    
    public boolean insert(Pendaftaran pendaftaran) {
        String sql = "INSERT INTO pendaftaran (no_pendaftaran, tgl_pendaftaran, kd_pasien, kd_perawat, kd_ruangan, keluhan, diagnosa, status) VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, pendaftaran.getNoPendaftaran());
            pstmt.setDate(2, new java.sql.Date(pendaftaran.getTglPendaftaran().getTime()));
            pstmt.setString(3, pendaftaran.getKdPasien());
            pstmt.setString(4, pendaftaran.getKdPerawat());
            pstmt.setString(5, pendaftaran.getKdRuangan());
            pstmt.setString(6, pendaftaran.getKeluhan());
            pstmt.setString(7, pendaftaran.getDiagnosa());
            pstmt.setString(8, pendaftaran.getStatus());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        }
    }
    
    public List<Pendaftaran> getAll() {
        List<Pendaftaran> list = new ArrayList<>();
        String sql = "SELECT p.*, ps.nama_pasien, pr.nama_perawat, r.nama_ruangan " +
                     "FROM pendaftaran p " +
                     "LEFT JOIN pasien ps ON p.kd_pasien = ps.kd_pasien " +
                     "LEFT JOIN perawat pr ON p.kd_perawat = pr.kd_perawat " +
                     "LEFT JOIN ruangan r ON p.kd_ruangan = r.kd_ruangan " +
                     "ORDER BY p.tgl_pendaftaran DESC";
        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Pendaftaran pend = new Pendaftaran();
                pend.setId(rs.getInt("id"));
                pend.setNoPendaftaran(rs.getString("no_pendaftaran"));
                pend.setTglPendaftaran(rs.getDate("tgl_pendaftaran"));
                pend.setKdPasien(rs.getString("kd_pasien"));
                pend.setNamaPasien(rs.getString("nama_pasien"));
                pend.setKdPerawat(rs.getString("kd_perawat"));
                pend.setNamaPerawat(rs.getString("nama_perawat"));
                pend.setKdRuangan(rs.getString("kd_ruangan"));
                pend.setNamaRuangan(rs.getString("nama_ruangan"));
                pend.setKeluhan(rs.getString("keluhan"));
                pend.setDiagnosa(rs.getString("diagnosa"));
                pend.setStatus(rs.getString("status"));
                list.add(pend);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean update(Pendaftaran pendaftaran) {
        String sql = "UPDATE pendaftaran SET keluhan=?, diagnosa=?, status=? WHERE no_pendaftaran=?";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, pendaftaran.getKeluhan());
            pstmt.setString(2, pendaftaran.getDiagnosa());
            pstmt.setString(3, pendaftaran.getStatus());
            pstmt.setString(4, pendaftaran.getNoPendaftaran());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        }
    }
    
    public boolean delete(String noPendaftaran) {
        String sql = "DELETE FROM pendaftaran WHERE no_pendaftaran=?";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, noPendaftaran);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        }
    }
    
    public int getCount() {
        String sql = "SELECT COUNT(*) FROM pendaftaran";
        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public String generateNoPendaftaran() {
        int count = getCount();
        return "REG" + new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date()) + String.format("%03d", count + 1);
    }
}
