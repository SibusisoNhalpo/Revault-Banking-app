/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

    package banking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.Timer;

public class SIGNUP1 extends javax.swing.JFrame {

    private final JPanel gradientPanel = new JPanel();
    
    
    public SIGNUP1() {
        initComponents();
        // === PASSWORD STRENGTH BAR ===
passwordStrengthBar = new JProgressBar(0, 100);
passwordStrengthBar.setValue(0);
passwordStrengthBar.setStringPainted(true);
passwordStrengthBar.setForeground(new Color(255, 255, 225));
passwordStrengthBar.setBackground(Color.BLUE);

// Add to panel (you can adjust position as needed)
mainPanel.add(passwordStrengthBar);
passwordStrengthBar.setBounds(380, 250, 150, 15);

// === LIVE PASSWORD STRENGTH CHECKER ===
passwordField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
    public void insertUpdate(javax.swing.event.DocumentEvent e) { checkStrength(); }
    public void removeUpdate(javax.swing.event.DocumentEvent e) { checkStrength(); }
    public void changedUpdate(javax.swing.event.DocumentEvent e) { checkStrength(); }

    private void checkStrength() {
        String password = new String(passwordField.getPassword());
        int score = 0;

        if (password.length() >= 6) score += 25;
        if (password.matches(".*[A-Z].*")) score += 25;
        if (password.matches(".*[0-9].*")) score += 25;
        if (password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) score += 25;

        passwordStrengthBar.setValue(score);

        if (score <= 25) {
            passwordStrengthBar.setForeground(Color.RED);
            passwordStrengthBar.setString("Weak");
        } else if (score <= 50) {
            passwordStrengthBar.setForeground(Color.ORANGE);
            passwordStrengthBar.setString("Fair");
        } else if (score <= 75) {
            passwordStrengthBar.setForeground(Color.YELLOW);
            passwordStrengthBar.setString("Good");
        } else {
            passwordStrengthBar.setForeground(Color.GREEN);
            passwordStrengthBar.setString("Strong");
        }
    }
});

// === THEME TOGGLE BUTTON ===
toggleThemeBtn = new JButton(" Dark Mode"); 
toggleThemeBtn.setBounds(20, 20, 140, 30);
toggleThemeBtn.setFocusPainted(false);
toggleThemeBtn.setBackground(new Color(0, 0, 104));
toggleThemeBtn.setForeground(Color.WHITE);
toggleThemeBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
mainPanel.add(toggleThemeBtn);

// When clicked, toggle dark/light mode
toggleThemeBtn.addActionListener(e -> {
    darkMode = !darkMode;
    applyTheme();
});

