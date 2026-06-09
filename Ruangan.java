package models;

public class Ruangan {
    private int id;
    private String kdRuangan;
    private String namaRuangan;
    private String jenisRuangan;
    private int kapasitas;
    private String tersedia;
    private double hargaPerHari;
    
    public Ruangan() {}
    
    public Ruangan(String kdRuangan, String namaRuangan, String jenisRuangan, int kapasitas, String tersedia, double hargaPerHari) {
        this.kdRuangan = kdRuangan;
        this.namaRuangan = namaRuangan;
        this.jenisRuangan = jenisRuangan;
        this.kapasitas = kapasitas;
        this.tersedia = tersedia;
        this.hargaPerHari = hargaPerHari;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getKdRuangan() { return kdRuangan; }
    public void setKdRuangan(String kdRuangan) { this.kdRuangan = kdRuangan; }
    public String getNamaRuangan() { return namaRuangan; }
    public void setNamaRuangan(String namaRuangan) { this.namaRuangan = namaRuangan; }
    public String getJenisRuangan() { return jenisRuangan; }
    public void setJenisRuangan(String jenisRuangan) { this.jenisRuangan = jenisRuangan; }
    public int getKapasitas() { return kapasitas; }
    public void setKapasitas(int kapasitas) { this.kapasitas = kapasitas; }
    public String getTersedia() { return tersedia; }
    public void setTersedia(String tersedia) { this.tersedia = tersedia; }
    public double getHargaPerHari() { return hargaPerHari; }
    public void setHargaPerHari(double hargaPerHari) { this.hargaPerHari = hargaPerHari; }
}