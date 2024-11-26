/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GiaoDien;

import com.sales.DAO.OrderDAO;
import com.sales.DAO.Order_DetailDAO;
import com.sales.Entity.Order;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author NganTTK_PC09494
 */
public class FormHoaDon extends javax.swing.JFrame {

    OrderDAO orderDAO = new OrderDAO();
    Order_DetailDAO orderDetailDAO = new Order_DetailDAO();
    DefaultTableModel modelTblSP = new DefaultTableModel(); //model table sản phẩm

    List<Object[]> list;
    Object[] ob = null;

    public FormHoaDon() {
        initComponents();
        dateChooserNgayBatDau.setDateFormatString("MM/dd/yyyy"); //Đổi định dạng khi hiển thị ngày trong dateChooser
        dateChooserNgayKetThuc.setDateFormatString("MM/dd/yyyy");
        dateChooserNgayTao.setDateFormatString("MM/dd/yyyy");
        fillTable();
        fillCombobox();
        
    }
    
    public void clearForm() {
        String t = "";
        txtNguoiTao.setText(t);
        txtKhachHang.setText(t);
        txtTongTien.setText(t);
        dateChooserNgayTao.setDate(null);
        cboTrangThai.setSelectedIndex(0);
        modelTblSP.setRowCount(0);
        
    }

    public void fillCombobox() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addElement("Bình Thường");
        model.addElement(("Hủy"));

