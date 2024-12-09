/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GiaoDien;

import com.sales.Utils.DateHelper;
import com.sales.Utils.JdbcHelper;
import com.sales.Utils.XImage;
import com.sales.Utils.XValidate;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author NganTTK_PC09494
 */
public class FormTongHopThongKe extends javax.swing.JFrame {

    List<Object[]> listThongKeSanPham = new ArrayList<>();
    List<Object[]> listThongKeTonKho = new ArrayList<>();
    List<Object[]> listThongKeKhachHang = new ArrayList<>();
    List<Object[]> listThongKeNhanVien = new ArrayList<>();
    DecimalFormat df = new DecimalFormat("###,###");
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * Creates new form FormTongHopThongKe
     */
    public FormTongHopThongKe() {
        initComponents();
        init();
    }

    public void init() {
        setLocationRelativeTo(null);
        setIconImage(XImage.XImage());
        setTitle("PHẦN MỀM QUẢN LÝ GIÀY THỂ THAO");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        thongKeSanPham("1999-02-02", formatter.format(new Date()));
        chkThongKeSanPhamGiam.setSelected(true);
        chkTonKhoTang.setSelected(true);
        thongKeTonKho();
        chkThongKeKhachHangGiam.setSelected(true);
        thongKeKhachHang("1999-02-02", formatter.format(new Date()));
        chkNhanVienGiam.setSelected(true);
        thongKeNhanVien("2000-01-01", formatter.format(new Date()));
        dateChooserDenSanPham.getDateEditor().getUiComponent().setFocusable(false);
        dateChooserTuSanPham.getDateEditor().getUiComponent().setFocusable(false);
        dateChooserTuNhanVien.getDateEditor().getUiComponent().setFocusable(false);
        dateChooserDenNhanVien.getDateEditor().getUiComponent().setFocusable(false);
        dateChooserTuKhachHang.getDateEditor().getUiComponent().setFocusable(false);
        dateChooserDenKhachHang.getDateEditor().getUiComponent().setFocusable(false);
    }

