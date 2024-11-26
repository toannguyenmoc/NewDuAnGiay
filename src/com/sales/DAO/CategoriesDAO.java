/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sales.DAO;

import com.sales.Entity.Categories;
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
public class CategoriesDAO extends SaleDAO<Categories, Integer> {

    String INSERT_SQL = "INSERT INTO CATEGORIES(NAME, IMAGE, ACTIVE) VALUES(?, ?, ?)";
    String UPDATE_SQL = "UPDATE CATEGORIES SET NAME = ?, IMAGE = ?, ACTIVE = ? WHERE ID = ?";
    String DELETE_SQL = "DELETE FROM CATEGORIES WHERE ID = ?";
    String SELECT_ALL_SQL = "SELECT * FROM CATEGORIES";
    String SELECT_BYID_SQL = "SELECT * FROM CATEGORIES WHERE ID = ?";
    String SELECT_BYSQL_SQL = "";

    @Override
    public void insert(Categories entity) {
            JdbcHelper.update(INSERT_SQL,
                    entity.getName(),
                    entity.getImage(),
                    entity.getActive());
    }

    @Override
    public void update(Categories entity) {
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
    public List<Categories> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public Categories selectByID(Integer id) {
        List<Categories> list = selectBySQL(SELECT_BYID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Categories> selectBySQL(String sql, Object... args) {
        List<Categories> list = new ArrayList<>();
        ResultSet resultSet = JdbcHelper.query(sql, args);
        try {
            while (resultSet.next() == true) {
                Categories categories = new Categories();
                categories.setId(resultSet.getInt("ID"));
                categories.setName(resultSet.getString("NAME"));
                categories.setImage(resultSet.getString("IMAGE"));
                categories.setActive(resultSet.getBoolean("ACTIVE"));

                list.add(categories);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(CategoriesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
