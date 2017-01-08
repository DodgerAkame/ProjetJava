package view;
import com.sun.prism.j2d.J2DPipeline;
import core.Histogram;
import core.Metadata;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicArrowButton;
import org.w3c.dom.NamedNodeMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Melh
 */
public class DropPane extends JPanel implements ActionListener{

        private DropTarget dropTarget;
        private DropTargetHandler dropTargetHandler;
        private Point dragPoint;

        private boolean dragOver = false;
        private BufferedImage target;
        private JLabel message;  
 //       private javax.swing.JButton bubu;
        private static int aa = 0;
        private static int bb = 0;
        private static int sizeI = 0;
        private static int Size = 0;
        private static ArrayList<String> adresse = new ArrayList<String>();
        private static ArrayList<String> adresse2 = new ArrayList<String>();
        private static ArrayList<ImageIcon> imagef = new ArrayList<ImageIcon>();
        private ArrayList<JButton> listButton = new ArrayList<>();

    public static ArrayList<ImageIcon> getImagef() {
        return imagef;
    }

    public static ArrayList<String> getAdresse2() {
        return adresse2;
    }

    public static int getAa() {
        return aa;
    }

    public static int getBb() {
        return bb;
    }
 /**
     * constructor, 
     * 
     *
     
     */
        public DropPane() {
            message = new JLabel();
//            setLayout(new GridBagLayout());
//            message.setFont(message.getFont().deriveFont(Font.BOLD, 30));
//            add(message);


        }

