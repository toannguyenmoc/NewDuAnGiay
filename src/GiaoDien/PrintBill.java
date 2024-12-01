package GiaoDien;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
            ArrayList<String> subtotal, double totalAmount, double taxAmount,
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

        // Nút in hóa đơn
        JPanel buttonPanel = new JPanel();
        JButton printButton = new JButton("Print");
        printButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Bill sent to printer!");
            // Thêm logic in thực tế nếu cần
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
            return new Dimension(400, 800); // Điều chỉnh kích thước nếu cần
        }
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
        int yShift = 20; // Khoảng cách dòng
        int maxWidth = 250; // Chiều rộng tối đa của nội dung
        int billWidth = 300; // Kích thước chiều rộng của bill
        int centerX = billWidth / 2; // Tâm ngang của hóa đơn

        // Font in đậm và kích thước lớn hơn cho tiêu đề
        Font boldFont = new Font("Monospaced", Font.BOLD, 12);
        Font regularFont = new Font("Monospaced", Font.PLAIN, 11);

        // Vẽ logo (nếu có)
        if (icon != null) {
            g2d.drawImage(icon.getImage(), centerX - 45, y, 90, 30, null);
            y += yShift + 30;
        }

        // Tiêu đề hóa đơn
        g2d.setFont(boldFont);
        drawCenteredText(g2d, "CỬA HÀNG NƯỚC UỐNG BALANCE", centerX, y);
        y += yShift;
        drawCenteredText(g2d, "---------------------------", centerX, y);
        y += yShift;
        drawCenteredText(g2d, "PHIẾU TẠM TÍNH", centerX, y);
        y += yShift;

        // Thông tin cơ bản
        g2d.setFont(regularFont);
        drawMultilineText(g2d, "Ngày: " + date, 10, y, maxWidth, yShift);
        y += yShift;
        drawMultilineText(g2d, "Số hóa đơn: " + invoiceNumber, 10, y, maxWidth, yShift);
        y += yShift;
        drawMultilineText(g2d, "Khách hàng: Khách mua lẻ", 10, y, maxWidth, yShift);
        y += yShift;
        drawMultilineText(g2d, "---------------------------", 10, y, maxWidth, yShift);
        y += yShift;

        // Phần tiêu đề sản phẩm
        g2d.setFont(boldFont);
        drawMultilineText(g2d, "STT | Sản Phẩm", 10, y, maxWidth, yShift);
        y += yShift;

        // Chi tiết sản phẩm
        g2d.setFont(regularFont);
        for (int i = 0; i < itemName.size(); i++) {
            drawMultilineText(g2d, (i + 1) + "   " + formatText(itemName.get(i), 14)
                    + "   " + quantity.get(i)
                    + " X  " + String.format("%.0f", subtotal.get(i)), 10, y, maxWidth, yShift);
            y += yShift;
        }
        drawMultilineText(g2d, "---------------------------", 10, y, maxWidth, yShift);
        y += yShift;

        // Tổng tiền
        g2d.setFont(boldFont);
        drawMultilineText(g2d, "Tổng cộng: " + String.format("%.0f", totalAmount), 10, y, maxWidth, yShift);
        y += yShift;

        // Ghi chú
        g2d.setFont(regularFont);
        drawMultilineText(g2d, "* Ghi chú:", 10, y, maxWidth, yShift);
        y += yShift;
        drawMultilineText(g2d, "1. Giá đã bao gồm thuế VAT.", 10, y, maxWidth, yShift);
        y += yShift;
        drawMultilineText(g2d, "2. Vui lòng giữ hóa đơn khi đổi trả.", 10, y, maxWidth, yShift);
        y += yShift;

        // Lời cảm ơn
        g2d.setFont(boldFont);
        drawCenteredText(g2d, "CẢM ƠN QUÝ KHÁCH!", centerX, y);
        y += yShift;
        drawCenteredText(g2d, "HẸN GẶP LẠI!", centerX, y);
    }

// Vẽ văn bản căn giữa
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

    // Phương thức main để chạy thử
    public static void main(String[] args) {
        ArrayList<String> itemName = new ArrayList<>();
        ArrayList<String> quantity = new ArrayList<>();
        ArrayList<String> itemPrice = new ArrayList<>();
        ArrayList<String> subtotal = new ArrayList<>();

        // Thêm dữ liệu mẫu
        itemName.add("Giày thể thao");
        quantity.add("2");
        itemPrice.add("500000");
        subtotal.add("1000000");
        itemName.add("Giày thể thao");
        quantity.add("2");
        itemPrice.add("500000");
        subtotal.add("1000000");

        double totalAmount = 1000000;
        double taxAmount = 100000;
        String date = "01/12/2024 10:00:00";
        String invoiceNumber = "HD123456";

        new PrintBill(itemName, quantity, itemPrice, subtotal, totalAmount, taxAmount, date, invoiceNumber);
    }
}
