/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GiaoDien;

import com.sales.DAO.CustomerDAO;
import com.sales.DAO.OrderDAO;
import com.sales.DAO.Order_DetailDAO;
import com.sales.DAO.Product_VariantDAO;
import com.sales.Entity.Order;
import com.sales.Entity.Order_Detail;
import com.sales.Entity.Product_Variant;
import com.sales.Entity.User;
import com.sales.Utils.Auth;
import com.sales.Utils.JdbcHelper;
import com.sales.Utils.SessionStorage;
import com.sales.Utils.XImage;
import com.sales.Utils.XValidate;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.mail.Header;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author NganTTK_PC09494
 */
public class FormTaoHoaDon extends javax.swing.JFrame {

    OrderDAO orderDAO = new OrderDAO();
    Order_DetailDAO orderDetailDAO = new Order_DetailDAO();
    XValidate validate = new XValidate();
    DefaultTableModel model;
    Object[] product = null;
    int tongSanPham = 1;
    User user = Auth.user;
    CustomerDAO customerDAO = new CustomerDAO();
    Order order;
    Integer idNow = 0;

    public Integer getIdNow() {
        return idNow;
    }

    public FormTaoHoaDon() {
        initComponents();
        init();
        setLocationRelativeTo(null);
        setIconImage(XImage.XImage());
        setTitle("PHẦN MỀM QUẢN LÝ GIÀY THỂ THAO");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setModelTableSanPham();
        SimpleDateFormat formatterDate = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatterTime = new SimpleDateFormat("hh:mm a");
        lblNgayHoaDon.setText("Ngày : " + formatterDate.format(new Date()));
        lblThoiGianHoaDon.setText("Thời gian : " + formatterTime.format(new Date()));
        txtTongTien.setText("0");
        txtTienThua.setText("0");
        txtTongTien.setEditable(false);
        txtTienThua.setEditable(false);
        txtTenKhachHang.setEditable(false);
        chkTienMat.setSelected(true);
        loadOrCreateOrder();
        loadOrderDetail();
    }

