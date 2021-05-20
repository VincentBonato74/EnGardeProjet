package Vue;

import Modele.Jeu;
import Patterns.Observateur;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;


import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class InterfaceGraphique implements Runnable, Observateur {
    Jeu jeu;
    CollecteurEvenements control;
    NiveauGraphique niv;
    JFrame frame;
    Box barreLaterale;
    JPanel pan;
    public int hauteurPanel, largeurPanel;

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
        frame = new JFrame("En Garde");
        niv = new NiveauGraphique(jeu);
        niv.addMouseListener(new AdaptateurSouris(niv, control));
        frame.add(niv);
        pan = new JPanel();
        pan.setLayout(null);
        pan.setBackground(new Color(0, 0, 0, 255));
        pan.setBorder(BorderFactory.createBevelBorder(0, Color.yellow, Color.yellow));
        pan.setOpaque(true);

        JButton Niveau = createButton("Nouvelle Partie", "Niveau", 0);
        JButton Charger = createButton("Charger Une Partie", "Charger", 2);
        JButton Option = createButton("Option", "Option", 4);
        JButton Quitter = createButton("Quitter le jeu", "quit", 6);
        pan.add(Niveau);
        pan.add(Charger);
        pan.add(Option);
        pan.add(Quitter);

        Timer chrono = new Timer(16 , new AdaptateurTemps(control));
        chrono.start();

        control.fixerInterfaceGraphique(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 660);
        frame.setVisible(true);

        largeurPanel = (frame.getSize().width/2);
        hauteurPanel = (frame.getSize().height);
        pan.setPreferredSize(new Dimension(largeurPanel, hauteurPanel));



    }

    //Fonction pour Ajouter ou enlever le Menu en fonction des pages
    public void BoutonMenu(){
        if(niv.Menu){
            frame.add(pan, BorderLayout.WEST);
        }else{
            frame.remove(pan);
            frame.revalidate();
        }
    }

    public void MAJPanelMenu(){
        if(largeurPanel != ((pan.getWidth() + getLargeur()) / 2)){
            System.out.println("Redimmensionnement");
            largeurPanel = (pan.getWidth() + getLargeur()) / 2;
            frame.remove(pan);
            pan.setPreferredSize(new Dimension(largeurPanel, hauteurPanel));
            frame.add(pan, BorderLayout.WEST);
            frame.revalidate();
        }
    }

    public void changeBackground(){
        niv.changeBackground();
        BoutonMenu();
    }

    private JLabel createLabel(String s) {
        JLabel lab = new JLabel(s);
        lab.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lab;
    }

    private JButton createButton(String s, String c, int i) {
        JButton but = new JButton(s);
        but.addActionListener(new AdaptateurCommande(control, c));
        but.setAlignmentX(Component.CENTER_ALIGNMENT);
        but.setForeground(Color.yellow);
        but.setBackground(Color.BLACK);
        but.setBorder(BorderFactory.createBevelBorder(0, Color.yellow, Color.yellow));
        but.setBounds(137, 35+(i*70), 275, 110);
        but.setFocusable(false);
        return but;
    }

    @Override
    public void metAJour() {
        niv.repaint();
    }

    public void animJoueur() {
        niv.animJoueur();
        metAJour();
    }

    public boolean getMenu(){
        return niv.Menu;
    }

    public boolean getPartie(){
        return niv.Partie;
    }

    public int getHauteur(){
        return niv.hauteur;
    }

    public int getLargeur(){
        return niv.largeur;
    }
}
