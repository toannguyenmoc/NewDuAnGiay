/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sales.DAO;

import com.sales.Entity.Product_Variant;
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
public class Product_VariantDAO extends SaleDAO<Product_Variant, Integer> {

    String INSERT_SQL = "INSERT INTO PRODUCT_VARIANTS(COLOR_ID, SIZE_ID, PRODUCT_ID, PRICE, QUANTITY, IMAGE, CODE, ACTIVE) VALUES(?,?,?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE PRODUCT_VARIANTS SET COLOR_ID = ?, SIZE_ID = ?, PRODUCT_ID = ?, PRICE = ?, QUANTITY = ?, IMAGE = ?, CODE = ?, ACTIVE = ? WHERE ID = ?";
    String DELETE_SQL = "DELETE FROM PRODUCT_VARIANTS WHERE ID = ?";
    String SELECT_ALL_SQL = "SELECT * FROM PRODUCT_VARIANTS";
    String SELECT_BYID_SQL = "SELECT * FROM PRODUCT_VARIANTS WHERE ID = ?";
    String SELECT_BYSQL_SQL = "";
    String SELECT_BY_OBJECT = "EXEC sp_SearchSanPham ?";
    String SELECT_NAME_BYID = "EXEC GetProductDetailsByVariantId ?";
  
     
    
    public List<Product_Variant> selectByObject(String chuoi) {
        List<Product_Variant> list = this.selectBySQL(SELECT_BY_OBJECT,'%' + chuoi + '%');
        if(list.isEmpty()){
            return null;
        }
        return list;
    }
  
    @Override
    public void insert(Product_Variant entity) {
        JdbcHelper.update(INSERT_SQL,
                entity.getColorId(),
                entity.getSizeId(),
                entity.getProductId(),
                entity.getPrice(),
                entity.getQuantity(),
                entity.getImage(),
                entity.getCode(),
                entity.getActive());
    }

    @Override
    public void update(Product_Variant entity) {
        JdbcHelper.update(UPDATE_SQL,
                entity.getColorId(),
                entity.getSizeId(),
                entity.getProductId(),
                entity.getPrice(),
                entity.getQuantity(),
                entity.getImage(),
                entity.getCode(),
                entity.getActive(),
                entity.getId());
    }
    
    public void update(Product_Variant entity,int id) {
        JdbcHelper.update(UPDATE_SQL,
                entity.getColorId(),
                entity.getSizeId(),
                entity.getProductId(),
                entity.getPrice(),
                entity.getQuantity(),
                entity.getImage(),
                entity.getCode(),
                entity.getActive(),
                id);
    }

    @Override
    public void delete(Integer id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<Product_Variant> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public Product_Variant selectByID(Integer id) {
        List<Product_Variant> list = selectBySQL(SELECT_BYID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public Product_Variant selectName_ByID(Integer id) {
        List<Product_Variant> list = selectBySQL(SELECT_NAME_BYID, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Product_Variant> selectBySQL(String sql, Object... args) {
        List<Product_Variant> list = new ArrayList<>();
        ResultSet resultSet = JdbcHelper.query(sql, args);
        try {
            while (resultSet.next() == true) {
                Product_Variant product_variant = new Product_Variant();
                product_variant.setId(resultSet.getInt("ID"));
                product_variant.setColorId(resultSet.getInt("COLOR_ID"));
                product_variant.setSizeId(resultSet.getInt("SIZE_ID"));
                product_variant.setProductId(resultSet.getInt("PRODUCT_ID"));
                product_variant.setPrice(resultSet.getInt("PRICE"));
                product_variant.setQuantity(resultSet.getInt("QUANTITY"));
                product_variant.setImage(resultSet.getString("IMAGE"));
                product_variant.setCode(resultSet.getString("CODE"));
                product_variant.setActive(resultSet.getBoolean("ACTIVE"));

                list.add(product_variant);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(Product_VariantDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