    public void init() {
        setLocationRelativeTo(null);
        setIconImage(XImage.XImage());
        setTitle("PHẦN MỀM QUẢN LÝ GIÀY THỂ THAO");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void taoHoaDonMoi() {
        Order neworder = new Order();
        neworder.setCustomersId(1);
        neworder.setUserId(1);
        neworder.setCreateDate(new Date());
        neworder.setTotal(0);
        neworder.setStatus(2);
        orderDAO.insert(neworder);
        System.out.println("Tạo thành công");

    }

    public void loadOrCreateOrder() {
        List<Order> orders = OrderDAO.findByStatus(2);

        if (orders != null && !orders.isEmpty()) { // Kiểm tra đúng logic
            order = orders.get(0); // Lấy đơn hàng đầu tiên
            System.out.println("Order found: " + order);
        } else {
            order = createNewOrder(); // Tạo hóa đơn mới
            System.out.println("Created new order: " + order);
        }

        // Cập nhật giao diện
        txtMaHoaDon.setText("Mã hóa đơn: " + order.getId());
        idNow = order.getId();
    }

    // Phương thức tạo hóa đơn mới
    private Order createNewOrder() {
        taoHoaDonMoi(); // Thực hiện tạo hóa đơn mới
        List<Order> newOrders = OrderDAO.findByStatus(2); // Tìm lại danh sách hóa đơn
        if (newOrders == null || newOrders.isEmpty()) {
            throw new IllegalStateException("Failed to create a new order."); // Xử lý lỗi nếu không tạo được hóa đơn
        }
        return newOrders.get(0); // Trả về hóa đơn mới tạo
    }

    public void loadOrderDetail() {
        try {
            List<Order_Detail> orderDetails = orderDetailDAO.selectAllByIDOrder(order.getId());
            if (orderDetails != null && !orderDetails.isEmpty()) {
                for (int i = 0; i < orderDetails.size(); i++) {
                    Order_Detail ord = orderDetails.get(i);
                    Object[] pro = orderDAO.selectProductById(ord.getProductVariantId(), ord.getId());
                    if (pro != null && pro.length >= 6) {
                        Object[] row = {
                            tongSanPham, // STT
                            pro[1], // Tên sản phẩm
                            pro[2], // Size
                            pro[3], // Màu
                            dinhDangMoney(Integer.parseInt(pro[4].toString())), // Giá
                            pro[5], // Số lượng
                            false // Cột xóa (Boolean)
                        };
                        model.addRow(row); // Thêm hàng mới
                        tongSanPham++;
                        txtTongTien.setText(dinhDangMoney(tinhTongTien()));
                    }
                }
            } else {
                return;
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi tải chi tiết hóa đơn: " + e.getMessage());
        }
    }

    public void setModelTableSanPham() {
        String[] header = {"STT", "Tên Sản phẩm", "Size", "Màu", "Giá", "Số lượng", "Xóa"};
        model = new DefaultTableModel(header, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                // Cột cuối cùng là kiểu Boolean
                return column == (header.length - 1) ? Boolean.class : String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                // Chỉ cho phép chỉnh sửa cột cuối cùng
                return column == (header.length - 1);
            }
        };
        tblSanPham.setModel(model);
    }

    public void addOrderDetail(Order_Detail odt) {
        try {
            orderDetailDAO.insert(odt);
            System.out.println("Thêm sản phẩm thành công");
        } catch (Exception e) {
            System.out.println("Thêm sản phẩm thất bại");
        }
    }

    public void addProduct() {
        try {
            String code = txtMaCode.getText();

            // Lấy thông tin sản phẩm từ cơ sở dữ liệu
            product = orderDAO.selectProductByCode(code);

            // Kiểm tra nếu sản phẩm không tồn tại
            if (product == null || product.length < 5 || product[1] == null) {
                System.out.println("Không tìm thấy sản phẩm với mã: " + code);
                return; // Thoát nếu không tìm thấy
            }

            // Kiểm tra sản phẩm có trùng trong bảng không
            int index = checkTrungSanPham(product[1].toString());
            if (index != -1) {
                // Sản phẩm đã tồn tại, tăng số lượng
                Object soLuongObj = tblSanPham.getValueAt(index, 5); // Cột số lượng cố định tại index = 5
                int soLuong = (soLuongObj != null) ? Integer.parseInt(soLuongObj.toString()) : 0;
                tblSanPham.setValueAt(soLuong + 1, index, 5); // Cập nhật cột số lượng

                // Cập nhật cột số lượng trong database
                Object[] update = {soLuong + 1, order.getId(), product[6]};
                try {
                    orderDetailDAO.updateSoLuong(update);
                    System.out.println("Cập nhật số lượng thành công.");
                } catch (Exception e) {
                    System.out.println("Cập nhật số lượng thất bại: " + e.getMessage());
                }

            } else {
                // Sản phẩm chưa tồn tại, thêm sản phẩm mới
                Object[] row = {
                    tongSanPham, // STT
                    product[1], // Tên sản phẩm
                    product[2], // Size
                    product[3], // Màu
                    product[4], // Giá
                    1, // Số lượng mặc định
                    false // Cột xóa (Boolean)
                };

                Order_Detail orderDetail = new Order_Detail();
                orderDetail.setOrderId(order.getId());
                orderDetail.setProductVariantId((int) product[6]);
                orderDetail.setQuantity(1);
                orderDetail.setPrice((int) product[4]);

                try {
                    orderDetailDAO.insert(orderDetail);  // Thêm hàng mới trong database
                    System.out.println("Thêm sản phẩm mới thành công.");
                } catch (Exception e) {
                    System.out.println("Thêm sản phẩm thất bại: " + e);
                }

                model.addRow(row); // Thêm hàng mới vào bảng
                tongSanPham++;
            }

            // Reset giao diện
            txtMaCode.setText("");
            lblHinhAnhSanPham.setIcon(null);
            txtTongTien.setText(dinhDangMoney(tinhTongTien()));

        } catch (NumberFormatException e) {
            System.out.println("Lỗi chuyển đổi số lượng: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Lỗi không xác định: " + e.getMessage());
        }
    }

    public int checkTrungSanPham(String ten) {
        // Kiểm tra nếu bảng không có dữ liệu
        if (tblSanPham.getRowCount() == 0) {
            return -1;
        }
        // Duyệt trực tiếp các hàng trong bảng
        for (int i = 0; i < tblSanPham.getRowCount(); i++) {
            // Lấy giá trị từ cột thứ 2 (index = 1) và so sánh với 'ten'
            if (ten.equals(tblSanPham.getValueAt(i, 1).toString())) {
                return i; // Trả về chỉ số hàng nếu tìm thấy
            }
        }
        return -1; // Không tìm thấy
    }

    public void getImage() {
        try {
            String code = txtMaCode.getText();
            product = orderDAO.selectProductByCode(code);
            String image = product[0].toString();
            ImageIcon img = XImage.read(image, 170, 170);
            lblHinhAnhSanPham.setToolTipText(image);
            lblHinhAnhSanPham.setIcon(img);
        } catch (Exception e) {
            System.out.println("Lấy hình bị lỗi:" + e);
        }
    }

    public void delete() {
        // Duyệt ngược từ dòng cuối lên để tránh ảnh hưởng chỉ số khi xóa
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            Boolean isSelected = (Boolean) model.getValueAt(i, model.getColumnCount() - 1);
            List<Order_Detail> orderDetails = orderDetailDAO.selectAllByIDOrder(order.getId());
            if (isSelected != null && isSelected) {
                model.removeRow(i);
                orderDetailDAO.delete(orderDetails.get(i).getId());
                tongSanPham--;
            }
        }
    }

