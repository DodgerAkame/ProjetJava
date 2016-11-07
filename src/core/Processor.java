/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import ij.ImagePlus;
import ij.gui.HistogramWindow;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author dodger
 */
public class Processor {

    public static void histogram(Graphics g, String path) throws IOException {
        ImagePlus ip = new ImagePlus();
        ip = new ImagePlus("image", ImageIO.read(new File(path)));
        
        HistogramWindow hw = new HistogramWindow(ip);
        int[] hip = hw.getHistogram();

        int max = 0;

        for (int i = 0; i < hip.length; i++) {
            g.drawLine(i + 30, 0, i + 30, -hip[i] / 100);
            if (hip[i] > max) {
                max = hip[i];
            }

        }
        
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gp = new GradientPaint(30, 10, Color.white, hip.length, 0, Color.black);

        g2d.setPaint(gp);
        g2d.fillRect(30, 10, hip.length, 10);

        GradientPaint gp2 = new GradientPaint(0, 0, Color.white, 10, -max / 100, Color.black);
        g2d.setPaint(gp2);
        g2d.fillRect(10, -max / 100, 10, max / 100);
        
       

        g.dispose();
    }

}
