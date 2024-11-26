/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sales.DAO;

import com.sales.Entity.Order_Detail;
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
public class Order_DetailDAO extends SaleDAO<Order_Detail, Integer> {

     String INSERT_SQL = "INSERT INTO ORDER_DETAILS(ORDER_ID, PRODUCT_VARIANT_ID, QUANTITY, PRICE) VALUES(?,?,?,?)";
    String UPDATE_SQL = "UPDATE ORDER_DETAILS SET ORDER_ID = ?, PRODUCT_VARIANT_ID = ?, QUANTITY = ?, PRICE = ? WHERE ID = ?";
    String DELETE_SQL = "DELETE FROM ORDER_DETAILS WHERE ID = ?";
    String SELECT_ALL_SQL = "SELECT * FROM ORDER_DETAILS";
    String SELECT_BYID_SQL = "SELECT * FROM ORDER_DETAILS WHERE ID = ?";
    String SELECT_BY_ID_ORDER_SQL = "EXEC GetOderDetailByIdOrder ?";

    @Override
    public void insert(Order_Detail entity) {
            JdbcHelper.update(INSERT_SQL, entity.getOrderId(),
                    entity.getProductVariantId(),
                    entity.getQuantity(),
                    entity.getPrice());
    }

    @Override
    public void update(Order_Detail entity) {
            JdbcHelper.update(UPDATE_SQL, entity.getOrderId(),
                    entity.getProductVariantId(),
                    entity.getQuantity(),
                    entity.getPrice(),
                    entity.getId());
    }

    @Override
    public void delete(Integer id) {
            JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<Order_Detail> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public Order_Detail selectByID(Integer id) {
        List<Order_Detail> list = selectBySQL(SELECT_BYID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Order_Detail> selectBySQL(String sql, Object... args) {
        List<Order_Detail> list = new ArrayList<>();
        ResultSet resultSet = JdbcHelper.query(sql, args);
        try {
            while (resultSet.next() == true) {
                Order_Detail order_detail = new Order_Detail();
                order_detail.setId(resultSet.getInt("ID"));
                order_detail.setOrderId(resultSet.getInt("ORDER_ID"));
                order_detail.setProductVariantId(resultSet.getInt("PRODUCT_VARIANT_ID"));
                order_detail.setQuantity(resultSet.getInt("QUANTITY"));
                order_detail.setPrice(resultSet.getInt("PRICE"));

                list.add(order_detail);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(Order_Detail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<Object[]> selectByIDOrder(Integer id) {
        List<Object[]> list = new ArrayList<>();
        ResultSet rs = JdbcHelper.query(SELECT_BY_ID_ORDER_SQL, id);
        try {
            while (rs.next() == true) {
                Object[] ob = new Object[3];
                ob[0] = rs.getString("ProductName");
                ob[1] = rs.getInt("Qty");
                ob[2] = rs.getInt("Price");
                list.add(ob);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(Order_Detail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
