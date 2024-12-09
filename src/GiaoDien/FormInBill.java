/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GiaoDien;

import com.sales.DAO.OrderDAO;
import com.sales.DAO.Order_DetailDAO;
import com.sales.DAO.Product_VariantDAO;
import com.sales.Entity.Order;
import com.sales.Entity.Order_Detail;
import com.sales.Utils.MailHelper;
import com.sales.Utils.SessionStorage;
import com.sales.Utils.XImage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class FormInBill extends javax.swing.JFrame {

    List<Order_Detail> list;
    Order_DetailDAO order_DetailDAO = new Order_DetailDAO();
    Product_VariantDAO product_VariantDAO = new Product_VariantDAO();
    OrderDAO orderDAO = new OrderDAO();
    FormTaoHoaDon idNow = new FormTaoHoaDon();
    Integer idBill = idNow.getIdNow()-1;
    Integer totalIn = orderDAO.selectByID(idBill).getTotal();
    /**
     * Creates new form FormInBill
     */
    public FormInBill() {
        initComponents();
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setIconImage(XImage.XImage());
        setTitle("HOÁ ĐƠN");
        bill.setEditable(false);
        list = order_DetailDAO.selectAllByIDOrder(idBill);
        bill_print();
        System.out.println(totalIn);
    }

    public void bill_print() {
        try {
            bill.setText("\tThe FurtureTech Office \n");
            bill.setText(bill.getText() + "\t589/King Road, \n");
            bill.setText(bill.getText() + "\tColombo, Srilanka,\n");
            bill.setText(bill.getText() + "\t+9411 123456789, \n");
            bill.setText(bill.getText() + "----------------------------------------------------------------\n");
            bill.setText(bill.getText() + "Iteam \t\tQty \tPrice \n");
            bill.setText(bill.getText() + "----------------------------------------------------------------\n");

            for (int i = 0; i < list.size(); i++) {

                int name = list.get(i).getProductVariantId();
                String qt = list.get(i).getQuantity() + "";
                String prc = list.get(i).getPrice() + "";
                
                System.out.println(list.get(i).getProductVariantId());

                bill.setText(bill.getText() + "\t" + name + "\t" + qt + "\t" + prc + " \n");

            }
            bill.setText(bill.getText() + "\t\t----------------------------------------------------------------\n");
            //  bill.setText(bill.getText() + "\t\tTax :\t" + totalIn.getTax() + "\n");
            //bill.setText(bill.getText() + "\t\tDiscount :\t" + totalIn.getDiscount() + "\n");
            bill.setText(bill.getText() + "\t\tTotal :\t" + totalIn + "\n");
            bill.setText(bill.getText() + "\t\t====================================\n");
            bill.setText(bill.getText() + "\tThanks For Your Business...!" + "\n");
            bill.setText(bill.getText() + "\t\t----------------------------------------------------------------\n");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String bill_Html() {

        try {
            // Tạo nội dung HTML
            StringBuilder htmlBill = new StringBuilder();
            htmlBill.append("<html><body>");
            htmlBill.append("<h2 style='text-align: center;'>The FutureTech Office</h2>");
            htmlBill.append("<p style='text-align: center;'>589/King Road,<br>Colombo, Sri Lanka,<br>+9411 123456789</p>");
            htmlBill.append("<hr>");
            htmlBill.append("<table style='width: 100%; border-collapse: collapse;'>");
            htmlBill.append("<tr><th style='border: 1px solid black; padding: 8px;'>Item</th><th style='border: 1px solid black; padding: 8px;'>Qty</th><th style='border: 1px solid black; padding: 8px;'>Price</th></tr>");

            for (int i = 0; i < list.size(); i++) {
                int name = list.get(i).getProductVariantId();
                String qt = list.get(i).getQuantity() + "";
                String prc = list.get(i).getPrice() + "";

                htmlBill.append("<tr>");
                htmlBill.append("<td style='border: 1px solid black; padding: 8px;'>").append(name).append("</td>");
                htmlBill.append("<td style='border: 1px solid black; padding: 8px;'>").append(qt).append("</td>");
                htmlBill.append("<td style='border: 1px solid black; padding: 8px;'>").append(prc).append("</td>");
                htmlBill.append("</tr>");
            }

            htmlBill.append("</table>");
            htmlBill.append("<hr>");
            //htmlBill.append("<p>Tax: ").append(totalIn.getTax()).append("</p>");
            //htmlBill.append("<p>Discount: ").append(totalIn.getDiscount()).append("</p>");
            htmlBill.append("<p>Total: ").append(totalIn).append("</p>");
            htmlBill.append("<hr>");
            htmlBill.append("<p style='text-align: center;'>Thanks For Your Business...!</p>");
            htmlBill.append("</body></html>");

            return htmlBill.toString();
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        bill = new javax.swing.JTextPane();
        jPanel1 = new javax.swing.JPanel();
        txtFrom = new javax.swing.JTextField();
        txtTo = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnSend = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jScrollPane1.setViewportView(bill);

        jPanel1.setLayout(new java.awt.GridLayout(0, 1, 0, 5));

        txtFrom.setText("jTextField2");
        jPanel1.add(txtFrom);

        txtTo.setText("jTextField3");
        jPanel1.add(txtTo);

        jPanel2.setLayout(new java.awt.GridLayout(0, 1, 0, 5));

        jLabel1.setText("From:");
        jPanel2.add(jLabel1);

        jLabel2.setText("To:");
        jPanel2.add(jLabel2);

        btnSend.setText("Send");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSend)
                .addGap(27, 27, 27))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSend)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        MailHelper.sendEmail(txtTo.getText(), "", "Hoá Đơn", bill_Html());
    }//GEN-LAST:event_btnSendActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormInBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormInBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormInBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormInBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormInBill().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane bill;
    private javax.swing.JButton btnSend;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtFrom;
    private javax.swing.JTextField txtTo;
    // End of variables declaration//GEN-END:variables
}
