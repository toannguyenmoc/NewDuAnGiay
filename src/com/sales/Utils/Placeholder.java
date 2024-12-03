/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sales.Utils;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;

/**
 *
 * @author Admin
 */
public class Placeholder {
        public static void Placeholder(JTextField text) {
        String placeholder = "Tìm kiếm ...";

        text.setText(placeholder);
        text.setForeground(new java.awt.Color(150, 150, 150));

        text.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (text.getText().equals(placeholder)) {
                    text.setText("");
                    text.setForeground(new java.awt.Color(0, 0, 0));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (text.getText().isEmpty()) {
                    text.setText(placeholder);
                    text.setForeground(new java.awt.Color(150, 150, 150));
                }
            }
        });
    }
}
