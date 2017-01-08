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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Melh
 */
public class Histogram extends JPanel {

    private int[] global;
    private int[] red;
    private int[] blue;
    private int[] green;

    public Histogram() {
        global = new int[255];
        red = new int[255];
        blue = new int[255];
        green = new int[255];
    }

    /**
     * The function will use ImageJ's tool for Histogram to draw one on the
     * application's GUI
     *
     * @param g
     * @param path This'll be changed
     * @throws IOException
     */
    public void histogram(String path) throws IOException {
        ImagePlus ip = new ImagePlus("image", ImageIO.read(new File(path)));

        HistogramWindow hw = new HistogramWindow(ip);
        int[] hip = hw.getHistogram();

        for (int i = 0; i < hip.length; i++){
            if (hip[i] > 30000) hip[i] = 30000;
        }
        
        setGlobal(hip);

    }

    /**
     * This method will generate the red Histogram in an int array to draw it
     * when the Histogram generation is done
     *
     * @param path
     * @throws IOException
     */
    public void redhistogram(String path) throws IOException {
        BufferedImage bi = ImageIO.read(new File(path));
        int[] redhist = new int[256];

        for (int i = 0; i < 256; i++) {
            redhist[i] = 0;
        }

        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                int pixel = bi.getRGB(i, j);
                int red = (pixel & 0x00ff0000) >> 16;
                if (red > 50000) red = 50000;
                redhist[red] += 1;
                if (redhist[red] > 50000) redhist[red] = 30000;
            }
        }

        setRed(redhist);
    }

    /**
     * This method will generate the green Histogram in an int array to draw it
     * when the Histogram generation is done
     *
     * @param path
     * @throws IOException
     */
    public void greenhistogram(String path) throws IOException {

        BufferedImage bi = ImageIO.read(new File(path));
        int[] greenhist = new int[256];

        for (int i = 0; i < 256; i++) {
            greenhist[i] = 0;
        }

        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                int pixel = bi.getRGB(i, j);
                int green = (pixel & 0x0000ff00) >> 8;
                greenhist[green] += 1;
                if (greenhist[green] > 50000) greenhist[green] = 30000;
            }
        }

        setGreen(greenhist);

    }

    /**
     * This method will generate the blue Histogram in an int array to draw it
     * when the Histogram generation is done
     *
     * @param g
     * @param path
     * @throws IOException
     */
    public void bluehistogram(String path) throws IOException {

        BufferedImage bi = ImageIO.read(new File(path));
        int[] bluehist = new int[256];

        for (int i = 0; i < 256; i++) {
            bluehist[i] = 0;
        }

        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                int pixel = bi.getRGB(i, j);
                int blue = (pixel & 0x000000ff);
                bluehist[blue] += 1;
                if (bluehist[blue] > 50000) bluehist[blue] = 30000;
                
            }
        }

        setBlue(bluehist);

    }

    public int[] getGlobal() {
        return global;
    }

    public void setGlobal(int[] global) {
        this.global = global;
    }

    public int[] getRed() {
        return red;
    }

    public void setRed(int[] red) {
        this.red = red;
    }

    public int[] getBlue() {
        return blue;
    }

    public void setBlue(int[] blue) {
        this.blue = blue;
    }

    public int[] getGreen() {
        return green;
    }

    public void setGreen(int[] green) {
        this.green = green;
    }
    
//     public void drawHistogram(int pos, Graphics gra) {
//        Histogram histogramtemp = listHist.get(pos);
//        //Graphics g = jPanel10.getGraphics();
//        Graphics g = gra;
//        Graphics2D g2d = (Graphics2D) g;
//        g.translate(20, 100);
//        GradientPaint gp;
//
//        for (int i = 0; i < 256; i++) {
//
//            
//            
//            g.setColor(Color.black);
//            g.drawLine(i, 0, i, -(histogramtemp.getGlobal())[i] / 300);
//            g.drawLine(i + 300, 0, i + 300, -(histogramtemp.getRed())[i] / 300);
//            g.drawLine(i, 200, i, (-(histogramtemp.getGreen())[i] / 300) + 200);
//            g.drawLine(i + 300, 200, i + 300, (-(histogramtemp.getBlue())[i] / 300) + 200);
//
//            gp = new GradientPaint(30, 10, Color.white, histogramtemp.getGlobal().length, 0, Color.black);
//            g2d.setPaint(gp);
//            g2d.fillRect(0, 10, histogramtemp.getGlobal().length, 10);
//
//            gp = new GradientPaint(300, 10, Color.white, histogramtemp.getGlobal().length + 300, 0, Color.red);
//            g2d.setPaint(gp);
//            g2d.fillRect(300, 10, histogramtemp.getGlobal().length, 10);
//
//            gp = new GradientPaint(30, 210, Color.white, histogramtemp.getGlobal().length, 210, Color.green);
//            g2d.setPaint(gp);
//            g2d.fillRect(0, 210, histogramtemp.getGlobal().length, 10);
//
//            gp = new GradientPaint(300, 10, Color.white, histogramtemp.getGlobal().length + 300, 0, Color.blue);
//            g2d.setPaint(gp);
//            g2d.fillRect(300, 210, histogramtemp.getGlobal().length, 10);
//
//        }
//        //repaint();
//        //revalidate();
//        g.dispose();
//    }

}
