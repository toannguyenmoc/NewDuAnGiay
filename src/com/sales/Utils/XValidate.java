/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sales.Utils;

import com.sales.DAO.BrandDAO;
import com.sales.DAO.CategoriesDAO;
import com.sales.DAO.ColorDAO;
import com.sales.DAO.CustomerDAO;
import com.sales.DAO.ProductDAO;
import com.sales.DAO.Product_VariantDAO;
import com.sales.DAO.SizeDAO;
import com.sales.DAO.UserDAO;
import com.sales.Entity.Brand;
import com.sales.Entity.Categories;
import com.sales.Entity.Color;
import com.sales.Entity.Customer;
import com.sales.Entity.Product;
import com.sales.Entity.Product_Variant;
import com.sales.Entity.Size;
import com.sales.Entity.User;
import com.toedter.calendar.JDateChooser;
import java.util.Date;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Admin
 */
public class XValidate {

    public static boolean checkHoTen(JTextField text) {
        String hoTen = text.getText();
        String rgx = "^[\\p{L}]+(?: [\\p{L}]+)*$";
        if (hoTen.isEmpty()) {
            JOptionPane.showMessageDialog(text.getRootPane(), "Họ Tên không được để trống");
            return false;
        }
        if (!hoTen.matches(rgx)) {
            JOptionPane.showMessageDialog(text.getRootPane(), "Họ Tên không dúng định dạng");
            return false;
        }
        return true;
    }

    public static boolean checkDienThoai(JTextField text) {
        String dienThoai = text.getText();
        String rgx = "0[0-9]{8,10}";
        if (dienThoai.isEmpty()) {
            JOptionPane.showMessageDialog(text.getRootPane(), "Vui lòng nhập số điện thoại");
            return false;
        }
        if (!dienThoai.matches(rgx)) {
            JOptionPane.showMessageDialog(text.getRootPane(), "Số điện thoại không đúng");
            return false;
        }
        return true;
    }