    public void sapXepThongKeSanPham() {
        DefaultTableModel model = (DefaultTableModel) tblThongKeSanPham.getModel();
        model.setRowCount(0);
        if (chkThongKeSanPhamTang.isSelected()) {
            Collections.reverse(listThongKeSanPham);
            for (Object[] row : listThongKeSanPham) {
                model.addRow(new Object[]{row[0], row[1], row[2], row[3], row[4]});
            }
        } else {
            Collections.reverse(listThongKeSanPham);
            for (Object[] row : listThongKeSanPham) {
                model.addRow(new Object[]{row[0], row[1], row[2], row[3], row[4]});
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
                model.addRow(new Object[]{tenSanPham, bienTheSanPham, soLuongBan, df.format(donGia), df.format(soLuongBan * donGia)});
                listThongKeSanPham.add(new Object[]{tenSanPham, bienTheSanPham, soLuongBan, df.format(donGia), df.format(soLuongBan * donGia)});
            }
            if (chkThongKeSanPhamTang.isSelected()) {
                sapXepThongKeSanPham();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void moiSanPham(){
        thongKeSanPham("1999-01-01", formatter.format(new Date()));
        dateChooserTuSanPham.setDate(null);
        dateChooserDenSanPham.setDate(null);
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
                model.addRow(new Object[]{tenSanPham, bienTheSanPham, soLuongTon, df.format(donGia)});
                listThongKeTonKho.add(new Object[]{tenSanPham, bienTheSanPham, soLuongTon, df.format(donGia)});
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

    public void thongKeKhachHang(String to, String from) {
        String sql = "{CALL SP_ThongKeKhachHangThanThietTuNgayDenNgay(?, ?)}";
        DefaultTableModel model = (DefaultTableModel) tblThongKeKhachHang.getModel();
        model.setRowCount(0);
        listThongKeKhachHang.clear();
        try {
            ResultSet rs = JdbcHelper.query(sql, to, from);
                        if (rs.next() == false) {
                JOptionPane.showMessageDialog(this, "Không có khách hàng nào mua sản phẩm trong thời gian này");
                return;
            }
            while (rs.next()) {
                String tenKhachHang = rs.getString("TenKhachHang");
                String gioiTinh = rs.getString("GioiTinh");
                int soLuongDonHang = rs.getInt("SoLuongDonHangDaMua");
                int soLuongSanPham = rs.getInt("SoLuongSanPhamDaMua");
                double tongTien = rs.getDouble("TongTien");
                model.addRow(new Object[]{tenKhachHang, gioiTinh, soLuongDonHang, soLuongSanPham, df.format(tongTien)});
                listThongKeKhachHang.add(new Object[]{tenKhachHang, gioiTinh, soLuongDonHang, soLuongSanPham, df.format(tongTien)});
            }
            if (chkThongKeKhachHangTang.isSelected()) {
                sapXepThongKeKhachHang();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sapXepThongKeKhachHang() {
        DefaultTableModel model = (DefaultTableModel) tblThongKeKhachHang.getModel();
        model.setRowCount(0);
        Collections.reverse(listThongKeKhachHang);
        for (Object[] objects : listThongKeKhachHang) {
            model.addRow(new Object[]{objects[0], objects[1], objects[2], objects[3], objects[4]});
        }
    }
    
    public void moiKhachHang(){
        thongKeKhachHang("1999-01-01", formatter.format(new Date()));
        dateChooserTuKhachHang.setDate(null);
        dateChooserDenKhachHang.setDate(null);
    }

    public void thongKeNhanVien(String to, String from) {
        String sql = "{CALL ThongKeNhanVien(?, ?)}";
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        listThongKeNhanVien.clear();
        try {
            ResultSet rs = JdbcHelper.query(sql, to, from);
                        if (rs.next() == false) {
                JOptionPane.showMessageDialog(this, "Không có nhân viên nào bán được sản phẩm trong thời gian này trong thời gian này");
                return;
            }
            while (rs.next()) {
                int id = rs.getInt("ID");
                String tenNhanVien = rs.getString("TEN_NHAN_VIEN");
                int soLuongHoaDon = rs.getInt("SO_LUONG_HOA_DON");
                int tongTien = rs.getInt("TONG_TIEN");
                model.addRow(new Object[]{id, tenNhanVien, soLuongHoaDon, df.format(tongTien)});
                listThongKeNhanVien.add(new Object[]{id, tenNhanVien, soLuongHoaDon, df.format(tongTien)});
            }
            if (chkNhanVienTang.isSelected()) {
                sapXepNhanVien();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sapXepNhanVien() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        Collections.reverse(listThongKeNhanVien);
        for (Object[] objects : listThongKeNhanVien) {
            model.addRow(new Object[]{objects[0], objects[1], objects[2], objects[3]});
        }
    }
    
    public void moiNhanVien(){
        thongKeNhanVien("1999-01-01", formatter.format(new Date()));
        dateChooserTuNhanVien.setDate(null);
        dateChooserDenNhanVien.setDate(null);
    }

    public void xuatExcel() {
        try {
//         
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet worksheet = workbook.createSheet("Chuyên đề");
            XSSFRow row = null;
            XSSFCell cell = null;
            row = worksheet.createRow(0); //Tạo hàng thứ 1
            row.setHeight((short) 500); //Cài đặt chiều cao
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("Thống kê sản phẩm");

            row = worksheet.createRow(1);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("STT");

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("Tên Sản Phẩm");

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("Loại");

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("Số lượng bán");

            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("Đơn giá");
            
            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue("Tổng tiền");
            

            int i = 2; // Khởi tạo giá trị i
            for (int j = 0; j < listThongKeSanPham.size(); j++) {
                row = worksheet.createRow(i); // Tạo hàng thứ  nhất
                //Tạo STT
                cell = row.createCell(0, CellType.NUMERIC);
                cell.setCellValue(i - 1);
                //Tạo mã chuyên đề okkkkkkkkkkkkkkkkkkkk
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(tblThongKeSanPham.getValueAt(j, 0).toString());
                //Tạo tên chuyên đề ok 
                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(tblThongKeSanPham.getValueAt(j, 1).toString());
                cell = row.createCell(3, CellType.NUMERIC);
                cell.setCellValue(Integer.parseInt(tblThongKeSanPham.getValueAt(j, 2).toString()));
                cell = row.createCell(4, CellType.NUMERIC);
                cell.setCellValue(Integer.parseInt(tblThongKeSanPham.getValueAt(j, 3).toString().replaceAll(",", "")));
                cell = row.createCell(5, CellType.NUMERIC);
                cell.setCellValue(Integer.parseInt(tblThongKeSanPham.getValueAt(j, 4).toString().replaceAll(",", "")) );
                i++;
            }
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
            fileChooser.setSelectedFile(new File("")); // Tên mặc định cho file

            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                // Đảm bảo tên file có phần mở rộng .xlsx
                if (!fileToSave.getAbsolutePath().endsWith(".xlsx")) {
                    fileToSave = new File(fileToSave + ".xlsx");
                }
                // Ghi dữ liệu vào file đã chọn
                try {
                    FileOutputStream fos = new FileOutputStream(fileToSave);
                    workbook.write(fos);
                    fos.close();
                    // Hiển thị thông báo và hỏi người dùng có muốn mở file hay không
                    if (JOptionPane.showConfirmDialog(this, "Bạn muốn mở Excel không?", "Xuất Excel thành công", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        if (Desktop.isDesktopSupported()) {
                            Desktop desktop = Desktop.getDesktop();
                            if (fileToSave.exists()) {
                                desktop.open(fileToSave);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Có lỗi khi lưu file: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn" + e.getMessage());
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
        bgrThongKeKhachHang = new javax.swing.ButtonGroup();
        bgrNhanVien = new javax.swing.ButtonGroup();
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
        jButton2 = new javax.swing.JButton();
        btnMoiSanPham = new javax.swing.JButton();
        pnlThongKeKhachHang = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblThongKeKhachHang = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        dateChooserTuKhachHang = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        dateChooserDenKhachHang = new com.toedter.calendar.JDateChooser();
        btnTimKiemKhachHang = new javax.swing.JButton();
        chkThongKeKhachHangTang = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        chkThongKeKhachHangGiam = new javax.swing.JCheckBox();
        btnMoiKhachHang = new javax.swing.JButton();
        pnlThongKeDoanhThu = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        btnTimKiemNhanVien = new javax.swing.JButton();
        dateChooserDenNhanVien = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        dateChooserTuNhanVien = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        chkNhanVienTang = new javax.swing.JCheckBox();
        chkNhanVienGiam = new javax.swing.JCheckBox();
        btnMoiNhanVien = new javax.swing.JButton();
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
        dateChooserTuSanPham.setFocusCycleRoot(true);

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
        chkThongKeSanPhamGiam.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkThongKeSanPhamGiamItemStateChanged(evt);
            }
        });
        chkThongKeSanPhamGiam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkThongKeSanPhamGiamActionPerformed(evt);
            }
        });

        jButton2.setText("Xuất Excel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnMoiSanPham.setText("Mới");
        btnMoiSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiSanPhamActionPerformed(evt);
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
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateChooserTuSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(dateChooserDenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnMoiSanPham)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 158, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(chkThongKeSanPhamTang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkThongKeSanPhamGiam)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)
                        .addGap(11, 11, 11))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        pnlThongKeSanPhamLayout.setVerticalGroup(
            pnlThongKeSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongKeSanPhamLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(pnlThongKeSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongKeSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(dateChooserDenSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dateChooserTuSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongKeSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(chkThongKeSanPhamTang)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(chkThongKeSanPhamGiam)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlThongKeSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnMoiSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        jTabbedPane1.addTab("Thống kê sản phẩm", pnlThongKeSanPham);

        pnlThongKeKhachHang.setBackground(new java.awt.Color(255, 255, 255));

        tblThongKeKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Tên khách hàng", "Giới tính", "Số lượng đơn hàng đã mua", "Số lượng sản phẩm đã mua", "Tổng tiền"
            }
        ));
        jScrollPane3.setViewportView(tblThongKeKhachHang);

        jLabel5.setText("Từ");

        dateChooserTuKhachHang.setDateFormatString("dd-MM-yyyy");

        jLabel6.setText("Đến");

        dateChooserDenKhachHang.setDateFormatString("dd-MM-yyyy");

        btnTimKiemKhachHang.setText("Tìm kiếm");
        btnTimKiemKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemKhachHangActionPerformed(evt);
            }
        });

        bgrThongKeKhachHang.add(chkThongKeKhachHangTang);
        chkThongKeKhachHangTang.setText("Tăng");
        chkThongKeKhachHangTang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkThongKeKhachHangTangItemStateChanged(evt);
            }
        });

        jLabel7.setText("Sắp xếp tổng tiền");

        bgrThongKeKhachHang.add(chkThongKeKhachHangGiam);
        chkThongKeKhachHangGiam.setText("Giảm");

        btnMoiKhachHang.setText("Mới");
        btnMoiKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiKhachHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlThongKeKhachHangLayout = new javax.swing.GroupLayout(pnlThongKeKhachHang);
        pnlThongKeKhachHang.setLayout(pnlThongKeKhachHangLayout);
        pnlThongKeKhachHangLayout.setHorizontalGroup(
            pnlThongKeKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongKeKhachHangLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(dateChooserTuKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateChooserDenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTimKiemKhachHang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMoiKhachHang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 229, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkThongKeKhachHangTang)
                .addGap(18, 18, 18)
                .addComponent(chkThongKeKhachHangGiam)
                .addGap(84, 84, 84))
            .addGroup(pnlThongKeKhachHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3))
        );
        pnlThongKeKhachHangLayout.setVerticalGroup(
            pnlThongKeKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongKeKhachHangLayout.createSequentialGroup()
                .addContainerGap(64, Short.MAX_VALUE)
                .addGroup(pnlThongKeKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongKeKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(dateChooserDenKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dateChooserTuKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongKeKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(chkThongKeKhachHangTang)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(chkThongKeKhachHangGiam)
                        .addComponent(btnTimKiemKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnMoiKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        jTabbedPane1.addTab("Thống kê khách hàng", pnlThongKeKhachHang);

        pnlThongKeDoanhThu.setBackground(new java.awt.Color(255, 255, 255));

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Tên nhân viên", "Số lượng hóa đơn đã bán", "Tổng tiền"
            }
        ));
        jScrollPane4.setViewportView(tblNhanVien);

        jLabel8.setText("Sắp xếp theo tổng tiền");

        btnTimKiemNhanVien.setText("Tìm kiếm");
        btnTimKiemNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemNhanVienActionPerformed(evt);
            }
        });

        dateChooserDenNhanVien.setDateFormatString("dd-MM-yyyy");

        jLabel9.setText("Đến");

        dateChooserTuNhanVien.setDateFormatString("dd-MM-yyyy");

        jLabel10.setText("Từ");

        bgrNhanVien.add(chkNhanVienTang);
        chkNhanVienTang.setText("Tăng");
        chkNhanVienTang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkNhanVienTangItemStateChanged(evt);
            }
        });
        chkNhanVienTang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkNhanVienTangActionPerformed(evt);
            }
        });

        bgrNhanVien.add(chkNhanVienGiam);
        chkNhanVienGiam.setText("Giảm");

        btnMoiNhanVien.setText("Mới");
        btnMoiNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiNhanVienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlThongKeDoanhThuLayout = new javax.swing.GroupLayout(pnlThongKeDoanhThu);
        pnlThongKeDoanhThu.setLayout(pnlThongKeDoanhThuLayout);
        pnlThongKeDoanhThuLayout.setHorizontalGroup(
            pnlThongKeDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongKeDoanhThuLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(pnlThongKeDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongKeDoanhThuLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1047, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(15, Short.MAX_VALUE))
                    .addGroup(pnlThongKeDoanhThuLayout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(dateChooserTuNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dateChooserDenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTimKiemNhanVien)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMoiNhanVien)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(chkNhanVienTang, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkNhanVienGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))))
        );
        pnlThongKeDoanhThuLayout.setVerticalGroup(
            pnlThongKeDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongKeDoanhThuLayout.createSequentialGroup()
                .addContainerGap(64, Short.MAX_VALUE)
                .addGroup(pnlThongKeDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongKeDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(dateChooserDenNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dateChooserTuNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongKeDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnTimKiemNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(chkNhanVienTang)
                        .addComponent(chkNhanVienGiam)
                        .addComponent(btnMoiNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        jTabbedPane1.addTab("Thống kê nhân viên", pnlThongKeDoanhThu);

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
        if (XValidate.checkBoTrongNgayTimKiem(dateChooserTuSanPham, "Vui lòng nhập ngày bắt đầu tìm kiếm!")
                && XValidate.checkBoTrongNgayTimKiem(dateChooserDenSanPham, "Vui lòng nhập ngày kết thúc!")
                && XValidate.checkThoiGianTimKiem(dateChooserTuSanPham, dateChooserDenSanPham)) {
            thongKeSanPham(formatter.format(dateTo), formatter.format(dateFrom));
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void chkThongKeSanPhamTangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkThongKeSanPhamTangItemStateChanged
        sapXepThongKeSanPham();
    }//GEN-LAST:event_chkThongKeSanPhamTangItemStateChanged

    private void chkTonKhoTangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkTonKhoTangItemStateChanged
        if (txtTimKiemThongKeTonKho.getText().trim().equals("")) {
            thongKeTonKho();
        } else {
            thongKeTonKho();
            timKiemTonKho();

        }
    }//GEN-LAST:event_chkTonKhoTangItemStateChanged

    private void chkTonKhoGiamItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkTonKhoGiamItemStateChanged
        if (txtTimKiemThongKeTonKho.getText().trim().equals("")) {
            thongKeTonKho();
        } else {
            thongKeTonKho();
            timKiemTonKho();

        }

    }//GEN-LAST:event_chkTonKhoGiamItemStateChanged

    private void btnTimKiemTonKhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemTonKhoActionPerformed
        timKiemTonKho();
    }//GEN-LAST:event_btnTimKiemTonKhoActionPerformed

    private void txtTimKiemThongKeTonKhoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemThongKeTonKhoKeyReleased
        if (txtTimKiemThongKeTonKho.getText().trim().equals("")) {
            thongKeTonKho();
        }
        else{
            timKiemTonKho();
        }
    }//GEN-LAST:event_txtTimKiemThongKeTonKhoKeyReleased

    private void chkThongKeSanPhamGiamItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkThongKeSanPhamGiamItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_chkThongKeSanPhamGiamItemStateChanged

    private void btnTimKiemKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemKhachHangActionPerformed
        Date to = dateChooserTuKhachHang.getDate();
        Date from = dateChooserDenKhachHang.getDate();
        if (XValidate.checkBoTrongNgayTimKiem(dateChooserTuKhachHang, "Vui lòng nhập ngày bắt đầu tìm kiếm!")
                && XValidate.checkBoTrongNgayTimKiem(dateChooserDenKhachHang, "Vui lòng nhập ngày kết thúc!")
                && XValidate.checkThoiGianTimKiem(dateChooserTuKhachHang, dateChooserDenKhachHang)) {
            thongKeKhachHang(formatter.format(to), formatter.format(from));
        }
    }//GEN-LAST:event_btnTimKiemKhachHangActionPerformed

    private void chkThongKeKhachHangTangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkThongKeKhachHangTangItemStateChanged
        sapXepThongKeKhachHang();
    }//GEN-LAST:event_chkThongKeKhachHangTangItemStateChanged

    private void btnTimKiemNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemNhanVienActionPerformed
        Date to = dateChooserTuNhanVien.getDate();
        Date from = dateChooserDenNhanVien.getDate();
        if (XValidate.checkBoTrongNgayTimKiem(dateChooserTuNhanVien, "Vui lòng nhập ngày bắt đầu tìm kiếm!")
                && XValidate.checkBoTrongNgayTimKiem(dateChooserDenNhanVien, "Vui lòng nhập ngày kết thúc!")
                && XValidate.checkThoiGianTimKiem(dateChooserTuNhanVien, dateChooserDenNhanVien)) {
            thongKeNhanVien(formatter.format(to), formatter.format(from));
        }
    }//GEN-LAST:event_btnTimKiemNhanVienActionPerformed

    private void chkNhanVienTangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkNhanVienTangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkNhanVienTangActionPerformed

    private void chkNhanVienTangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkNhanVienTangItemStateChanged
        sapXepNhanVien();
    }//GEN-LAST:event_chkNhanVienTangItemStateChanged

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        xuatExcel();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnMoiSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiSanPhamActionPerformed
           moiSanPham();
    }//GEN-LAST:event_btnMoiSanPhamActionPerformed

    private void btnMoiKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiKhachHangActionPerformed
        moiKhachHang();
    }//GEN-LAST:event_btnMoiKhachHangActionPerformed

    private void btnMoiNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiNhanVienActionPerformed
        moiNhanVien();
    }//GEN-LAST:event_btnMoiNhanVienActionPerformed

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
            java.util.logging.Logger.getLogger(FormTongHopThongKe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormTongHopThongKe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormTongHopThongKe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormTongHopThongKe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormTongHopThongKe().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrNhanVien;
    private javax.swing.ButtonGroup bgrThongKeKhachHang;
    private javax.swing.ButtonGroup bgrThongKeSanPham;
    private javax.swing.ButtonGroup bgrThongKeTonKho;
    private javax.swing.JButton btnMoiKhachHang;
    private javax.swing.JButton btnMoiNhanVien;
    private javax.swing.JButton btnMoiSanPham;
    private javax.swing.JButton btnTimKiemKhachHang;
    private javax.swing.JButton btnTimKiemNhanVien;
    private javax.swing.JButton btnTimKiemTonKho;
    private javax.swing.JCheckBox chkNhanVienGiam;
    private javax.swing.JCheckBox chkNhanVienTang;
    private javax.swing.JCheckBox chkThongKeKhachHangGiam;
    private javax.swing.JCheckBox chkThongKeKhachHangTang;
    private javax.swing.JCheckBox chkThongKeSanPhamGiam;
    private javax.swing.JCheckBox chkThongKeSanPhamTang;
    private javax.swing.JCheckBox chkTonKhoGiam;
    private javax.swing.JCheckBox chkTonKhoTang;
    private com.toedter.calendar.JDateChooser dateChooserDenKhachHang;
    private com.toedter.calendar.JDateChooser dateChooserDenNhanVien;
    private com.toedter.calendar.JDateChooser dateChooserDenSanPham;
    private com.toedter.calendar.JDateChooser dateChooserTuKhachHang;
    private com.toedter.calendar.JDateChooser dateChooserTuNhanVien;
    private com.toedter.calendar.JDateChooser dateChooserTuSanPham;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel pnlThongKeDoanhThu;
    private javax.swing.JPanel pnlThongKeKhachHang;
    private javax.swing.JPanel pnlThongKeSanPham;
    private javax.swing.JPanel pnlThongKeTonKho;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTable tblThongKeKhachHang;
    private javax.swing.JTable tblThongKeSanPham;
    private javax.swing.JTable tblThongKeTonKho;
    private javax.swing.JTextField txtTimKiemThongKeTonKho;
    // End of variables declaration//GEN-END:variables
}
