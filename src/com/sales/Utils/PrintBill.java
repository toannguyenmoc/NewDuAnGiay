package com.sales.Utils;

import com.sales.DAO.Order_DetailDAO;
import com.sales.Utils.SessionStorage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.mail.Session;

public class PrintBill extends JFrame {

    private ArrayList<String> itemName;
    private ArrayList<String> quantity;
    private ArrayList<String> itemPrice;
    private ArrayList<String> subtotal;
    private double totalAmount;
    private double taxAmount;
    private String date;
    private String invoiceNumber;

    public PrintBill(ArrayList<String> itemName, ArrayList<String> quantity, ArrayList<String> itemPrice,
            ArrayList<String> subtotal, double totalAmount,
            String date, String invoiceNumber) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
        this.subtotal = subtotal;
        this.totalAmount = totalAmount;
        this.taxAmount = taxAmount;
        this.date = date;
        this.invoiceNumber = invoiceNumber;

        setTitle("Print Bill");
        setSize(300, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel hiển thị hóa đơn
        BillPanel billPanel = new BillPanel();
        JScrollPane scrollPane = new JScrollPane(billPanel);
        add(scrollPane, BorderLayout.CENTER);
        billPanel.setBackground(Color.WHITE);

        // Nút in hóa đơn
        JPanel buttonPanel = new JPanel();
        JButton printButton = new JButton("Print");
        printButton.addActionListener(e -> {
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            printerJob.setPrintable(new Printable() {
                @Override
                public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
                    if (pageIndex > 0) {
                        return NO_SUCH_PAGE;
                    }
                    Graphics2D g2d = (Graphics2D) g;

                    // Cấu hình giấy và lề
                    double paperWidth = cm_to_pp(5.8); // 58mm in points
                    double paperHeight = 500; // Chiều cao tạm thời, tự điều chỉnh theo nội dung
                    double marginTop = 2 / 25.4 * 72; // 2cm
                    double marginBottom = 1 / 25.4 * 72; // 1cm
                    double marginLeft = 1 / 25.4 * 72; // 1cm
                    double marginRight = 1 / 25.4 * 72; // 1cm

                    Paper paper = new Paper();

                    paper.setSize(paperWidth, paperHeight);
                    // Thiết lập vùng in
                    double imageableWidth = paperWidth - marginLeft - marginRight;
                    double imageableHeight = paperHeight - marginTop - marginBottom;

                    // Cấu hình vùng in
                    paper.setImageableArea(0, 10, imageableWidth, imageableHeight);

                    pageFormat.setPaper(paper);

                    // Đặt góc tọa độ vẽ nội dung
                    g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                    // Đặt font mặc định
                    g2d.setFont(new Font("Monospaced", Font.PLAIN, 10));
                    g2d.setColor(Color.BLACK);

                    // Gọi phương thức vẽ nội dung từ `BillPanel`
                    BillPanel billPanel = new BillPanel();

                    billPanel.setSize((int) paperWidth, (int) paperHeight); // Đặt kích thước cho BillPanel
                    billPanel.paint(g2d); // Vẽ nội dung panel trực tiếp vào Graphics2D

                    return PAGE_EXISTS;
                }
            });

// Hiển thị hộp thoại in và thực hiện in
            if (printerJob.printDialog()) {
                try {
                    printerJob.print();
                } catch (PrinterException ex) {
                    System.out.println("Lỗi in : " + ex);

                }
            }

        });
        buttonPanel.add(printButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private class BillPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // Đường dẫn logo
            ImageIcon icon = new ImageIcon("D:\\NewDuAnGiay\\src\\Image\\logo1.jpg"); // Thay đường dẫn logo thực tế

            // In hóa đơn
            printInvoice(g2d, icon, itemName, convertToIntList(quantity),
                    convertToDoubleList(itemPrice), convertToDoubleList(subtotal),
                    totalAmount, taxAmount, date, invoiceNumber);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(220, 800); // Điều chỉnh kích thước nếu cần
        }
    }

    public double cm_to_pp(double cm) {
        return toPPI(cm * 0.393600787);
    }

    public double toPPI(double inch) {
        return inch * 72d;
    }

    // Helper: Chuyển đổi ArrayList<String> sang ArrayList<Integer>
    private ArrayList<Integer> convertToIntList(ArrayList<String> list) {
        ArrayList<Integer> result = new ArrayList<>();
        for (String str : list) {
            result.add(Integer.parseInt(str));
        }
        return result;
    }

    // Helper: Chuyển đổi ArrayList<String> sang ArrayList<Double>
    private ArrayList<Double> convertToDoubleList(ArrayList<String> list) {
        ArrayList<Double> result = new ArrayList<>();
        for (String str : list) {
            result.add(Double.parseDouble(str));
        }
        return result;
    }

    public void printInvoice(Graphics2D g2d, ImageIcon icon, List<String> itemName,
            List<Integer> quantity, List<Double> itemPrice,
            List<Double> subtotal, double totalAmount, double taxAmount, String date, String invoiceNumber) {
        int y = 20; // Điểm y ban đầu
        int yShift = 15; // Khoảng cách dòng
        int billWidth = 230; // Chiều rộng hóa đơn (58mm ~ 220 pixels)
        int centerX = 75; // Điểm giữa chiều ngang của hóa đơn

        // Font in đậm và kích thước lớn hơn cho tiêu đề
        Font boldFont = new Font("Monospaced", Font.BOLD, 10);
        Font regularFont = new Font("Monospaced", Font.PLAIN, 8);

        // Vẽ logo (nếu có)
        if (icon != null) {
            g2d.drawImage(icon.getImage(), centerX - 45, y, 90, 30, null);
            y += yShift + 30;
        }

        // Tiêu đề hóa đơn
        g2d.setFont(boldFont);
        drawCenteredText(g2d, "CỬA HÀNG GIÀY BALANCE", centerX, y);
        y += yShift;
        g2d.drawString("-----------------------", 10, y);
        y += yShift;
        drawCenteredText(g2d, "PHIẾU TẠM TÍNH", centerX, y);
        y += yShift;

        // Thông tin cơ bản
        g2d.setFont(regularFont);
        g2d.drawString("Ngày: " + date, 10, y);
        y += yShift;
        g2d.drawString("Số hóa đơn: " + invoiceNumber, 10, y);
        y += yShift;
        g2d.drawString("Khách hàng: Khách mua lẻ", 10, y);
        y += yShift;
        g2d.drawString("------------------------------", 10, y);
        y += yShift;

        // Tiêu đề bảng sản phẩm
        g2d.setFont(boldFont);
        g2d.drawString("STT | Sản Phẩm", 10, y);
        g2d.drawString("SL  Đơn Giá  Thành Tiền", 10, y + yShift);
        y += yShift * 2;

        // Danh sách sản phẩm
        g2d.setFont(regularFont);
        int x = 0;
        for (int i = 0; i < itemName.size(); i++) {
            y+=5;
            drawMultilineText(g2d, (i + 1) + " | " + itemName.get(i), 10, 200 + x, 160, 10);
            System.out.println(itemName.get(i).length());
            g2d.drawString("    " + quantity.get(i) + " x " + String.format("%.0f", itemPrice.get(i)), 10, y + yShift);
            g2d.drawString(String.format("%.0f", subtotal.get(i)), billWidth - 120, y + yShift);
            y += yShift * 2;
            x += 30;
        }
        g2d.drawString("------------------------------", 10, y);
        y += yShift;

        // Tổng tiền
        g2d.setFont(boldFont);
        g2d.drawString("Tổng cộng: " + String.format("%.0f", totalAmount), 10, y);
        y += yShift;

        g2d.drawString("*LƯU Ý:", 10, y);
        y += yShift;
        g2d.setFont(regularFont);
        g2d.drawString("1.Giá đã bao gồm thuế VAT và có", 10, y);
        g2d.drawString("giá trị xuất hóa đơn trong ngày.", 10, y + yShift);
        y += yShift;
        g2d.drawString("2.Vui lòng giữ hóa đơn khi đổi", 10, y + yShift);
        g2d.drawString("trả hàng.", 10, y + yShift * 2);
        y += yShift * 3;

        //Thông tin liên hệ 
        g2d.setFont(boldFont);
        drawCenteredText(g2d, "CỬA HÀNG GIÀY BALANCE", centerX, y);
        y += yShift;

        g2d.setFont(regularFont);
        drawCenteredText(g2d, "Số 22,Thường Thạnh,Cái Răng,CT", centerX, y);
        y += yShift;
        drawCenteredText(g2d, "ĐT: 0123456789", centerX, y);
        y += yShift;
        drawCenteredText(g2d, "Facebook: Shop Giày Balance", centerX, y);
        y += yShift;

        // Lời cảm ơn
        g2d.setFont(boldFont);
        drawCenteredText(g2d, "CẢM ƠN QUÝ KHÁCH!", centerX, y);
        y += yShift;
        drawCenteredText(g2d, "HẸN GẶP LẠI!", centerX, y);
    }