// Apply the theme initially
applyTheme();
        
   

            // === Apply animated gradient background ===
            gradientPanel.setLayout(new BorderLayout());
            this.setContentPane(gradientPanel);
            gradientPanel.add(mainPanel, BorderLayout.CENTER);
            gradientPanel.setBackground(Color.WHITE);

            // === Transparent + styled main panel ===
            mainPanel.setOpaque(false);
            mainPanel.setBackground(new Color(255, 255, 255, 150));
            mainPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 204), 2, true));

            // === Input fields styling ===
            Color fieldBg = new Color(20, 22, 40);
            Color fieldBorder = new Color(0, 255, 204);
            Color fieldGlow = new Color(0, 180, 255);
            Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

            JTextField[] fields = {nameField, surnameField, emailField, phoneField, passwordField};
            for (JTextField field : fields) {
                field.setBackground(fieldBg);
                field.setForeground(Color.WHITE);
                field.setCaretColor(Color.WHITE);
                field.setFont(fieldFont);
                field.setOpaque(true);
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(fieldBorder, 2, true),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                ));

                field.addFocusListener(new FocusAdapter() {
                    public void focusGained(FocusEvent evt) {
                        field.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(fieldGlow, 2, true),
                                BorderFactory.createEmptyBorder(5, 10, 5, 10)
                        ));
                    }

                    public void focusLost(FocusEvent evt) {
                        field.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(fieldBorder, 2, true),
                                BorderFactory.createEmptyBorder(5, 10, 5, 10)
                        ));
                    }
                });
        }

        // === Buttons styling ===
        Color btnBg = new Color(0, 102, 255);
        Color btnHover = new Color(0, 255, 204);
        Color btnClick = new Color(0, 180, 255);

        JButton[] buttons = {btnRegister, btnBack};
        for (JButton btn : buttons) {
            btn.setBackground(btnBg);
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setBorder(BorderFactory.createLineBorder(btnHover, 1, true));

            btn.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    btn.setBackground(btnHover);
                    btn.setForeground(Color.BLACK);
                    btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1, true));
                }

                public void mouseExited(MouseEvent evt) {
                    btn.setBackground(btnBg);
                    btn.setForeground(Color.WHITE);
                    btn.setBorder(BorderFactory.createLineBorder(btnHover, 1, true));
                }

                public void mousePressed(MouseEvent evt) {
                    btn.setBackground(btnClick);
                }

                public void mouseReleased(MouseEvent evt) {
                    btn.setBackground(btnHover);
                }
            });
        }
    }
      
    // --- Keep your initComponents() here (don’t edit or delete) ---
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        btnRegister = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        phoneField = new javax.swing.JTextField();
        emailField = new javax.swing.JTextField();
        surnameField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setUndecorated(true);
        setResizable(false);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));

        btnRegister.setBackground(new java.awt.Color(51, 102, 255));
        btnRegister.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnRegister.setForeground(new java.awt.Color(255, 255, 255));
        btnRegister.setText("REGISTER");
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });

        btnBack.setBackground(new java.awt.Color(51, 102, 255));
        btnBack.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnBack.setForeground(new java.awt.Color(255, 255, 255));
        btnBack.setText("BACK");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel2.setText("Welcome");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel3.setText("Register your account below");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("NAME");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("SURNAME");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("EMAIL");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("PHONE NUMBER");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("PASSWORD");

        passwordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordFieldActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(0, 0, 102));
        jPanel3.setToolTipText("");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICTURES/logo.jpg"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Modern. Secure. Invest");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 188, Short.MAX_VALUE)))
                .addGap(24, 24, 24))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(124, 124, 124)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addContainerGap(139, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(btnRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel4))
                                        .addGap(54, 54, 54))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel7))
                                        .addGap(18, 18, 18)))
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(phoneField, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(surnameField, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(31, 31, 31))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(93, 93, 93))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(58, 58, 58))))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(nameField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(surnameField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(22, 22, 22)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phoneField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(32, 32, 32)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(58, 58, 58)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        new login11().setVisible(true);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed
        // TODO add your handling code here:
      
    String name = nameField.getText().trim();
    String surname = surnameField.getText().trim();
    // If you used JPasswordField:
    String password = new String(passwordField.getPassword()).trim();
    String phone = phoneField.getText().trim();
    String email = emailField.getText().trim();

    if (name.isEmpty() || surname.isEmpty() || password.isEmpty() || phone.isEmpty() || email.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill in all fields!");
        return;
    }

    // Make a unique-ish account number. You can improve this later.
    String accountNumber = "REV" + (int)(10000000 + Math.random() * 90000000); // 8-digit

    String insertSql = "INSERT INTO users (name, surname, password, phone, email, account_number) VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(insertSql)) {

        ps.setString(1, name);
        ps.setString(2, surname);
        ps.setString(3, password);
        ps.setString(4, phone);
        ps.setString(5, email);
        ps.setString(6, accountNumber);

        int rows = ps.executeUpdate(); // <-- must call this
        if (rows > 0) {
            JOptionPane.showMessageDialog(this, "Registration successful!\nYour Account Number: " + accountNumber);
            // clear fields
            nameField.setText("");
            surnameField.setText("");
            passwordField.setText("");
            phoneField.setText("");
            emailField.setText("");
            new login11().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to register. Please try again.");
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Unexpected error: " + ex.getMessage());
}
    }//GEN-LAST:event_btnRegisterActionPerformed

    private void passwordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordFieldActionPerformed
        private JProgressBar passwordStrengthBar;
        private boolean darkMode = true; // default theme
        private JButton toggleThemeBtn;

        
        /*
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SIGNUP1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SIGNUP1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SIGNUP1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SIGNUP1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SIGNUP1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnRegister;
    private javax.swing.JTextField emailField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextField nameField;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JTextField phoneField;
    private javax.swing.JTextField surnameField;
    // End of variables declaration//GEN-END:variables


// === APPLY THEME TOGGLE ===
private void applyTheme() {
    Color bgColor, fieldBg, fieldFg, panelBg, btnBg, btnFg;

    if (darkMode) {
        bgColor = new Color(15, 15, 30);
        fieldBg = new Color(30, 30, 50);
        fieldFg = Color.WHITE;
        panelBg = new Color(25, 25, 45, 200);
        btnBg = new Color(0, 102, 255);
        btnFg = Color.WHITE;
        toggleThemeBtn.setText(" Dark Mode");
    } else {
        bgColor = Color.WHITE;
        fieldBg = new Color(240, 240, 240);
        fieldFg = Color.BLACK;
        panelBg = Color.WHITE;
        btnBg = new Color(0, 102, 255);
        btnFg = Color.WHITE;
        toggleThemeBtn.setText("☀️ Light Mode");
    }

    mainPanel.setBackground(panelBg);

    // Update text fields
    JTextField[] fields = {nameField, surnameField, emailField, phoneField, passwordField};
    for (JTextField f : fields) {
        f.setBackground(fieldBg);
        f.setForeground(fieldFg);
        f.setCaretColor(fieldFg);
    }

    JButton[] buttons = {btnRegister, btnBack};
    for (JButton b : buttons) {
        b.setBackground(btnBg);
        b.setForeground(btnFg);
    }

    passwordStrengthBar.setBackground(bgColor);
    passwordStrengthBar.setForeground(darkMode ? Color.CYAN : Color.BLUE);

    repaint();
}


}
