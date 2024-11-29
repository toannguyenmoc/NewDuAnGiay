/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GiaoDien;

import com.chart.ModelChart;
import com.sales.DAO.StatisticDAO;
import com.sales.Entity.User;
import com.sales.Utils.Auth;
import com.sales.Utils.DateHelper;
import com.sales.Utils.XImage;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;

/**
 *
 * @author NganTTK_PC09494
 */
public class FormManHinhChinh extends javax.swing.JFrame {

    User user = Auth.user;

    /**
     * Creates new form FormManHinhChinh
     */
    public FormManHinhChinh() {
        initComponents();
        init();
    }

    public void init() {
        // Cài đặt giao diện của JFrame
        setLocationRelativeTo(null);
        setIconImage(XImage.XImage());  // XImage là một lớp tùy chỉnh bạn có thể thay thế
        setTitle("PHẦN MỀM QUẢN LÝ GIÀY THỂ THAO");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        kiemtra();
        Quyen();
        if (Auth.user != null) {
            lblTenNhanVien.setText(user.getFullName());
            lblQuyen.setText(user.getRole() ? "Quản Lý" : "Nhân Viên");
        }
    }

    public void Quyen() {
        if (Auth.isLogin() && !Auth.isManager()) {
            for (MouseListener ml : lblNhanVien.getMouseListeners()) {
                lblNhanVien.removeMouseListener(ml);
            }
            for (MouseListener ml : lblThongKe.getMouseListeners()) {
                lblThongKe.removeMouseListener(ml);
            }
        } else {
            BieuDoDoanhThuNhanVien();
        }
    }

    public void BieuDoDoanhThuNhanVien() {
        // Lấy dữ liệu thống kê doanh thu theo năm 2024
        StatisticDAO thongKe = new StatisticDAO();
        List<Object[]> list = thongKe.getThongKeDoanhThu(2024); // Lấy doanh thu theo năm

        // Tạo danh sách để lưu tên nhân viên và doanh thu của từng nhân viên cho từng tháng
        List<String> danhSachTenNhanVien = new ArrayList<>();
        List<double[]> doanhThuThang = new ArrayList<>();

        // Duyệt qua danh sách nhân viên và doanh thu của từng người
        for (int i = 0; i < list.size(); i++) {
            Object[] dt = list.get(i);
            String tenNhanVien = dt[0].toString(); // Lấy tên nhân viên từ cột 1

            // Lấy doanh thu của nhân viên cho từng tháng từ cột 2 đến cột 13
            double[] doanhThu = new double[12];
            for (int j = 1; j <= 12; j++) {
                doanhThu[j - 1] = Double.parseDouble(dt[j].toString()); // Doanh thu từng tháng
            }

            // Thêm tên nhân viên vào legend
            chart.addLegend(tenNhanVien, new Color((int) (Math.random() * 0x1000000)));

            // Thêm doanh thu vào danh sách doanh thu
            doanhThuThang.add(doanhThu);
        }

        // Thêm dữ liệu vào biểu đồ (dữ liệu cho từng tháng và doanh thu của mỗi nhân viên)
        for (int month = 0; month < 12; month++) {
            // Lấy doanh thu cho từng tháng của tất cả nhân viên
            double[] doanhThuThangCuaTatCaNhanVien = new double[doanhThuThang.size()];
            for (int i = 0; i < doanhThuThang.size(); i++) {
                doanhThuThangCuaTatCaNhanVien[i] = doanhThuThang.get(i)[month]; // Doanh thu của nhân viên i cho tháng month
            }
            chart.addData(new ModelChart("Tháng " + (month + 1), doanhThuThangCuaTatCaNhanVien));
        }
    }

