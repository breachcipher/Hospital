package models;

import java.util.Date;

public class Pasien {
    private int id;
    private String kdPasien;
    private String namaPasien;
    private String tempatLahir;
    private Date tglLahir;
    private String jk;
    private String alamat;
    private String noTelp;
    
    public Pasien() {}
    
    public Pasien(String kdPasien, String namaPasien, String tempatLahir, Date tglLahir, String jk, String alamat, String noTelp) {
        this.kdPasien = kdPasien;
        this.namaPasien = namaPasien;
        this.tempatLahir = tempatLahir;
        this.tglLahir = tglLahir;
        this.jk = jk;
        this.alamat = alamat;
        this.noTelp = noTelp;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getKdPasien() { return kdPasien; }
    public void setKdPasien(String kdPasien) { this.kdPasien = kdPasien; }
    public String getNamaPasien() { return namaPasien; }
    public void setNamaPasien(String namaPasien) { this.namaPasien = namaPasien; }
    public String getTempatLahir() { return tempatLahir; }
    public void setTempatLahir(String tempatLahir) { this.tempatLahir = tempatLahir; }
    public Date getTglLahir() { return tglLahir; }
    public void setTglLahir(Date tglLahir) { this.tglLahir = tglLahir; }
    public String getJk() { return jk; }
    public void setJk(String jk) { this.jk = jk; }
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    public String getNoTelp() { return noTelp; }
    public void setNoTelp(String noTelp) { this.noTelp = noTelp; }
}