         protected void Clearall() {
            Runnable run = new Runnable() {
                @Override
                public void run() {
                                adresse.clear();
            adresse2.clear();
            imagef.clear();
            removeAll();
            aa = 0;
            bb = 0;
            sizeI =0;
            Size = 0;
            revalidate();
            repaint();
                }
 
            };
            SwingUtilities.invokeLater(run);
        }

            
             /**
     * getter from var adresse
     */
        public static ArrayList<String> getAdresse() {
            return adresse;
        }
         /**
     * define the size of the frame of this class
     */
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(500, 350);
        }
 /**
     * getter droptarget
     * if drop target is null : instance of droptarget
     */
        protected DropTarget getMyDropTarget() {
            if (dropTarget == null) {
                dropTarget = new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, null);
            }
            return dropTarget;
        }
 /**
     * getter dropTargetHandler
     * if dropTargetHandler is null : instance of dropTargetHandler
     */
        protected DropTargetHandler getDropTargetHandler() {
            if (dropTargetHandler == null) {
                dropTargetHandler = new DropTargetHandler();
            }
            return dropTargetHandler;
        }

        @Override
        public void addNotify() {
            super.addNotify();
            try {
                getMyDropTarget().addDropTargetListener(getDropTargetHandler());
            } catch (TooManyListenersException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void removeNotify() {
            super.removeNotify();
            getMyDropTarget().removeDropTargetListener(getDropTargetHandler());
        }
 /**
     * create a frame with pink color when a drag is coming
     * @param g
     */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (dragOver) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(80, 0, 0, 30));
                g2d.fill(new Rectangle(getWidth(), getHeight()));
                if (dragPoint != null && target != null) {
                    int x = dragPoint.x - 12;
                    int y = dragPoint.y - 12;
                    g2d.drawImage(target, x, y, this);
                }
                g2d.dispose();
            }
        }
        

 /**
     * execution of calcul and analisys of the drop selection
     * @param files
     */        
        protected void importFiles(final List files) {
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    adresse.clear();
                    ArrayList<String> img = ListImage(files);                   
                    ArrayList<ImageIcon> image = list(img);
                    imagef = image;
                    ArrayList<Integer> calc = calcul(img.size());
                    paint(calc.get(0), calc.get(1), image);
                    revalidate();
                }
 
            };
            SwingUtilities.invokeLater(run);
        }
        
 /**
     * create a list with all path include in the drop files
     * @param files
     */        
        private ArrayList<String> ListImage(List files) {
             ListImage img = new ListImage(files);
             ArrayList<String> image;
             for(int i =0; i<img.getAdresse().size(); i++){
                 adresse.add(img.getAdresse().get(i));
                 adresse2.add(img.getAdresse().get(i));
             }
             image = adresse;
            return image;
                }
        
 /**
     * create a list of ImageIcon resizes and define a limit of memory use for images (500 Mbytes)
     * @param img
     */
        private ArrayList<ImageIcon> list(ArrayList<String> img) {
            
            ArrayList<ImageIcon> image = new ArrayList<>();
            int x = (int) Math.ceil(Math.sqrt((double)img.size()));
            int y = (int) Math.floor(Math.sqrt((double)img.size()));

                   for(int i =0; i< img.size(); i++){
                        ImageIcon temp = new ImageIcon(img.get(i));
                        ImageIcon _icon = new ImageIcon(temp.getImage().getScaledInstance((temp.getIconHeight())/x, (temp.getIconWidth())/y, Image.SCALE_REPLICATE));//);
                       
                       //ImageIcon _image = new ImageIcon(img.get(i));
                       Size = Size + (_icon.getIconHeight()*_icon.getIconWidth()*3);
                       
                       if(Size > 512000000){
                           //message.setText("Error Memory over 500Mo ");
                           int a = img.size()-image.size();
                            ImageIcon icon = new ImageIcon(temp.getImage().getScaledInstance((temp.getIconHeight())/x, (temp.getIconWidth())/y, Image.SCALE_AREA_AVERAGING));//);
                       JOptionPane.showMessageDialog(message, "Image "+img.get(i)+" and the "+a+" files next to this image can't be load cause : \r\n"+""+"Memory over 500 Mo", "Dialog" , 0, icon);
                           return image;
                       }else {
                       image.add(_icon);
                       }
                    }
                   return image;
        }
 /**
     * define and calcul the var aa and bb and update size 
     * @param size
     */
         private ArrayList<Integer> calcul(int size) {
             
             int sizef = sizeI + size;
             System.out.println("sizef : "+sizef);
            int a = (int) Math.ceil(Math.sqrt((double)sizef));
            int b = (int) Math.floor(Math.sqrt((double)sizef));
            ArrayList<Integer> calcul = new ArrayList<>();
            if(aa !=0 || bb !=0){
                if(Math.max(aa, a) == a){
                    aa = a;
                }
            if(Math.max(bb, b)== b){
                bb = b;
            }
            }
            if(aa ==0 && bb == 0){
                aa=a;
                bb=b;
            }
                sizeI = sizef;
                    calcul.add(aa);
                   calcul.add(bb);System.out.println(sizeI);
                   return calcul;
        }
         
 /**
     * create a grid and paint the List of ImageIcon
     * @param img
     * @param a
     * @param b
     */
          public void paint(int a, int b, ArrayList<ImageIcon> img) {
              listButton.clear();
                setLayout(new GridLayout(b,a,0,0));
                int ie = 0;
                JButton bubu = new JButton();
                for(int j =0; j<img.size(); j++){
                    String S = "bubu"+j;
                    JButton button = new JButton();
                    button.setName(S);
                    button.setText(Integer.toString(j));
                    listButton.add(button);
                }
                        for(ImageIcon i : img){ //JLabel toto = new JLabel(icon);
                        bubu = listButton.get(ie);
                        bubu.setIcon(i);
                        Rectangle r = new Rectangle(1024/a, 1024/b);
                        bubu.setBounds(r);
                        bubu.setText(Integer.toString(ie));
                        add(bubu);
                        bubu.addActionListener(this);
                        ie = ie+1;
                        } 
                  
              }
        
           public  void    actionPerformed(ActionEvent e)
    {
            try {
                JButton source=(JButton) e.getSource();
                int i = Integer.parseInt(source.getText());
                String Adr = adresse.get(i);
                ArrayList<String> adre = new ArrayList<>();
                adre.add(Adr);
                ArrayList<ImageIcon> img = new ArrayList<>();
                ImageIcon image = new ImageIcon(Adr);
                img.add(image);
                //UniqueImageView uiv = new UniqueImageView();
                Itest inter = new Itest();
                ArrayList<Tampon> t = new ArrayList<>();
                t.add(inter.getTampon1());
                t.add(inter.getTampon2());
                t.add(inter.getTampon3());
                t.add(inter.getTampon5());
                t.add(inter.getTampon6());
                t.add(inter.getTampon7());
                t.add(inter.getTampon9());
                for (Tampon tampon : t) {
                    tampon.setAdr(adre);
                    tampon.setImg(img);
                    tampon.setA(1);
                    tampon.setB(1);
                }
                
                Metadata data = inter.getMetadata();
                data.readAndDisplayMetadata(Adr);
                Map<String, NamedNodeMap> attributes = data.getAttributes();
                StringBuffer sb = new StringBuffer();
                Iterator it = attributes.keySet().iterator();
                
                while (it.hasNext()) {
                    String buffer = it.next().toString();
                    sb.append(buffer + " : ");
                    NamedNodeMap mapbuffer = attributes.get(buffer);
                    
                    for (int ii = 0; ii < mapbuffer.getLength(); ii++) {
                        sb.append("      ");
                        sb.append(mapbuffer.item(ii).getNodeValue());
                        sb.append("\n");
                    }
                }
                data.setSb(sb.toString());
                //inter.setJTextArea(sb.toString());
                ArrayList<Histogram> listHisto = new ArrayList<>();
                Histogram histogram = new Histogram();
                
                histogram.histogram(Adr);
                histogram.redhistogram(Adr);
                histogram.greenhistogram(Adr);
                histogram.bluehistogram(Adr);
                listHisto.add(histogram);
                
                inter.setListHist(listHisto);
                inter.setHistogram(histogram.getGlobal(), histogram.getRed(), histogram.getBlue(), histogram.getBlue());
                inter.getPrev().setVisible(false);
                inter.getNext().setVisible(false);
                inter.getImageName().setVisible(false);
                inter.setVisible(true);
                
                inter.addWindowListener(new WindowListener() {
                    @Override
                    public void windowOpened(WindowEvent e) {}
                    
                    @Override
                    public void windowClosing(WindowEvent e) {
                    }
                    
                    @Override
                    public void windowClosed(WindowEvent e) { }
                    
                    @Override
                    public void windowIconified(WindowEvent e) {}
                    
                    @Override
                    public void windowDeiconified(WindowEvent e) {}
                    
                    @Override
                    public void windowActivated(WindowEvent e) {
                        inter.drawHistogram(0);
                    }
                    
                    @Override
                    public void windowDeactivated(WindowEvent e) {}
                }); } catch (IOException ex) {
                Logger.getLogger(DropPane.class.getName()).log(Level.SEVERE, null, ex);
            }
    }


              
     

        protected class DropTargetHandler implements DropTargetListener {

            protected void processDrag(DropTargetDragEvent dtde) {
                if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                    dtde.acceptDrag(DnDConstants.ACTION_COPY);
                } else {
                    dtde.rejectDrag();
                }
            }

            @Override
            public void dragEnter(DropTargetDragEvent dtde) {
                processDrag(dtde);
                SwingUtilities.invokeLater(new DragUpdate(true, dtde.getLocation()));
                repaint();
            }

            @Override
            public void dragOver(DropTargetDragEvent dtde) {
                processDrag(dtde);
                SwingUtilities.invokeLater(new DragUpdate(true, dtde.getLocation()));
                repaint();
            }

            @Override
            public void dropActionChanged(DropTargetDragEvent dtde) {
            }

            @Override
            public void dragExit(DropTargetEvent dte) {
                SwingUtilities.invokeLater(new DragUpdate(false, null));
                repaint();
            }

            @Override
            public void drop(DropTargetDropEvent dtde) {

                SwingUtilities.invokeLater(new DragUpdate(false, null));

                Transferable transferable = dtde.getTransferable();
                if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                    dtde.acceptDrop(dtde.getDropAction());
                    try {

                        List transferData = (List) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                        if (transferData != null && transferData.size() > 0) {
                            importFiles(transferData);
                            dtde.dropComplete(true);
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    dtde.rejectDrop();
                }
            }
        }

        public class DragUpdate implements Runnable {

            private boolean dragOver;
            private Point dragPoint;

            public DragUpdate(boolean dragOver, Point dragPoint) {
                this.dragOver = dragOver;
                this.dragPoint = dragPoint;
            }

            @Override
            public void run() {
                DropPane.this.dragOver = dragOver;
                DropPane.this.dragPoint = dragPoint;
                DropPane.this.repaint();
            }
        }
        
    }
    
