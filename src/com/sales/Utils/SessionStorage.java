/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sales.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author NganTTK_PC09494
 */
public class SessionStorage {
    private static SessionStorage instance;
    private Map<String, Object> sessionData;

    private SessionStorage() {
        sessionData = new HashMap<>();
    }

    public static SessionStorage getInstance() {
        if (instance == null) {
            instance = new SessionStorage();
        }
        return instance;
    }

    public void setAttribute(String key, Object value) {
        sessionData.put(key, value);
    }

    public Object getAttribute(String key) {
        return sessionData.get(key);
    }

    public void removeAttribute(String key) {
        sessionData.remove(key);
    }
}
    