    public void searchCustomer() {
        String phone = txtSoDienThoai.getText();
        String nameCustomer = orderDAO.selectCustomerByPhone(phone) != null ? orderDAO.selectCustomerByPhone(phone).toString() : "Khách hàng chưa được tạo";
        txtTenKhachHang.setText(nameCustomer);
    }

    public String dinhDangMoney(int originalDate) {
        // Lấy NumberFormat theo định dạng mặc định (Locale Việt Nam)
        NumberFormat formatter = NumberFormat.getInstance(Locale.US);

        // Định dạng và in ra
        return formatter.format(originalDate);
    }

    public int tinhTongTien() {
        // Kiểm tra bảng có dữ liệu hay không
        if (tblSanPham.getRowCount() == 0) {
            return 0;
        }
        int tongTien = 0;
        // Duyệt qua từng hàng trong bảng
        for (int i = 0; i < tblSanPham.getRowCount(); i++) {
            try {
                // Lấy giá trị từ cột "Giá" (index = 4) và "Số lượng" (index = 5)
                Object giaObj = tblSanPham.getValueAt(i, 4); // Cột giá
                Object soLuongObj = tblSanPham.getValueAt(i, 5); // Cột số lượng

                // Kiểm tra giá trị không null và chuyển đổi sang số
                int gia = (giaObj != null) ? Integer.parseInt(giaObj.toString().replaceAll(",", "")) : 0;
                int soLuong = (soLuongObj != null) ? Integer.parseInt(soLuongObj.toString()) : 0;

                // Tính tổng tiền: Giá x Số lượng
                tongTien += gia * soLuong;

            } catch (NumberFormatException e) {
                System.out.println("Lỗi chuyển đổi giá trị tại hàng " + i + ": " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Lỗi không xác định tại hàng " + i + ": " + e.getMessage());
            }
        }

        return tongTien; // Trả về tổng tiền
    }

    public void createHoaDon() {
        try {
            // Kiểm tra thông tin khách hàng và phương thức thanh toán
            if (!txtTenKhachHang.getText().isEmpty() && !txtTenKhachHang.getText().equalsIgnoreCase("Khách hàng chưa được tạo") && (chkTienMat.isSelected() || chkThanhToanOnline.isSelected())) {

                int idUser = 1;   //user.getId();
                int idCustomer = customerDAO.selectByObject(txtSoDienThoai.getText()).get(0).getId();
                Date createDate = new Date();
                order.setUserId(idUser);
                order.setCustomersId(idCustomer);
                order.setCreateDate(createDate);
                order.setTotal(Integer.parseInt(txtTongTien.getText().replaceAll(",", "")));
                order.setStatus(0);

                orderDAO.update(order);
                JOptionPane.showMessageDialog(this, "Tạo hóa đơn thành công!");
                clearForm();
                loadOrCreateOrder(); //Sau khi taoh hóa đơn. Thêm một hóa đơn làm bảng nháp
            } else {
                JOptionPane.showMessageDialog(this, "Cần nhập đầy đủ thông tin!");
            }
        } catch (Exception e) {
            System.out.println("Lỗi tạo hóa đơn: " + e.getMessage());
        }
    }

    public Object[] getRowData(int rowIndex) {
        int columnCount = tblSanPham.getColumnCount(); // Số cột trong bảng
        Object[] rowData = new Object[columnCount];   // Mảng lưu dữ liệu của hàng
        // Kiểm tra chỉ số hàng có hợp lệ không
        if (rowIndex >= 0 && rowIndex < tblSanPham.getRowCount()) {
            // Duyệt qua từng cột và lấy dữ liệu
            for (int col = 0; col < columnCount; col++) {
                rowData[col] = tblSanPham.getValueAt(rowIndex, col);
            }
        } else {
            throw new IllegalArgumentException("Chỉ số hàng không hợp lệ: " + rowIndex);
        }
        return rowData; // Trả về mảng chứa dữ liệu của hàng
    }

    public void createChiTietHoaDon() {
        if (tblSanPham.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Chưa có sản phẩm");
        }
        List<Object[]> list = new ArrayList<>();
        for (int i = 0; i < tblSanPham.getRowCount(); i++) {
            Object[] ob = getRowData(i);
        }
    }

    public void tinhTienThua() {
        int tienThua = Integer.parseInt(txtTienKhachDua.getText()) - Integer.parseInt(txtTongTien.getText());
        String tienDaDinhDang = dinhDangMoney(tienThua);
        txtTienThua.setText(tienDaDinhDang);
    }

    public void clearForm() {
        txtSoDienThoai.setText("");
        txtTenKhachHang.setText("");
        txtTongTien.setText("");
        txtTienKhachDua.setText("");
        txtTienThua.setText("");
        chkTienMat.setSelected(false);
        chkThanhToanOnline.setSelected(false);
        model.setRowCount(0);
        loadOrCreateOrder();
        txtMaHoaDon.setText("Mã hóa đơn:" + order.getId());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtMaCode = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btnXoaSanPham = new javax.swing.JButton();
        btnThemVaoGioHang = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        lblHinhAnhSanPham = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtSoDienThoai = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtTenKhachHang = new javax.swing.JTextField();
        btnThemKhachHang = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lblNgayHoaDon = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblThoiGianHoaDon = new javax.swing.JLabel();
        txtTienKhachDua = new javax.swing.JTextField();
        txtTienThua = new javax.swing.JTextField();
        txtTongTien = new javax.swing.JTextField();
        txtMaHoaDon = new javax.swing.JLabel();
        chkTienMat = new javax.swing.JCheckBox();
        chkThanhToanOnline = new javax.swing.JCheckBox();
        pnlChucNang = new javax.swing.JPanel();
        btnLamMoiHoaDon = new javax.swing.JButton();
        btnInHoaDon = new javax.swing.JButton();
        btnTaoHoaDon = new javax.swing.JButton();
        btnGuiMail = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        txtMaCode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMaCodeKeyReleased(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setText("Mã code");

        btnXoaSanPham.setBackground(new java.awt.Color(255, 51, 0));
        btnXoaSanPham.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnXoaSanPham.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaSanPham.setText("Xóa");
        btnXoaSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSanPhamActionPerformed(evt);
            }
        });

