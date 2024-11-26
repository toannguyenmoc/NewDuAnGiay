/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sales.DAO;

import com.sales.Entity.Color;
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
public class ColorDAO extends SaleDAO<Color, Integer> {

    String INSERT_SQL = "INSERT INTO COLORS(NAME, ACTIVE) VALUES(?, ?)";
    String UPDATE_SQL = "UPDATE COLORS SET NAME = ?, ACTIVE = ? WHERE ID = ?";
    String DELETE_SQL = "DELETE FROM COLORS WHERE ID = ?";
    String SELECT_ALL_SQL = "SELECT * FROM COLORS";
    String SELECT_BYID_SQL = "SELECT * FROM COLORS WHERE ID = ?";
    String SELECT_BYSQL_SQL = "";
String SELECT_BY_OBJECT = "EXEC sp_SearchMauSac ?";
String SELECT_BYNAME_SQL = "SELECT * FROM COLORS WHERE NAME = ?";

public Color selectByName(String name) {
        List<Color> list = selectBySQL(SELECT_BYNAME_SQL, name);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public void insert(Color entity) {
            JdbcHelper.update(INSERT_SQL,
                    entity.getName(),
                    entity.getActive());
    }
      public List<Color> selectByObject(String chuoi) {
        List<Color> list = this.selectBySQL(SELECT_BY_OBJECT,'%' + chuoi + '%');
        if(list.isEmpty()){
            return null;
        }
        return list;
    }
    @Override
    public void update(Color entity) {
            JdbcHelper.update(UPDATE_SQL,
                    entity.getName(),
                    entity.getActive(),
                    entity.getId());
    }

    @Override
    public void delete(Integer id) {
            JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<Color> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public Color selectByID(Integer id) {
        List<Color> list = selectBySQL(SELECT_BYID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Color> selectBySQL(String sql, Object... args) {
        List<Color> list = new ArrayList<>();
        ResultSet resultSet = JdbcHelper.query(sql, args);
        try {
            while (resultSet.next() == true) {
                Color color = new Color();
                color.setId(resultSet.getInt("ID"));
                color.setName(resultSet.getString("NAME"));
                color.setActive(resultSet.getBoolean("ACTIVE"));

                list.add(color);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(ColorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
