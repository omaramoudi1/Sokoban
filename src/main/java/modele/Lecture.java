package modele;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class Lecture {
    private List<String> lignes;
    private int nbLignes;
    private int tailleLignes;

    public Lecture(String nomFichier)throws IOException{
lignes = new ArrayList<>();
BufferedReader br=new BufferedReader(new FileReader(nomFichier));
String ligne;
while((ligne=br.readLine())!=null){
    lignes.add(ligne);
}
br.close();
nbLignes=lignes.size();
tailleLignes=lignes.get(0).length();
    }

    public List<String> getLignes(){
        return lignes;
    }
    public int getnbLignes(){
        return nbLignes;
    }
    public int getTailleLignes(){
        return tailleLignes;
    }
}
