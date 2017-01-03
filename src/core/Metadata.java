/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

/**
 *
 * @author Dodger
 */
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.imageio.stream.*;
import javax.imageio.metadata.*;
import javax.swing.JPanel;
import org.w3c.dom.*;

public class Metadata extends JPanel {

    private static Map<String, NamedNodeMap> attributes = new HashMap<String, NamedNodeMap>();
    private String sb;

    public String getSb() {
        return sb;
    }

    public void setSb(String sb) {
        this.sb = sb;
    }
//    public static void main(String[] args) {
//        Metadata meta = new Metadata();
//        meta.readAndDisplayMetadata("C:" + File.separatorChar + "Users" + File.separatorChar + "Dodger" + File.separatorChar + "Pictures" + File.separatorChar + "1476245780.nannerpudding_fewks.jpg");
//
//        Iterator it = attributes.keySet().iterator();
//
//        while (it.hasNext()) {
//            String buffer = it.next().toString();
//            NamedNodeMap mapbuffer = attributes.get(buffer);
//            System.out.println(buffer + " : ");
//            for (int i = 0; i < mapbuffer.getLength(); i++) {
//                System.out.print("      ");
//                System.out.println(mapbuffer.item(i).getNodeValue());
//            }
//        }
//
//    }
    public static Map<String, NamedNodeMap> getAttributes() {
        return attributes;
    }

    public static void setAttributes(Map<String, NamedNodeMap> attributes) {
        Metadata.attributes = attributes;
    }

    public void readAndDisplayMetadata(String fileName) {
        try {

            File file = new File(fileName);
            ImageInputStream iis = ImageIO.createImageInputStream(file);
            Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);

            if (readers.hasNext()) {

                ImageReader reader = readers.next();
                reader.setInput(iis, true);
                IIOMetadata metadata = reader.getImageMetadata(0);

                String[] names = metadata.getMetadataFormatNames();
                int length = names.length;
                for (int i = 0; i < length; i++) {
                    System.out.println("Format name: " + names[i]);
                    displayMetadata(metadata.getAsTree(names[i]));
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    void displayMetadata(Node node) {

        NamedNodeMap map = node.getAttributes();
        if (map != null) {

            int length = map.getLength();
            for (int i = 0; i < length; i++) {
                Node attr = map.item(i);

            }
        }

        Node child = node.getFirstChild();

        if (child == null) {
            return;
        }

        while (child != null) {

            if (child.getFirstChild() == null) {
                attributes.put(child.getNodeName(), child.getAttributes());
            }

            displayMetadata(child);
            child = child.getNextSibling();
        }

    }
}
