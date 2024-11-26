/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sales.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class DateHelper {
    private static final SimpleDateFormat DATE_FORMATER = new SimpleDateFormat("dd-MM-yyyy");

    public static Date now() {
        return new Date();
    }

    public static Date toDate(String date, String... pattern) {
        String defaultPat = DATE_FORMATER.toPattern();
        try {
            if (pattern.length > 0) {
                DATE_FORMATER.applyPattern(pattern[0]);
            }
            if (date == null) {
                return DateHelper.now();
            }
            return DATE_FORMATER.parse(date);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            DATE_FORMATER.applyPattern(defaultPat);
        }
    }

    public static String toString(Date date, String... pattern) {
        if (pattern.length > 0) {
            DATE_FORMATER.applyPattern(pattern[0]);
        }
        if (date == null) {
            date = DateHelper.now();
        }
        return DATE_FORMATER.format(date);
    }

    /*
* Bổ sung số ngày vào thời gian
 * @param date thời gian hiện có
 * @param days số ngày cần bổ sung váo date
 * @return Date kết quả
     */
//    public static Date addDays(Date date, int days) {
//        Date newDate = new Date(date.getTime() + days * 24 * 60 * 60 * 1000); // Tạo đối tượng Date mới
//        return newDate;
//    }
    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days); // Thêm số ngày
        return calendar.getTime();
    }

    public static Date addYears(Date date, int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, years); 
        return calendar.getTime();
    }

    /**
     * Bổ sung số ngày vào thời gian hiện hành
     *
     * @param số ngày cần bổ sung vào thời gian hiện tại
     * @return Date kết quả
     */
    public static Date add(int days) {
        Date now = DateHelper.now();
        return new Date(now.getTime() + days * 24 * 60 * 60 * 1000); // Tạo đối tượng Date mới với thời gian được cộng thêm
    }
}
