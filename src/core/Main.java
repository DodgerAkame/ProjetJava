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
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author dodger
 */
public class Main extends JFrame {

    private Processor p = new Processor();

    public Main() {
        super("My Frame");

        //you can set the content pane of the frame
        //to your custom class.
        setContentPane(new DrawPane());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(400, 400);

        setVisible(true);
    }

    //create a component that you can actually draw on.
    class DrawPane extends JPanel {

        public void paintComponent(Graphics g) {
//          
            g.translate(0, 10);

            try {
                //p.histogram(g, "src" + File.separatorChar + "image.jpg");
                //p.redhistogram(g, "src" + File.separatorChar + "image.jpg");
                //p.splitRGB(g, "src" + File.separatorChar + "image.jpg","RED");
                //p.binarize(g, "src" + File.separatorChar + "image.jpg", 127);
                //p.doDFT(g, "src" + File.separatorChar + "image.jpg");
                //p.brightness(g, "src" + File.separatorChar + "image.jpg", "plus");
                //p.contrast(g, "src" + File.separatorChar + "image.jpg", "plus");
                //BufferedImage bi = ImageIO.read(new File("src" + File.separatorChar + "image.jpg"));
                //p.doIDFT(g, "src" + File.separatorChar +"fft_image.jpg", bi.getWidth(), bi.getHeight());
<<<<<<< HEAD
                p.posterize(g, "src" + File.separatorChar + "image.jpg", 5);
=======
                p.posterize(g, "src" + File.separatorChar + "image.jpg");
>>>>>>> master
            } catch (Exception e) {
                e.printStackTrace();
            }

<<<<<<< HEAD
           
=======
            System.out.println("Done.");
>>>>>>> master
            g.dispose();

        }
    }

    public static void main(String args[]) {
        new Main();
    }

}
