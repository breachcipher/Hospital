package views;

import dao.PendaftaranDAO;
import dao.PasienDAO;
import dao.PerawatDAO;
import dao.RuanganDAO;
import models.Pendaftaran;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

public class FormPendaftaran extends JPanel {
    private JTextField txtNoPendaftaran;
    private JComboBox<String> cbPasien, cbPerawat, cbRuangan;
    private JTextArea taKeluhan, taDiagnosa;
    private JComboBox<String> cbStatus;
    private JSpinner spnTglPendaftaran;
    private JTable table;
    private DefaultTableModel tableModel;
    private PendaftaranDAO pendaftaranDAO;
    private PasienDAO pasienDAO;
    private PerawatDAO perawatDAO;
    private RuanganDAO ruanganDAO;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    public FormPendaftaran() {
        pendaftaranDAO = new PendaftaranDAO();
        pasienDAO = new PasienDAO();
        perawatDAO = new PerawatDAO();
        ruanganDAO = new RuanganDAO();
        initComponents();
        refreshTable();
        generateNoPendaftaran();
        loadComboBoxes();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Pendaftaran Pasien"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // No Pendaftaran
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("No. Pendaftaran:"), gbc);
        gbc.gridx = 1;
        txtNoPendaftaran = new JTextField(20);
        txtNoPendaftaran.setEditable(false);
        txtNoPendaftaran.setBackground(Color.LIGHT_GRAY);
        formPanel.add(txtNoPendaftaran, gbc);
        
        // Tanggal Pendaftaran
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Tanggal Pendaftaran:"), gbc);
        gbc.gridx = 1;
        spnTglPendaftaran = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor de = new JSpinner.DateEditor(spnTglPendaftaran, "yyyy-MM-dd");
        spnTglPendaftaran.setEditor(de);
        formPanel.add(spnTglPendaftaran, gbc);
        
        // Pasien
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Pasien:"), gbc);
        gbc.gridx = 1;
        cbPasien = new JComboBox<>();
        cbPasien.setPreferredSize(new Dimension(250, 25));
        formPanel.add(cbPasien, gbc);
        
        // Perawat
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Perawat:"), gbc);
        gbc.gridx = 1;
        cbPerawat = new JComboBox<>();
        cbPerawat.setPreferredSize(new Dimension(250, 25));
        formPanel.add(cbPerawat, gbc);
        
        // Ruangan
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Ruangan:"), gbc);
        gbc.gridx = 1;
        cbRuangan = new JComboBox<>();
        cbRuangan.setPreferredSize(new Dimension(250, 25));
        formPanel.add(cbRuangan, gbc);
        
        // Keluhan
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Keluhan:"), gbc);
        gbc.gridx = 1;
        taKeluhan = new JTextArea(3, 30);
        JScrollPane spKeluhan = new JScrollPane(taKeluhan);
        formPanel.add(spKeluhan, gbc);
        
