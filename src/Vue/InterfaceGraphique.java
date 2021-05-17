package Vue;

import Modele.Jeu;
import Patterns.Observateur;

import javax.swing.*;

public class InterfaceGraphique implements Runnable, Observateur {
    Jeu jeu;
    CollecteurEvenements control;
    NiveauGraphique niv;

    InterfaceGraphique(Jeu j,CollecteurEvenements c){
        jeu = j;
        control = c;
        jeu.ajouteObservateur(this);
    }

    public static void demarrer(Jeu j, CollecteurEvenements control){
        SwingUtilities.invokeLater(new InterfaceGraphique(j,control));
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("En Garde");
        niv = new NiveauGraphique(jeu);
        //niv.addMouseListener(new AdaptateurSouris(niv, control));
        frame.add(niv);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setVisible(true);
    }

    @Override
    public void metAJour() {
        niv.repaint();
    }
}
