/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sales.DAO;

import com.sales.Entity.Customer;
import com.sales.Utils.JdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GF63
 */
public class CustomerDAO extends SaleDAO<Customer, Integer> {

    String INSERT_SQL = "INSERT INTO CUSTOMERS(NAME, GENDER, PHONE, EMAIL, DATE_OF_BIRTH, ADDRESS, ACTIVE) VALUES (?, ?, ?, ?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE CUSTOMERS SET NAME = ?, GENDER = ?, PHONE = ?, EMAIL = ?, DATE_OF_BIRTH = ?, ADDRESS = ?, ACTIVE = ? WHERE ID = ?";
    String DELETE_SQL = "DELETE FROM CUSTOMERS WHERE ID = ?";
    String SELECTALL_SQL = "SELECT * FROM CUSTOMERS";
    String SELECTBYID_SQL = "SELECT * FROM CUSTOMERS WHERE ID = ?";
    String SELECT_BY_OBJECT = "EXEC SP_SearchKhachHang ?";
    String SELECT_BYPHONE_SQL = "SELECT * FROM CUSTOMERS WHERE PHONE = ?";


    @Override
    public void insert(Customer entity) {
            JdbcHelper.update(INSERT_SQL,
                    entity.getName(),
                    entity.getGender(),
                    entity.getPhone(),
                    entity.getEmail(),
                    entity.getDateOfBirth(),
                    entity.getAddress(),
                    entity.getActive());
    }

    @Override
    public void update(Customer entity){
            JdbcHelper.update(UPDATE_SQL,
                    entity.getName(),
                    entity.getGender(),
                    entity.getPhone(),
                    entity.getEmail(),
                    entity.getDateOfBirth(),
                    entity.getAddress(),
                    entity.getActive(),
                    entity.getId());
    }
    
    public void update(Customer entity, int id){
            JdbcHelper.update(UPDATE_SQL,
                    entity.getName(),
                    entity.getGender(),
                    entity.getPhone(),
                    entity.getEmail(),
                    entity.getDateOfBirth(),
                    entity.getAddress(),
                    entity.getActive(),
                    id);
    }

    @Override
    public void delete(Integer key) {
            JdbcHelper.update(DELETE_SQL, key);
    }
    
    @Override
    public List<Customer> selectAll() {
        return this.selectBySQL(SELECTALL_SQL);
    }

    @Override
    public Customer selectByID(Integer id) {
        List<Customer> list = this.selectBySQL(SELECTBYID_SQL, id);
        if (list.isEmpty()) // ko co phan tu nao thi return null
        {
            return null;
        }
        return list.get(0); //Con co lay gia tri dau tien
    }

    public List<Customer> selectByObject(String chuoi) {
        List<Customer> list = this.selectBySQL(this.SELECT_BY_OBJECT, '%' + chuoi + '%');
        return list.isEmpty() ? null : list;
    }
    
    public Customer selectByPhone(String phone) {
        List<Customer> list = selectBySQL(SELECT_BYPHONE_SQL, phone);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    @Override
    public List<Customer> selectBySQL(String sql, Object... args) {
        List<Customer> list = new ArrayList<>();
        ResultSet rs = JdbcHelper.query(sql, args);
        try {
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("ID"));
                customer.setName(rs.getNString("NAME"));
                customer.setGender(rs.getBoolean("GENDER"));
                customer.setPhone(rs.getString("PHONE"));
                customer.setEmail(rs.getString("EMAIL"));
                customer.setDateOfBirth(rs.getDate("DATE_OF_BIRTH"));
                customer.setAddress(rs.getNString("ADDRESS"));
                customer.setActive(rs.getBoolean("ACTIVE"));
                
                list.add(customer);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
