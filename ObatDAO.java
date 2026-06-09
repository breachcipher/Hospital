package dao;

import database.DatabaseConnection;
import models.Obat;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ObatDAO {
    
    public boolean insert(Obat obat) {
        String sql = "INSERT INTO obat (kd_obat, nama_obat, jenis_obat, stok, harga, expired_date) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, obat.getKdObat());
            pstmt.setString(2, obat.getNamaObat());
            pstmt.setString(3, obat.getJenisObat());
            pstmt.setInt(4, obat.getStok());
            pstmt.setDouble(5, obat.getHarga());
            pstmt.setDate(6, new java.sql.Date(obat.getExpiredDate().getTime()));
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        }
    }
    
    public List<Obat> getAll() {
        List<Obat> list = new ArrayList<>();
        String sql = "SELECT * FROM obat ORDER BY kd_obat";
        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Obat o = new Obat();
                o.setId(rs.getInt("id"));
                o.setKdObat(rs.getString("kd_obat"));
                o.setNamaObat(rs.getString("nama_obat"));
                o.setJenisObat(rs.getString("jenis_obat"));
                o.setStok(rs.getInt("stok"));
                o.setHarga(rs.getDouble("harga"));
                o.setExpiredDate(rs.getDate("expired_date"));
                list.add(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean update(Obat obat) {
        String sql = "UPDATE obat SET nama_obat=?, jenis_obat=?, stok=?, harga=?, expired_date=? WHERE kd_obat=?";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, obat.getNamaObat());
            pstmt.setString(2, obat.getJenisObat());
            pstmt.setInt(3, obat.getStok());
            pstmt.setDouble(4, obat.getHarga());
            pstmt.setDate(5, new java.sql.Date(obat.getExpiredDate().getTime()));
            pstmt.setString(6, obat.getKdObat());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        }
    }
    
    public boolean delete(String kdObat) {
        String sql = "DELETE FROM obat WHERE kd_obat=?";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, kdObat);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        }
    }
    
    public int getCount() {
        String sql = "SELECT COUNT(*) FROM obat";
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
        return String.format("OBT%03d", count + 1);
    }
}