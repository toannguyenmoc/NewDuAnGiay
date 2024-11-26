/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sales.Utils;

import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Admin
 */
public class MailHelper {
        public static String generateCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            code.append(characters.charAt(index));
        }
        return code.toString();
    }
 
        
    //Tham số tryền vào email gửi đến, mã code muốn gửi, tiêu đề email, nội dung email 
    public static void sendEmail(String mailNguoiNhan, String verificationCode, String titleEmail, String contentEmail) {
         final String senderEmail = "nguyentritai2412021@gmail.com";
         final String password = "lnvh jpsf wkxi fhiy";
        // yesx bvus nyze kkki
        Properties pro = new Properties();
        pro.setProperty("mail.smtp.auth", "true");
        pro.setProperty("mail.smtp.starttls.enable", "true");
        pro.setProperty("mail.smtp.host", "smtp.gmail.com");
        pro.setProperty("mail.smtp.port", "587");
        pro.put("mail.smtp.socketFactory.port", "587");
        pro.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        pro.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(pro, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailNguoiNhan));
            message.setSubject(titleEmail);
            String emailContent = contentEmail + verificationCode;
            message.setText(emailContent);

            Transport.send(message);
            System.out.println("Email đã được gửi thành công.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
