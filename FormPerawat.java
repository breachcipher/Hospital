package views;

import dao.PerawatDAO;
import models.Perawat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FormPerawat extends JPanel {
    private JTextField txtKdPerawat, txtNamaPerawat, txtNoTelp, txtAlamat;
    private JComboBox<String> cbSpesialis;
    private JRadioButton rbLaki, rbPerempuan;
    private ButtonGroup bgJk;
    private JTable table;
    private DefaultTableModel tableModel;
    private PerawatDAO perawatDAO;
    
    public FormPerawat() {
        perawatDAO = new PerawatDAO();
        initComponents();
        refreshTable();
        generateKode();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Data Perawat"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Kode Perawat
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Kode Perawat:"), gbc);
        gbc.gridx = 1;
        txtKdPerawat = new JTextField(15);
        txtKdPerawat.setEditable(false);
        txtKdPerawat.setBackground(Color.LIGHT_GRAY);
        formPanel.add(txtKdPerawat, gbc);
        
        // Nama Perawat
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Nama Perawat:"), gbc);
        gbc.gridx = 1;
        txtNamaPerawat = new JTextField(20);
        formPanel.add(txtNamaPerawat, gbc);
        
        // Spesialis
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Spesialis:"), gbc);
        gbc.gridx = 1;
        String[] spesialisOptions = {"Spesialis Anak", "Spesialis Penyakit Dalam", "Spesialis Bedah", "Spesialis Jantung", "Spesialis Saraf"};
        cbSpesialis = new JComboBox<>(spesialisOptions);
        formPanel.add(cbSpesialis, gbc);
        
        // Jenis Kelamin
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Jenis Kelamin:"), gbc);
        gbc.gridx = 1;
        JPanel jkPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rbLaki = new JRadioButton("Laki-laki");
        rbPerempuan = new JRadioButton("Perempuan");
        bgJk = new ButtonGroup();
        bgJk.add(rbLaki);
        bgJk.add(rbPerempuan);
        jkPanel.add(rbLaki);
        jkPanel.add(rbPerempuan);
        formPanel.add(jkPanel, gbc);
        
        // No Telepon
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("No. Telepon:"), gbc);
        gbc.gridx = 1;
        txtNoTelp = new JTextField(20);
        formPanel.add(txtNoTelp, gbc);
        
        // Alamat
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Alamat:"), gbc);
        gbc.gridx = 1;
        txtAlamat = new JTextField(30);
        formPanel.add(txtAlamat, gbc);
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnTambah = new JButton("Tambah");
        JButton btnEdit = new JButton("Edit");
        JButton btnHapus = new JButton("Hapus");
        JButton btnReset = new JButton("Reset");
        
        btnTambah.addActionListener(e -> tambahPerawat());
        btnEdit.addActionListener(e -> editPerawat());
        btnHapus.addActionListener(e -> hapusPerawat());
        btnReset.addActionListener(e -> resetForm());
        
        btnPanel.add(btnTambah);
        btnPanel.add(btnEdit);
        btnPanel.add(btnHapus);
        btnPanel.add(btnReset);
        formPanel.add(btnPanel, gbc);
        
        // Table
        String[] columns = {"Kode", "Nama Perawat", "Spesialis", "JK", "No Telp", "Alamat"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Daftar Perawat"));
        
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtKdPerawat.setText(table.getValueAt(row, 0).toString());
                    txtNamaPerawat.setText(table.getValueAt(row, 1).toString());
                    cbSpesialis.setSelectedItem(table.getValueAt(row, 2).toString());
                    String jk = table.getValueAt(row, 3).toString();
                    if (jk.equals("L")) rbLaki.setSelected(true);
                    else rbPerempuan.setSelected(true);
                    txtNoTelp.setText(table.getValueAt(row, 4).toString());
                    txtAlamat.setText(table.getValueAt(row, 5).toString());
                }
            }
        });
        
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void tambahPerawat() {
        if (txtNamaPerawat.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama Perawat harus diisi!");
            return;
        }
        
        String jk = rbLaki.isSelected() ? "L" : "P";
        Perawat perawat = new Perawat(
            txtKdPerawat.getText(),
            txtNamaPerawat.getText(),
            cbSpesialis.getSelectedItem().toString(),
            jk,
            txtNoTelp.getText(),
            txtAlamat.getText()
        );
        
        if (perawatDAO.insert(perawat)) {
            JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!");
            refreshTable();
            resetForm();
        }
    }
    
    private void editPerawat() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan diedit!");
            return;
        }
        
        String jk = rbLaki.isSelected() ? "L" : "P";
        Perawat perawat = new Perawat(
            txtKdPerawat.getText(),
            txtNamaPerawat.getText(),
            cbSpesialis.getSelectedItem().toString(),
            jk,
            txtNoTelp.getText(),
            txtAlamat.getText()
        );
        
        if (perawatDAO.update(perawat)) {
            JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
            refreshTable();
            resetForm();
        }
    }
    
    private void hapusPerawat() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String kdPerawat = table.getValueAt(row, 0).toString();
            if (perawatDAO.delete(kdPerawat)) {
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                refreshTable();
                resetForm();
            }
        }
    }
    
    private void resetForm() {
        txtNamaPerawat.setText("");
        cbSpesialis.setSelectedIndex(0);
        bgJk.clearSelection();
        txtNoTelp.setText("");
        txtAlamat.setText("");
        generateKode();
        table.clearSelection();
    }
    
    private void generateKode() {
        txtKdPerawat.setText(perawatDAO.generateKode());
    }
    
    public void refreshTable() {
        tableModel.setRowCount(0);
        for (Perawat p : perawatDAO.getAll()) {
            tableModel.addRow(new Object[]{
                p.getKdPerawat(), p.getNamaPerawat(), p.getSpesialis(),
                p.getJk(), p.getNoTelp(), p.getAlamat()
            });
        }
    }
}