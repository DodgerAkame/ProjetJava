/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import fftprocess.FFT;
import fftprocess.InverseFFT;
import fftprocess.TwoDArray;
import ij.ImagePlus;
import ij.LookUpTable;
import ij.gui.HistogramWindow;
import ij.plugin.ChannelSplitter;

import ij.plugin.filter.Convolver;
import ij.process.FHT;
import ij.process.ImageProcessor;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.LookupOp;
import java.awt.image.RescaleOp;
import java.awt.image.ShortLookupTable;
import java.io.File;
import java.io.IOException;
<<<<<<< HEAD
import javafx.scene.chart.BubbleChart;
=======
>>>>>>> master
import javax.imageio.ImageIO;

/**
 *
 * @author dodger
 */
public class Processor {

    private boolean is8bitgray = false;
    private boolean isFrequency = false;

    public Processor() {

    }

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
    public void splitRGB(Graphics g, String path, String channel) throws IOException {
        if (!is8bitgray || !isFrequency) {
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
    }

    /**
     * This method will apply a 3x3 convolution matrix on the picture with
     * ImageJ's convolution tool
     *
     * @param g
     * @param path
     * @param matrix input type is like (1, 2, 3, 4, 5, 6, 7, 8, 9)
     * @throws IOException
     */
    public void convolution3(Graphics g, String path, int[] matrix) throws IOException {
        if (!is8bitgray && !isFrequency) {
            ImagePlus ip = new ImagePlus("image", ImageIO.read(new File(path)));
            ImageProcessor iproc = ip.getProcessor();

            iproc.convolve3x3(matrix);
            Image img = iproc.getBufferedImage();

            g.drawImage(img, 0, 0, ip);
        }
    }

    /**
     * This method will convert the picture in 8-bit grayscale if not done yet,
     * and binarize the picture with ImageJ's method
     *
     * @param g
     * @param path
     * @param trigger has to be between 0 and 255 included. If not, will be
     * reaffected to 127
     * @throws IOException
     */
    public void binarize(Graphics g, String path, int trigger) throws IOException {
        if (!is8bitgray || !isFrequency) {
            is8bitgray = true;
        }
        if (trigger > 255 || trigger < 0) {
            trigger = 127;
        }

        ImagePlus ip = new ImagePlus("image", ImageIO.read(new File(path)));
        ImageProcessor iproc = ip.getProcessor();
        ImageProcessor grayproc = iproc.convertToByte(true);
        grayproc.threshold(trigger);
        Image binary = grayproc.getBufferedImage();

        g.drawImage(binary, 0, 0, ip);

    }

    public void doDFT(Graphics g, String path) throws IOException {

        BufferedImage img = ImageIO.read(new File("src" + File.separatorChar + "image.jpg"));
        int size = 2;
        while (size < img.getHeight() && size < img.getWidth()) {
            size = size * 2;
        }
        BufferedImage bi = new BufferedImage(size, size, BufferedImage.TYPE_BYTE_GRAY);
        bi.getGraphics().drawImage(img, 0, 0, null);
        int[] pixels = new int[bi.getHeight() * bi.getWidth()];
        int counter = 0;
        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                pixels[counter] = bi.getRGB(i, j);
                counter++;
            }
        }

        FFT fft = new FFT(pixels, bi.getWidth(), bi.getHeight());
        int[] results = fft.getPixels();

        BufferedImage fftimg = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        counter = 0;
        for (int i = 0; i < fftimg.getWidth(); i++) {
            for (int j = 0; j < fftimg.getHeight(); j++) {
                fftimg.setRGB(i, j, results[counter]);
                counter++;
            }
        }

