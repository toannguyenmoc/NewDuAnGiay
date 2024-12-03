/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sales.Utils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

/**
 *
 * @author GF63
 */
public class PanelRound extends JPanel {

    public int getRoundTopRight() {
        return roundTopRight;
    }

    public void setRoundTopRight(int roundTopRight) {
        this.roundTopRight = roundTopRight;
        repaint();

    }

    public int getRoundBottomleft() {
        return roundTopleft;
    }

    public void setRoundBottomleft(int roundBottomleft) {
        this.roundTopleft = roundBottomleft;
        repaint();

    }

    public int getRoundBottomRight() {
        return roundBottomRight;
    }

    public void setRoundBottomRight(int roundBottomRight) {
        this.roundBottomRight = roundBottomRight;
        repaint();
    }

    public int getRoundBottomLeft() {
        return roundBottomLeft;
    }

    public void setRoundBottomLeft(int roundBottomLeft) {
        this.roundBottomLeft = roundBottomLeft;
        repaint();
    }

    private int roundTopRight = 0;
    private int roundTopleft = 0;
    private int roundBottomLeft = 0;
    private int roundBottomRight = 0;

    public PanelRound() {
        setOpaque(false);
    }
@Override
protected void paintComponent(Graphics grphcs) {
    super.paintComponent(grphcs);

    Graphics2D g2 = (Graphics2D) grphcs.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setColor(getBackground());

    // Tạo vùng bo tròn
    Area area = new Area(createRoundTopLeft());
    if (roundTopRight > 0) {
        area.intersect(new Area(createRoundTopRight()));
    }
    if (roundBottomLeft > 0) {
        area.intersect(new Area(createRoundBotTomLeft()));
    }
    if (roundBottomRight > 0) {
        area.intersect(new Area(createRoundBotTomRight()));
    }

    g2.fill(area);
    g2.dispose();
}
 public Shape createRoundTopRight(){
        int width = getWidth();
        int heght = getHeight();
        int roundX = Math.min(width, roundTopRight);
        int roundY = Math.min(heght, roundTopRight);
        
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, heght, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(0, 0, width - roundX / 2, heght)));
        area.add(new Area( new Rectangle2D.Double(0, roundY / 2, width, heght - roundY / 2)));
        return area;
    }
    
    public Shape createRoundTopLeft(){
        int width = getWidth();
        int heght = getHeight();
        int roundX = Math.min(width, roundTopleft);
        int roundY = Math.min(heght, roundTopleft);
        
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, heght, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(roundX/2, 0, width - roundX / 2, heght)));
        area.add(new Area( new Rectangle2D.Double(0, roundY / 2, width, heght - roundY / 2)));
        return area;
    }
    
    public Shape createRoundBotTomLeft(){
        int width = getWidth();
        int heght = getHeight();
        int roundX = Math.min(width, roundBottomLeft);
        int roundY = Math.min(heght, roundBottomLeft);
        
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, heght, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(roundX/2, 0, width - roundX / 2, heght)));
        area.add(new Area( new Rectangle2D.Double(0, 0, width, heght - roundY / 2)));
        return area;
    }
    
    public Shape createRoundBotTomRight(){
        int width = getWidth();
        int heght = getHeight();
        int roundX = Math.min(width, roundBottomRight);
        int roundY = Math.min(heght, roundBottomRight);
        
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, heght, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(0, 0, width - roundX / 2, heght)));
        area.add(new Area( new Rectangle2D.Double(0, 0, width, heght - roundY / 2)));
        return area;
    }
}
