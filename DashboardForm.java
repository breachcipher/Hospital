package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardForm extends JFrame {
    
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private JPanel sidebar; // Deklarasikan sebagai field
    
    public DashboardForm() {
        setTitle("Hospital Management System - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 750);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Sidebar Panel
        sidebar = new JPanel();
        sidebar.setBackground(new Color(41, 128, 185));
        sidebar.setPreferredSize(new Dimension(250, getHeight()));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS)); // Perbaiki: gunakan BoxLayout
        
        // Header sidebar
        JLabel titleLabel = new JLabel("Hospital Management", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(titleLabel);
        
        // Menu buttons
        String[] menus = {"Dashboard", "Data Perawat", "Data Pasien", "Data Obat", "Data Ruangan", "Pendaftaran", "Keluar"};
        String[] icons = {"📊", "👩‍⚕️", "👨‍👩‍👧", "💊", "🏥", "📝", "🚪"};
        
        for (int i = 0; i < menus.length; i++) {
            JButton menuBtn = createMenuButton(icons[i] + " " + menus[i]);
            final String menuName = menus[i];
            menuBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (menuName.equals("Keluar")) {
                        System.exit(0);
                    } else if (menuName.equals("Dashboard")) {
                        cardLayout.show(contentPanel, "Dashboard");
                    } else if (menuName.equals("Data Perawat")) {
                        cardLayout.show(contentPanel, "Perawat");
                        refreshPanel("Perawat");
                    } else if (menuName.equals("Data Pasien")) {
                        cardLayout.show(contentPanel, "Pasien");
                        refreshPanel("Pasien");
                    } else if (menuName.equals("Data Obat")) {
                        cardLayout.show(contentPanel, "Obat");
                        refreshPanel("Obat");
                    } else if (menuName.equals("Data Ruangan")) {
                        cardLayout.show(contentPanel, "Ruangan");
                        refreshPanel("Ruangan");
                    } else if (menuName.equals("Pendaftaran")) {
                        cardLayout.show(contentPanel, "Pendaftaran");
                        refreshPanel("Pendaftaran");
                    }
                }
            });
            sidebar.add(menuBtn);
            sidebar.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        
        // Content Panel with CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        
        // Add panels for each menu
        contentPanel.add(new DashboardPanel(), "Dashboard");
        contentPanel.add(new FormPerawat(), "Perawat");
        contentPanel.add(new FormPasien(), "Pasien");
        contentPanel.add(new FormObat(), "Obat");
        contentPanel.add(new FormRuangan(), "Ruangan");
        contentPanel.add(new FormPendaftaran(), "Pendaftaran");
        
        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(250, 50));
        btn.setMinimumSize(new Dimension(250, 50));
        btn.setPreferredSize(new Dimension(250, 50));
        btn.setBackground(new Color(52, 152, 219));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(41, 128, 185));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(52, 152, 219));
            }
        });
        return btn;
    }
    
    private void refreshPanel(String panelName) {
        Component comp = null;
        if (panelName.equals("Perawat")) comp = contentPanel.getComponent(1);
        else if (panelName.equals("Pasien")) comp = contentPanel.getComponent(2);
        else if (panelName.equals("Obat")) comp = contentPanel.getComponent(3);
        else if (panelName.equals("Ruangan")) comp = contentPanel.getComponent(4);
        else if (panelName.equals("Pendaftaran")) comp = contentPanel.getComponent(5);
        
        if (comp != null) {
            if (comp instanceof FormPerawat) ((FormPerawat) comp).refreshTable();
            else if (comp instanceof FormPasien) ((FormPasien) comp).refreshTable();
            else if (comp instanceof FormObat) ((FormObat) comp).refreshTable();
            else if (comp instanceof FormRuangan) ((FormRuangan) comp).refreshTable();
            else if (comp instanceof FormPendaftaran) ((FormPendaftaran) comp).refreshTable();
        }
    }
    
    // Dashboard Panel with statistics
    class DashboardPanel extends JPanel {
        public DashboardPanel() {
            setLayout(new GridBagLayout());
            setBackground(new Color(236, 240, 241));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(20, 20, 20, 20);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            
            JLabel title = new JLabel("Selamat Datang di Hospital Management System");
            title.setFont(new Font("Arial", Font.BOLD, 24));
            title.setForeground(new Color(41, 128, 185));
            gbc.gridx = 0; gbc.gridy = 0;
            gbc.gridwidth = 2;
            add(title, gbc);
            
            // Statistics cards
            gbc.gridwidth = 1;
            gbc.gridy = 1;
            add(createStatCard("👩‍⚕️ Total Perawat", "5", new Color(52, 152, 219)), gbc);
            gbc.gridx = 1;
            add(createStatCard("👨‍👩‍👧 Total Pasien", "5", new Color(46, 204, 113)), gbc);
            gbc.gridx = 0; gbc.gridy = 2;
            add(createStatCard("💊 Total Obat", "5", new Color(241, 196, 15)), gbc);
            gbc.gridx = 1;
            add(createStatCard("🏥 Total Ruangan", "5", new Color(231, 76, 60)), gbc);
            
            JLabel info = new JLabel("Silakan pilih menu di sebelah kiri untuk mengelola data");
            info.setFont(new Font("Arial", Font.PLAIN, 14));
            info.setForeground(Color.GRAY);
            gbc.gridx = 0; gbc.gridy = 3;
            gbc.gridwidth = 2;
            add(info, gbc);
        }
        
        private JPanel createStatCard(String title, String value, Color color) {
            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createLineBorder(color, 2));
            card.setPreferredSize(new Dimension(200, 120));
            
            JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
            valueLabel.setFont(new Font("Arial", Font.BOLD, 30));
            valueLabel.setForeground(color);
            valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            card.add(Box.createRigidArea(new Dimension(0, 20)));
            card.add(titleLabel);
            card.add(Box.createRigidArea(new Dimension(0, 10)));
            card.add(valueLabel);
            card.add(Box.createRigidArea(new Dimension(0, 20)));
            
            return card;
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new DashboardForm().setVisible(true);
            }
        });
    }
}
