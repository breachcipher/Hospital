package views;

import dao.RuanganDAO;
import models.Ruangan;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FormRuangan extends JPanel {
    private JTextField txtKdRuangan, txtNamaRuangan, txtKapasitas, txtHarga;
    private JComboBox<String> cbJenisRuangan, cbTersedia;
    private JTable table;
    private DefaultTableModel tableModel;
    private RuanganDAO ruanganDAO;
    
    public FormRuangan() {
        ruanganDAO = new RuanganDAO();
        initComponents();
        refreshTable();
        generateKode();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Data Ruangan"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Kode Ruangan
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Kode Ruangan:"), gbc);
        gbc.gridx = 1;
        txtKdRuangan = new JTextField(15);
        txtKdRuangan.setEditable(false);
        txtKdRuangan.setBackground(Color.LIGHT_GRAY);
        formPanel.add(txtKdRuangan, gbc);
        
        // Nama Ruangan
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Nama Ruangan:"), gbc);
        gbc.gridx = 1;
        txtNamaRuangan = new JTextField(25);
        formPanel.add(txtNamaRuangan, gbc);
        
        // Jenis Ruangan
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Jenis Ruangan:"), gbc);
        gbc.gridx = 1;
        String[] jenisOptions = {"VIP", "Kelas 1", "Kelas 2", "Kelas 3", "ICU"};
        cbJenisRuangan = new JComboBox<>(jenisOptions);
        formPanel.add(cbJenisRuangan, gbc);
        
        // Kapasitas
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Kapasitas (orang):"), gbc);
        gbc.gridx = 1;
        txtKapasitas = new JTextField(10);
        formPanel.add(txtKapasitas, gbc);
        
        // Tersedia
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Status Tersedia:"), gbc);
        gbc.gridx = 1;
        cbTersedia = new JComboBox<>(new String[]{"Ya", "Tidak"});
        formPanel.add(cbTersedia, gbc);
        
        // Harga per hari
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Harga per Hari (Rp):"), gbc);
        gbc.gridx = 1;
        txtHarga = new JTextField(15);
        formPanel.add(txtHarga, gbc);
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnTambah = new JButton("Tambah");
        JButton btnEdit = new JButton("Edit");
        JButton btnHapus = new JButton("Hapus");
        JButton btnReset = new JButton("Reset");
        
        btnTambah.addActionListener(e -> tambahRuangan());
        btnEdit.addActionListener(e -> editRuangan());
        btnHapus.addActionListener(e -> hapusRuangan());
        btnReset.addActionListener(e -> resetForm());
        
        btnPanel.add(btnTambah);
        btnPanel.add(btnEdit);
        btnPanel.add(btnHapus);
        btnPanel.add(btnReset);
        formPanel.add(btnPanel, gbc);
        
        // Table
        String[] columns = {"Kode", "Nama Ruangan", "Jenis Ruangan", "Kapasitas", "Tersedia", "Harga/Hari"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Daftar Ruangan"));
        
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtKdRuangan.setText(table.getValueAt(row, 0).toString());
                    txtNamaRuangan.setText(table.getValueAt(row, 1).toString());
                    cbJenisRuangan.setSelectedItem(table.getValueAt(row, 2).toString());
                    txtKapasitas.setText(table.getValueAt(row, 3).toString());
                    cbTersedia.setSelectedItem(table.getValueAt(row, 4).toString());
                    String harga = table.getValueAt(row, 5).toString().replace("Rp ", "").replace(",", "");
                    txtHarga.setText(harga);
                }
            }
        });
        
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void tambahRuangan() {
        if (txtNamaRuangan.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama Ruangan harus diisi!");
            return;
        }
        
        try {
            Ruangan ruangan = new Ruangan(
                txtKdRuangan.getText(),
                txtNamaRuangan.getText(),
                cbJenisRuangan.getSelectedItem().toString(),
                Integer.parseInt(txtKapasitas.getText()),
                cbTersedia.getSelectedItem().toString(),
                Double.parseDouble(txtHarga.getText())
            );
            
            if (ruanganDAO.insert(ruangan)) {
                JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!");
                refreshTable();
                resetForm();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Kapasitas dan Harga harus berupa angka!");
        }
    }
    
    private void editRuangan() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan diedit!");
            return;
        }
        
        try {
            Ruangan ruangan = new Ruangan(
                txtKdRuangan.getText(),
                txtNamaRuangan.getText(),
                cbJenisRuangan.getSelectedItem().toString(),
                Integer.parseInt(txtKapasitas.getText()),
                cbTersedia.getSelectedItem().toString(),
                Double.parseDouble(txtHarga.getText())
            );
            
            if (ruanganDAO.update(ruangan)) {
                JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
                refreshTable();
                resetForm();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Kapasitas dan Harga harus berupa angka!");
        }
    }
    
    private void hapusRuangan() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String kdRuangan = table.getValueAt(row, 0).toString();
            if (ruanganDAO.delete(kdRuangan)) {
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                refreshTable();
                resetForm();
            }
        }
    }
    
    private void resetForm() {
        txtNamaRuangan.setText("");
        cbJenisRuangan.setSelectedIndex(0);
        txtKapasitas.setText("");
        cbTersedia.setSelectedIndex(0);
        txtHarga.setText("");
        generateKode();
        table.clearSelection();
    }
    
    private void generateKode() {
        txtKdRuangan.setText(ruanganDAO.generateKode());
    }
    
    public void refreshTable() {
        tableModel.setRowCount(0);
        for (Ruangan r : ruanganDAO.getAll()) {
            tableModel.addRow(new Object[]{
                r.getKdRuangan(), r.getNamaRuangan(), r.getJenisRuangan(),
                r.getKapasitas(), r.getTersedia(), String.format("Rp %,d", (long) r.getHargaPerHari())
            });
        }
    }
}