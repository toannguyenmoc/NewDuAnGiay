/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sales.DAO;

import com.sales.Entity.Product;
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
public class ProductDAO extends SaleDAO<Product, Integer> {

    String INSERT_SQL = "INSERT INTO PRODUCTS(CATEGORY_ID, BRAND_ID, NAME, DESCRIPTION, IMAGE, ACTIVE) VALUES(?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE PRODUCTS SET CATEGORY_ID = ?, BRAND_ID = ?, NAME = ?, DESCRIPTION = ?, IMAGE = ?, ACTIVE = ? WHERE ID = ?";
    String DELETE_SQL = "DELETE FROM PRODUCTS WHERE ID = ?";
    String SELECT_ALL_SQL = "SELECT * FROM PRODUCTS";
    String SELECT_BYID_SQL = "SELECT * FROM PRODUCTS WHERE ID = ?";
    String SELECT_BYSQL_SQL = "";

    @Override
    public void insert(Product entity) {
            JdbcHelper.update(INSERT_SQL,
                    entity.getCategoryId(),
                    entity.getBrandId(),
                    entity.getName(),
                    entity.getDescription(),
                    entity.getImage(),
                    entity.getActive());
    }

    @Override
    public void update(Product entity) {
            JdbcHelper.update(UPDATE_SQL,
                    entity.getCategoryId(),
                    entity.getBrandId(),
                    entity.getName(),
                    entity.getDescription(),
                    entity.getImage(),
                    entity.getActive(),
                    entity.getId());
    }

    @Override
    public void delete(Integer id) {
            JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<Product> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public Product selectByID(Integer id) {
        List<Product> list = selectBySQL(SELECT_BYID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Product> selectBySQL(String sql, Object... args) {
        List<Product> list = new ArrayList<>();
        ResultSet resultSet = JdbcHelper.query(sql, args);
        try {
            while (resultSet.next() == true) {
                Product product = new Product();
                product.setId(resultSet.getInt("ID"));
                product.setCategoryId(resultSet.getInt("CATEGORY_ID"));
                product.setBrandId(resultSet.getInt("BRAND_ID"));
                product.setName(resultSet.getString("NAME"));
                product.setDescription(resultSet.getString("DESCRIPTION"));
                product.setImage(resultSet.getString("IMAGE"));
                product.setActive(resultSet.getBoolean("ACTIVE"));
                
                list.add(product);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
