/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sales.DAO;

import com.sales.Entity.Brand;
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
public class BrandDAO extends SaleDAO<Brand, Integer> {

    String INSERT_SQL = "INSERT INTO BRANDS(NAME, IMAGE, ACTIVE) VALUES(?, ?, ?)";
    String UPDATE_SQL = "UPDATE BRANDS SET NAME = ?, IMAGE = ?, ACTIVE = ? WHERE ID = ?";
    String DELETE_SQL = "DELETE FROM BRANDS WHERE ID = ?";
    String SELECT_ALL_SQL = "SELECT * FROM BRANDS";
    String SELECT_BYID_SQL = "SELECT * FROM BRANDS WHERE ID = ?";
    String SELECT_BYSQL_SQL = "";
    String SELECT_BYNAME_SQL = "SELECT * FROM BRANDS WHERE NAME = ?";

    public Brand selectByName(String name) {
        List<Brand> list = selectBySQL(SELECT_BYNAME_SQL, name);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public void insert(Brand entity) {
            JdbcHelper.update(INSERT_SQL,
                    entity.getName(),
                    entity.getImage(),
                    entity.getActive());
    }

    @Override
    public void update(Brand entity) {
            JdbcHelper.update(UPDATE_SQL,
                    entity.getName(),
                    entity.getImage(),
                    entity.getActive(),
                    entity.getId());
    }

    @Override
    public void delete(Integer id) {
            JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<Brand> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public Brand selectByID(Integer id) {
        List<Brand> list = selectBySQL(SELECT_BYID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Brand> selectBySQL(String sql, Object... args) {
        List<Brand> list = new ArrayList<>();
        ResultSet resultSet = JdbcHelper.query(sql, args);
        try {
            while (resultSet.next() == true) {
                Brand brand = new Brand();
                brand.setId(resultSet.getInt("ID"));
                brand.setName(resultSet.getString("NAME"));
                brand.setImage(resultSet.getString("IMAGE"));
                brand.setActive(resultSet.getBoolean("ACTIVE"));

                list.add(brand);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(BrandDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
