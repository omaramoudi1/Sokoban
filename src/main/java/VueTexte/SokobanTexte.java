package VueTexte;

import modele.*;
import java.io.IOException;

public class SokobanTexte {
    public static void main(String[] args) throws IOException {
        Carte carte = new Carte("map1.txt");
        new ModeTexte(carte).jouer();
    }
}
