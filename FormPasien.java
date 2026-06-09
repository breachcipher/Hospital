package views;

import dao.PasienDAO;
import models.Pasien;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

public class FormPasien extends JPanel {
    private JTextField txtKdPasien, txtNamaPasien, txtTempatLahir, txtNoTelp, txtAlamat;
    private JComboBox<String> cbJk;
    private JSpinner spnTglLahir;
    private JTable table;
    private DefaultTableModel tableModel;
    private PasienDAO pasienDAO;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    public FormPasien() {
        pasienDAO = new PasienDAO();
        initComponents();
        refreshTable();
        generateKode();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Data Pasien"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Kode Pasien
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Kode Pasien:"), gbc);
        gbc.gridx = 1;
        txtKdPasien = new JTextField(15);
        txtKdPasien.setEditable(false);
        txtKdPasien.setBackground(Color.LIGHT_GRAY);
        formPanel.add(txtKdPasien, gbc);
        
        // Nama Pasien
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Nama Pasien:"), gbc);
        gbc.gridx = 1;
        txtNamaPasien = new JTextField(20);
        formPanel.add(txtNamaPasien, gbc);
        
        // Tempat Lahir
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Tempat Lahir:"), gbc);
        gbc.gridx = 1;
        txtTempatLahir = new JTextField(15);
        formPanel.add(txtTempatLahir, gbc);
        
        // Tanggal Lahir
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Tanggal Lahir:"), gbc);
        gbc.gridx = 1;
        spnTglLahir = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor de = new JSpinner.DateEditor(spnTglLahir, "yyyy-MM-dd");
        spnTglLahir.setEditor(de);
        formPanel.add(spnTglLahir, gbc);
        
        // Jenis Kelamin
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Jenis Kelamin:"), gbc);
        gbc.gridx = 1;
        cbJk = new JComboBox<>(new String[]{"L", "P"});
        formPanel.add(cbJk, gbc);
        
        // No Telepon
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("No. Telepon:"), gbc);
        gbc.gridx = 1;
        txtNoTelp = new JTextField(20);
        formPanel.add(txtNoTelp, gbc);
        
        // Alamat
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("Alamat:"), gbc);
        gbc.gridx = 1;
        txtAlamat = new JTextField(30);
        formPanel.add(txtAlamat, gbc);
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = 7;
        gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnTambah = new JButton("Tambah");
        JButton btnEdit = new JButton("Edit");
        JButton btnHapus = new JButton("Hapus");
        JButton btnReset = new JButton("Reset");
        
        btnTambah.addActionListener(e -> tambahPasien());
        btnEdit.addActionListener(e -> editPasien());
        btnHapus.addActionListener(e -> hapusPasien());
        btnReset.addActionListener(e -> resetForm());
        
        btnPanel.add(btnTambah);
        btnPanel.add(btnEdit);
        btnPanel.add(btnHapus);
        btnPanel.add(btnReset);
        formPanel.add(btnPanel, gbc);
        
        // Table
        String[] columns = {"Kode", "Nama Pasien", "Tempat Lahir", "Tgl Lahir", "JK", "No Telp", "Alamat"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Daftar Pasien"));
        
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtKdPasien.setText(table.getValueAt(row, 0).toString());
                    txtNamaPasien.setText(table.getValueAt(row, 1).toString());
                    txtTempatLahir.setText(table.getValueAt(row, 2).toString());
                    try {
                        spnTglLahir.setValue(new SimpleDateFormat("yyyy-MM-dd").parse(table.getValueAt(row, 3).toString()));
                    } catch (Exception ex) {}
                    cbJk.setSelectedItem(table.getValueAt(row, 4).toString());
                    txtNoTelp.setText(table.getValueAt(row, 5).toString());
                    txtAlamat.setText(table.getValueAt(row, 6).toString());
                }
            }
        });
        
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void tambahPasien() {
        if (txtNamaPasien.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama Pasien harus diisi!");
            return;
        }
        
        Pasien pasien = new Pasien(
            txtKdPasien.getText(),
            txtNamaPasien.getText(),
            txtTempatLahir.getText(),
            (java.util.Date) spnTglLahir.getValue(),
            cbJk.getSelectedItem().toString(),
            txtAlamat.getText(),
            txtNoTelp.getText()
        );
        
        if (pasienDAO.insert(pasien)) {
            JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!");
            refreshTable();
            resetForm();
        }
    }
    
    private void editPasien() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan diedit!");
            return;
        }
        
        Pasien pasien = new Pasien(
            txtKdPasien.getText(),
            txtNamaPasien.getText(),
            txtTempatLahir.getText(),
            (java.util.Date) spnTglLahir.getValue(),
            cbJk.getSelectedItem().toString(),
            txtAlamat.getText(),
            txtNoTelp.getText()
        );
        
        if (pasienDAO.update(pasien)) {
            JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
            refreshTable();
            resetForm();
        }
    }
    
    private void hapusPasien() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String kdPasien = table.getValueAt(row, 0).toString();
            if (pasienDAO.delete(kdPasien)) {
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                refreshTable();
                resetForm();
            }
        }
    }
    
    private void resetForm() {
        txtNamaPasien.setText("");
        txtTempatLahir.setText("");
        spnTglLahir.setValue(new java.util.Date());
        cbJk.setSelectedIndex(0);
        txtNoTelp.setText("");
        txtAlamat.setText("");
        generateKode();
        table.clearSelection();
    }
    
    private void generateKode() {
        txtKdPasien.setText(pasienDAO.generateKode());
    }
    
    public void refreshTable() {
        tableModel.setRowCount(0);
        for (Pasien p : pasienDAO.getAll()) {
            tableModel.addRow(new Object[]{
                p.getKdPasien(), p.getNamaPasien(), p.getTempatLahir(),
                new SimpleDateFormat("yyyy-MM-dd").format(p.getTglLahir()),
                p.getJk(), p.getNoTelp(), p.getAlamat()
            });
        }
    }
}