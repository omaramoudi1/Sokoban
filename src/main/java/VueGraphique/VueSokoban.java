package VueGraphique;

import modele.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class VueSokoban extends JFrame implements KeyListener {

    private Carte modele;
    private JLabel[][] cases;
    private JLabel compteurLabel;
    private JLabel caisseLabel;
    private JLabel timerLabel;
    private JButton boutonRestart;
    private JPanel gridPanel;
    private Timer chrono;

    private ImageIcon mur = new ImageIcon(getClass().getResource("/img/mur.gif"));
    private ImageIcon sol = new ImageIcon(getClass().getResource("/img/sol.gif"));
    private ImageIcon caisse = new ImageIcon(getClass().getResource("/img/caisse1.gif"));
    private ImageIcon destination = new ImageIcon(getClass().getResource("/img/but.gif"));
    private ImageIcon robotHaut = new ImageIcon(getClass().getResource("/img/Haut.gif"));
    private ImageIcon robotBas = new ImageIcon(getClass().getResource("/img/Bas.gif"));
    private ImageIcon robotGauche = new ImageIcon(getClass().getResource("/img/Gauche.gif"));
    private ImageIcon robotDroite = new ImageIcon(getClass().getResource("/img/Droite.gif"));
    private ImageIcon caisseOk = new ImageIcon(getClass().getResource("/img/caisse2.gif"));

    public VueSokoban(Carte carte) {
        this.modele = carte;
        setTitle("Sokoban");
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(1, 3));
        compteurLabel = new JLabel("Etapes : 0");
        caisseLabel = new JLabel("Caisses bien placees : 0");
        timerLabel = new JLabel("Temps : 120s");
        topPanel.add(compteurLabel);
        topPanel.add(caisseLabel);
        topPanel.add(timerLabel);
        add(topPanel, BorderLayout.NORTH);

        boutonRestart = new JButton("Recommencer");
        boutonRestart.addActionListener(e -> {
            try {
                modele.reinitialiser();
                modele.demarrerChrono(120);
                startTimer();
                rechargerAffichage();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(boutonRestart);
        add(bottomPanel, BorderLayout.SOUTH);

        creerGrille();
        modele.demarrerChrono(120);
        startTimer();
        affichage();

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        requestFocusInWindow();

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void creerGrille() {
        if (gridPanel != null) remove(gridPanel);

        int hauteur = modele.getNbLignes();
        int largeur = modele.getNbColonnes();
        gridPanel = new JPanel(new GridLayout(hauteur, largeur));
        cases = new JLabel[hauteur][largeur];

        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                JLabel label = new JLabel();
                label.setPreferredSize(new Dimension(32, 32));
                cases[i][j] = label;
                gridPanel.add(label);
            }
        }

        add(gridPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void startTimer() {
        if (chrono != null) chrono.cancel();
        chrono = new Timer();
        chrono.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                int restant = modele.getTempsRestant();
                timerLabel.setText("Temps : " + restant + "s");
                if (restant <= 0) {
                    chrono.cancel();
                    JOptionPane.showMessageDialog(VueSokoban.this, "Temps écoulé ! Partie perdue.");
                    System.exit(0);
                }
            }
        }, 0, 1000);
    }

    public void affichage() {
        for (int i = 0; i < modele.getNbLignes(); i++) {
            for (int j = 0; j < modele.getNbColonnes(); j++) {
                block b = modele.getPlateau(i, j);
                JLabel label = cases[i][j];

                if (b.joueur()) {
                    Directions dir = modele.getRobot().getDirection();
                    if (dir != null) {
                        if (dir.getDLig() == -1) label.setIcon(robotHaut);
                        else if (dir.getDLig() == 1) label.setIcon(robotBas);
                        else if (dir.getDCol() == -1) label.setIcon(robotGauche);
                        else if (dir.getDCol() == 1) label.setIcon(robotDroite);
                    } else {
                        label.setIcon(robotHaut);
                    }
                } else if (b.caisse() && b instanceof destination) {
                    label.setIcon(caisseOk);
                } else if (b.caisse()) {
                    label.setIcon(caisse);
                } else if (b instanceof mur) {
                    label.setIcon(mur);
                } else if (b instanceof destination) {
                    label.setIcon(destination);
                } else if (b instanceof sol) {
                    label.setIcon(sol);
                } else {
                    label.setIcon(null);
                }
            }
        }
        compteurLabel.setText("Étapes : " + modele.getCompteurEtapes());
        caisseLabel.setText("Caisses bien placées : " + modele.getCaissesSurButs());
    }

    private void rechargerAffichage() {
        creerGrille();
        affichage();
        requestFocusInWindow();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        Directions d = null;

        if (code == KeyEvent.VK_LEFT) d = new Directions(0, -1);
        else if (code == KeyEvent.VK_RIGHT) d = new Directions(0, 1);
        else if (code == KeyEvent.VK_UP) d = new Directions(-1, 0);
        else if (code == KeyEvent.VK_DOWN) d = new Directions(1, 0);

        if (d != null && modele.deplacerEtCompter(d)) {
            affichage();
            if (modele.finDePartie()) {
                int etapes = modele.getCompteurEtapes();
                modele.ajouterAuScoreTotal();
                chrono.cancel();
                int choix = JOptionPane.showOptionDialog(
                        this,
                        "Gagné en " + etapes + " étapes ! Que veux-tu faire ?",
                        "Niveau terminé",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new Object[]{"Recommencer", "Niveau suivant"},
                        "Niveau suivant"
                );

                if (choix == 0) {
                    try {
                        modele.reinitialiser();
                        modele.demarrerChrono(120);
                        startTimer();
                        rechargerAffichage();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (choix == 1) {
                    try {
                        if (modele.niveauSuivant()) {
                            modele.demarrerChrono(120);
                            startTimer();
                            rechargerAffichage();
                        } else {
                            JOptionPane.showMessageDialog(this, "Bravo, tu as terminé tous les niveaux !");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}