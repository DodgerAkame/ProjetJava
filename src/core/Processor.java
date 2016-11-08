/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import ij.ImagePlus;
import ij.gui.HistogramWindow;
import ij.plugin.ChannelSplitter;
import ij.plugin.filter.Convolver;
import ij.process.ImageProcessor;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author dodger
 */
public class Processor {

    public Processor() {

    }

    public void histogram(Graphics g, String path) throws IOException {
        ImagePlus ip = new ImagePlus("image", ImageIO.read(new File(path)));

        HistogramWindow hw = new HistogramWindow(ip);
        int[] hip = hw.getHistogram();

        System.out.println(hip.length);

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

    }

    public void redhistogram(Graphics g, String path) throws IOException {
        BufferedImage bi = ImageIO.read(new File(path));
        int[] redhist = new int[256];

        for (int i = 0; i < 256; i++) {
            redhist[i] = 0;
        }

        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                int pixel = bi.getRGB(i, j);
                int red = (pixel & 0x00ff0000) >> 16;
                redhist[red] += 1;
            }
        }

        for (int i = 0; i < 256; i++) {
            g.drawLine(i, 0, i, -redhist[i] / 100);
        }

    }

    public void greenhistogram(Graphics g, String path) throws IOException {
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
            }
        }

        for (int i = 0; i < 256; i++) {
            g.drawLine(i, 0, i, -greenhist[i] / 100);
        }

    }

    public void bluehistogram(Graphics g, String path) throws IOException {
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
            }
        }

        for (int i = 0; i < 256; i++) {
            g.drawLine(i, 0, i, -bluehist[i] / 100);
        }

    }

    public void spiltRGB(Graphics g, String path, String channel) throws IOException {
        ImagePlus ip = new ImagePlus("image", ImageIO.read(new File(path)));
        ImagePlus[] channels = ChannelSplitter.split(ip);
        Image img;
        if (channel.equals("RED")) {
            img = channels[0].getImage();
        } else if (channel.equals("GREEN")) {
            img = channels[1].getImage();
        } else {
            img = channels[2].getImage();
        }

        g.drawImage(img, 0, 0, ip);
    }
    
    public void convolution3(Graphics g, String path, int[] matrix) throws IOException{
        ImagePlus ip = new ImagePlus("image", ImageIO.read(new File(path)));
        ImageProcessor iproc = ip.getProcessor();

        iproc.convolve3x3(matrix);
        Image img = iproc.getBufferedImage();
        
        g.drawImage(img, 0, 0, ip);
    }

}
