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
    private String name;
    private int a,b;
    //private JButton bubu = new javax.swing.JButton();

    public Tampon(){
        Interface_1 i = new Interface_1();
        adr = i.getDropPane1().getAdresse();
            a = (int) Math.ceil(Math.sqrt((double)adr.size()));
            b = (int) Math.floor(Math.sqrt((double)adr.size())); 
        name = i.getDropPane1().getEffect();
            System.out.println(a+" "+b);
        System.out.println("name is: "+name);
        Processor process = new Processor();
        try {
            img = process.evaluate(a,b,adr, name);
        } catch (IOException ex) {
            Logger.getLogger(Tampon.class.getName()).log(Level.SEVERE, null, ex);
        }
        importFiles();
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
                        //bubu.setVisible(Boolean.FALSE);
                        //toto.add(bubu);
                        add(bubu);
                        ie++;
                        }       
    }
     @Override
    public Dimension getPreferredSize() {
         return new Dimension(500, 350); //To change body of generated methods, choose Tools | Templates.
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public void setA(int a) {
        this.a = a;
    }

    public void setB(int b) {
        this.b = b;
    }

    public void setAdr(ArrayList<String> adr) {
        this.adr = adr;
    }

    public void setName(String name) {
        this.name = name;
    }
    
  

    public ArrayList<String> getAdr() {
        return adr;
    }

    public String getName() {
        return name;
    }

}
