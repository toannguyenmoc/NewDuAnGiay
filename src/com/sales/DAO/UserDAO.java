/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sales.DAO;

import com.sales.Entity.User;
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
public class UserDAO extends SaleDAO<User, Integer> {

    String INSERT_SQL = "INSERT INTO USERS(FULL_NAME,PASSWORD,ROLE,GENDER,PHONE,EMAIL,ADDRESS,ACTIVE) VALUES(?,?,?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE USERS SET FULL_NAME = ?,PASSWORD = ?,ROLE = ?,GENDER = ?,PHONE = ?,EMAIL = ?,ADDRESS = ?, ACTIVE =? WHERE ID = ?";
    String DELETE_SQL = "DELETE FROM USERS WHERE ID = ?";
    String SELECT_ALL_SQL = "SELECT * FROM USERS";
    String SELECT_BYID_SQL = "SELECT * FROM USERS WHERE ID = ?";
    //String SELECT_BYSQL_SQL = "EXEX UserLogin @Email = ?, @PlainPassword = ?";
    String UPDATE_PASS = "UPDATE USERS SET PASSWORD = ? WHERE ID = ?";
    String SELECT_EMAIL = "SELECT * FROM USERS WHERE EMAIL = ?";

    @Override
    public void insert(User entity) {
        JdbcHelper.update(INSERT_SQL, entity.getFullName(),
                entity.getPassword(),
                entity.getRole(),
                entity.getGender(),
                entity.getPhone(),
                entity.getEmail(),
                entity.getAddress(),
                entity.getActive());
    }

    @Override
    public void update(User entity) {
        JdbcHelper.update(UPDATE_SQL, entity.getFullName(),
                entity.getPassword(),
                entity.getRole(),
                entity.getGender(),
                entity.getPhone(),
                entity.getEmail(),
                entity.getAddress(),
                entity.getActive(),
                entity.getId());
    }

    public void updatePassword(User entity) {
        JdbcHelper.update(UPDATE_PASS,
                entity.getPassword(),
                entity.getId());
    }

    @Override
    public void delete(Integer id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<User> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public User selectByID(Integer id) {
        List<User> list = selectBySQL(SELECT_BYID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public User selectByEmail(String email) {
        List<User> list = selectBySQL(SELECT_EMAIL, email);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public User userLogin(String email, String pass) {
        String sql = "{CALL SP_UserLogin(?, ?)}";
        List<User> list = this.selectBySQL(sql, email, pass);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<User> selectBySQL(String sql, Object... args) {
        List<User> list = new ArrayList<>();
        ResultSet resultSet = JdbcHelper.query(sql, args);
        try {
            while (resultSet.next() == true) {
                User user = new User();
                user.setId(resultSet.getInt("ID"));
                user.setFullName(resultSet.getString("FULL_NAME"));
                user.setPassword(resultSet.getString("PASSWORD"));
                user.setRole(resultSet.getBoolean("ROLE"));
                user.setGender(resultSet.getBoolean("GENDER"));
                user.setPhone(resultSet.getString("PHONE"));
                user.setEmail(resultSet.getString("EMAIL"));
                user.setAddress(resultSet.getString("ADDRESS"));
                user.setActive(resultSet.getBoolean("ACTIVE"));

                list.add(user);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
