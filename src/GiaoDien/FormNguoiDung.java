/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GiaoDien;

import com.sales.DAO.UserDAO;
import com.sales.Entity.User;
import com.sales.Utils.MailHelper;
import com.sales.Utils.XImage;
import com.sales.Utils.XValidate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author NganTTK_PC09494
 */
public class FormNguoiDung extends javax.swing.JFrame {

    List<User> list = new ArrayList<>();
    User user = new User();
    UserDAO userDAO = new UserDAO();

    /**
     * Creates new form FormNguoiDung
     */
    public FormNguoiDung() {
        initComponents();
        init();
    }

    public void init() {
        loadDataToTable();
        fillComBoBox();
        setLocationRelativeTo(null);
        setIconImage(XImage.XImage());
        setTitle("PHẦN MỀM QUẢN LÝ GIÀY THỂ THAO");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void search() {
        DefaultTableModel model = (DefaultTableModel) tblNguoiDung.getModel();
        model.setRowCount(0);
        List<User> listSearch = new ArrayList<>();
        String search[] = txtTimKiem.getText().trim().split(" ");
        if (search.length == 1 && !search[0].equals("")) {
            for (User user : list) {
                if (user.getFullName().toLowerCase().contains(search[0].toLowerCase()) || String.valueOf(user.getId()).toLowerCase().equals(search[0].toLowerCase())) {
                    listSearch.add(user);
                }
            }
        }
        if (search.length > 1) {
            for (User user : list) {
                boolean check = true;
                for (int i = 0; i < search.length; i++) {
                    if (!user.getFullName().toLowerCase().contains(search[i].toLowerCase())) {
                        check = false;
                        break;
                    }
                }
                if (check) {
                    listSearch.add(user);
                }
            }
        }
        if (listSearch.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy!");
        }
        for (User user : listSearch) {
            model.addRow(new Object[]{user.getId(), user.getFullName(), user.getRole() ? "Quản lý" : "Nhân viên", user.getGender() ? "Nam" : "Nữ", user.getPhone(), user.getEmail(), user.getAddress(), user.getActive() ? "Đang hoạt động" : "Ngừng hoạt động"});
        }
    }

    public void loadDataToTable() {

        try {
            list = userDAO.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String header[] = {"ID", "Họ tên", "Quyền", "Giới tính", "Điện thoại", "Email", "Địa chỉ", "Trạng thái"};
        DefaultTableModel model = new DefaultTableModel(header, 0);
        for (User user : list) {
            model.addRow(new Object[]{user.getId(), user.getFullName(), user.getRole() ? "Quản lý" : "Nhân viên", user.getGender() ? "Nam" : "Nữ", user.getPhone(), user.getEmail(), user.getAddress(), user.getActive() ? "Đang hoạt động" : "Ngừng hoạt động"});
        }
        tblNguoiDung.setModel(model);
    }

    public void insert(User user) {
        try {
            userDAO.insert(user);
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
            MailHelper.sendEmail(txtEmail.getText().trim(), user.getPassword(), "Mật khẩu đăng nhập", "Mật khẩu đăng nhập của bạn là: ");
            refresh();
            loadDataToTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            e.printStackTrace();
        }

    }

    public void update(User user) {
        try {
            userDAO.update(user);
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            loadDataToTable();
            refresh();
            if (!txtTimKiem.getText().trim().equals("")) {
                search();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            e.printStackTrace();
        }
    }

    public void delete(User user) {
        try {
            userDAO.delete(user.getId());
            JOptionPane.showMessageDialog(this, "Xóa thành công!");
            refresh();
            loadDataToTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Xóa thất bại!");
        }

    }

    public void fillComBoBox() {
        DefaultComboBoxModel boxModel = new DefaultComboBoxModel();
        boxModel.addElement("Nhân viên");
        boxModel.addElement("Quản lý");
        cboQuyen.setModel(boxModel);
    }

    public void setForm() {
        txtTenNguoiDung.setText(user.getFullName());
        txtSoDienThoai.setText(user.getPhone());
        txtDiaChi.setText(user.getAddress());
        txtEmail.setText(user.getEmail());
        if (user.getGender()) {
            chkNam.setSelected(true);
        } else if(user.getGender() == false){
            chkNu.setSelected(true);
        }
        if (user.getRole()) {
            cboQuyen.setSelectedItem("Quản lý");
        } else if(user.getRole() == false) {
            cboQuyen.setSelectedItem("Nhân viên");
        }
        if (user.getActive()) {
            chkDangHoatDong.setSelected(true);
        } else if(user.getActive() == false){
            chkNgungHoatDong.setSelected(true);
        }
    }

    public void getForm() {
        user.setFullName(txtTenNguoiDung.getText().trim());
        user.setEmail(txtEmail.getText().trim());
        user.setAddress(txtDiaChi.getText().trim());
        user.setPhone(txtSoDienThoai.getText().trim());
        user.setPassword(MailHelper.generateCode(9));
        user.setGender((chkNam.isSelected()) ? true : false);
        user.setRole((cboQuyen.getSelectedItem().equals("Quản lý")) ? true : false);
        user.setActive((chkDangHoatDong.isSelected()) ? true : false);
    }

    public void refresh() {
        user = new User();
        user.setRole(false);
        user.setGender(false);
        user.setActive(false);
        setForm();
        bgrGioiTinh.clearSelection();
        bgrQuyen.clearSelection();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrGioiTinh = new javax.swing.ButtonGroup();
        bgrQuyen = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNguoiDung = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtTenNguoiDung = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtSoDienThoai = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        chkNam = new javax.swing.JCheckBox();
        chkNu = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        cboQuyen = new javax.swing.JComboBox<>();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        chkDangHoatDong = new javax.swing.JCheckBox();
        chkNgungHoatDong = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tblNguoiDung.setBackground(new java.awt.Color(204, 255, 255));
        tblNguoiDung.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblNguoiDung.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Tên", "Email", "SĐT", "Địa chỉ", "Giới tính", "Quyền"
            }
        ));
        tblNguoiDung.setGridColor(new java.awt.Color(0, 0, 0));
        tblNguoiDung.setRowHeight(22);
        tblNguoiDung.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNguoiDungMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNguoiDung);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setText("QUẢN LÝ NHÂN VIÊN");

