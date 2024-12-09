/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GiaoDien;

import com.sales.DAO.CustomerDAO;
import com.sales.DAO.OrderDAO;
import com.sales.DAO.Order_DetailDAO;
import com.sales.DAO.Product_VariantDAO;
import com.sales.Entity.Order;
import com.sales.Entity.Order_Detail;
import com.sales.Utils.MailHelper;
import com.sales.Utils.SessionStorage;
import com.sales.Utils.XImage;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class FormGuiBillQuaMail extends javax.swing.JFrame {

    List<Order_Detail> list;
    Order_DetailDAO order_DetailDAO = new Order_DetailDAO();
    Product_VariantDAO product_VariantDAO = new Product_VariantDAO();
    OrderDAO orderDAO = new OrderDAO();
    FormTaoHoaDon idNow = new FormTaoHoaDon();
    Integer idBill = idNow.getIdNow() - 1;
    Integer totalIn = orderDAO.selectByID(idBill).getTotal();
    DecimalFormat df = new DecimalFormat("###,###");
    // Lấy thời gian hiện tại
    LocalDateTime now = LocalDateTime.now();
    // Định dạng theo kiểu dd/MM/yyyy HH:mm
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    String formattedDateTime = now.format(formatter);

    /**
     * Creates new form FormInBill
     */
    public FormGuiBillQuaMail() {
        initComponents();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setIconImage(XImage.XImage());
        setTitle("HOÁ ĐƠN");
        bill.setEditable(false);
        txtTenKhachHang.setEditable(false);
        txtTo.setEditable(false);
        txtTenKhachHang.setText(tenKhachHang());
        txtTo.setText(emailKhachHang());
        bill_print();
        System.out.println(totalIn);
    }

    public String emailKhachHang() {
        int idKhachHang = orderDAO.selectByID(idBill).getCustomersId();
        CustomerDAO customerDAO = new CustomerDAO();
        String email = customerDAO.selectByID(idKhachHang).getEmail();
        return email;
    }

    public String tenKhachHang() {
        int idKhachHang = orderDAO.selectByID(idBill).getCustomersId();
        CustomerDAO customerDAO = new CustomerDAO();
        String name = customerDAO.selectByID(idKhachHang).getName();
        return name;
    }

    public void bill_print() {
        list = order_DetailDAO.selectAllByIDOrder(idBill);
        try {
            bill.setText("\tSHOP GIÀY BALANCE \n");
            bill.setText(bill.getText() + "phường Thường Thạnh,quận Cái Răng, TP Cần Thơ\n");
            bill.setText(bill.getText() + "\tĐT: +84 382035448 \n\n");
            bill.setText(bill.getText() + "\tHOÁ ĐƠN BÁN HÀNG\n\n");
            bill.setText(bill.getText() + "\t" + formattedDateTime + "\n");
            bill.setText(bill.getText() + "--------------------------------------------------------------------\n");
            bill.setText(bill.getText() + "Đơn Giá\t\tSố Lượng\tThành Tiền \n");
            bill.setText(bill.getText() + "--------------------------------------------------------------------\n");

            for (int i = 0; i < list.size(); i++) {

                int idPro_Var = list.get(i).getProductVariantId();
                String namePro = product_VariantDAO.selectNameByID_ProVar(idPro_Var);
                int qt = list.get(i).getQuantity();
                int prc = list.get(i).getPrice();
                int thanhTien = qt * prc;

                bill.setText(bill.getText() + namePro + "\n");
                bill.setText(bill.getText() + df.format(prc) + "\t\t" + qt + "\t" + df.format(thanhTien) + " \n");
                bill.setText(bill.getText() + "--------------------------------------------------------------------\n");
            }
            //bill.setText(bill.getText() + "\t\t--------------------------------------------------------------------\n");
            //  bill.setText(bill.getText() + "\t\tTax :\t" + totalIn.getTax() + "\n");
            //bill.setText(bill.getText() + "\t\tDiscount :\t" + totalIn.getDiscount() + "\n");
            bill.setText(bill.getText() + "\t\tTotal :\t" + df.format(totalIn) + "\n");
            bill.setText(bill.getText() + "\t\t======================================\n");
            bill.setText(bill.getText() + "\tThanks For Your Business...!" + "\n");
            bill.setText(bill.getText() + "\t\t--------------------------------------------------------------------\n");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String generateHtmlBill() {
        list = order_DetailDAO.selectAllByIDOrder(idBill);
        StringBuilder htmlContent = new StringBuilder();

        try {
            // Phần đầu HTML
            htmlContent.append("<html>")
                    .append("<head><style>")
                    .append("table { width: 100%; border-collapse: collapse; }")
                    .append("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }")
                    .append("th { background-color: #f2f2f2; }")
                    .append("</style></head>")
                    .append("<body>")
                    .append("<h2 style='text-align:center;'>SHOP GIÀY BALANCE</h2>")
                    .append("<p style='text-align:center;'>phường Thường Thạnh, quận Cái Răng, TP Cần Thơ</p>")
                    .append("<p style='text-align:center;'>ĐT: +84 382035448</p>")
                    .append("<hr>")
                    .append("<h3 style='text-align:center;'>HOÁ ĐƠN BÁN HÀNG</h3>")
                    .append("<p style='text-align:center;'>").append(formattedDateTime).append("</p>")
                    .append("<hr>")
                    .append("<table>")
                    .append("<thead>")
                    .append("<tr>")
                    .append("<th>Tên Sản Phẩm</th>")
                    .append("<th>Đơn Giá</th>")
                    .append("<th>Số Lượng</th>")
                    .append("<th>Thành Tiền</th>")
                    .append("</tr>")
                    .append("</thead>")
                    .append("<tbody>");

            // Phần nội dung hóa đơn
            for (int i = 0; i < list.size(); i++) {
                int idProVar = list.get(i).getProductVariantId();
                String namePro = product_VariantDAO.selectNameByID_ProVar(idProVar);
                int quantity = list.get(i).getQuantity();
                int price = list.get(i).getPrice();
                int totalPrice = quantity * price;

                htmlContent.append("<tr>")
                        .append("<td>").append(namePro).append("</td>")
                        .append("<td>").append(df.format(price)).append("</td>")
                        .append("<td>").append(quantity).append("</td>")
                        .append("<td>").append(df.format(totalPrice)).append("</td>")
                        .append("</tr>");
            }

            // Tổng cộng
            htmlContent.append("</tbody>")
                    .append("</table>")
                    .append("<p style='text-align:right;'><strong>Total: ")
                    .append(df.format(totalIn))
                    .append("</strong></p>")
                    .append("<hr>")
                    .append("<p style='text-align:center;'>Thanks for your business!</p>")
                    .append("</body>")
                    .append("</html>");
        } catch (Exception e) {
            e.printStackTrace(); // Ghi chi tiết lỗi vào console
        }

        return htmlContent.toString();
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
        txtTenKhachHang = new javax.swing.JTextField();
        txtTo = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnSend = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jScrollPane1.setViewportView(bill);

        jPanel1.setLayout(new java.awt.GridLayout(0, 1, 0, 5));
        jPanel1.add(txtTenKhachHang);
        jPanel1.add(txtTo);

        jPanel2.setLayout(new java.awt.GridLayout(0, 1, 0, 5));

        jLabel1.setText("Tên : ");
        jPanel2.add(jLabel1);

        jLabel2.setText("Email :");
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
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
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
        try {
            MailHelper.sendEmail(txtTo.getText(), "", "Hoá Đơn", generateHtmlBill());
            JOptionPane.showMessageDialog(this, "Gửi hoá đơn thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gửi hoá đơn thất bại!");
        }

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
            java.util.logging.Logger.getLogger(FormGuiBillQuaMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormGuiBillQuaMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormGuiBillQuaMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormGuiBillQuaMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormGuiBillQuaMail().setVisible(true);
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
    private javax.swing.JTextField txtTenKhachHang;
    private javax.swing.JTextField txtTo;
    // End of variables declaration//GEN-END:variables
}
