package view;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Melh
 */
public class ListImage {
        
    private ArrayList<String> Adresse;

    public List<String> getAdresse() {
        return Adresse;
    }

    public void setAdresse(ArrayList<String> Adresse) {
        this.Adresse = Adresse;
    }

    public ListImage(List files) {
        this.Adresse = new ArrayList<String>();
                   for(int i =0; i< files.size(); i++){
                       String S = "" + files.get(i);
                       if(S.contains(".png")||S.contains(".jpg")||S.contains(".gif")||S.contains(".bmp")||S.contains(".JPG")||S.contains(".PNG")||S.contains(".GIF")||S.contains(".BMP")){
                        Adresse.add(S);
                       }
                    }
    }
    
    
}
