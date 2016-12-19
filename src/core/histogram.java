/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import ij.ImagePlus;
import ij.gui.HistogramWindow;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Melh
 */
public class histogram {
    private boolean is8bitgray = false;
    private boolean isFrequency = false;
        /**
     * The function will use ImageJ's tool for histogram to draw one on the
     * application's GUI
     *
     * @param g
     * @param path This'll be changed
     * @throws IOException
     */
    public void histogram(Graphics g, String path) throws IOException {
        ImagePlus ip = new ImagePlus("image", ImageIO.read(new File(path)));

        HistogramWindow hw = new HistogramWindow(ip);
        int[] hip = hw.getHistogram();

        int max = 0;

        for (int i = 0; i < hip.length; i++) {
            g.drawLine(i + 30, 0, i + 30, -hip[i] / 100);
            if (hip[i] > max) {
                max = hip[i];
            }

        }

        /*Graphics2D g2d = (Graphics2D) g;
        GradientPaint gp = new GradientPaint(30, 10, Color.white, hip.length, 0, Color.black);

        g2d.setPaint(gp);
        g2d.fillRect(30, 10, hip.length, 10);

        GradientPaint gp2 = new GradientPaint(0, 0, Color.white, 10, -max / 100, Color.black);
        g2d.setPaint(gp2);
        g2d.fillRect(10, -max / 100, 10, max / 100);*/
    }

    /**
     * This method will generate the red histogram in an int array to draw it
     * when the histogram generation is done
     *
     * @param g
     * @param path
     * @throws IOException
     */
    public void redhistogram(Graphics g, String path) throws IOException {
        if (!is8bitgray || !isFrequency) {
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

    }

    /**
     * This method will generate the green histogram in an int array to draw it
     * when the histogram generation is done
     *
     * @param g
     * @param path
     * @throws IOException
     */
    public void greenhistogram(Graphics g, String path) throws IOException {
        if (!is8bitgray || !isFrequency) {
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

    }

    /**
     * This method will generate the blue histogram in an int array to draw it
     * when the histogram generation is done
     *
     * @param g
     * @param path
     * @throws IOException
     */
    public void bluehistogram(Graphics g, String path) throws IOException {
        if (!is8bitgray || !isFrequency) {
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
    }

    /**
     * This method will extract the subchannel of the picture indicated with the
     * argument channel, and display it in grayscale with ImageJ's splitter
     *
     * @param g
     * @param path
     * @param channel should be RED, GREEN or BLUE
     * @throws IOException
     */
}