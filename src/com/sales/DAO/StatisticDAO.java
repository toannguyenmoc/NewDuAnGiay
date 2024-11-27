/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sales.DAO;

import com.sales.Utils.JdbcHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class StatisticDAO {

    private List<Object[]> getListofArray(String sql, String[] cols, Object... args) {
        try {
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Object[]> getThongKeTonKho(Boolean sapXep) { // 0: tăng dần, 1: giảm dần
        String sql = "{CALL SP_ThongKeTonKho(?)}";
        String[] cols = {"TenSanPham", "BienTheSanPham", "SoLuongTon", "DonGia"};
        return getListofArray(sql, cols, sapXep);
    }

    public List<Object[]> getDoanhThuTuNgayDenNgay(Date tuNgay, Date denNgay) {
        String sql = "{CALL SP_ThongKeDoanhThuTuNgayDenNgay(?,?)}";
        String[] cols = {"TenSanPham", "BienTheSanPham", "SoLuongBan", "DoanhThu"};
        return getListofArray(sql, cols, tuNgay, denNgay);
    }

    public List<Object[]> getSanPhamBanChayNhatTuNgayDenNgay(Date tuNgay, Date denNgay) {
        String sql = "{CALL SP_ThongKeSanPhamBanChayNhatTuNgayDenNgay(?,?)}";
        String[] cols = {"TenSanPham", "BienTheSanPham", "SoLuongBan", "DonGia"};
        return getListofArray(sql, cols, tuNgay, denNgay);
    }

    public List<Object[]> getKhachHangThanThietTuNgayDenNgay(Date tuNgay, Date denNgay) {
        String sql = "{CALL SP_ThongKeKhachHangThanThietTuNgayDenNgay(?,?)}";
        String[] cols = {"TenKhachHang","GioiTinh","SoLuongDonHangDaMua","SoLuongSanPhamDaMua","TongTien"};
        return getListofArray(sql, cols, tuNgay, denNgay);
    }

    public List<Object[]> getDoanhThuTheoNam(Integer nam) {
        String sql = "{CALL SP_ThongKeDoanhThuTheoNam(?)}";
        String[] cols = {"Thang", "TongDoanhThu"};
        return getListofArray(sql, cols, nam);
    }

    public List<Object[]> timKiemSanPham(String name) { // 0: tăng dần, 1: giảm dần
        String sql = "{CALL SP_SearchSanPham (?)}";
        String[] cols = {"ID", "TENSP", "TENMAU", "SIZE", "LOAISP", "THUONGHIEU", "QUANTITY", "PRICE", "MOTA", "ACTIVE", "CODE", "IMAGE"};
        return getListofArray(sql, cols, name);
    }
}
