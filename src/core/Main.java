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
            g.translate(0, 150);

            try {
                //p.histogram(g, "src" + File.separatorChar + "image.jpg");
                //p.redhistogram(g, "src" + File.separatorChar + "image.jpg");
                //p.greenhistogram(g, "src" + File.separatorChar + "image.jpg");
                //p.bluehistogram(g, "src" + File.separatorChar + "image.jpg");
                //p.spiltRGB(g, "src" + File.separatorChar + "image.jpg", "BLUE");
                int[] matrix = new int[9];
                matrix[0] = -1;
                matrix[1] = -1;
                matrix[2] = -1;
                matrix[3] = -1;
                matrix[4] = 24;
                matrix[5] = -1;
                matrix[6] = -1;
                matrix[7] = -1;
                matrix[8] = -1;
                p.convolution3(g, "src" + File.separatorChar + "image.jpg", matrix);
            } catch (Exception e) {
                e.printStackTrace();
            }

            g.dispose();

        }
    }

    public static void main(String args[]) {
        new Main();
    }

}
