/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sales.DAO;

import com.sales.Entity.Size;
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
public class SizeDAO extends SaleDAO<Size, Integer> {

String INSERT_SQL = "INSERT INTO SIZES(NAME, ACTIVE) VALUES(?, ?)";
    String UPDATE_SQL = "UPDATE SIZES SET NAME = ?, ACTIVE = ? WHERE ID = ?";
    String DELETE_SQL = "DELETE FROM SIZES WHERE ID = ?";
    String SELECT_ALL_SQL = "SELECT * FROM SIZES";
    String SELECT_BYID_SQL = "SELECT * FROM SIZES WHERE ID = ?";
    String SELECT_BYSQL_SQL = "";
    String SELECT_BY_OBJECT = "EXEC sp_SearchKichThuoc ?";
    String SELECT_BYNAME_SQL = "SELECT * FROM SIZES WHERE NAME = ?";

    
     public List<Size> selectByObject(String chuoi) {
        List<Size> list = this.selectBySQL(SELECT_BY_OBJECT,'%' + chuoi + '%');
        if(list.isEmpty()){
            return null;
        }
        return list;
    }
      public Size selectByNAME(String id) {
        List<Size> list = selectBySQL(SELECT_BYNAME_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public void insert(Size entity) {
            JdbcHelper.update(INSERT_SQL,
                    entity.getName(),
                    entity.getActive());
    }

    @Override
    public void update(Size entity) {
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
    public List<Size> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public Size selectByID(Integer id) {
        List<Size> list = selectBySQL(SELECT_BYID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Size> selectBySQL(String sql, Object... args) {
        List<Size> list = new ArrayList<>();
        ResultSet resultSet = JdbcHelper.query(sql, args);
        try {
            while (resultSet.next() == true) {
                Size size = new Size();
                size.setId(resultSet.getInt("ID"));
                size.setName(resultSet.getString("NAME"));
                size.setActive(resultSet.getBoolean("ACTIVE"));

                list.add(size);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(SizeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
