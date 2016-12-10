/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import Interface.Interface_1;
import Interface.Tampon;
import fftprocess.FFT;
import fftprocess.InverseFFT;
import fftprocess.TwoDArray;
import ij.ImagePlus;
import ij.plugin.ChannelSplitter;

import ij.process.ImageProcessor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author dodger
 */
public class Processor extends JPanel {

    private boolean is8bitgray = false;
    private boolean isFrequency = false;
    private JLabel message;
    private JButton bubu;
    private ArrayList<ImageIcon> image;

    public ArrayList<ImageIcon> getImage() {
        return image;
    }

    public Processor(){
        message = new JLabel();
        Tampon t = new Tampon();
        String name = t.getName();
        ArrayList<String> adr = t.getAdr();
        try {
            image = evaluate(adr, name);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        paint(t.getA(), t.getB(), image);
        revalidate();
        repaint();
        System.out.println("j'ai fini le paint des " + image.size() + " éléments");

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 350); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList<ImageIcon> evaluate(ArrayList<String> adr, String name) throws IOException {
        ArrayList<ImageIcon> eval = new ArrayList<ImageIcon>();
        ImageIcon i = new ImageIcon();
        for (int j = 0; j < adr.size(); j++) {
            switch (name) {
                case "splitRGB":
                    i = splitRGB(adr.get(j), "RED");
                    break;
                case "convolution3":
                    int toto[] = {1, 2, 3, 4, 5, 6, 7, 8, 9};
                    i = convolution3(adr.get(j), toto);
                    break;
                case "binarize":
                    i = binarize(adr.get(j), 127);
                    break;
                case "doDFT":
                    i = doDFT(adr.get(j));
                    break;
                case "doIDFT":
                    i = doIDFT(adr.get(j), 5, 5);
                    break;
                case "posterize":
                    i = posterize(adr.get(j), 127);
                    break;

            }
            eval.add(i);
        }
        return eval;
    }

    public void paint(int a, int b, ArrayList<ImageIcon> img) {
        setLayout(new GridLayout(b, a, 0, 0));

        for (ImageIcon i : img) { //JLabel toto = new JLabel(icon);
            bubu = new JButton(i);
            Rectangle r = new Rectangle(1024 / a, 1024 / b);
            bubu.setBounds(r);
            //bubu.setVisible(Boolean.FALSE);
            //toto.add(bubu);
            add(bubu);
        }

    }

    public ImageIcon splitRGB(String path, String channel) {
        try {
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

                return new ImageIcon(img);
            }
            return new ImageIcon(ImageIO.read(new File(path)));
        } catch (Exception e) {
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
     * @throws IOException
     */
    public ImageIcon convolution3(String path, int[] matrix) {
        try {
            if (!is8bitgray && !isFrequency) {
                ImagePlus ip = new ImagePlus("image", ImageIO.read(new File(path)));
                ImageProcessor iproc = ip.getProcessor();

                iproc.convolve3x3(matrix);
                Image img = iproc.getBufferedImage();

                return new ImageIcon(img);
            }
            return new ImageIcon(ImageIO.read(new File(path)));
        } catch (Exception e) {
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
            if (!is8bitgray || !isFrequency) {
                is8bitgray = true;

                if (trigger > 255 || trigger < 0) {
                    trigger = 127;
                }

                ImagePlus ip = new ImagePlus("image", ImageIO.read(new File(path)));
                ImageProcessor iproc = ip.getProcessor();
                ImageProcessor grayproc = iproc.convertToByte(true);
                grayproc.threshold(trigger);
                Image binary = grayproc.getBufferedImage();

                return new ImageIcon(binary);
            }
            return new ImageIcon(ImageIO.read(new File(path)));
        } catch (Exception e) {
            return new ImageIcon();
        }
    }

    public ImageIcon doDFT(String path) {
        try {
            if (!isFrequency) {
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

                isFrequency = true;
                return new ImageIcon(fftimg);
            }
            return new ImageIcon(ImageIO.read(new File(path)));
        } catch (Exception e) {
            return new ImageIcon();
        }
    }

    //TODO Problème ici
    public ImageIcon doIDFT(String path, int w, int h) {
        try {
            if (isFrequency) {
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

                isFrequency = false;
                return new ImageIcon(result);
            }
            return new ImageIcon(ImageIO.read(new File(path)));
        } catch (Exception e) {
            return new ImageIcon();
        }
    }
//TODO slider not a button like bubu, bubu the button and not bubu the slider.
//    public ImageIcon contrast(String path, String how){
//       try{ if (!is8bitgray || !isFrequency) {
//
//            BufferedImage img = ImageIO.read(new File(path));
//            float contrastFactor = 0;
//            if (how.equals("plus")) {
//                contrastFactor = 15;
//            } else if (how.equals("minus")) {
//                contrastFactor = -15;
//            }
//
//            RescaleOp op = new RescaleOp(1f, contrastFactor, null);
//            img = op.filter(img, img);
//            return new ImageIcon(img);
//        }
//        return new ImageIcon(ImageIO.read(new File(path)));
//     } catch (Exception e){
//          return new ImageIcon(); 
//       }
//    }
//
//    public ImageIcon brightness(String path, String how) throws IOException {
//      try{  if (!is8bitgray || !isFrequency) {
//            BufferedImage img = ImageIO.read(new File(path));
//            float scaleFactor = 0;
//            if (how.equals("plus")) {
//                scaleFactor = 1.1f;
//            } else if (how.equals("minus")) {
//                scaleFactor = 0.9f;
//            }
//
//            RescaleOp op = new RescaleOp(scaleFactor, 0, null);
//            img = op.filter(img, img);
//            return new ImageIcon(img);
//        }
//        return new ImageIcon(ImageIO.read(new File(path)));
//           } catch (Exception e){
//          return new ImageIcon(); 
//}
//    }

    public ImageIcon posterize(String path, int level) {
        try {
            if (!is8bitgray || !isFrequency) {
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
            }
            return new ImageIcon(ImageIO.read(new File(path)));
        } catch (Exception e) {
            return new ImageIcon();
        }

    }
}