    public void BieuDo() {
        chart.addLegend("Toàn", new Color(245, 189, 135));
        chart.addLegend("Ngân", new Color(135, 189, 245));
        chart.addLegend("Minh", new Color(189, 135, 245));
        chart.addLegend("Tài", new Color(139, 229, 222));
        chart.addLegend("An", new Color(120, 200, 222));
        chart.addData(new ModelChart("January", new double[]{500, 200, 800, 890, 500}));
        chart.addData(new ModelChart("February", new double[]{600, 750, 90, 150, 350}));
        chart.addData(new ModelChart("March", new double[]{200, 350, 460, 900, 150}));
        chart.addData(new ModelChart("April", new double[]{480, 150, 750, 700, 250}));
        chart.addData(new ModelChart("May", new double[]{350, 540, 300, 150, 500}));
        chart.addData(new ModelChart("June", new double[]{190, 280, 81, 200, 150}));
        chart.addData(new ModelChart("July", new double[]{190, 280, 81, 200, 50}));
    }

    public void BieuDoDoanhThu() {
        StatisticDAO thongKe = new StatisticDAO();
        List<Object[]> list = thongKe.getDoanhThuTheoNam(2024);
        chart.addLegend("Tổng Doanh Thu", new Color(245, 189, 135));
        for (Object[] dt : list) {
            chart.addData(new ModelChart("Tháng " + dt[0], new double[]{Integer.parseInt(dt[1].toString())}));
        }
    }

    public void BieuDoSanPham() {
        StatisticDAO thongKe = new StatisticDAO();
        List<Object[]> list = thongKe.getSanPhamBanChayNhatTuNgayDenNgay(DateHelper.toDate("01-11-2024"), DateHelper.toDate("02-11-2024"));
        chart.addLegend("Số lượng", new Color(245, 189, 135));
        for (Object[] dt : list) {
            chart.addData(new ModelChart(dt[0] + "", new double[]{Integer.parseInt(dt[2].toString())}));
        }
    }

    public void BieuDoKhachHang() {
        StatisticDAO thongKe = new StatisticDAO();
        List<Object[]> list = thongKe.getKhachHangThanThietTuNgayDenNgay(DateHelper.toDate("01-11-2024"), DateHelper.toDate("02-11-2024"));
        chart.addLegend("Đơn Hàng", new Color(245, 189, 135));
        chart.addLegend("Sản Phẩm", new Color(135, 189, 245));
        for (Object[] dt : list) {
            chart.addData(new ModelChart(dt[0] + "", new double[]{Integer.parseInt(dt[2].toString()), Integer.parseInt(dt[3].toString())}));
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

        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        pnlBieuDo = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        chart = new com.chart.Chart();
        jPanel2 = new javax.swing.JPanel();
        lblTenNhanVien = new javax.swing.JLabel();
        lblQuyen = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        lblTrangChu = new javax.swing.JLabel();
        lblKhachHang = new javax.swing.JLabel();
        lblSanPham = new javax.swing.JLabel();
        lblHoaDon = new javax.swing.JLabel();
        lblTaoHoaDon = new javax.swing.JLabel();
        lblThongKe = new javax.swing.JLabel();
        lblDoiMatKhau = new javax.swing.JLabel();
        lblNhanVien = new javax.swing.JLabel();
        lblLoaiSanPham = new javax.swing.JLabel();
        lblThoat = new javax.swing.JLabel();
        lblThuocTinh = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        lblThuongHieu = new javax.swing.JLabel();
        lblGioiThieu = new javax.swing.JLabel();
        lblHuongDan = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(153, 204, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("50 ");
        jPanel4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 22, -1, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel16.setText("Khách hàng");
        jPanel4.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 125, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/client.png"))); // NOI18N
        jLabel2.setText("jLabel2");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(121, 22, 131, -1));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 140, 267, 214));

        jPanel5.setBackground(new java.awt.Color(153, 204, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("85");
        jPanel5.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 14, 85, -1));

