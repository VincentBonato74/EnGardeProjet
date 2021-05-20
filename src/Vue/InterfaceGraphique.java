package Vue;

import Modele.Jeu;
import Patterns.Observateur;


import javax.swing.*;
import java.awt.*;

public class InterfaceGraphique implements Runnable, Observateur {
    Jeu jeu;
    CollecteurEvenements control;
    NiveauGraphique niv;
    JButton avancer, reculer;

    InterfaceGraphique(Jeu j,CollecteurEvenements c){
        jeu = j;
        control = c;
        jeu.ajouteObservateur(this);
    }

    public static void demarrer(Jeu j, CollecteurEvenements control){
        SwingUtilities.invokeLater(new InterfaceGraphique(j,control));
    }

    private JLabel createLabel(String s) {
        JLabel lab = new JLabel(s);
        lab.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lab;
    }

    private JToggleButton createToggleButton(String s, String c) {
        JToggleButton but = new JToggleButton(s);
        but.addActionListener(new AdaptateurCommande(control, c));
        but.setAlignmentX(Component.CENTER_ALIGNMENT);
        but.setFocusable(false);
        return but;
    }

    private JButton createButton(String s, String c) {
        JButton but = new JButton(s);
        but.addActionListener(new AdaptateurCommande(control, c));
        but.setAlignmentX(Component.CENTER_ALIGNMENT);
        but.setFocusable(false);
        return but;
    }


    @Override
    public void run() {
        JFrame frame = new JFrame("En Garde");
        niv = new NiveauGraphique(jeu);
        niv.addMouseListener(new AdaptateurSouris(niv, control));
        frame.add(niv);

        // Décompte des pas et poussées
        Box barreLaterale = Box.createVerticalBox();
        barreLaterale.add(createLabel("En Garde"));
        barreLaterale.add(Box.createGlue());

        avancer = createButton("Avancer", "avancer");
        barreLaterale.add(avancer);

        reculer = createButton("Reculer","reculer");
        barreLaterale.add(reculer);

        frame.add(barreLaterale, BorderLayout.LINE_END);




        Timer time = new Timer(16, new AdaptateurTemps(control));
        time.start();

        control.fixerInterfaceGraphique(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 660);
        frame.setVisible(true);
    }

    @Override
    public void metAJour() {
        niv.repaint();
    }

    public void animJoueur() {
        niv.animJoueur();
        metAJour();
    }

    /*public void SelectionCarte(int val ,int x, int y, int l, int h){
        niv.selectCarte(val ,x, y, l, h);
        metAJour();
    }*/
}
