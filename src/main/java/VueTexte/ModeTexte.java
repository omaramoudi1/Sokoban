package VueTexte;

import modele.*;
import java.io.IOException;
import java.util.*;

public class ModeTexte {
    private Carte carte;
    private Map<Character, Directions> commandes;

    public ModeTexte(Carte carte) {
        this.carte = carte;
        commandes = new HashMap<>();
        commandes.put('z', new Directions('z'));
        commandes.put('s', new Directions('s'));
        commandes.put('q', new Directions('q'));
        commandes.put('d', new Directions('d'));
    }

    public void jouer() {
        carte.demarrerChrono(120);
        System.out.println(carte);

        while (true) {
            if (carte.tempsEcoule()) {
                System.out.println("Temps écoulé ! Partie perdue.");
                break;
            }

            System.out.print("Entrer une direction (z, q, s, d), r pour recommencer ou x pour quitter : ");
            char c = Outil.lireCaractere();

            if (c == 'x') {
                System.out.println("Jeu terminé. Score final : " + carte.getScoreTotal());
                break;
            }

            if (c == 'r') {
                try {
                    carte.reinitialiser();
                    carte.demarrerChrono(120);
                    System.out.println("Carte rechargée :");
                    System.out.println(carte);
                } catch (IOException e) {
                    System.out.println("Erreur de rechargement de la carte.");
                }
                continue;
            }

            Directions d = commandes.get(c);
            if (d == null) {
                System.out.println("Commande invalide.");
                continue;
            }

            if (carte.deplacerEtCompter(d)) {
                System.out.println(carte);
                System.out.println("Étapes : " + carte.getCompteurEtapes());
                System.out.println("Caisses bien placées : " + carte.getCaissesSurButs());
                System.out.println("Temps restant : " + carte.getTempsRestant() + "s");
            }

            if (carte.finDePartie()) {
                int etapes = carte.getCompteurEtapes();
                carte.ajouterAuScoreTotal();
                System.out.println("Gagné ! Étapes : " + etapes + " | Score total : " + carte.getScoreTotal());

                try {
                    if (carte.niveauSuivant()) {
                        carte.demarrerChrono(120);
                        System.out.println("\n--- Niveau " + carte.getNiveauActuel() + " ---");
                        System.out.println(carte);
                    } else {
                        System.out.println("Bravo, tu as terminé tous les niveaux ! Score final : " + carte.getScoreTotal());
                        break;
                    }
                } catch (IOException e) {
                    System.out.println("Erreur de chargement du niveau suivant.");
                    break;
                }
            }
        }
    }
}
