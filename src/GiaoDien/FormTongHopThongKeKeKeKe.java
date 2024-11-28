/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GiaoDien;

import com.sales.Utils.DateHelper;
import com.sales.Utils.JdbcHelper;
import com.sales.Utils.XImage;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author NganTTK_PC09494
 */
public class FormTongHopThongKeKeKeKe extends javax.swing.JFrame {

    List<Object[]> listThongKeSanPham = new ArrayList<>();
    List<Object[]> listThongKeTonKho = new ArrayList<>();

    /**
     * Creates new form FormTongHopThongKe
     */
    public FormTongHopThongKeKeKeKe() {
        initComponents();
        init();
    }

    public void init() {
        setLocationRelativeTo(null);
        setIconImage(XImage.XImage());
        setTitle("PHẦN MỀM QUẢN LÝ GIÀY THỂ THAO");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        thongKeSanPham("1999-02-02", DateHelper.toString(new Date(), "yyyy-MM-dd"));
        chkThongKeSanPhamGiam.setSelected(true);
        chkTonKhoTang.setSelected(true);
        thongKeTonKho();
    }

    public void sapXepThongKeSanPham() {
        DefaultTableModel model = (DefaultTableModel) tblThongKeSanPham.getModel();
        model.setRowCount(0);
        if (chkThongKeSanPhamTang.isSelected()) {
            Collections.reverse(listThongKeSanPham);
            for (Object[] row : listThongKeSanPham) {
                String tenSanPham = (String) row[0];
                String bienTheSanPham = (String) row[1];
                int soLuongBan = (Integer) row[2];
                double donGia = (Double) row[3];
                double doanhThu = (Double) row[4];
                model.addRow(new Object[]{tenSanPham, bienTheSanPham, soLuongBan, donGia, doanhThu});
            }
        } else {
            Collections.reverse(listThongKeSanPham);
            for (Object[] row : listThongKeSanPham) {
                String tenSanPham = (String) row[0];
                String bienTheSanPham = (String) row[1];
                int soLuongBan = (Integer) row[2];
                double donGia = (Double) row[3];
                double doanhThu = (Double) row[4];
                model.addRow(new Object[]{tenSanPham, bienTheSanPham, soLuongBan, donGia, doanhThu});
            }
        }

    }

