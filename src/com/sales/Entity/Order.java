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
    private Boolean status;

    public Order() {
    }

    public Order(int id, int userId, int customerId, Date createDate, int total, Boolean status) {
        this.id = id;
        this.userId = userId;
        this.customersId = customerId;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    
    
}