        // Diagnosa
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("Diagnosa:"), gbc);
        gbc.gridx = 1;
        taDiagnosa = new JTextArea(3, 30);
        JScrollPane spDiagnosa = new JScrollPane(taDiagnosa);
        formPanel.add(spDiagnosa, gbc);
        
        // Status
        gbc.gridx = 0; gbc.gridy = 7;
        formPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        cbStatus = new JComboBox<>(new String[]{"Menunggu", "Diperiksa", "Selesai"});
        formPanel.add(cbStatus, gbc);
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = 8;
        gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnTambah = new JButton("Tambah");
        JButton btnEdit = new JButton("Edit");
        JButton btnHapus = new JButton("Hapus");
        JButton btnReset = new JButton("Reset");
        
        btnTambah.addActionListener(e -> tambahPendaftaran());
        btnEdit.addActionListener(e -> editPendaftaran());
        btnHapus.addActionListener(e -> hapusPendaftaran());
        btnReset.addActionListener(e -> resetForm());
        
        btnPanel.add(btnTambah);
        btnPanel.add(btnEdit);
        btnPanel.add(btnHapus);
        btnPanel.add(btnReset);
        formPanel.add(btnPanel, gbc);
        
        // Table
        String[] columns = {"No. Pendaftaran", "Tgl Pendaftaran", "Pasien", "Perawat", "Ruangan", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Daftar Pendaftaran"));
        
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    loadPendaftaranDetail(row);
                }
            }
        });
        
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void loadComboBoxes() {
        cbPasien.removeAllItems();
        for (String p : pasienDAO.getKodeList()) {
            cbPasien.addItem(p);
        }
        
        cbPerawat.removeAllItems();
        for (String p : perawatDAO.getKodeList()) {
            cbPerawat.addItem(p);
        }
        
        cbRuangan.removeAllItems();
        for (String r : ruanganDAO.getKodeList()) {
            cbRuangan.addItem(r);
        }
    }
    
    private void tambahPendaftaran() {
        if (cbPasien.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(this, "Pilih pasien!");
            return;
        }
        
        String kdPasien = cbPasien.getSelectedItem().toString().split(" - ")[0];
        String kdPerawat = cbPerawat.getSelectedItem().toString().split(" - ")[0];
        String kdRuangan = cbRuangan.getSelectedItem().toString().split(" - ")[0];
        
        Pendaftaran pend = new Pendaftaran();
        pend.setNoPendaftaran(txtNoPendaftaran.getText());
        pend.setTglPendaftaran((java.util.Date) spnTglPendaftaran.getValue());
        pend.setKdPasien(kdPasien);
        pend.setKdPerawat(kdPerawat);
        pend.setKdRuangan(kdRuangan);
        pend.setKeluhan(taKeluhan.getText());
        pend.setDiagnosa(taDiagnosa.getText());
        pend.setStatus(cbStatus.getSelectedItem().toString());
        
        if (pendaftaranDAO.insert(pend)) {
            JOptionPane.showMessageDialog(this, "Pendaftaran berhasil!");
            refreshTable();
            resetForm();
        }
    }
    
    private void editPendaftaran() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan diedit!");
            return;
        }
        
        Pendaftaran pend = new Pendaftaran();
        pend.setNoPendaftaran(txtNoPendaftaran.getText());
        pend.setKeluhan(taKeluhan.getText());
        pend.setDiagnosa(taDiagnosa.getText());
        pend.setStatus(cbStatus.getSelectedItem().toString());
        
        if (pendaftaranDAO.update(pend)) {
            JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
            refreshTable();
            resetForm();
        }
    }
    
    private void hapusPendaftaran() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus pendaftaran ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String noPendaftaran = table.getValueAt(row, 0).toString();
            if (pendaftaranDAO.delete(noPendaftaran)) {
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                refreshTable();
                resetForm();
            }
        }
    }
    
    private void loadPendaftaranDetail(int row) {
        String noPendaftaran = table.getValueAt(row, 0).toString();
        for (Pendaftaran p : pendaftaranDAO.getAll()) {
            if (p.getNoPendaftaran().equals(noPendaftaran)) {
                txtNoPendaftaran.setText(p.getNoPendaftaran());
                spnTglPendaftaran.setValue(p.getTglPendaftaran());
                taKeluhan.setText(p.getKeluhan());
                taDiagnosa.setText(p.getDiagnosa());
                cbStatus.setSelectedItem(p.getStatus());
                break;
            }
        }
    }
    
    private void resetForm() {
        generateNoPendaftaran();
        spnTglPendaftaran.setValue(new java.util.Date());
        cbPasien.setSelectedIndex(0);
        cbPerawat.setSelectedIndex(0);
        cbRuangan.setSelectedIndex(0);
        taKeluhan.setText("");
        taDiagnosa.setText("");
        cbStatus.setSelectedIndex(0);
        table.clearSelection();
        loadComboBoxes();
    }
    
    private void generateNoPendaftaran() {
        txtNoPendaftaran.setText(pendaftaranDAO.generateNoPendaftaran());
    }
    
    public void refreshTable() {
        tableModel.setRowCount(0);
        for (Pendaftaran p : pendaftaranDAO.getAll()) {
            tableModel.addRow(new Object[]{
                p.getNoPendaftaran(),
                sdf.format(p.getTglPendaftaran()),
                p.getNamaPasien(),
                p.getNamaPerawat(),
                p.getNamaRuangan(),
                p.getStatus()
            });
        }
    }
}