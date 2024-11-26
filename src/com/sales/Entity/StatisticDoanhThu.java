/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sales.Entity;

/**
 *
 * @author Admin
 */
public class StatisticDoanhThu {
    private int thang;
    private int doanhThu;

    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public int getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(int doanhThu) {
        this.doanhThu = doanhThu;
    }

    public StatisticDoanhThu() {
    }

    public StatisticDoanhThu(int thang, int doanhThu) {
        this.thang = thang;
        this.doanhThu = doanhThu;
    }
    
}
