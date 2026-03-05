/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package banking;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 * Transactions viewer with filtering, CSV export and monthly PDF generation.
 * Option A layout: PDF controls at the bottom.
 */
public class Transcations extends JFrame {

    // UI components
    private JTable table;
    private DefaultTableModel model;

    private JComboBox<String> filterType;
    private JTextField dateFrom, dateTo;
    private JButton btnSearch, btnExport, btnBack;

    // PDF controls (bottom)
    private JButton btnPDF;
    private JComboBox<String> cmbMonth;
    private JComboBox<String> cmbYear;

    // internal
    private String email;

    public Transcations() {
        setTitle("Transaction History");
        setSize(950, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // store logged-in email
        email = Session.getEmail();

        // ---------- FILTERS (top) ----------
        JLabel lblFilter = new JLabel("Filter:");
        lblFilter.setBounds(20, 20, 100, 30);
        add(lblFilter);

        filterType = new JComboBox<>(new String[]{
            "All", "DEPOSIT", "WITHDRAW", "TRANSFER", "AIRTIME"
        });
        filterType.setBounds(80, 20, 150, 30);
        add(filterType);

        JLabel lblFrom = new JLabel("From:");
        lblFrom.setBounds(250, 20, 50, 30);
        add(lblFrom);

        dateFrom = new JTextField("2025-01-01"); // default
        dateFrom.setBounds(300, 20, 120, 30);
        add(dateFrom);

        JLabel lblTo = new JLabel("To:");
        lblTo.setBounds(430, 20, 30, 30);
        add(lblTo);

        dateTo = new JTextField("2025-12-31"); // default
        dateTo.setBounds(460, 20, 120, 30);
        add(dateTo);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(600, 20, 100, 30);
        btnSearch.addActionListener(e -> loadTransactions());
        add(btnSearch);

        btnExport = new JButton("Export CSV");
        btnExport.setBounds(710, 20, 120, 30);
        btnExport.addActionListener(e -> exportCSV());
        add(btnExport);

        // ---------- TABLE ----------
        model = new DefaultTableModel(
            new Object[]{"Date", "Description", "Type", "Amount", "Balance"},
            0
        );

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 70, 900, 410);
        add(scrollPane);

        // ---------- BACK BUTTON (bottom right) ----------
        btnBack = new JButton("Back to Dashboard");
        btnBack.setBounds(720, 530, 200, 40);
        btnBack.addActionListener(e -> {
            new dashborad().setVisible(true);
            dispose();
        });
        add(btnBack);

        // ---------- PDF CONTROLS (bottom left) ----------
        cmbMonth = new JComboBox<>(new String[]{
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        });
        cmbMonth.setBounds(20, 530, 120, 30);
        add(cmbMonth);

        cmbYear = new JComboBox<>(new String[]{"2024", "2025", "2026"});
        cmbYear.setBounds(150, 530, 100, 30);
        add(cmbYear);

        btnPDF = new JButton("Download PDF");
        btnPDF.setBounds(270, 530, 150, 30);
        btnPDF.addActionListener(e -> {
            if (email == null || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please log in first.");
                return;
            }
            String monthName = cmbMonth.getSelectedItem().toString();
            String year = cmbYear.getSelectedItem().toString();
            generateMonthlyStatement(email, monthName, year);
        });
        add(btnPDF);

        // Load transactions initially
        loadTransactions();
    }

    private void goBack() {
        new dashborad().setVisible(true);
        dispose();
    }

