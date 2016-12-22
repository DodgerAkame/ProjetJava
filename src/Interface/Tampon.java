/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import core.Processor;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Melh
 */
public class Tampon extends JPanel{

   
    private ArrayList<String> adr;
    private ArrayList<ImageIcon> img;
    private int w, bin, h,p,a,b;
    private String color, name;
    private int conv[];
    //private JButton bubu = new javax.swing.JButton();

    public Tampon(){
               Interface_1 i = new Interface_1();
        adr = i.getDropPane1().getAdresse2();
            a = (int) Math.ceil(Math.sqrt((double)adr.size()));
            b = (int) Math.floor(Math.sqrt((double)adr.size()));
        importFiles();

 }
    
   public void eva(){

   } 
    
     protected void importFiles() {
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    //adresse.clear();
                    paint(a, b, img);
                    revalidate();
                }
 
            };
            SwingUtilities.invokeLater(run);
        }
      public void paint(int a, int b, ArrayList<ImageIcon> img) {      
        setLayout(new GridLayout(b,a,0,0));
                int ie =0;
                        System.out.println("2. nb layout : " + a*b+ ", x : " +a+ ", y : "+ b);
                        for(ImageIcon i : img){
                        JButton bubu = new JButton(i);
                        Rectangle r = new Rectangle(1024/a, 1024/b);
                        bubu.setBounds(r);
                        bubu.setName(Integer.toString(ie));
                        add(bubu);
                        ie++;
                        }       
    }
     @Override
    public Dimension getPreferredSize() {
         return new Dimension(500, 350); //To change body of generated methods, choose Tools | Templates.
    }

 public ArrayList<String> getAdr() {
        return adr;
    }

    public void setAdr(ArrayList<String> adr) {
        this.adr = adr;
    }

    public ArrayList<ImageIcon> getImg() {
        return img;
    }

    public void setImg(ArrayList<ImageIcon> img) {
        this.img = img;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getBin() {
        return bin;
    }

    public void setBin(int bin) {
        this.bin = bin;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getConv() {
        return conv;
    }

    public void setConv(int[] conv) {
        this.conv = conv;
    }

   
    
}
