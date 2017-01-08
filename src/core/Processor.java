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
import ij.plugin.ChannelSplitter;
import ij.process.ImageProcessor;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Melh
 */
public class Processor {

    private ArrayList<ImageIcon> image;

    public ArrayList<ImageIcon> getImage() {
        return image;
    }

    public Processor() {
    }

    public ArrayList<ImageIcon> evaluate(int a, int b, ArrayList<String> adr, String name, String color, int conv[], int bin, int w, int h, int p, float stepbri, float stepcon) throws IOException {//int b, int c
        ArrayList<ImageIcon> image2 = new ArrayList<>();
        for (int j = 0; j < adr.size(); j++) {

            ImageIcon _icon = new ImageIcon(new ImageIcon(adr.get(j)).getImage().getScaledInstance(1024 / a, 1024 / b, Image.SCALE_FAST));
            switch (name) {
                case "splitRGB":
                    _icon = splitRGB(adr.get(j), color);
                    break;
                case "convolution3":
                    _icon = convolution3(adr.get(j), conv);
                    break;
                case "binarize":
                    _icon = binarize(adr.get(j), bin);
                    break;
                case "doDFT":
                    _icon = doDFT(adr.get(j));
                    break;
                case "doIDFT":
                    _icon = doIDFT(adr.get(j), w, h);
                    break;
                case "posterize":
                    _icon = posterize(adr.get(j), p);
                    break;
                case "brightness":
                    _icon = brightness(adr.get(j), stepbri);
                    break;
                case "contrast":
                    _icon = contrast(adr.get(j), stepcon);
                    break;
            }
            ImageIcon _icon2 = new ImageIcon(new ImageIcon(_icon.getImage()).getImage().getScaledInstance(1024 / a, 1024 / b, Image.SCALE_FAST));
            image2.add(_icon2);

        }
        return image2;
    }

    public ImageIcon splitRGB(String path, String channel) {
        try {

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

            return new ImageIcon(img);
        } catch (IOException e) {
            return new ImageIcon();
        }
    }

    /**
     * This method will apply a 3x3 convolution matrix on the picture with
     * ImageJ's convolution tool
     *
     * @param g
     * @param path
     * @param matrix input type is like (1, 2, 3, 4, 5, 6, 7, 8, 9)
     * @return
     * @throws IOException
     */
    public ImageIcon convolution3(String path, int[] matrix) {
        try {

            ImagePlus ip = new ImagePlus("image", ImageIO.read(new File(path)));
            ImageProcessor iproc = ip.getProcessor();

            iproc.convolve3x3(matrix);
            Image img = iproc.getBufferedImage();

            return new ImageIcon(img);

        } catch (IOException e) {
            return new ImageIcon();
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
    public ImageIcon binarize(String path, int trigger) {
        try {

            if (trigger > 255 || trigger < 0) {
                trigger = 127;
            }

            ImagePlus ip = new ImagePlus("image", ImageIO.read(new File(path)));
            ImageProcessor iproc = ip.getProcessor();
            ImageProcessor grayproc = iproc.convertToByte(true);
            grayproc.threshold(trigger);
            Image binary = grayproc.getBufferedImage();

            return new ImageIcon(binary);
        } catch (IOException e) {
            return new ImageIcon();
        }
    }

    public ImageIcon doDFT(String path) {
        try {

            BufferedImage img = ImageIO.read(new File(path));
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

            return new ImageIcon(fftimg);

        } catch (IOException e) {
            return new ImageIcon();
        }
    }

    //TODO Problème ici
    public ImageIcon doIDFT(String path, int w, int h) {
        try {

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

            return new ImageIcon(result);

        } catch (Exception e) {
            e.printStackTrace();
            return new ImageIcon();
        }
    }
//TODO slider not a button like bubu, bubu the button and not bubu the slider.

    public ImageIcon contrast(String path, float step) {
        try {
            BufferedImage img = ImageIO.read(new File(path));
            BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    int pixel = img.getRGB(i, j);
                    int red = (pixel & 0x00ff0000) >> 16;
                    int green = (pixel & 0x0000ff00) >> 8;
                    int blue = (pixel & 0x000000ff);

                    int newRed = (int) (step * (red - 128) + 128);
                    int newGreen = (int) (step * (green - 128) + 128);
                    int newBlue = (int) (step * (blue - 128) + 128);

                    int newPixel = (0xff000000 | newRed << 16 | newGreen << 8 | newBlue);
                    result.setRGB(i, j, newPixel);
                }
            }
            return new ImageIcon(result);
        } catch (Exception e) {
            return new ImageIcon();
        }
    }

    public ImageIcon brightness(String path, float step) throws IOException {
        try {
            BufferedImage img = ImageIO.read(new File(path));

            RescaleOp op = new RescaleOp(1f, step, null);
            img = op.filter(img, img);
           
            return new ImageIcon(img);
        } catch (Exception e) {
            return new ImageIcon();
        }
    }

    public ImageIcon posterize(String path, int level) {
        try {

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

            return new ImageIcon(img);
        } catch (Exception e) {
            return new ImageIcon();
        }

    }
}
