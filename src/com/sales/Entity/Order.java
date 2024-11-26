/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sales.Entity;

import java.util.Date;

/**
 *
 * @author GF63
 */
public class Order {
    private int id;
    private int userId;
    private int customersId;
    private Date createDate;
    private int total;
    private int status;

    public Order() {
    }

    public Order(int id, int userId, int customersId, Date createDate, int total, int status) {
        this.id = id;
        this.userId = userId;
        this.customersId = customersId;
        this.createDate = createDate;
        this.total = total;
        this.status = status;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCustomersId() {
        return customersId;
    }

    public void setCustomersId(int customerId) {
        this.customersId = customerId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
}
