package VueGraphique;

import java.io.IOException;

import modele.Carte;

public class Sokoban {
    public static void main(String[] args) throws IOException {
       
            Carte carte = new Carte("map1.txt");
            new VueSokoban(carte);
        
    }
}