        g.drawImage(fftimg.getScaledInstance(512, 512, BufferedImage.SCALE_REPLICATE), 0, 0, null);
        isFrequency = true;
    }

    //TODO Problème ici
    public void doIDFT(Graphics g, String path, int w, int h) throws IOException {
        if (!isFrequency) {
            BufferedImage bi = ImageIO.read(new File(path));
            int[] pixels = new int[bi.getHeight() * bi.getWidth()];
            int counter = 0;
            for (int i = 0; i < bi.getWidth(); i++) {
                for (int j = 0; j < bi.getHeight(); j++) {
                    pixels[counter] = bi.getRGB(i, j);
                    counter++;
                }
            }

            TwoDArray tda = new TwoDArray(pixels, w, h);
            InverseFFT ifft = new InverseFFT();
            TwoDArray output = new TwoDArray();
            output = ifft.transform(tda);
            int[] outputInt = new int[bi.getHeight() * bi.getWidth()];
            outputInt = ifft.getPixels(output);

            BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            counter = 0;
            for (int i = 0; i < result.getWidth(); i++) {
                for (int j = 0; j < result.getHeight(); j++) {
                    result.setRGB(i, j, outputInt[counter]);
                    counter++;
                }
            }

            g.drawImage(result, 0, 0, null);
            isFrequency = false;
        }
    }

    public void contrast(Graphics g, String path, String how) throws IOException {
        if (!is8bitgray || !isFrequency) {

            BufferedImage img = ImageIO.read(new File(path));
            float contrastFactor = 0;
            if (how.equals("plus")) {
                contrastFactor = 15;
            } else if (how.equals("minus")) {
                contrastFactor = -15;
            }

            RescaleOp op = new RescaleOp(1f, contrastFactor, null);
            img = op.filter(img, img);
            g.drawImage(img, 0, 0, null);
        }
    }

    public void brightness(Graphics g, String path, String how) throws IOException {
        if (!is8bitgray || !isFrequency) {
            BufferedImage img = ImageIO.read(new File(path));
            float scaleFactor = 0;
            if (how.equals("plus")) {
                scaleFactor = 1.1f;
            } else if (how.equals("minus")) {
                scaleFactor = 0.9f;
            }

            RescaleOp op = new RescaleOp(scaleFactor, 0, null);
            img = op.filter(img, img);
            g.drawImage(img, 0, 0, null);
        }

    }

<<<<<<< HEAD
    public void posterize(Graphics g, String path, int level) throws IOException {

        BufferedImage bi = ImageIO.read(new File(path));

        int[] pixels = new int[bi.getHeight() * bi.getWidth()];
        int counter = 0;
        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                pixels[counter] = bi.getRGB(i, j);
                counter++;
            }
        }

        int[] levels = new int[level + 1];
        int reste = 255 % level;
        for (int i = 0; i < level; i++) {
            levels[i] = (i + 1) * ((255 - reste) / level);
        }
        levels[level] = 255;

        for (int i = 0; i < pixels.length; i++) {
            int pixel = pixels[i];
            int red = (pixel & 0x00ff0000) >> 16;
            int green = (pixel & 0x0000ff00) >> 8;
            int blue = (pixel & 0x000000ff);

            boolean redChanged = false;
            boolean greenChanged = false;
            boolean blueChanged = false;
            for (int j = 0; j < level; j++) {
                if (red < levels[j] && !redChanged) {
                    red = (int) Math.floor(levels[j] / 2);
                    redChanged = true;
                }
                if (green < levels[j] && !greenChanged) {
                    green = (int) Math.floor(levels[j] / 2);
                    greenChanged = true;
                }
                if (blue < levels[j] && !blueChanged) {
                    blue = (int) Math.floor(levels[j] / 2);
                    blueChanged = true;
                }

                if (redChanged && greenChanged && blueChanged) {
                    break;
                }
            }

            pixel = (0xff000000 | red << 16 | green << 8 | blue);
            pixels[i] = pixel;

        }

        counter = 0;
        BufferedImage img = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                img.setRGB(i, j, pixels[counter]);
                counter++;
            }
        }

        System.out.println("J'ai fini");
        g.drawImage(img, 0, 0, null);
=======
    public void posterize(Graphics g, String path) throws IOException {
        short[] posterize = new short[256];
        for (int i = 0; i < 256; i++) {
            posterize[i] = (short) (i - (i % 32));
        }
        BufferedImageOp posterizeOp
                = new LookupOp(new ShortLookupTable(0, posterize), null);
        BufferedImage bi = ImageIO.read(new File(path));
        
        BufferedImage result = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_RGB);
        result = posterizeOp.filter(bi, null);

        g.drawImage(result, 0, 0, null);
>>>>>>> master
    }

    //TODO regarder PDF pour fonctions restantes
}