// Phương thức căn chỉnh text theo chiều ngang
    private void drawCenteredText(Graphics2D g2d, String text, int centerX, int y) {
        FontMetrics metrics = g2d.getFontMetrics();
        int textWidth = metrics.stringWidth(text);
        g2d.drawString(text, centerX - (textWidth / 2), y);
    }

// Vẽ văn bản tự động xuống dòng
    private void drawMultilineText(Graphics2D g2d, String text, int x, int y, int maxWidth, int lineHeight) {
        FontMetrics metrics = g2d.getFontMetrics();
        int lineStart = 0;
        int lineEnd;

        while (lineStart < text.length()) {
            lineEnd = lineStart;
            int lineWidth = 0;

            while (lineEnd < text.length() && (lineWidth + metrics.charWidth(text.charAt(lineEnd)) <= maxWidth)) {
                lineWidth += metrics.charWidth(text.charAt(lineEnd));
                lineEnd++;
            }

            // Nếu không xuống hết, quay lại từ dấu cách gần nhất
            if (lineEnd < text.length() && text.charAt(lineEnd) != ' ') {
                int lastSpace = text.lastIndexOf(' ', lineEnd);
                if (lastSpace > lineStart) {
                    lineEnd = lastSpace;
                }
            }

            // Vẽ dòng
            g2d.drawString(text.substring(lineStart, lineEnd).trim(), x, y);
            y += lineHeight; // Xuống dòng

            // Di chuyển sang phần tiếp theo
            lineStart = lineEnd + 1;
        }
    }

// Định dạng văn bản để giới hạn ký tự hiển thị
    private String formatText(String text, int maxChars) {
        return text.length() > maxChars ? text.substring(0, maxChars - 3) + "..." : text;
    }
    
}
