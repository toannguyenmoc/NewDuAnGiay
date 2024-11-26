/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sales.Entity;

/**
 *
 * @author GF63
 */
public class Brand {
    private int id;
    private String name;
    private String image;
    private Boolean active;

    public Brand() {
    }

    public Brand(int id, String name, String image, Boolean active) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.active = active;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
     public String toString() {
        return name;  
    }
}
