/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sales.Utils;

import java.awt.Image;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author Admin
 */
public class XImage {
    public static Image XImage() {
        URL url = XImage.class.getResource("/Icon/icon_shoe.png");
        if (url != null) {
            return new ImageIcon(url).getImage();
        } else {
            // Xử lý trường hợp tài nguyên không được tìm thấy
            return null;
        }
    }


    public static boolean save(File src) {
        File dst = new File("src\\Image", src.getName());
        if (!dst.getParentFile().exists()) {
            dst.getParentFile().mkdirs(); // tạo thư mục
        }
        try {
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(dst.getAbsolutePath());
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static ImageIcon read(String fileName) {
        File path = new File("src\\Image", fileName);
        return new ImageIcon(path.getAbsolutePath());
    }

    public static ImageIcon read(String fileName, int w, int h) {
        File path = new File("src\\Image", fileName);
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(path.getAbsolutePath()).getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));
        return imageIcon;
    }
}
