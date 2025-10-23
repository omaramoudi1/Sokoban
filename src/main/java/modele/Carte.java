package modele;

import java.io.IOException;
import java.util.*;

public class Carte {
    private block[][] plateau;
    private int hauteur;
    private int largeur;
    private Robot robot;
    private List<int[]> buts;
    private int compteurEtapes = 0;
    private int scoreTotal = 0;
    private String nomCarte;
    private int niveauActuel = 2;
    private final int niveauMax = 3;
    private long tempsDepart;
    private int tempsLimiteSec = 120;

    public Carte(String nomFichier) throws IOException {
        this.nomCarte = nomFichier;
        chargerCarte(nomFichier);
    }

    public void chargerCarte(String nomFichier) throws IOException {
        Lecture lecture = new Lecture(nomFichier);
        List<String> lignes = lecture.getLignes();

        hauteur = lignes.size();
        largeur = lignes.get(0).length();
        plateau = new block[hauteur][largeur];
        buts = new ArrayList<>();

        for (int i = 0; i < hauteur; i++) {
            String ligne = lignes.get(i);
            for (int j = 0; j < ligne.length(); j++) {
                char c = ligne.charAt(j);
                switch (c) {
                    case ' ':  plateau[i][j] = new sol();  break;
                    case '#':  plateau[i][j] = new mur();  break;
                    case '.':  plateau[i][j] = new destination();  buts.add(new int[]{i, j});  break;
                    case '@':  plateau[i][j] = new sol();  plateau[i][j].setJoueur(true);  robot = new Robot(i, j);  break;
                    case '+':  plateau[i][j] = new destination();  plateau[i][j].setJoueur(true);  robot = new Robot(i, j);  buts.add(new int[]{i, j});  break;
                    case '$': plateau[i][j] = new sol(); plateau[i][j].setCaisse(true); break;
                    case '*':plateau[i][j] = new destination();plateau[i][j].setCaisse(true);buts.add(new int[]{i, j});break;
                    case '/':plateau[i][j] = new Vide();break;
                }
            }
        }
    }

    public void reinitialiser() throws IOException {
        compteurEtapes = 0;
        chargerCarte(nomCarte);
    }

    public boolean deplacerEtCompter(Directions d) {
        boolean bouge = deplacer(d);
        if (bouge) compteurEtapes++;
        return bouge;
    }

    public boolean deplacer(Directions d) {
        int i = robot.posLigne();
        int j = robot.posColonne();

        int i2 = i + d.getDLig();
        int j2 = j + d.getDCol();

        if (i2 < 0 || i2 >= hauteur || j2 < 0 || j2 >= largeur) return false;
        if (plateau[i2][j2].getSymbol() == '#') return false;

        if (!plateau[i2][j2].caisse()) {
            plateau[i][j].setJoueur(false);
            plateau[i2][j2].setJoueur(true);
            robot.deplacerRobot(d);
            return true;
        } else {
            int i3 = i2 + d.getDLig();
            int j3 = j2 + d.getDCol();
            if (i3 < 0 || i3 >= hauteur || j3 < 0 || j3 >= largeur) return false;
            if (plateau[i3][j3].caisse() || plateau[i3][j3].getSymbol() == '#') return false;

            plateau[i][j].setJoueur(false);
            plateau[i2][j2].setJoueur(true);
            plateau[i2][j2].setCaisse(false);
            plateau[i3][j3].setCaisse(true);
            robot.deplacerRobot(d);
            return true;
        }
    }

    public boolean finDePartie() {
        for (int[] pos : buts) {
            int i = pos[0];
            int j = pos[1];
            if (!plateau[i][j].caisse()) return false;
        }
        
        return true;
    }

    public int getCaissesSurButs() {
        int count = 0;
        for (int[] pos : buts) {
            int i = pos[0];
            int j = pos[1];
            if (plateau[i][j].caisse()) count++;
        }
        return count;
    }

    public void demarrerChrono(int secondesLimite) {
        this.tempsLimiteSec = secondesLimite;
        this.tempsDepart = System.currentTimeMillis();
    }

    public int getTempsRestant() {
        long maint = System.currentTimeMillis();
        long ecoule = (maint - tempsDepart) / 1000;
        return Math.max(0, tempsLimiteSec - (int) ecoule);
    }

    public boolean tempsEcoule() {
        return getTempsRestant() <= 0;
    }

    public boolean niveauSuivant() throws IOException {
        if (niveauActuel < niveauMax) {
            niveauActuel++;
            compteurEtapes = 0;
            nomCarte = "map" + niveauActuel + ".txt";
            chargerCarte(nomCarte);
            return true;
        }
        return false;
    }

    public int getNiveauActuel() {
        return niveauActuel;
    }

    public int getNiveauMax() {
        return niveauMax;
    }

    public block getPlateau(int i, int j) {
        return plateau[i][j];
    }

    public int getNbLignes() {
        return hauteur;
    }

    public int getNbColonnes() {
        return largeur;
    }

    public Robot getRobot() {
        return robot;
    }

    public int getCompteurEtapes() {
        return compteurEtapes;
    }

    public int getScoreTotal() {
        return scoreTotal;
    }

    public void ajouterAuScoreTotal() {
        scoreTotal += compteurEtapes;
        compteurEtapes = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                sb.append(plateau[i][j].getSymbol());
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}