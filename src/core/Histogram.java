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
public class Histogram {

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
                redhist[red] += 1;
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

}
