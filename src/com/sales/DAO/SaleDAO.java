/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sales.DAO;

import java.util.List;

/**
 *
 * @author GF63
 */
public abstract class SaleDAO<E, K> {

    public abstract void insert(E entity);

    public abstract void update(E entity);

    public abstract void delete(K id);

    public abstract List<E> selectAll();

    public abstract E selectByID(Integer id);

    public abstract List<E> selectBySQL(String sql, Object... args);
}