        cboTrangThai.setModel(model);
    }

    public void getDetail(int index) {
        ob = list.get(index);
        try {
            txtNguoiTao.setText(ob[1].toString());
            txtKhachHang.setText(ob[2].toString());
            System.out.println(ob[3].toString());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dateChooserNgayTao.setDate(sdf.parse(ob[3].toString()));
            txtTongTien.setText(ob[4].toString());
            cboTrangThai.setSelectedIndex(Integer.parseInt(ob[5].toString()));
        } catch (Exception e) {
            System.out.println("Lỗi:" + e);
        }
    }

    public void fillTableSanPham() {
        try {
            String[] header = {"STT", "Tên Sản phẩm", "Số lượng", "Giá"};
            modelTblSP = new DefaultTableModel(header, 0);

            List<Object[]> list = orderDetailDAO.selectByIDOrder((Integer) ob[0]);
            int stt = 1;
            for (Object[] ob : list) {
                Object[] row = {stt, ob[0], ob[1], ob[2]};
                modelTblSP.addRow(row);
            }

            tblSanPham.setModel(modelTblSP);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void fillTable() {
        try {
            String[] header = {"ID", "Người tạo", "Khách hàng", "Ngày tạo", "Tổng tiền", "Trạng thái"};
            DefaultTableModel model = new DefaultTableModel(header, 0);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); //Định dạng ngày
            Date dateBD = new Date();
            dateBD = sdf.parse("1970/01/01");   //Ngày bắt đầu mặc định 

            Date dateKT = new Date();

            if (dateChooserNgayBatDau.getDate() == null && dateChooserNgayKetThuc.getDate() == null) { //Trường hợp người dùng kh nhập tìm kiếm
                list = orderDAO.selectByDate(dateBD, dateKT);
            } else if (dateChooserNgayBatDau.getDate() != null && dateChooserNgayKetThuc.getDate() == null) { //TH: Chỉ nhập ngày bắt đầu , ngày kt null
                dateBD = dateChooserNgayBatDau.getDate();
                dateKT = new Date(); //Ngày Kết thúc lấy ngày hiện tại
                list = orderDAO.selectByDate(dateBD, dateKT);
            } else if (dateChooserNgayBatDau.getDate() == null && dateChooserNgayKetThuc.getDate() != null) { //TH: Chỉ nhập ngày kết thúc
                dateBD = sdf.parse("1970-01-01"); //Ngày bắt đầu lấy ngày mặc định
                list = orderDAO.selectByDate(dateBD, dateChooserNgayKetThuc.getDate());
            } else { // TH: Nhập tìm kiếm bằng cả 2 ngày
                list = orderDAO.selectByDate(dateChooserNgayBatDau.getDate(), dateChooserNgayKetThuc.getDate());
            }
            for (Object[] row : list) {
                model.addRow(row);
            }
            tblHoaDon.setModel(model);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void update() {
         try {
            Order order = orderDAO.selectByID(1);
            int status = cboTrangThai.getSelectedIndex();
            order.setStatus(status); 
            System.out.println("Hóa đơn: " + order.getId());
            orderDAO.update(order);
            fillTable();
            
            clearForm();
            JOptionPane.showMessageDialog(this, "Cập nhật trạng thái thành công");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cập nhật trạng thái không thành công");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblNguoiTao = new javax.swing.JLabel();
        txtNguoiTao = new javax.swing.JTextField();
        lblKhachHang = new javax.swing.JLabel();
        txtKhachHang = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        dateChooserNgayTao = new com.toedter.calendar.JDateChooser();
        lblTongTien = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JTextField();
        lblTrangThai = new javax.swing.JLabel();
        cboTrangThai = new javax.swing.JComboBox<>();
        lblTimKiem = new javax.swing.JPanel();
        btnTim = new javax.swing.JButton();
        dateChooserNgayBatDau = new com.toedter.calendar.JDateChooser();
        dateChooserNgayKetThuc = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        btnSua = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setText("HÓA ĐƠN");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1218, 117, 126, -1));

        lblNguoiTao.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblNguoiTao.setText("Người tạo");
        jPanel1.add(lblNguoiTao, new org.netbeans.lib.awtextra.AbsoluteConstraints(997, 186, -1, -1));

        txtNguoiTao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel1.add(txtNguoiTao, new org.netbeans.lib.awtextra.AbsoluteConstraints(1102, 183, 382, 36));

        lblKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblKhachHang.setText("Khách hàng");
        jPanel1.add(lblKhachHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(983, 249, -1, -1));

        txtKhachHang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel1.add(txtKhachHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(1102, 246, 382, 36));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Ngày Tạo");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1003, 313, -1, -1));

        dateChooserNgayTao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel1.add(dateChooserNgayTao, new org.netbeans.lib.awtextra.AbsoluteConstraints(1102, 313, 382, 36));

        lblTongTien.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTongTien.setText("Tổng tiền");
        jPanel1.add(lblTongTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(992, 388, 92, -1));

        txtTongTien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel1.add(txtTongTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(1102, 385, 382, 36));

        lblTrangThai.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTrangThai.setText("Trạng thái");
        jPanel1.add(lblTrangThai, new org.netbeans.lib.awtextra.AbsoluteConstraints(986, 457, 98, -1));

        cboTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cboTrangThai, new org.netbeans.lib.awtextra.AbsoluteConstraints(1102, 454, 382, 36));

        lblTimKiem.setBackground(new java.awt.Color(255, 255, 255));
        lblTimKiem.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tìm kiếm"));

        btnTim.setBackground(new java.awt.Color(255, 255, 0));
        btnTim.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnTim.setIcon(new javax.swing.ImageIcon("D:\\DuAnCuaHangGiay\\src\\Icon\\add.png")); // NOI18N
        btnTim.setText("Tìm");
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });

        dateChooserNgayBatDau.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        dateChooserNgayKetThuc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Từ ngày: ");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Đến ngày:");

        javax.swing.GroupLayout lblTimKiemLayout = new javax.swing.GroupLayout(lblTimKiem);
        lblTimKiem.setLayout(lblTimKiemLayout);
        lblTimKiemLayout.setHorizontalGroup(
            lblTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lblTimKiemLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(lblTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateChooserNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(60, 60, 60)
                .addGroup(lblTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lblTimKiemLayout.createSequentialGroup()
                        .addComponent(dateChooserNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(btnTim))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        lblTimKiemLayout.setVerticalGroup(
            lblTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lblTimKiemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(lblTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(lblTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnTim)
                    .addComponent(dateChooserNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateChooserNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(lblTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 117, -1, -1));

        tblHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
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
        tblHoaDon.setGridColor(new java.awt.Color(0, 0, 0));
        tblHoaDon.setRowHeight(22);
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHoaDon);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 271, 734, 448));

        btnSua.setBackground(new java.awt.Color(255, 255, 0));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSua.setIcon(new javax.swing.ImageIcon("D:\\DuAnCuaHangGiay\\src\\Icon\\edit.png")); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        jPanel1.add(btnSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(1300, 650, 100, -1));

        btnMoi.setBackground(new java.awt.Color(255, 255, 0));
        btnMoi.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnMoi.setIcon(new javax.swing.ImageIcon("D:\\DuAnCuaHangGiay\\src\\Icon\\clear.png")); // NOI18N
        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });
        jPanel1.add(btnMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 650, -1, -1));

        tblSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
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
        tblSanPham.setRowHeight(22);
        jScrollPane2.setViewportView(tblSanPham);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(986, 524, 498, 88));

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("QUẢN LÝ HÓA ĐƠN");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(jLabel5)
                .addContainerGap(1285, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(23, 23, 23))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -15, 1600, 90));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        fillTable();
    }//GEN-LAST:event_btnTimActionPerformed

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        int index = tblHoaDon.getSelectedRow();
        getDetail(index);

        txtNguoiTao.setEditable(false);
        txtKhachHang.setEditable(false);
        dateChooserNgayTao.setEnabled(false);
        txtTongTien.setEditable(false);
        fillTableSanPham();
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

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
            java.util.logging.Logger.getLogger(FormHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormHoaDon().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnTim;
    private javax.swing.JComboBox<String> cboTrangThai;
    private com.toedter.calendar.JDateChooser dateChooserNgayBatDau;
    private com.toedter.calendar.JDateChooser dateChooserNgayKetThuc;
    private com.toedter.calendar.JDateChooser dateChooserNgayTao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblKhachHang;
    private javax.swing.JLabel lblNguoiTao;
    private javax.swing.JPanel lblTimKiem;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JLabel lblTrangThai;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtKhachHang;
    private javax.swing.JTextField txtNguoiTao;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}
