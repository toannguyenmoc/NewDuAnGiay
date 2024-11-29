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
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GF63
 */
public class OrderDAO extends SaleDAO<Order, Integer> {

    String INSERT_SQL = "INSERT INTO ORDERS(USER_ID, CUSTOMER_ID, CREATE_DATE, TOTAL, STATUS) VALUES (?, ?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE ORDERS SET STATUS = ? WHERE ID = ?";
    String DELETE_SQL = "DELETE FROM ORDERS WHERE ID = ?";
    String SELECTALL_SQL = "SELECT * FROM ORDERS";
    String SELECTBYID_SQL = "SELECT * FROM ORDERS WHERE ID = ?";
    String SELECTBYDATE_SQL = "EXEC GetOrdersByDate ?, ?";
    String SELECT_PRODUCT_BY_CODE = "EXEC GetProductByCode ?";
    String SELECT_CUSTOMER_BY_PHONE = "EXEC GetCustomerByPhone ?";
    static String SELECT_BY_STATUS = "SELECT * FROM ORDERS WHERE STATUS = ?";
    String SELECT_PRODUCT_BY_ID = "EXEC GetProductByID @ID_PRODUCT= ?, @ID_ORDER_DETAIL = ?";

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
                order.setCustomersId(rs.getInt("CUSTOMER_ID"));
                order.setCreateDate(rs.getDate("CREATE_DATE"));
                order.setTotal(rs.getInt("TOTAL"));
                order.setStatus(rs.getInt("STATUS"));

                list.add(order);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Object[]> selectByDate(Date startDate, Date endDate) {
        try {
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = JdbcHelper.query(SELECTBYDATE_SQL, startDate, endDate);;
            while (rs.next()) {
                Object[] entity = new Object[6];
                entity[0] = rs.getInt("OrderID");
                entity[1] = rs.getString("UserName");
                entity[2] = rs.getString("CustomerName");
                entity[3] = rs.getDate("CreateDate");
                entity[4] = rs.getInt("Total");
                entity[5] = rs.getInt("Status");
                list.add(entity);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
    
    public Object[] selectProductByCode(String code) {
        try {
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = JdbcHelper.query(SELECT_PRODUCT_BY_CODE, code);;
            while (rs.next()) {
                Object[] entity = new Object[7];
                entity[0] = rs.getString("IMAGE");
                entity[1] = rs.getString("NAME");
                entity[2] = rs.getString("SIZE");
                entity[3] = rs.getString("COLOR");
                entity[4] = rs.getInt("PRICE");
                entity[5] = rs.getInt("ACTIVE");
                entity[6] = rs.getInt("ID");
                
                list.add(entity);
            }
            return list.get(0);
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
    
    public String selectCustomerByPhone(String phone) {
            try {
                return JdbcHelper.value(SELECT_CUSTOMER_BY_PHONE, phone).toString();
            } catch (Exception e) {
                return null;
            }
    }
    
    public Object[] selectProductById(Integer idPro, Integer idOrd) {
        try {
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = JdbcHelper.query(SELECT_PRODUCT_BY_ID, idPro, idOrd);;
            while (rs.next()) {
                Object[] entity = new Object[7];
                entity[0] = rs.getString("IMAGE");
                entity[1] = rs.getString("NAME");
                entity[2] = rs.getString("SIZE");
                entity[3] = rs.getString("COLOR");
                entity[4] = rs.getInt("PRICE");
                entity[5] = rs.getInt("QUANTITY");
                entity[6] = rs.getInt("ID");
                
                list.add(entity);
            }
            return list.get(0);
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
    
    public static List<Order> findByStatus(int status){
        try {
            List<Order> list = new ArrayList<>();
            ResultSet resultSet = JdbcHelper.query(SELECT_BY_STATUS, status);
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                int customerId = resultSet.getInt("customer_id");
                Date createdDate = resultSet.getDate("create_date");
                int total = resultSet.getInt("total");
                Order order = new Order();
                order.setId(id);
                order.setUserId(userId);
                order.setCustomersId(customerId);
                order.setCreateDate(createdDate);
                order.setTotal(total);
                list.add(order);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