    // ---------------- LOAD TRANSACTIONS -------------------
    private void loadTransactions() {
        String loggedEmail = Session.getEmail();

        if (loggedEmail == null || loggedEmail.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please log in first.");
            return;
        }

        String selectedType = (String) filterType.getSelectedItem();
        String from = dateFrom.getText().trim();
        String to = dateTo.getText().trim();

        model.setRowCount(0);

        // Build SQL dynamically to avoid parameter index issues
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT date, description, type, amount, balance FROM transactions WHERE email = ? ");

        if (from != null && !from.isEmpty() && to != null && !to.isEmpty()) {
            sql.append("AND date BETWEEN ? AND ? ");
        }

        if (selectedType != null && !selectedType.equalsIgnoreCase("All")) {
            sql.append("AND type = ? ");
        }

        sql.append("ORDER BY date DESC");

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int idx = 1;
            ps.setString(idx++, loggedEmail);

            if (from != null && !from.isEmpty() && to != null && !to.isEmpty()) {
                ps.setString(idx++, from);
                ps.setString(idx++, to);
            }

            if (selectedType != null && !selectedType.equalsIgnoreCase("All")) {
                ps.setString(idx++, selectedType);
            }

            try (ResultSet rs = ps.executeQuery()) {
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    model.addRow(new Object[]{
                        rs.getString("date"),
                        rs.getString("description"),
                        rs.getString("type"),
                        "R " + String.format("%.2f", rs.getDouble("amount")),
                        "R " + String.format("%.2f", rs.getDouble("balance"))
                    });
                }
                if (!found) {
                    JOptionPane.showMessageDialog(this, "No transactions found for this filter.");
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ---------------- EXPORT TO CSV --------------------
    private void exportCSV() {
        try (FileWriter fw = new FileWriter("transactions_export.csv")) {

            // write headers
            for (int i = 0; i < model.getColumnCount(); i++) {
                fw.write(model.getColumnName(i));
                if (i < model.getColumnCount() - 1) fw.write(",");
            }
            fw.write("\n");

            // write rows
            for (int r = 0; r < model.getRowCount(); r++) {
                for (int c = 0; c < model.getColumnCount(); c++) {
                    Object val = model.getValueAt(r, c);
                    fw.write(val == null ? "" : val.toString());
                    if (c < model.getColumnCount() - 1) fw.write(",");
                }
                fw.write("\n");
            }

            JOptionPane.showMessageDialog(this, "CSV Exported Successfully: transactions_export.csv");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Export Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ---------------- GENERATE MONTHLY PDF ----------------
    private void generateMonthlyStatement(String userEmail, String monthName, String year) {
        int monthNumber = monthNameToNumber(monthName); // 1..12
        if (monthNumber == -1) {
            JOptionPane.showMessageDialog(this, "Invalid month selected.");
            return;
        }

        String fileName = "Statement_" + monthName + "_" + year + ".pdf";

        String sql = "SELECT date, description, type, amount, balance FROM transactions " +
                     "WHERE email = ? AND MONTH(date) = ? AND YEAR(date) = ? ORDER BY date ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, userEmail);
            pst.setInt(2, monthNumber);
            pst.setInt(3, Integer.parseInt(year));

            try (ResultSet rs = pst.executeQuery()) {

                Document doc = new Document();
                PdfWriter.getInstance(doc, new java.io.FileOutputStream(fileName));
                doc.open();

                doc.add(new Paragraph("REVAULT BANK – MONTHLY STATEMENT"));
                doc.add(new Paragraph("Email: " + userEmail));
                doc.add(new Paragraph("Month: " + monthName + " / " + year));
                doc.add(new Paragraph("\n"));

                PdfPTable pdfTable = new PdfPTable(5);
                pdfTable.addCell("Date");
                pdfTable.addCell("Description");
                pdfTable.addCell("Type");
                pdfTable.addCell("Amount");
                pdfTable.addCell("Balance");

                double totalDeposit = 0;
                double totalWithdraw = 0;
                double endingBalance = 0;

                boolean any = false;
                while (rs.next()) {
                    any = true;
                    String date = rs.getString("date");
                    String desc = rs.getString("description");
                    String type = rs.getString("type");
                    double amt = rs.getDouble("amount");
                    double bal = rs.getDouble("balance");

                    pdfTable.addCell(date);
                    pdfTable.addCell(desc);
                    pdfTable.addCell(type);
                    pdfTable.addCell("R " + String.format("%.2f", amt));
                    pdfTable.addCell("R " + String.format("%.2f", bal));

                    if ("DEPOSIT".equalsIgnoreCase(type)) totalDeposit += amt;
                    if ("WITHDRAW".equalsIgnoreCase(type) || "WITHDRAWAL".equalsIgnoreCase(type)) totalWithdraw += amt;

                    endingBalance = bal;
                }

                if (!any) {
                    doc.add(new Paragraph("No transactions found for the selected month."));
                } else {
                    doc.add(pdfTable);
                    doc.add(new Paragraph("\nTotals:"));
                    doc.add(new Paragraph("Total Deposits: R " + String.format("%.2f", totalDeposit)));
                    doc.add(new Paragraph("Total Withdrawals: R " + String.format("%.2f", totalWithdraw)));
                    doc.add(new Paragraph("Ending Balance: R " + String.format("%.2f", endingBalance)));
                }

                doc.close();

                JOptionPane.showMessageDialog(this, "PDF Statement saved: " + fileName);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "PDF Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // helper: convert full month name to number (1-12)
    private int monthNameToNumber(String month) {
        switch (month.toLowerCase()) {
            case "january": return 1;
            case "february": return 2;
            case "march": return 3;
            case "april": return 4;
            case "may": return 5;
            case "june": return 6;
            case "july": return 7;
            case "august": return 8;
            case "september": return 9;
            case "october": return 10;
            case "november": return 11;
            case "december": return 12;
            default: return -1;
        }
    }

    // ---------------- OPTIONAL: reusable saveTransaction ----------------
    public static void saveTransaction(String email, String description, String type, double amount, double balanceAfter) {
        String sql = "INSERT INTO transactions (email, description, type, amount, balance) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, description);
            ps.setString(3, type);
            ps.setDouble(4, amount);
            ps.setDouble(5, balanceAfter);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ----------------- main -----------------------------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Transcations().setVisible(true);
        });
    }


/**
 *
 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        P8 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        P8.setBackground(new java.awt.Color(255, 255, 255));
        P8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        P8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(P8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(P8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

           
    /**
     * @param args the command line arguments
     */
   
        /* Create and display the form */
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel P8;
    // End of variables declaration//GEN-END:variables

}