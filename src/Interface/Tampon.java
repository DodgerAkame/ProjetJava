/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import core.Processor;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Melh
 */
public class Tampon extends JPanel{

   
    
    private ArrayList<String> adr = new ArrayList<>();
    private String name;
    private int a,b;

    public Tampon(){
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
