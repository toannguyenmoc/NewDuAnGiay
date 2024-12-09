/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GiaoDien;

import com.sales.DAO.OrderDAO;
import com.sales.DAO.Order_DetailDAO;
import com.sales.Entity.Order;
import com.sales.Utils.XImage;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    int index;

    public FormHoaDon() {
        initComponents();
        setLocationRelativeTo(null);
        setIconImage(XImage.XImage());
        setTitle("PHẦN MỀM QUẢN LÝ GIÀY THỂ THAO");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        dateChooserNgayBatDau.setDateFormatString("dd/MM/yyyy"); //Đổi định dạng khi hiển thị ngày trong dateChooser
        dateChooserNgayKetThuc.setDateFormatString("dd/MM/yyyy");
        dateChooserNgayTao.setDateFormatString("dd/MM/yyyy");

        dateChooserNgayBatDau.getDateEditor().getUiComponent().setFocusable(false);
        dateChooserNgayKetThuc.getDateEditor().getUiComponent().setFocusable(false);
        dateChooserNgayTao.getDateEditor().getUiComponent().setFocusable(false);
        btnHuy.setEnabled(false);
        chkAll.setSelected(true);
        fillTable();
        fillCombobox();
        setTitle("PHẦN MỀM QUẢN LÝ GIÀY THỂ THAO");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setIconImage(XImage.XImage());
    }

    public void clearForm() {
        String t = "";
        txtNguoiTao.setText(t);
        txtKhachHang.setText(t);
        txtTongTien.setText(t);
        dateChooserNgayTao.setDate(null);
        modelTblSP.setRowCount(0);
        tblHoaDon.setRowSelectionAllowed(false);

    }

    public void fillCombobox() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addElement("Bình Thường");
        model.addElement(("Hủy"));
        ;
    }

    public void getDetail(int index) {
        ob = list.get(index);
        try {
            txtNguoiTao.setText(ob[1].toString());
            txtKhachHang.setText(ob[2].toString());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dateChooserNgayTao.setDate(sdf.parse(ob[3].toString()));
            txtTongTien.setText(dinhDangMoney(Integer.parseInt(ob[4].toString())));

        } catch (Exception e) {
            System.out.println("Lỗi:" + e);
        }
    }

    public String dinhDangDate(String originalDate) {
        // Định dạng ban đầu
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        // Định dạng mong muốn
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            // Chuyển đổi từ chuỗi thành kiểu Date
            Date date = inputFormat.parse(originalDate);
            // Chuyển đổi Date thành chuỗi với định dạng mới
            return outputFormat.format(date);

        } catch (ParseException e) {
            System.err.println("Lỗi định dạng ngày: " + e.getMessage());
            return null;
        }
    }

    public void fillTableSanPham() {
        try {
            String[] header = {"STT", "Tên Sản phẩm", "Số lượng", "Giá"};
            modelTblSP = new DefaultTableModel(header, 0);

            List<Object[]> list = orderDetailDAO.selectByIDOrder((Integer) ob[0]);

            int stt = 1;
            for (Object[] ob : list) {
                Object[] row = {stt, ob[0], ob[1], dinhDangMoney((int) ob[2])};
                modelTblSP.addRow(row);
                stt++;
            }

            tblSanPham.setModel(modelTblSP);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String dinhDangMoney(int originalDate) {
        // Lấy NumberFormat theo định dạng mặc định (Locale Việt Nam)
        NumberFormat formatter = NumberFormat.getInstance(Locale.US);

        // Định dạng và in ra
        return formatter.format(originalDate);
    }

    public void fillTable() {
        try {
            String[] header = {"ID", "Người tạo", "Khách hàng", "Ngày tạo", "Tổng tiền", "Trạng thái"};
            DefaultTableModel model = new DefaultTableModel(header, 0);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); //Định dạng ngày
            Date dateBD = new Date();
            Date dateKT = new Date();

            dateBD = sdf.parse("1970/01/01");   //Ngày bắt đầu mặc định 

            if (dateBD.after(dateKT)) {
                JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc");
                return;
            }
            if (dateChooserNgayBatDau.getDate() == null && dateChooserNgayKetThuc.getDate() == null) { //Trường hợp người dùng kh nhập tìm kiếm
                list = orderDAO.selectByDate(dateBD, dateKT);
            } else if (dateChooserNgayBatDau.getDate() != null && dateChooserNgayKetThuc.getDate() == null) { //TH: Chỉ nhập ngày bắt đầu , ngày kt null
                dateBD = dateChooserNgayBatDau.getDate();
                dateKT = new Date(); //Ngày Kết thúc lấy ngày hiện tại
                list = orderDAO.selectByDate(dateBD, dateKT);
            } else if (dateChooserNgayBatDau.getDate() == null && dateChooserNgayKetThuc.getDate() != null) { //TH: Chỉ nhập ngày kết thúc
                dateBD = sdf.parse("1970/01/01"); //Ngày bắt đầu lấy ngày mặc định
                list = orderDAO.selectByDate(dateBD, dateChooserNgayKetThuc.getDate());
            } else { // TH: Nhập tìm kiếm bằng cả 2 ngày
                if (dateChooserNgayBatDau.getDate().after(dateChooserNgayKetThuc.getDate())) {
                    JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải nhỏ hơn ngày kết thúc!");
                } else {
                    list = orderDAO.selectByDate(dateChooserNgayBatDau.getDate(), dateChooserNgayKetThuc.getDate());
                }
            }

            for (Object[] row : list) {
                if (chkHoanThanh.isSelected()) {
                    if((int)row[5] == 1 )
                        continue;
                } else if (chkHuy.isSelected()) {
                    if((int)row[5] == 0 )
                        continue;
                }
                String tongTien = dinhDangMoney((int) row[4]);
                String status = (int) row[5] == 0 ? "Hoàn thành" : "Hủy";
                Object[] rowDinhDang = {row[0], row[1], row[2], dinhDangDate(row[3].toString()), tongTien, status};
                model.addRow(rowDinhDang);
            }
            tblHoaDon.setModel(model);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update() {
        try {
            Order order = orderDAO.selectByID((Integer) tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 0));
            if (order.getStatus() == 0) {
                int isDeleted = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn hủy hóa đơn không?", "Hủy hóa đơn", JOptionPane.YES_NO_OPTION);
                if (isDeleted == 0) {
                    order.setStatus(1);
                    orderDAO.update(order);
                    fillTable();
                    clearForm();
                    JOptionPane.showMessageDialog(this, "Hủy thành công");
                }

            } else {
                JOptionPane.showMessageDialog(this, "Hóa đơn này đã hủy! Vui lòng không hủy nữa");
            }
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(this, "Hủy không thành công");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
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
        lblTimKiem = new javax.swing.JPanel();
        btnTim = new javax.swing.JButton();
        dateChooserNgayBatDau = new com.toedter.calendar.JDateChooser();
        dateChooserNgayKetThuc = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnReset = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnHuy = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        chkAll = new javax.swing.JCheckBox();
        chkHoanThanh = new javax.swing.JCheckBox();
        chkHuy = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setText("HÓA ĐƠN");

        lblNguoiTao.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblNguoiTao.setText("Người tạo");

        txtNguoiTao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblKhachHang.setText("Khách hàng");

        txtKhachHang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Ngày Tạo");

        dateChooserNgayTao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblTongTien.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTongTien.setText("Tổng tiền");

        txtTongTien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblTimKiem.setBackground(new java.awt.Color(255, 255, 255));
        lblTimKiem.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tìm kiếm"));

        btnTim.setBackground(new java.awt.Color(255, 255, 0));
        btnTim.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
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

        btnReset.setBackground(new java.awt.Color(255, 255, 0));
        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout lblTimKiemLayout = new javax.swing.GroupLayout(lblTimKiem);
        lblTimKiem.setLayout(lblTimKiemLayout);
        lblTimKiemLayout.setHorizontalGroup(
            lblTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lblTimKiemLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(lblTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateChooserNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(31, 31, 31)
                .addGroup(lblTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lblTimKiemLayout.createSequentialGroup()
                        .addComponent(dateChooserNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnTim)
                        .addGap(18, 18, 18)
                        .addComponent(btnReset))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        lblTimKiemLayout.setVerticalGroup(
            lblTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lblTimKiemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(lblTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(2, 2, 2)
                .addGroup(lblTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateChooserNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateChooserNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(lblTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnTim)
                        .addComponent(btnReset)))
                .addContainerGap(34, Short.MAX_VALUE))
        );

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

        tblSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "STT", "Tên sản phẩm", "Số lượng", "Title 4"
            }
        ));
        tblSanPham.setRowHeight(22);
        jScrollPane2.setViewportView(tblSanPham);

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

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridLayout(1, 0, 40, 0));

        btnHuy.setBackground(new java.awt.Color(255, 255, 0));
        btnHuy.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnHuy.setText("Hủy");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });
        jPanel2.add(btnHuy);

        btnMoi.setBackground(new java.awt.Color(255, 255, 0));
        btnMoi.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });
        jPanel2.add(btnMoi);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Chi tiết hóa đơn");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.GridLayout(1, 0, 15, 0));

        chkAll.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(chkAll);
        chkAll.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        chkAll.setText("All");
        chkAll.setPreferredSize(new java.awt.Dimension(30, 29));
        chkAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAllActionPerformed(evt);
            }
        });
        jPanel4.add(chkAll);

        chkHoanThanh.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(chkHoanThanh);
        chkHoanThanh.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        chkHoanThanh.setText("Hoàn thành");
        chkHoanThanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkHoanThanhActionPerformed(evt);
            }
        });
        jPanel4.add(chkHoanThanh);

        chkHuy.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(chkHuy);
        chkHuy.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        chkHuy.setText("Hủy");
        chkHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkHuyActionPerformed(evt);
            }
        });
        jPanel4.add(chkHuy);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(156, 156, 156))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(136, 136, 136)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(lblTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel4)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(dateChooserNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(lblKhachHang)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(lblNguoiTao)
                                            .addGap(43, 43, 43)
                                            .addComponent(txtNguoiTao, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 734, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(326, 326, 326)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(122, 122, 122))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(73, 73, 73)
                                .addComponent(lblNguoiTao))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(31, 31, 31)
                                .addComponent(txtNguoiTao, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblKhachHang)
                                    .addComponent(txtKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(249, 249, 249)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateChooserNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(lblTongTien))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(40, 40, 40)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(40, 40, 40))
        );

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
        tblHoaDon.setRowSelectionAllowed(true);
        index = tblHoaDon.getSelectedRow();
        getDetail(index);
        btnHuy.setEnabled(true);
        txtNguoiTao.setEditable(false);
        txtKhachHang.setEditable(false);
        dateChooserNgayTao.getDateEditor().getUiComponent().setFocusable(false);
        txtTongTien.setEditable(false);
        fillTableSanPham();
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        update();
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        dateChooserNgayBatDau.setDate(null);
        dateChooserNgayKetThuc.setDate(null);
        fillTable();
    }//GEN-LAST:event_btnResetActionPerformed

    private void chkHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkHuyActionPerformed

        fillTable();
    }//GEN-LAST:event_chkHuyActionPerformed

    private void chkAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAllActionPerformed
        fillTable();
    }//GEN-LAST:event_chkAllActionPerformed

    private void chkHoanThanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkHoanThanhActionPerformed
        fillTable();
    }//GEN-LAST:event_chkHoanThanhActionPerformed

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
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnTim;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkAll;
    private javax.swing.JCheckBox chkHoanThanh;
    private javax.swing.JCheckBox chkHuy;
    private com.toedter.calendar.JDateChooser dateChooserNgayBatDau;
    private com.toedter.calendar.JDateChooser dateChooserNgayKetThuc;
    private com.toedter.calendar.JDateChooser dateChooserNgayTao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblKhachHang;
    private javax.swing.JLabel lblNguoiTao;
    private javax.swing.JPanel lblTimKiem;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtKhachHang;
    private javax.swing.JTextField txtNguoiTao;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}
