package models;

import java.util.Date;

public class Pendaftaran {
    private int id;
    private String noPendaftaran;
    private Date tglPendaftaran;
    private String kdPasien;
    private String namaPasien;
    private String kdPerawat;
    private String namaPerawat;
    private String kdRuangan;
    private String namaRuangan;
    private String keluhan;
    private String diagnosa;
    private String status;
    
    public Pendaftaran() {}
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNoPendaftaran() { return noPendaftaran; }
    public void setNoPendaftaran(String noPendaftaran) { this.noPendaftaran = noPendaftaran; }
    public Date getTglPendaftaran() { return tglPendaftaran; }
    public void setTglPendaftaran(Date tglPendaftaran) { this.tglPendaftaran = tglPendaftaran; }
    public String getKdPasien() { return kdPasien; }
    public void setKdPasien(String kdPasien) { this.kdPasien = kdPasien; }
    public String getNamaPasien() { return namaPasien; }
    public void setNamaPasien(String namaPasien) { this.namaPasien = namaPasien; }
    public String getKdPerawat() { return kdPerawat; }
    public void setKdPerawat(String kdPerawat) { this.kdPerawat = kdPerawat; }
    public String getNamaPerawat() { return namaPerawat; }
    public void setNamaPerawat(String namaPerawat) { this.namaPerawat = namaPerawat; }
    public String getKdRuangan() { return kdRuangan; }
    public void setKdRuangan(String kdRuangan) { this.kdRuangan = kdRuangan; }
    public String getNamaRuangan() { return namaRuangan; }
    public void setNamaRuangan(String namaRuangan) { this.namaRuangan = namaRuangan; }
    public String getKeluhan() { return keluhan; }
    public void setKeluhan(String keluhan) { this.keluhan = keluhan; }
    public String getDiagnosa() { return diagnosa; }
    public void setDiagnosa(String diagnosa) { this.diagnosa = diagnosa; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}