        btnThemVaoGioHang.setBackground(new java.awt.Color(255, 255, 0));
        btnThemVaoGioHang.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnThemVaoGioHang.setForeground(new java.awt.Color(0, 102, 102));
        btnThemVaoGioHang.setText("Thêm");
        btnThemVaoGioHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemVaoGioHangActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102), 2));

        lblHinhAnhSanPham.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblHinhAnhSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblHinhAnhSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tblSanPham.setBackground(new java.awt.Color(204, 255, 204));
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
        tblSanPham.setGridColor(new java.awt.Color(0, 0, 0));
        tblSanPham.setRowHeight(22);
        jScrollPane1.setViewportView(tblSanPham);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMaCode, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnThemVaoGioHang)
                .addContainerGap(66, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 842, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXoaSanPham)
                .addGap(51, 51, 51))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 592, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 30, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtMaCode, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(69, 69, 69))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(btnThemVaoGioHang)
                                .addGap(72, 72, 72)))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnXoaSanPham)
                        .addGap(94, 94, 94))))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 900, 730));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Số điện thoại");

        txtSoDienThoai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSoDienThoaiKeyReleased(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel7.setText("Tiền thừa");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setText("Tên khách hàng");

        txtTenKhachHang.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        btnThemKhachHang.setBackground(new java.awt.Color(255, 255, 0));
        btnThemKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnThemKhachHang.setForeground(new java.awt.Color(0, 102, 102));
        btnThemKhachHang.setText("Thêm");
        btnThemKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKhachHangActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setText("Thanh toán");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 102, 102));
        jLabel14.setText("HÓA ĐƠN");

        lblNgayHoaDon.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        lblNgayHoaDon.setText("Ngày :");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("Tổng tiền");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setText("Tiền khách đưa");

        lblThoiGianHoaDon.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        lblThoiGianHoaDon.setText("Thời gian:");

        txtTienKhachDua.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTienKhachDua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienKhachDuaKeyReleased(evt);
            }
        });

        txtTienThua.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtTongTien.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        txtMaHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtMaHoaDon.setText("Mã hóa đơn: ");

        chkTienMat.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(chkTienMat);
        chkTienMat.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        chkTienMat.setText("Tiền mặt");

        chkThanhToanOnline.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(chkThanhToanOnline);
        chkThanhToanOnline.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        chkThanhToanOnline.setText("Thanh toán online");
        chkThanhToanOnline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkThanhToanOnlineActionPerformed(evt);
            }
        });

        pnlChucNang.setBackground(new java.awt.Color(255, 255, 255));
        pnlChucNang.setLayout(new java.awt.GridLayout(1, 0, 20, 0));

        btnLamMoiHoaDon.setBackground(new java.awt.Color(255, 255, 0));
        btnLamMoiHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnLamMoiHoaDon.setForeground(new java.awt.Color(0, 102, 102));
        btnLamMoiHoaDon.setText("Tạo mới");
        btnLamMoiHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiHoaDonActionPerformed(evt);
            }
        });
        pnlChucNang.add(btnLamMoiHoaDon);

        btnInHoaDon.setBackground(new java.awt.Color(255, 255, 0));
        btnInHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnInHoaDon.setForeground(new java.awt.Color(0, 102, 102));
        btnInHoaDon.setText("In đơn");
        pnlChucNang.add(btnInHoaDon);

        btnTaoHoaDon.setBackground(new java.awt.Color(255, 255, 0));
        btnTaoHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnTaoHoaDon.setForeground(new java.awt.Color(0, 102, 102));
        btnTaoHoaDon.setText("Thanh Toán");
        btnTaoHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHoaDonActionPerformed(evt);
            }
        });
        pnlChucNang.add(btnTaoHoaDon);

        btnGuiMail.setBackground(new java.awt.Color(255, 255, 0));
        btnGuiMail.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnGuiMail.setForeground(new java.awt.Color(0, 102, 102));
        btnGuiMail.setText("Gửi mail");
        btnGuiMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuiMailActionPerformed(evt);
            }
        });
        pnlChucNang.add(btnGuiMail);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(jLabel7)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(lblNgayHoaDon)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnThemKhachHang))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(chkTienMat)
                                    .addGap(27, 27, 27)
                                    .addComponent(chkThanhToanOnline))
                                .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(78, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblThoiGianHoaDon)
                        .addGap(168, 168, 168))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(226, 226, 226))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(pnlChucNang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNgayHoaDon)
                    .addComponent(lblThoiGianHoaDon))
                .addGap(34, 34, 34)
                .addComponent(txtMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemKhachHang))
                .addGap(36, 36, 36)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(chkTienMat)
                    .addComponent(chkThanhToanOnline))
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(35, 35, 35)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
                .addComponent(pnlChucNang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(106, 106, 106))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 0, 640, 810));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("QUẢN LÝ ORDER");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 797, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemVaoGioHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemVaoGioHangActionPerformed
        addProduct();
    }//GEN-LAST:event_btnThemVaoGioHangActionPerformed

    private void txtMaCodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMaCodeKeyReleased
        getImage();
    }//GEN-LAST:event_txtMaCodeKeyReleased

    private void btnXoaSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSanPhamActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaSanPhamActionPerformed

    private void txtSoDienThoaiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoDienThoaiKeyReleased
        searchCustomer();
    }//GEN-LAST:event_txtSoDienThoaiKeyReleased

    private void btnTaoHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHoaDonActionPerformed
        createHoaDon();
    }//GEN-LAST:event_btnTaoHoaDonActionPerformed

    private void btnLamMoiHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiHoaDonActionPerformed
        loadOrCreateOrder();
    }//GEN-LAST:event_btnLamMoiHoaDonActionPerformed

    private void txtTienKhachDuaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachDuaKeyReleased
        int tienKhachDua = 0;
        try {
            if (!txtTienKhachDua.getText().equals("")) {
                tienKhachDua = Integer.parseInt(txtTienKhachDua.getText());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Tiền khách đưa không đúng định dạng");
            txtTienKhachDua.setText("");
        }
        int tienThua = tienKhachDua - Integer.parseInt(txtTongTien.getText().replaceAll(",", ""));
        if (tienThua > 0) {
            txtTienThua.setText(dinhDangMoney(tienThua) + "");
        } else {
            txtTienThua.setText(0 + "");
        }


    }//GEN-LAST:event_txtTienKhachDuaKeyReleased

    private void btnThemKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKhachHangActionPerformed
        SessionStorage.getInstance().setAttribute("SDTKhachHang", txtSoDienThoai.getText());
        new FormKhachHang().setVisible(true);
        // setVisible(false);
    }//GEN-LAST:event_btnThemKhachHangActionPerformed

    private void chkThanhToanOnlineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkThanhToanOnlineActionPerformed
        SessionStorage.getInstance().setAttribute("TongTien", txtTongTien.getText().replace(",", ""));
        new ThanhToanOnlineBangMaQR().setVisible(true);
    }//GEN-LAST:event_chkThanhToanOnlineActionPerformed

    private void btnGuiMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuiMailActionPerformed
        new FormInBill().setVisible(true);
    }//GEN-LAST:event_btnGuiMailActionPerformed

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
            java.util.logging.Logger.getLogger(FormTaoHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormTaoHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormTaoHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormTaoHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormTaoHoaDon().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuiMail;
    private javax.swing.JButton btnInHoaDon;
    private javax.swing.JButton btnLamMoiHoaDon;
    private javax.swing.JButton btnTaoHoaDon;
    private javax.swing.JButton btnThemKhachHang;
    private javax.swing.JButton btnThemVaoGioHang;
    private javax.swing.JButton btnXoaSanPham;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkThanhToanOnline;
    private javax.swing.JCheckBox chkTienMat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblHinhAnhSanPham;
    private javax.swing.JLabel lblNgayHoaDon;
    private javax.swing.JLabel lblThoiGianHoaDon;
    private javax.swing.JPanel pnlChucNang;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtMaCode;
    private javax.swing.JLabel txtMaHoaDon;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtTenKhachHang;
    private javax.swing.JTextField txtTienKhachDua;
    private javax.swing.JTextField txtTienThua;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}