    public static boolean checkEmailNhanVien(JTextField text, String maNV) {
        String email = text.getText();
        String rgx = "^[a-zA-Z0-9]{5,50}@(gmail.com|fpt.edu.vn)$";
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(text.getRootPane(), "Email không được để trống");
            return false;
        }
        if (!email.matches(rgx)) {
            JOptionPane.showMessageDialog(text.getRootPane(), "Email không đúng địng dạng @gmail.com hoặc @fpt.edu.vn");
            return false;
        }
        UserDAO userDAO = new UserDAO();
        List<User> list = userDAO.selectAll();
        for (User user : list) {
            if (!String.valueOf(user.getId()).equals(maNV) && user.getEmail().equalsIgnoreCase(email)) {
                JOptionPane.showMessageDialog(text.getRootPane(), "Email đã tồn tại");
                return false;
            }
        }
        return true;
    }

    public static boolean checkButtonGroup(ButtonGroup radio, String message) {
        if (radio.getSelection() == null) {
            JOptionPane.showMessageDialog(null, message);
            return false;
        }
        return true;
    }

    public static boolean checkMatKhau(JTextField matKhau, JTextField xacNhan) {
        String pass = matKhau.getText();
        String rev = xacNhan.getText();
        if (pass.isEmpty()) {
            JOptionPane.showMessageDialog(matKhau.getRootPane(), "Vui lòng nhập mật khẩu");
            return false;
        }
        if (rev.isEmpty()) {
            JOptionPane.showMessageDialog(xacNhan.getRootPane(), "Vui lòng xác nhận mật khẩu");
            return false;
        }
        if (!pass.equals(rev)) {
            JOptionPane.showMessageDialog(xacNhan.getRootPane(), "Xác nhận mật khẩu không chính xác");
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkBoTrong(JTextField text, String message) {
        if (text.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(text.getRootPane(), message);
            return false;
        }
        return true;
    }

    public static boolean checkTonTaiSdtUser(JTextField text, String id) {
        String sdt = text.getText().trim();
        UserDAO userDAO = new UserDAO();
        List<User> list = userDAO.selectAll();
        for (User user : list) {
            if (!String.valueOf(user.getId()).equals(id) && user.getPhone().equalsIgnoreCase(sdt)) {
                JOptionPane.showMessageDialog(text.getRootPane(), "Số điện thoại đã tồn tại!");
                return false;
            }
        }
        return true;
    }
      ColorDAO colorDao = new ColorDAO();
    public boolean checkTenMau(JTextField text)
    {
        String rgx = "[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ ]+$";
        String tenMau = text.getText().trim();
        Color color = colorDao.selectByName(tenMau);
        if(tenMau.length()== 0)
        {
            JOptionPane.showMessageDialog(text.getRootPane(), "Chưa nhập tên màu!");
            return false;
        }
        if(tenMau.matches(rgx)== false)
        {
            JOptionPane.showMessageDialog(text.getRootPane(), "Họ tên chỉ chứa alphabet và ký tự trắng");
            return false;
        }
         if(color != null)
        {
            JOptionPane.showMessageDialog(text.getRootPane(),"Tên màu đã tồn tại!");
            return false;
        }
        return true;
    }
    public boolean checkUpdateTenMau(JTextField text)
    {
        String rgx = "[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ ]+$";
        String tenMau = text.getText().trim();
        Color color = colorDao.selectByName(tenMau);
        if(tenMau.length()== 0)
        {
            JOptionPane.showMessageDialog(text.getRootPane(), "Chưa nhập tên màu!");
            return false;
        }
        if(tenMau.matches(rgx)== false)
        {
            JOptionPane.showMessageDialog(text.getRootPane(), "Họ tên chỉ chứa alphabet và ký tự trắng");
            return false;
        }
       
        return true;
    }
    SizeDAO sizeDao = new SizeDAO();
        public boolean checkKichThuoc(JTextField text)
    {
        String rgx = "^(3[6-9]|4[0-9]|50)$";
        String tenSize = text.getText().trim();
        Size size =sizeDao.selectByNAME(tenSize);
        if(tenSize.length()== 0)
        {
            JOptionPane.showMessageDialog(text.getRootPane(), "Chưa nhập kích thước!");
            return false;
        }
        if(tenSize.matches(rgx)== false)
        {
            JOptionPane.showMessageDialog(text.getRootPane(), "Size chỉ từ 36 - 50");
            return false;
        }
         if(size != null)
        {
            JOptionPane.showMessageDialog(text.getRootPane(),"Kích thước đã tồn tại!");
            return false;
        }
         return true;
    }
            public boolean checkUpdateKichThuoc(JTextField text)
    {
        String rgx = "^(3[6-9]|4[0-9]|50)$";
        String tenSize = text.getText().trim();
        Size size =sizeDao.selectByNAME(tenSize);
        if(tenSize.length()== 0)
        {
            JOptionPane.showMessageDialog(text.getRootPane(), "Chưa nhập kích thước!");
            return false;
        }
        if(tenSize.matches(rgx)== false)
        {
            JOptionPane.showMessageDialog(text.getRootPane(), "Size chỉ từ 36 - 50");
            return false;
        }
       
         return true;
    }
        
    public boolean checkTrangThaiColor(JCheckBox checkBox1, JCheckBox checkBox2) {
        
        if (!checkBox1.isSelected() && !checkBox2.isSelected()) {
            JOptionPane.showMessageDialog(null, "Chưa chọn hoạt động");
            return false; 
        }
        return true;  
    }
    public boolean checkTrangThaiSize(JCheckBox checkBox1, JCheckBox checkBox2) {
        
        if (!checkBox1.isSelected() && !checkBox2.isSelected()) {
            JOptionPane.showMessageDialog(null, "Chưa chọn hoạt động");
            return false; 
        }
        return true;  
    }
    BrandDAO brandDao = new BrandDAO();
     public boolean checkTenThuongHieu(JTextField text)
    {
        String rgx = "[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ ]+$";
        String tenThuongHieu = text.getText().trim();
        Brand brand = brandDao.selectByName(tenThuongHieu);
        if(tenThuongHieu.length()== 0)
        {
            JOptionPane.showMessageDialog(text.getRootPane(), "Chưa nhập tên thương Hiệu!");
            return false;
        }
        if(tenThuongHieu.matches(rgx)== false)
        {
            JOptionPane.showMessageDialog(text.getRootPane(), "Họ tên chỉ chứa alphabet và ký tự trắng");
            return false;
        }
         if(brand != null)
        {
            JOptionPane.showMessageDialog(text.getRootPane(),"Tên thương hiệu đã tồn tại!");
            return false;
        }
        return true;
    }public boolean checkUpdateTenThuongHieu(JTextField text)
    {
        String rgx = "[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ ]+$";
        String tenThuongHieu = text.getText().trim();
        Brand brand = brandDao.selectByName(tenThuongHieu);
        if(tenThuongHieu.length()== 0)
        {
            JOptionPane.showMessageDialog(text.getRootPane(), "Chưa nhập tên thương Hiệu!");
            return false;
        }
        if(tenThuongHieu.matches(rgx)== false)
        {
            JOptionPane.showMessageDialog(text.getRootPane(), "Họ tên chỉ chứa alphabet và ký tự trắng");
            return false;
        }
        
        return true;
    }
     public boolean checkTrangThaiThuongHieu(JCheckBox checkBox1, JCheckBox checkBox2) {
        
        if (!checkBox1.isSelected() && !checkBox2.isSelected()) {
            JOptionPane.showMessageDialog(null, "Chưa chọn hoạt động");
            return false; 
        }
        return true;  
    }
      ProductDAO productDao =new ProductDAO();
     public boolean checkTenSanPham(JTextField text)
    {
        String rgx = "[a-zA-Z0-9ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ ]+$";
        String tenSanPham = text.getText().trim();
        Product product = productDao.selectByNAME(tenSanPham);
        if(tenSanPham.length()== 0)
        {
            JOptionPane.showMessageDialog(text.getRootPane(), "Chưa nhập tên Sản Phẩm!");
            
            return false;
        }
        if(tenSanPham.matches(rgx)== false)
        {
            JOptionPane.showMessageDialog(text.getRootPane(), "Họ tên chỉ chứa alphabet và ký tự trắng");
            return false;
        }
         if(product != null)
        {
            JOptionPane.showMessageDialog(text.getRootPane(),"Tên Sản Phẩm đã tồn tại!");
            return false;
        }
        return true;
    }
      public boolean checkUpdateTenSanPham(JTextField text)
    {
        String rgx = "[a-zA-Z0-9ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ ]+$";
        String tenSanPham = text.getText().trim();
        Product product = productDao.selectByNAME(tenSanPham);
        if(tenSanPham.length()== 0)
        {
            JOptionPane.showMessageDialog(text.getRootPane(), "Chưa nhập tên Sản Phẩm!");
            
            return false;
        }
        if(tenSanPham.matches(rgx)== false)
        {
            JOptionPane.showMessageDialog(text.getRootPane(), "Họ tên chỉ chứa alphabet và ký tự trắng");
            return false;
        }
         
        return true;
    }
   
 
    public boolean checkGia(JTextField text){
        String txt = text.getText().trim();
        if(txt.isEmpty()){
            JOptionPane.showMessageDialog(text.getRootPane(), "Vui Lòng Nhập Giá");
            return false;
        }
        try {
             int gia = (Integer.parseInt(txt));
            if(gia <=0){
                JOptionPane.showMessageDialog(text.getRootPane(), "Giá Phải Là Số Dương");
                return false;
            }
             
        } catch (Exception e) {
            JOptionPane.showMessageDialog(text.getRootPane(), "Giá Không Phải Là Chữ");
            return false;
        }
        return true;
    }
     CategoriesDAO cateDao = new CategoriesDAO();
    public boolean checkLoaiSanPham(JTextField text)
    {
        String rgx = "[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ ]+$";
        String tenLoai = text.getText().trim();
        Categories ctg = cateDao.selectByName(tenLoai);
        if(tenLoai.length()== 0)
        {
            JOptionPane.showMessageDialog(text.getRootPane(), "Chưa nhập tên màu!");
            return false;
        }
        if(tenLoai.matches(rgx)== false)
        {
            JOptionPane.showMessageDialog(text.getRootPane(), "Họ tên chỉ chứa alphabet và ký tự trắng");
            return false;
        }
         if(ctg != null)
        {
            JOptionPane.showMessageDialog(text.getRootPane(),"Tên màu đã tồn tại!");
            return false;
        }
        return true;
    }
    public boolean checkUpdateLoaiSanPham(JTextField text)
    {
        String rgx = "[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ ]+$";
        String tenLoai = text.getText().trim();
        
        if(tenLoai.length()== 0)
        {
            JOptionPane.showMessageDialog(text.getRootPane(), "Chưa nhập tên màu!");
            return false;
        }
        if(tenLoai.matches(rgx)== false)
        {
            JOptionPane.showMessageDialog(text.getRootPane(), "Họ tên chỉ chứa alphabet và ký tự trắng");
            return false;
        }
       
        return true;
    }
    
    public static boolean checkThoiGianTimKiem(JDateChooser tuNgay, JDateChooser denNgay){
        Date to = tuNgay.getDate();
        Date from = denNgay.getDate();
        if(to.after(from)){
            JOptionPane.showMessageDialog(tuNgay.getRootPane(), "Vui lòng nhập ngày kết thúc sau ngày bắt đầu!");
            return false;
        }
        return true;
    }
    
    public static boolean checkBoTrongNgayTimKiem(JDateChooser day, String message){
        Date ngay = day.getDate();
        if(ngay==null){
            JOptionPane.showMessageDialog(day.getRootPane(), message);
            return false;
        }
        return true;
    }
    
    
    
    
    public boolean checkEmail_QuenMatKhau(JTextField text) {
        String email = text.getText();
        String rgx = "^[a-zA-Z0-9]{5,50}@(gmail.com|fpt.edu.vn)$";
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(text.getRootPane(), "Email không được để trống");
            return false;
        }
        if (!email.matches(rgx)) {
            JOptionPane.showMessageDialog(text.getRootPane(), "Email không đúng địng dạng @gmail.com hoặc @fpt.edu.vn");
            return false;
        }
        UserDAO userDAO = new UserDAO();
        User user = userDAO.selectByEmail(email);
        if (user == null) {
            JOptionPane.showMessageDialog(text.getRootPane(), "Email không tồn tại");
            return false;
        }
        return true;
    }
    
    
    CustomerDAO customerDAO = new CustomerDAO();

    public boolean checkSoDienThoaiKhachHang(JTextField text, String maKH) {
        String dienThoai = text.getText();
        String rgx = "0[0-9]{8,10}";
        Customer customer = customerDAO.selectByPhone(dienThoai);
        if (dienThoai.isEmpty()) {
            JOptionPane.showMessageDialog(text.getRootPane(), "Vui lòng nhập số điện thoại");
            return false;
        }
  if      (!dienThoai.matches(rgx)) {
            JOptionPane.showMessageDialog(text.getRootPane(), "Số điện thoại không đúng");
            return false;
        }
        CustomerDAO customerDAO = new CustomerDAO();
        List<Customer> list = customerDAO.selectAll();
        for (Customer customer1 : list) {
            if (!String.valueOf(customer1.getId()).equals(maKH) && customer1.getPhone().equalsIgnoreCase(dienThoai)) {
                JOptionPane.showMessageDialog(text.getRootPane(), "Số điện thoại đã tồn tại");
                return false;
            }
        }
        return true;

    }
    
     public static boolean checkDiaChi(JTextField text) {
        String diaChi = text.getText();
        if (diaChi.isEmpty()) {
            JOptionPane.showMessageDialog(text.getRootPane(), "Vui lòng nhập địa chỉ");
            return false;
        }
        return true;
    }
     
   public static boolean checkNgaySinh(JDateChooser text) {
    if (text.getDate() == null) { // Check if a date has been selected
        JOptionPane.showMessageDialog(text.getRootPane(), "Vui lòng chọn ngày sinh");
        return false;
    }
    return true;
}
    
    public static boolean checkEmailKhachHang(JTextField text, String maKH) {
        String email = text.getText();
        String rgx = "^[a-zA-Z0-9]{5,50}@(gmail.com|fpt.edu.vn)$";
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(text.getRootPane(), "Email không được để trống");
            return false;
        }
        if (!email.matches(rgx)) {
            JOptionPane.showMessageDialog(text.getRootPane(), "Email không đúng địng dạng @gmail.com hoặc @fpt.edu.vn");
            return false;
        }
        CustomerDAO customerDAO = new CustomerDAO();
        List<Customer> list = customerDAO.selectAll();
        for (Customer customer : list) {
            if (!String.valueOf(customer.getId()).equals(maKH) && customer.getEmail().equalsIgnoreCase(email)) {
                JOptionPane.showMessageDialog(text.getRootPane(), "Email đã tồn tại");
                return false;
            }
        }
        return true;
    }
    
     //check tiền khách đưa
    public boolean checkTien(String tien) {
        // Biểu thức chính quy cho định dạng tiền tệ
        String pattern = "^(\\d{1,3}(,\\d{3})*|\\d+)(\\.\\d{1,2})?$";
        // Kiểm tra chuỗi có khớp với biểu thức hay không
        return tien != null && tien.matches(pattern);
    
    }
    
    public boolean checkMaCodeSanPham(JTextField maCode ){
        String code = maCode.getText().trim();
        List<Product_Variant> list = new Product_VariantDAO().selectAll();
        
        for(Product_Variant product_Variant:list){
            if(product_Variant.getCode().equals(code)){
                JOptionPane.showMessageDialog(maCode.getRootPane(), "Mã code đã tồn tại!");
                return false;
            }
        }
        return true;
    }
}