    public void thongKeSanPham(String to, String from) {
        String sql = "{CALL SP_ThongKeSanPhamBanChayNhatTuNgayDenNgay(?, ?)}";
        DefaultTableModel model = (DefaultTableModel) tblThongKeSanPham.getModel();
        model.setRowCount(0);
        listThongKeSanPham.clear();
        try {
            ResultSet rs = JdbcHelper.query(sql, to, from);
            if (rs.next() == false) {
                JOptionPane.showMessageDialog(this, "Không có sản phẩm nào được bán trong thời gian này");
                return;
            }
            while (rs.next()) {
                String tenSanPham = rs.getString("TenSanPham");
                String bienTheSanPham = rs.getString("BienTheSanPham");
                int soLuongBan = rs.getInt("SoLuongBan");
                double donGia = rs.getDouble("DonGia");
                model.addRow(new Object[]{tenSanPham, bienTheSanPham, soLuongBan, donGia, soLuongBan * donGia});
                listThongKeSanPham.add(new Object[]{tenSanPham, bienTheSanPham, soLuongBan, donGia, soLuongBan * donGia});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void thongKeTonKho() {
        String sql = "{CALL SP_ThongKeTonKho(?)}";
        DefaultTableModel model = (DefaultTableModel) tblThongKeTonKho.getModel();
        listThongKeTonKho.clear();
        model.setRowCount(0);
        int sapXep = 0;
        if (chkTonKhoGiam.isSelected()) {
            sapXep = 0;
        } else {
            sapXep = 1;
        }
        try {
            ResultSet rs = JdbcHelper.query(sql, sapXep);
            while (rs.next()) {
                String tenSanPham = rs.getString("TenSanPham");
                String bienTheSanPham = rs.getString("BienTheSanPham");
                int soLuongTon = rs.getInt("SoLuongTon");
                double donGia = rs.getDouble("DonGia");
                model.addRow(new Object[]{tenSanPham, bienTheSanPham, soLuongTon, donGia});
                listThongKeTonKho.add(new Object[]{tenSanPham, bienTheSanPham, soLuongTon, donGia});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void timKiemTonKho() {
        List<Object[]> listTimKiem = new ArrayList();
        listTimKiem.clear();
        String timKiem[] = txtTimKiemThongKeTonKho.getText().trim().split(" ");
        DefaultTableModel model = (DefaultTableModel) tblThongKeTonKho.getModel();
        model.setRowCount(0);
        for (Object[] objects : listThongKeTonKho) {
            boolean kiemTra = true;
            String tenSanPham = objects[0].toString().toLowerCase();
            for (int i = 0; i < timKiem.length; i++) {
                if (!tenSanPham.contains(timKiem[i].toLowerCase())) {
                    kiemTra = false;
                    break;
                }
            }
            if (kiemTra) {
                listTimKiem.add(objects);
            }
        }
        for (Object[] objects : listTimKiem) {
            model.addRow(new Object[]{objects[0], objects[1], objects[2], objects[3]});
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrThongKeSanPham = new javax.swing.ButtonGroup();
        bgrThongKeTonKho = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnlThongKeSanPham = new javax.swing.JPanel();
        dateChooserTuSanPham = new com.toedter.calendar.JDateChooser();
        dateChooserDenSanPham = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblThongKeSanPham = new javax.swing.JTable();
        chkThongKeSanPhamTang = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        chkThongKeSanPhamGiam = new javax.swing.JCheckBox();
        pnlThongKeKhachHang = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        pnlThongKeDoanhThu = new javax.swing.JPanel();
        pnlThongKeTonKho = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblThongKeTonKho = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        btnTimKiemTonKho = new javax.swing.JButton();
        txtTimKiemThongKeTonKho = new javax.swing.JTextField();
        chkTonKhoTang = new javax.swing.JCheckBox();
        chkTonKhoGiam = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        pnlThongKeSanPham.setBackground(new java.awt.Color(255, 255, 255));

        dateChooserTuSanPham.setDateFormatString("dd-MM-yyyy");

        dateChooserDenSanPham.setDateFormatString("dd-MM-yyyy");

        jLabel1.setText("Từ");

        jLabel2.setText("Đến");

        tblThongKeSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Tên sản phẩm", "Loại", "Số lượng bán", "Đơn giá", "Tổng tiền"
            }
        ));
        jScrollPane1.setViewportView(tblThongKeSanPham);

        bgrThongKeSanPham.add(chkThongKeSanPhamTang);
        chkThongKeSanPhamTang.setText("Tăng");
        chkThongKeSanPhamTang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkThongKeSanPhamTangItemStateChanged(evt);
            }
        });
        chkThongKeSanPhamTang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkThongKeSanPhamTangActionPerformed(evt);
            }
        });

        jButton1.setText("Tìm kiếm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Sắp xếp theo số lượng");

        bgrThongKeSanPham.add(chkThongKeSanPhamGiam);
        chkThongKeSanPhamGiam.setText("Giảm");
        chkThongKeSanPhamGiam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkThongKeSanPhamGiamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlThongKeSanPhamLayout = new javax.swing.GroupLayout(pnlThongKeSanPham);
        pnlThongKeSanPham.setLayout(pnlThongKeSanPhamLayout);
        pnlThongKeSanPhamLayout.setHorizontalGroup(
            pnlThongKeSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongKeSanPhamLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(pnlThongKeSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongKeSanPhamLayout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(pnlThongKeSanPhamLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(dateChooserTuSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateChooserDenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(chkThongKeSanPhamTang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkThongKeSanPhamGiam)
                        .addGap(113, 113, 113))))
        );
        pnlThongKeSanPhamLayout.setVerticalGroup(
            pnlThongKeSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongKeSanPhamLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(pnlThongKeSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongKeSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(dateChooserDenSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                        .addComponent(dateChooserTuSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongKeSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(chkThongKeSanPhamTang)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(chkThongKeSanPhamGiam)))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        jTabbedPane1.addTab("Thống kê sản phẩm", pnlThongKeSanPham);

        pnlThongKeKhachHang.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable1);

        javax.swing.GroupLayout pnlThongKeKhachHangLayout = new javax.swing.GroupLayout(pnlThongKeKhachHang);
        pnlThongKeKhachHang.setLayout(pnlThongKeKhachHangLayout);
        pnlThongKeKhachHangLayout.setHorizontalGroup(
            pnlThongKeKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongKeKhachHangLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1053, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlThongKeKhachHangLayout.setVerticalGroup(
            pnlThongKeKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongKeKhachHangLayout.createSequentialGroup()
                .addContainerGap(143, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        jTabbedPane1.addTab("Thống kê khách hàng", pnlThongKeKhachHang);

        pnlThongKeDoanhThu.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnlThongKeDoanhThuLayout = new javax.swing.GroupLayout(pnlThongKeDoanhThu);
        pnlThongKeDoanhThu.setLayout(pnlThongKeDoanhThuLayout);
        pnlThongKeDoanhThuLayout.setHorizontalGroup(
            pnlThongKeDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1076, Short.MAX_VALUE)
        );
        pnlThongKeDoanhThuLayout.setVerticalGroup(
            pnlThongKeDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 540, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Thống kê doanh thu", pnlThongKeDoanhThu);

        pnlThongKeTonKho.setBackground(new java.awt.Color(255, 255, 255));

        tblThongKeTonKho.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Tên sản phẩm", "Loại", "Số lượng", "Đơn giá"
            }
        ));
        jScrollPane2.setViewportView(tblThongKeTonKho);

        jLabel4.setText("Sắp xếp theo số lượng");

        btnTimKiemTonKho.setText("Tìm kiếm");
        btnTimKiemTonKho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemTonKhoActionPerformed(evt);
            }
        });

        txtTimKiemThongKeTonKho.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemThongKeTonKhoKeyReleased(evt);
            }
        });

        bgrThongKeTonKho.add(chkTonKhoTang);
        chkTonKhoTang.setText("Tăng");
        chkTonKhoTang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkTonKhoTangItemStateChanged(evt);
            }
        });

        bgrThongKeTonKho.add(chkTonKhoGiam);
        chkTonKhoGiam.setText("Giảm");
        chkTonKhoGiam.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkTonKhoGiamItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout pnlThongKeTonKhoLayout = new javax.swing.GroupLayout(pnlThongKeTonKho);
        pnlThongKeTonKho.setLayout(pnlThongKeTonKhoLayout);
        pnlThongKeTonKhoLayout.setHorizontalGroup(
            pnlThongKeTonKhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongKeTonKhoLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pnlThongKeTonKhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongKeTonKhoLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1029, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(26, Short.MAX_VALUE))
                    .addGroup(pnlThongKeTonKhoLayout.createSequentialGroup()
                        .addComponent(txtTimKiemThongKeTonKho, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTimKiemTonKho)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chkTonKhoTang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkTonKhoGiam)
                        .addGap(90, 90, 90))))
        );
        pnlThongKeTonKhoLayout.setVerticalGroup(
            pnlThongKeTonKhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongKeTonKhoLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(pnlThongKeTonKhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiemThongKeTonKho, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiemTonKho, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkTonKhoTang)
                    .addComponent(chkTonKhoGiam))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Thống kê tồn kho", pnlThongKeTonKho);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        getContentPane().add(jPanel2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chkThongKeSanPhamTangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkThongKeSanPhamTangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkThongKeSanPhamTangActionPerformed

    private void chkThongKeSanPhamGiamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkThongKeSanPhamGiamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkThongKeSanPhamGiamActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Date dateTo = dateChooserTuSanPham.getDate();
        Date dateFrom = dateChooserDenSanPham.getDate();
        if (dateTo.after(dateFrom)) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày ");
        } else {
            thongKeSanPham(DateHelper.toString(dateTo, "yyyy-MM-dd"), DateHelper.toString(dateFrom, "yyyy-MM-dd"));
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void chkThongKeSanPhamTangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkThongKeSanPhamTangItemStateChanged

        sapXepThongKeSanPham();
    }//GEN-LAST:event_chkThongKeSanPhamTangItemStateChanged

    private void chkTonKhoTangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkTonKhoTangItemStateChanged
        if (txtTimKiemThongKeTonKho.getText().trim().equals("")) {
            timKiemTonKho();
        } else {
            sapXepThongKeSanPham();
        }
    }//GEN-LAST:event_chkTonKhoTangItemStateChanged

    private void chkTonKhoGiamItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkTonKhoGiamItemStateChanged
        thongKeTonKho();
    }//GEN-LAST:event_chkTonKhoGiamItemStateChanged

    private void btnTimKiemTonKhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemTonKhoActionPerformed
             timKiemTonKho();
    }//GEN-LAST:event_btnTimKiemTonKhoActionPerformed

    private void txtTimKiemThongKeTonKhoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemThongKeTonKhoKeyReleased
        if (txtTimKiemThongKeTonKho.getText().trim().equals("")) {
            thongKeTonKho();
        }
    }//GEN-LAST:event_txtTimKiemThongKeTonKhoKeyReleased

    /**
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
            java.util.logging.Logger.getLogger(FormTongHopThongKeKeKeKe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormTongHopThongKeKeKeKe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormTongHopThongKeKeKeKe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormTongHopThongKeKeKeKe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormTongHopThongKeKeKeKe().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrThongKeSanPham;
    private javax.swing.ButtonGroup bgrThongKeTonKho;
    private javax.swing.JButton btnTimKiemTonKho;
    private javax.swing.JCheckBox chkThongKeSanPhamGiam;
    private javax.swing.JCheckBox chkThongKeSanPhamTang;
    private javax.swing.JCheckBox chkTonKhoGiam;
    private javax.swing.JCheckBox chkTonKhoTang;
    private com.toedter.calendar.JDateChooser dateChooserDenSanPham;
    private com.toedter.calendar.JDateChooser dateChooserTuSanPham;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel pnlThongKeDoanhThu;
    private javax.swing.JPanel pnlThongKeKhachHang;
    private javax.swing.JPanel pnlThongKeSanPham;
    private javax.swing.JPanel pnlThongKeTonKho;
    private javax.swing.JTable tblThongKeSanPham;
    private javax.swing.JTable tblThongKeTonKho;
    private javax.swing.JTextField txtTimKiemThongKeTonKho;
    // End of variables declaration//GEN-END:variables
}
