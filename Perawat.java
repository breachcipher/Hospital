package models;

public class Perawat {
    private int id;
    private String kdPerawat;
    private String namaPerawat;
    private String spesialis;
    private String jk;
    private String noTelp;
    private String alamat;
    
    public Perawat() {}
    
    public Perawat(String kdPerawat, String namaPerawat, String spesialis, String jk, String noTelp, String alamat) {
        this.kdPerawat = kdPerawat;
        this.namaPerawat = namaPerawat;
        this.spesialis = spesialis;
        this.jk = jk;
        this.noTelp = noTelp;
        this.alamat = alamat;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getKdPerawat() { return kdPerawat; }
    public void setKdPerawat(String kdPerawat) { this.kdPerawat = kdPerawat; }
    public String getNamaPerawat() { return namaPerawat; }
    public void setNamaPerawat(String namaPerawat) { this.namaPerawat = namaPerawat; }
    public String getSpesialis() { return spesialis; }
    public void setSpesialis(String spesialis) { this.spesialis = spesialis; }
    public String getJk() { return jk; }
    public void setJk(String jk) { this.jk = jk; }
    public String getNoTelp() { return noTelp; }
    public void setNoTelp(String noTelp) { this.noTelp = noTelp; }
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
}