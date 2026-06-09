-- Buat database
CREATE DATABASE hospital_management;
USE hospital_management;

-- ==================== TABEL PERAWAT ====================
CREATE TABLE perawat (
    id INT AUTO_INCREMENT PRIMARY KEY,
    kd_perawat VARCHAR(10) UNIQUE NOT NULL,
    nama_perawat VARCHAR(100) NOT NULL,
    spesialis VARCHAR(50) NOT NULL,
    jk ENUM('L', 'P') NOT NULL,
    no_telp VARCHAR(20),
    alamat TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==================== TABEL PASIEN ====================
CREATE TABLE pasien (
    id INT AUTO_INCREMENT PRIMARY KEY,
    kd_pasien VARCHAR(10) UNIQUE NOT NULL,
    nama_pasien VARCHAR(100) NOT NULL,
    tempat_lahir VARCHAR(50),
    tgl_lahir DATE,
    jk ENUM('L', 'P') NOT NULL,
    alamat TEXT,
    no_telp VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==================== TABEL OBAT ====================
CREATE TABLE obat (
    id INT AUTO_INCREMENT PRIMARY KEY,
    kd_obat VARCHAR(10) UNIQUE NOT NULL,
    nama_obat VARCHAR(100) NOT NULL,
    jenis_obat VARCHAR(50),
    stok INT DEFAULT 0,
    harga DECIMAL(10,2) DEFAULT 0,
    expired_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==================== TABEL RUANGAN ====================
CREATE TABLE ruangan (
    id INT AUTO_INCREMENT PRIMARY KEY,
    kd_ruangan VARCHAR(10) UNIQUE NOT NULL,
    nama_ruangan VARCHAR(100) NOT NULL,
    jenis_ruangan VARCHAR(50),
    kapasitas INT DEFAULT 1,
    tersedia ENUM('Ya', 'Tidak') DEFAULT 'Ya',
    harga_per_hari DECIMAL(10,2) DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==================== TABEL PENDAFTARAN ====================
CREATE TABLE pendaftaran (
    id INT AUTO_INCREMENT PRIMARY KEY,
    no_pendaftaran VARCHAR(15) UNIQUE NOT NULL,
    tgl_pendaftaran DATE NOT NULL,
    kd_pasien VARCHAR(10),
    kd_perawat VARCHAR(10),
    kd_ruangan VARCHAR(10),
    keluhan TEXT,
    diagnosa TEXT,
    status ENUM('Menunggu', 'Diperiksa', 'Selesai') DEFAULT 'Menunggu',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (kd_pasien) REFERENCES pasien(kd_pasien) ON DELETE SET NULL,
    FOREIGN KEY (kd_perawat) REFERENCES perawat(kd_perawat) ON DELETE SET NULL,
    FOREIGN KEY (kd_ruangan) REFERENCES ruangan(kd_ruangan) ON DELETE SET NULL
);

-- ==================== DATA SAMPLE ====================
-- Data Perawat
INSERT INTO perawat (kd_perawat, nama_perawat, spesialis, jk, no_telp, alamat) VALUES
('PER001', 'Ns. Budi Santoso', 'Spesialis Anak', 'L', '0812-3456-7890', 'Jl. Sehat No. 1'),
('PER002', 'Ns. Siti Aminah', 'Spesialis Penyakit Dalam', 'P', '0812-3456-7891', 'Jl. Sehat No. 2'),
('PER003', 'Ns. Samin Teoan', 'Spesialis Bedah', 'P', '0812-3456-7892', 'Jl. Sehat No. 3'),
('PER004', 'Ns. Kama Penyakit', 'Spesialis Anak', 'L', '0812-3456-7893', 'Jl. Sehat No. 4'),
('PER005', 'Ns. Nama Sioran', 'Spesialis Anak', 'L', '0812-3456-7894', 'Jl. Sehat No. 5');

-- Data Pasien
INSERT INTO pasien (kd_pasien, nama_pasien, tempat_lahir, tgl_lahir, jk, alamat, no_telp) VALUES
('PSN001', 'Ahmad Wijaya', 'Jakarta', '1990-05-15', 'L', 'Jl. Mawar No. 10', '0813-1234-5678'),
('PSN002', 'Siti Nurhaliza', 'Bandung', '1985-08-20', 'P', 'Jl. Melati No. 5', '0813-1234-5679'),
('PSN003', 'Budi Santoso', 'Surabaya', '1995-03-10', 'L', 'Jl. Kenanga No. 7', '0813-1234-5680'),
('PSN004', 'Dewi Kartika', 'Medan', '2000-12-01', 'P', 'Jl. Anggrek No. 3', '0813-1234-5681'),
('PSN005', 'Rudi Hartono', 'Makassar', '1988-07-25', 'L', 'Jl. Flamboyan No. 12', '0813-1234-5682');

-- Data Obat
INSERT INTO obat (kd_obat, nama_obat, jenis_obat, stok, harga, expired_date) VALUES
('OBT001', 'Paracetamol 500mg', 'Analgesik', 100, 5000, '2025-12-31'),
('OBT002', 'Amoxicillin 500mg', 'Antibiotik', 50, 15000, '2025-10-31'),
('OBT003', 'Ibuprofen 400mg', 'Antiinflamasi', 75, 10000, '2025-08-31'),
('OBT004', 'Cetirizine 10mg', 'Antihistamin', 120, 8000, '2025-11-30'),
('OBT005', 'Vitamin C 500mg', 'Vitamin', 200, 3000, '2026-01-31');

-- Data Ruangan
INSERT INTO ruangan (kd_ruangan, nama_ruangan, jenis_ruangan, kapasitas, tersedia, harga_per_hari) VALUES
('RGN001', 'Ruang Melati', 'VIP', 1, 'Ya', 500000),
('RGN002', 'Ruang Mawar', 'Kelas 1', 2, 'Ya', 300000),
('RGN003', 'Ruang Anggrek', 'Kelas 2', 4, 'Ya', 150000),
('RGN004', 'Ruang Kenanga', 'Kelas 3', 6, 'Tidak', 100000),
('RGN005', 'Ruang Flamboyan', 'ICU', 1, 'Ya', 750000);

-- Data Pendaftaran
INSERT INTO pendaftaran (no_pendaftaran, tgl_pendaftaran, kd_pasien, kd_perawat, kd_ruangan, keluhan, diagnosa, status) VALUES
('REG2024001', CURDATE(), 'PSN001', 'PER001', 'RGN001', 'Demam tinggi selama 3 hari', 'Demam Berdarah', 'Diperiksa'),
('REG2024002', CURDATE(), 'PSN002', 'PER002', 'RGN002', 'Sesak nafas', 'Asma', 'Menunggu'),
('REG2024003', CURDATE(), 'PSN003', 'PER003', 'RGN003', 'Sakit kepala hebat', 'Migrain', 'Selesai');