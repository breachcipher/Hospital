package models;

import java.util.Date;

public class Obat {
    private int id;
    private String kdObat;
    private String namaObat;
    private String jenisObat;
    private int stok;
    private double harga;
    private Date expiredDate;
    
    public Obat() {}
    
    public Obat(String kdObat, String namaObat, String jenisObat, int stok, double harga, Date expiredDate) {
        this.kdObat = kdObat;
        this.namaObat = namaObat;
        this.jenisObat = jenisObat;
        this.stok = stok;
        this.harga = harga;
        this.expiredDate = expiredDate;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getKdObat() { return kdObat; }
    public void setKdObat(String kdObat) { this.kdObat = kdObat; }
    public String getNamaObat() { return namaObat; }
    public void setNamaObat(String namaObat) { this.namaObat = namaObat; }
    public String getJenisObat() { return jenisObat; }
    public void setJenisObat(String jenisObat) { this.jenisObat = jenisObat; }
    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }
    public double getHarga() { return harga; }
    public void setHarga(double harga) { this.harga = harga; }
    public Date getExpiredDate() { return expiredDate; }
    public void setExpiredDate(Date expiredDate) { this.expiredDate = expiredDate; }
}