        txtTenNguoiDung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenNguoiDungActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Tên người dùng");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Email");

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("Số điện thoại");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Địa chỉ ");

        txtDiaChi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("Giới Tính ");

        chkNam.setBackground(new java.awt.Color(255, 255, 255));
        bgrGioiTinh.add(chkNam);
        chkNam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        chkNam.setText("Nam");

        chkNu.setBackground(new java.awt.Color(255, 255, 255));
        bgrGioiTinh.add(chkNu);
        chkNu.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        chkNu.setText("Nữ");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Quyền");

        cboQuyen.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboQuyen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nhân viên", "Quản lí" }));

        btnThem.setBackground(new java.awt.Color(255, 204, 0));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnThem.setForeground(new java.awt.Color(0, 102, 102));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/add.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(255, 204, 0));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSua.setForeground(new java.awt.Color(0, 102, 102));
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/edit.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(255, 204, 0));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(0, 102, 102));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/delete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnMoi.setBackground(new java.awt.Color(255, 204, 0));
        btnMoi.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnMoi.setForeground(new java.awt.Color(0, 102, 102));
        btnMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/clear.png"))); // NOI18N
        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("THÔNG TIN");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("THÔNG TIN NHÂN VIÊN");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addContainerGap())
        );

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        btnTimKiem.setBackground(new java.awt.Color(0, 102, 102));
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnTimKiem.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiem.setText("Tìm kiếm ");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        chkDangHoatDong.setBackground(new java.awt.Color(255, 255, 255));
        bgrQuyen.add(chkDangHoatDong);
        chkDangHoatDong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        chkDangHoatDong.setText("Đang hoạt động");

        chkNgungHoatDong.setBackground(new java.awt.Color(255, 255, 255));
        bgrQuyen.add(chkNgungHoatDong);
        chkNgungHoatDong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        chkNgungHoatDong.setText("Ngừng hoạt động");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setText("Hoạt động");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnTimKiem))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1261, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(100, 100, 100))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(307, 307, 307)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenNguoiDung, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(chkNam)
                                .addGap(18, 18, 18)
                                .addComponent(chkNu))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel2)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(46, 46, 46)
                        .addComponent(chkDangHoatDong)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chkNgungHoatDong))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(101, 101, 101)
                                .addComponent(cboQuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel8))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDiaChi)
                    .addComponent(txtEmail)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSua)
                        .addGap(30, 30, 30)
                        .addComponent(btnMoi)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoa)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenNguoiDung, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(chkNam)
                                .addComponent(chkNu)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboQuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(chkDangHoatDong)
                                .addComponent(chkNgungHoatDong)))
                        .addGap(72, 72, 72))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(40, 181, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnThem)
                                .addComponent(btnSua)
                                .addComponent(btnMoi))
                            .addComponent(btnXoa, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tblNguoiDungMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNguoiDungMouseClicked
        int row = tblNguoiDung.getSelectedRow();
        String id = tblNguoiDung.getValueAt(row, 0).toString();
        try {
            user = userDAO.selectByID(Integer.parseInt(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        setForm();
    }//GEN-LAST:event_tblNguoiDungMouseClicked

    private void txtTenNguoiDungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenNguoiDungActionPerformed

    }//GEN-LAST:event_txtTenNguoiDungActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (XValidate.checkHoTen(txtTenNguoiDung)
                && XValidate.checkDienThoai(txtSoDienThoai)
                && XValidate.checkEmailNhanVien(txtEmail, "")
                && XValidate.checkButtonGroup(bgrQuyen, "Vui lòng chọn vai trò")
                && XValidate.checkButtonGroup(bgrGioiTinh, "Vui lòng chọn giới tính")
                && XValidate.checkTonTaiSdtUser(txtSoDienThoai, "")) {
            getForm();
            insert(user);
        }

    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if(user.getId() != 0){
            if (XValidate.checkHoTen(txtTenNguoiDung)
                    && XValidate.checkDienThoai(txtSoDienThoai)
                    && XValidate.checkEmailNhanVien(txtEmail, user.getId() + "")
                    && XValidate.checkButtonGroup(bgrQuyen, "Vui lòng chọn vai trò")
                    && XValidate.checkButtonGroup(bgrGioiTinh, "Vui lòng chọn giới tính")
                    && XValidate.checkBoTrong(txtEmail, "Chưa nhập địa chỉ")
                    && XValidate.checkTonTaiSdtUser(txtSoDienThoai, user.getId() + "")) {
                String pass = user.getPassword();
                getForm();
                user.setPassword(pass);
                update(user);
            }
        }
        
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        if(user.getId() != 0){
           int check = JOptionPane.showConfirmDialog(this, "Xóa", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (check == 0) {
            delete(user);
           }
       }
        
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        refresh();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void txtTimKiemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyPressed
        if (txtTimKiem.getText().isEmpty()) {
            loadDataToTable();
        }
    }//GEN-LAST:event_txtTimKiemKeyPressed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        if (txtTimKiem.getText().isEmpty()) {
            loadDataToTable();
        }
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        search();
    }//GEN-LAST:event_btnTimKiemActionPerformed

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
            java.util.logging.Logger.getLogger(FormNguoiDung.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormNguoiDung.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormNguoiDung.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormNguoiDung.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormNguoiDung().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrGioiTinh;
    private javax.swing.ButtonGroup bgrQuyen;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboQuyen;
    private javax.swing.JCheckBox chkDangHoatDong;
    private javax.swing.JCheckBox chkNam;
    private javax.swing.JCheckBox chkNgungHoatDong;
    private javax.swing.JCheckBox chkNu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblNguoiDung;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtTenNguoiDung;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