        jLabel18.setBackground(new java.awt.Color(204, 255, 204));
        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel18.setText("Sản phẩm");
        jPanel5.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 104, -1, -1));

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/cubes.png"))); // NOI18N
        jPanel5.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, -1, -1));

        jPanel3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 140, 267, 214));

        jPanel6.setBackground(new java.awt.Color(153, 204, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("150");

        jLabel20.setBackground(new java.awt.Color(204, 255, 204));
        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel20.setText("Đơn hàng");

        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/order-history.png"))); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20))
                .addGap(43, 43, 43)
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 115, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel20))
                    .addComponent(jLabel35))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 140, -1, 214));

        jPanel7.setBackground(new java.awt.Color(153, 204, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("85,560,000");

        jLabel22.setBackground(new java.awt.Color(204, 255, 204));
        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel22.setText("Doanh thu");

        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/profit.png"))); // NOI18N
        jLabel36.setText("jLabel36");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel21)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel22)
                        .addGap(87, 87, 87))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel36))))
        );

        jPanel3.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 140, -1, -1));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 102, 102));
        jLabel25.setText("Top sản phẩm bán chạy trong tháng");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addComponent(jLabel25)
                .addGap(27, 27, 27))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1201, 377, -1, 420));

        pnlBieuDo.setBackground(new java.awt.Color(204, 255, 204));
        pnlBieuDo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel28.setText("Biểu đồ tăng trưởng trong năm ");

        javax.swing.GroupLayout pnlBieuDoLayout = new javax.swing.GroupLayout(pnlBieuDo);
        pnlBieuDo.setLayout(pnlBieuDoLayout);
        pnlBieuDoLayout.setHorizontalGroup(
            pnlBieuDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBieuDoLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel28)
                .addContainerGap(580, Short.MAX_VALUE))
            .addGroup(pnlBieuDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlBieuDoLayout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(chart, javax.swing.GroupLayout.PREFERRED_SIZE, 835, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(23, Short.MAX_VALUE)))
        );
        pnlBieuDoLayout.setVerticalGroup(
            pnlBieuDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBieuDoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28)
                .addContainerGap(387, Short.MAX_VALUE))
            .addGroup(pnlBieuDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlBieuDoLayout.createSequentialGroup()
                    .addGap(33, 33, 33)
                    .addComponent(chart, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(18, Short.MAX_VALUE)))
        );

        jPanel3.add(pnlBieuDo, new org.netbeans.lib.awtextra.AbsoluteConstraints(314, 377, 880, 420));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTenNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTenNhanVien.setText("User name");
        jPanel2.add(lblTenNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 20, 120, 20));

        lblQuyen.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblQuyen.setForeground(new java.awt.Color(153, 153, 153));
        lblQuyen.setText("Admin");
        jPanel2.add(lblQuyen, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 48, 120, 20));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/bell (2).png"))); // NOI18N
        jLabel5.setToolTipText("");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(905, 40, -1, -1));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(947, 1, 14, 87));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/check-mail.png"))); // NOI18N
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(863, 40, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/avatar.png"))); // NOI18N
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 10, 80, -1));

        jPanel3.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, 1190, -1));

        jPanel9.setBackground(new java.awt.Color(0, 153, 153));

        lblTrangChu.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTrangChu.setForeground(new java.awt.Color(255, 255, 255));
        lblTrangChu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/homepage.png"))); // NOI18N
        lblTrangChu.setText("Trang chủ");
        lblTrangChu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblTrangChu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTrangChuMouseClicked(evt);
            }
        });

        lblKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblKhachHang.setForeground(new java.awt.Color(255, 255, 255));
        lblKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/customer (1).png"))); // NOI18N
        lblKhachHang.setText("Khách hàng");
        lblKhachHang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblKhachHangMouseClicked(evt);
            }
        });

        lblSanPham.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblSanPham.setForeground(new java.awt.Color(255, 255, 255));
        lblSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/best-product.png"))); // NOI18N
        lblSanPham.setText("Sản phẩm");
        lblSanPham.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSanPhamMouseClicked(evt);
            }
        });

        lblHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        lblHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/invoice (1).png"))); // NOI18N
        lblHoaDon.setText("Hóa đơn");
        lblHoaDon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHoaDonMouseClicked(evt);
            }
        });

        lblTaoHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTaoHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        lblTaoHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/sales.png"))); // NOI18N
        lblTaoHoaDon.setText("Tạo hóa đơn");
        lblTaoHoaDon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblTaoHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTaoHoaDonMouseClicked(evt);
            }
        });

        lblThongKe.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblThongKe.setForeground(new java.awt.Color(255, 255, 255));
        lblThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/analytics.png"))); // NOI18N
        lblThongKe.setText("Thống kê");
        lblThongKe.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblThongKe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblThongKeMouseClicked(evt);
            }
        });

        lblDoiMatKhau.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblDoiMatKhau.setForeground(new java.awt.Color(255, 255, 255));
        lblDoiMatKhau.setText("Đổi mật khẩu");
        lblDoiMatKhau.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDoiMatKhauMouseClicked(evt);
            }
        });

        lblNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblNhanVien.setForeground(new java.awt.Color(255, 255, 255));
        lblNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/new-employee.png"))); // NOI18N
        lblNhanVien.setText("Nhân viên");
        lblNhanVien.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNhanVienMouseClicked(evt);
            }
        });

        lblLoaiSanPham.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblLoaiSanPham.setForeground(new java.awt.Color(255, 255, 255));
        lblLoaiSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/categories.png"))); // NOI18N
        lblLoaiSanPham.setText("Loại sản phẩm");
        lblLoaiSanPham.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLoaiSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLoaiSanPhamMouseClicked(evt);
            }
        });

        lblThoat.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblThoat.setForeground(new java.awt.Color(255, 255, 255));
        lblThoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/exit (1).png"))); // NOI18N
        lblThoat.setText("Thoát");
        lblThoat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblThoatMouseClicked(evt);
            }
        });

        lblThuocTinh.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblThuocTinh.setForeground(new java.awt.Color(255, 255, 255));
        lblThuocTinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/colored-pencils.png"))); // NOI18N
        lblThuocTinh.setText("Thuộc tính");
        lblThuocTinh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblThuocTinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblThuocTinhMouseClicked(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel29.setText("LoGo");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Tên ứng dụng");

        lblThuongHieu.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblThuongHieu.setForeground(new java.awt.Color(255, 255, 255));
        lblThuongHieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/brand-image.png"))); // NOI18N
        lblThuongHieu.setText("Thương hiệu");
        lblThuongHieu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblThuongHieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblThuongHieuMouseClicked(evt);
            }
        });

        lblGioiThieu.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblGioiThieu.setForeground(new java.awt.Color(255, 255, 255));
        lblGioiThieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/training.png"))); // NOI18N
        lblGioiThieu.setText("Giới thiệu");
        lblGioiThieu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblGioiThieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblGioiThieuMouseClicked(evt);
            }
        });

        lblHuongDan.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblHuongDan.setForeground(new java.awt.Color(255, 255, 255));
        lblHuongDan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/guidebook.png"))); // NOI18N
        lblHuongDan.setText("Hướng dẫn");
        lblHuongDan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblHuongDan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHuongDanMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblDoiMatKhau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblThoat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(89, 89, 89))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTrangChu, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblLoaiSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTaoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblGioiThieu, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblHuongDan, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30))
                .addGap(50, 50, 50)
                .addComponent(lblTrangChu, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(lblNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblKhachHang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblLoaiSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblHoaDon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTaoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(lblThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblGioiThieu, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblHuongDan, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74)
                .addComponent(lblDoiMatKhau, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblThoat)
                .addGap(39, 39, 39))
        );

        jPanel3.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 302, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-5, -10, 1550, 810));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblTrangChuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTrangChuMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblTrangChuMouseClicked

    private void lblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNhanVienMouseClicked
        FormNguoiDung formNguoiDung = new FormNguoiDung();
        formNguoiDung.setVisible(true);
        closing(formNguoiDung);
    }//GEN-LAST:event_lblNhanVienMouseClicked

    private void lblDoiMatKhauMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDoiMatKhauMouseClicked
        FormDoiMatKhau formDoiMatKhau = new FormDoiMatKhau();
        formDoiMatKhau.setVisible(true);
        closing(formDoiMatKhau);
    }//GEN-LAST:event_lblDoiMatKhauMouseClicked

    private void lblThoatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThoatMouseClicked
        new FormDangNhap().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblThoatMouseClicked

    private void lblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblKhachHangMouseClicked
        FormKhachHang formKhachHang = new FormKhachHang();
        formKhachHang.setVisible(true);
        closing(formKhachHang);
    }//GEN-LAST:event_lblKhachHangMouseClicked

    private void lblLoaiSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLoaiSanPhamMouseClicked
        FormLoaiSanPham formLoaiSanPham = new FormLoaiSanPham();
        formLoaiSanPham.setVisible(true);
        closing(formLoaiSanPham);
    }//GEN-LAST:event_lblLoaiSanPhamMouseClicked

    private void lblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSanPhamMouseClicked
        FormSanPham formSanPham = new FormSanPham();
        formSanPham.setVisible(true);
        closing(formSanPham);
    }//GEN-LAST:event_lblSanPhamMouseClicked

    private void lblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHoaDonMouseClicked
        FormHoaDon formHoaDon = new FormHoaDon();
        formHoaDon.setVisible(true);
        closing(formHoaDon);
    }//GEN-LAST:event_lblHoaDonMouseClicked

    private void lblTaoHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTaoHoaDonMouseClicked
        FormTaoHoaDon formTaoHoaDon = new FormTaoHoaDon();
        formTaoHoaDon.setVisible(true);
        closing(formTaoHoaDon);
    }//GEN-LAST:event_lblTaoHoaDonMouseClicked

    private void lblThongKeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThongKeMouseClicked
        FormTongHopThongKe formThongKe = new FormTongHopThongKe();
        formThongKe.setVisible(true);
        closing(formThongKe);
    }//GEN-LAST:event_lblThongKeMouseClicked

    private void lblThuocTinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThuocTinhMouseClicked
        FormThuocTinh formThuocTinh = new FormThuocTinh();
        formThuocTinh.setVisible(true);
        closing(formThuocTinh);
    }//GEN-LAST:event_lblThuocTinhMouseClicked

    private void lblThuongHieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThuongHieuMouseClicked
        FormThuongHieu formThuongHieu = new FormThuongHieu();
        formThuongHieu.setVisible(true);
        closing(formThuongHieu);
    }//GEN-LAST:event_lblThuongHieuMouseClicked

    private void lblGioiThieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGioiThieuMouseClicked
        FormGioiThieu formGioiThieu = new FormGioiThieu();
        formGioiThieu.setVisible(true);
        closing(formGioiThieu);
    }//GEN-LAST:event_lblGioiThieuMouseClicked

    private void lblHuongDanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHuongDanMouseClicked
        FormHuongDan formHuongDan = new FormHuongDan();
        formHuongDan.setVisible(true);
        closing(formHuongDan);
    }//GEN-LAST:event_lblHuongDanMouseClicked

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
            java.util.logging.Logger.getLogger(FormManHinhChinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormManHinhChinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormManHinhChinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormManHinhChinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormManHinhChinh().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.chart.Chart chart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblDoiMatKhau;
    private javax.swing.JLabel lblGioiThieu;
    private javax.swing.JLabel lblHoaDon;
    private javax.swing.JLabel lblHuongDan;
    private javax.swing.JLabel lblKhachHang;
    private javax.swing.JLabel lblLoaiSanPham;
    private javax.swing.JLabel lblNhanVien;
    private javax.swing.JLabel lblQuyen;
    private javax.swing.JLabel lblSanPham;
    private javax.swing.JLabel lblTaoHoaDon;
    private javax.swing.JLabel lblTenNhanVien;
    private javax.swing.JLabel lblThoat;
    private javax.swing.JLabel lblThongKe;
    private javax.swing.JLabel lblThuocTinh;
    private javax.swing.JLabel lblThuongHieu;
    private javax.swing.JLabel lblTrangChu;
    private javax.swing.JPanel pnlBieuDo;
    // End of variables declaration//GEN-END:variables

    public void kiemtra() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                if (Auth.user == null) {
                    new FormDangNhap().setVisible(true);
                    dispose();
                }
            }
        });
    }

    public void closing(JFrame F) {
        FormManHinhChinh.this.setVisible(false);
        F.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                FormManHinhChinh.this.setVisible(true);
                F.dispose();
            }
        });
    }

}
