package views;

import dao.ObatDAO;
import models.Obat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

public class FormObat extends JPanel {
    private JTextField txtKdObat, txtNamaObat, txtStok, txtHarga;
    private JComboBox<String> cbJenisObat;
    private JSpinner spnExpiredDate;
    private JTable table;
    private DefaultTableModel tableModel;
    private ObatDAO obatDAO;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    public FormObat() {
        obatDAO = new ObatDAO();
        initComponents();
        refreshTable();
        generateKode();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Data Obat"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Kode Obat
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Kode Obat:"), gbc);
        gbc.gridx = 1;
        txtKdObat = new JTextField(15);
        txtKdObat.setEditable(false);
        txtKdObat.setBackground(Color.LIGHT_GRAY);
        formPanel.add(txtKdObat, gbc);
        
        // Nama Obat
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Nama Obat:"), gbc);
        gbc.gridx = 1;
        txtNamaObat = new JTextField(25);
        formPanel.add(txtNamaObat, gbc);
        
        // Jenis Obat
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Jenis Obat:"), gbc);
        gbc.gridx = 1;
        String[] jenisOptions = {"Analgesik", "Antibiotik", "Antiinflamasi", "Antihistamin", "Vitamin"};
        cbJenisObat = new JComboBox<>(jenisOptions);
        formPanel.add(cbJenisObat, gbc);
        
        // Stok
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Stok:"), gbc);
        gbc.gridx = 1;
        txtStok = new JTextField(10);
        formPanel.add(txtStok, gbc);
        
        // Harga
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Harga (Rp):"), gbc);
        gbc.gridx = 1;
        txtHarga = new JTextField(15);
        formPanel.add(txtHarga, gbc);
        
        // Expired Date
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Expired Date:"), gbc);
        gbc.gridx = 1;
        spnExpiredDate = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor de = new JSpinner.DateEditor(spnExpiredDate, "yyyy-MM-dd");
        spnExpiredDate.setEditor(de);
        formPanel.add(spnExpiredDate, gbc);
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnTambah = new JButton("Tambah");
        JButton btnEdit = new JButton("Edit");
        JButton btnHapus = new JButton("Hapus");
        JButton btnReset = new JButton("Reset");
        
        btnTambah.addActionListener(e -> tambahObat());
        btnEdit.addActionListener(e -> editObat());
        btnHapus.addActionListener(e -> hapusObat());
        btnReset.addActionListener(e -> resetForm());
        
        btnPanel.add(btnTambah);
        btnPanel.add(btnEdit);
        btnPanel.add(btnHapus);
        btnPanel.add(btnReset);
        formPanel.add(btnPanel, gbc);
        
        // Table
        String[] columns = {"Kode", "Nama Obat", "Jenis Obat", "Stok", "Harga", "Expired Date"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Daftar Obat"));
        
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtKdObat.setText(table.getValueAt(row, 0).toString());
                    txtNamaObat.setText(table.getValueAt(row, 1).toString());
                    cbJenisObat.setSelectedItem(table.getValueAt(row, 2).toString());
                    txtStok.setText(table.getValueAt(row, 3).toString());
                    txtHarga.setText(table.getValueAt(row, 4).toString());
                    try {
                        spnExpiredDate.setValue(sdf.parse(table.getValueAt(row, 5).toString()));
                    } catch (Exception ex) {}
                }
            }
        });
        
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void tambahObat() {
        if (txtNamaObat.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama Obat harus diisi!");
            return;
        }
        
        try {
            Obat obat = new Obat(
                txtKdObat.getText(),
                txtNamaObat.getText(),
                cbJenisObat.getSelectedItem().toString(),
                Integer.parseInt(txtStok.getText()),
                Double.parseDouble(txtHarga.getText()),
                (java.util.Date) spnExpiredDate.getValue()
            );
            
            if (obatDAO.insert(obat)) {
                JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!");
                refreshTable();
                resetForm();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Stok dan Harga harus berupa angka!");
        }
    }
    
    private void editObat() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan diedit!");
            return;
        }
        
        try {
            Obat obat = new Obat(
                txtKdObat.getText(),
                txtNamaObat.getText(),
                cbJenisObat.getSelectedItem().toString(),
                Integer.parseInt(txtStok.getText()),
                Double.parseDouble(txtHarga.getText()),
                (java.util.Date) spnExpiredDate.getValue()
            );
            
            if (obatDAO.update(obat)) {
                JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
                refreshTable();
                resetForm();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Stok dan Harga harus berupa angka!");
        }
    }
    
    private void hapusObat() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String kdObat = table.getValueAt(row, 0).toString();
            if (obatDAO.delete(kdObat)) {
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                refreshTable();
                resetForm();
            }
        }
    }
    
    private void resetForm() {
        txtNamaObat.setText("");
        cbJenisObat.setSelectedIndex(0);
        txtStok.setText("");
        txtHarga.setText("");
        spnExpiredDate.setValue(new java.util.Date());
        generateKode();
        table.clearSelection();
    }
    
    private void generateKode() {
        txtKdObat.setText(obatDAO.generateKode());
    }
    
    public void refreshTable() {
        tableModel.setRowCount(0);
        for (Obat o : obatDAO.getAll()) {
            tableModel.addRow(new Object[]{
                o.getKdObat(), o.getNamaObat(), o.getJenisObat(),
                o.getStok(), String.format("Rp %,d", (long) o.getHarga()),
                sdf.format(o.getExpiredDate())
            });
        }
    }
}