/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sales.DAO;

import com.sales.Entity.Order;
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
public class OrderDAO extends SaleDAO<Order, Integer> {

    String INSERT_SQL = "INSERT INTO ORDERS(USER_ID, CUSTOMER_ID, CREATE_DATE, TOTAL, STATUS) VALUES (?, ?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE ORDERS SET USER_ID = ?, CUSTOMER_ID = ?, CREATE_DATE = ?, TOTAL = ?, STATUS = ? WHERE ID = ?";
    String DELETE_SQL = "DELETE FROM ORDERS WHERE ID = ?";
    String SELECTALL_SQL = "SELECT * FROM ORDERS";
    String SELECTBYID_SQL = "SELECT * FROM ORDERS WHERE ID = ?";

    @Override
    public void insert(Order entity) {

            JdbcHelper.update(INSERT_SQL,
                    entity.getUserId(),
                    entity.getCustomersId(),
                    entity.getCreateDate(),
                    entity.getTotal(),
                    entity.getStatus());
    }

    @Override
    public void update(Order entity) {
            JdbcHelper.update(UPDATE_SQL,
                    entity.getUserId(),
                    entity.getCustomersId(),
                    entity.getCreateDate(),
                    entity.getTotal(),
                    entity.getStatus(),
                    entity.getId());
        
    }

    @Override
    public void delete(Integer key) {
            JdbcHelper.update(DELETE_SQL, key);
        
    }

    @Override
    public List<Order> selectAll() {
        return this.selectBySQL(SELECTALL_SQL);
    }

    @Override
    public Order selectByID(Integer id) {
        List<Order> list = this.selectBySQL(SELECTBYID_SQL, id);
        if (list.isEmpty()) // ko co phan tu nao thi return null
        {
            return null;
        }
        return list.get(0); //Con co lay gia tri dau tien
    }

    @Override
    public List<Order> selectBySQL(String sql, Object... args) {
        List<Order> list = new ArrayList<>();
        ResultSet rs = JdbcHelper.query(sql, args);
        try {
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("ID"));
                order.setUserId(rs.getInt("USER_ID"));
                order.setCustomersId(rs.getInt("CUSTOMERS_ID"));
                order.setCreateDate(rs.getDate("CREATE_DATE"));
                order.setTotal(rs.getInt("TOTAL"));
                order.setStatus(rs.getBoolean("STATUS"));
                list.add(order);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
