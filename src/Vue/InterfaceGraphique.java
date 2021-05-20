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
    JPanel pan, optionGauche, optionDroit;
    public int hauteurPanel, largeurPanel;
    JButton Niveau, Option, Charger, Quitter, Droit, Gauche;

    InterfaceGraphique(Jeu j,CollecteurEvenements c){
        jeu = j;
        control = c;
        jeu.ajouteObservateur(this);
    }

    public static void demarrer(Jeu j, CollecteurEvenements control){
        SwingUtilities.invokeLater(new InterfaceGraphique(j,control));
    }

    public NiveauGraphique niv(){
        return niv;
    }

    @Override
    public void run() {
        frame = new JFrame("En Garde");
        niv = new NiveauGraphique(jeu);
        niv.addMouseListener(new AdaptateurSouris(niv, control));
        frame.add(niv);


        Timer chrono = new Timer(16 , new AdaptateurTemps(control));
        chrono.start();

        control.fixerInterfaceGraphique(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 660);
        frame.setVisible(true);

        largeurPanel = (frame.getSize().width/2);
        hauteurPanel = (frame.getSize().height);

        pan = new JPanel(){
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);

                ImageIcon m = new ImageIcon("res/MenuGauche.png");
                Image monImage = m.getImage();
                g.drawImage(monImage, 0, 0,  (frame.getSize().width/2), (frame.getSize().height),this);

            }
        };
        pan.setLayout(null);
        pan.setBackground(new Color(0, 0, 0, 255));
        //pan.setBorder(BorderFactory.createBevelBorder(0, Color.yellow, Color.yellow));
        pan.setOpaque(true);

        Niveau = createButton("Nouvelle Partie", "Niveau", 0);
        Charger = createButton("Charger Une Partie", "Charger", 2);
        Option = createButton("Option", "Option", 4);
        Quitter = createButton("Quitter le jeu", "quit", 6);
        pan.add(Niveau);
        pan.add(Charger);
        pan.add(Option);
        pan.add(Quitter);

        pan.setPreferredSize(new Dimension(largeurPanel, hauteurPanel));

        optionGauche = new JPanel();
        //optionGauche.setLayout(null);
        optionGauche.setBackground(new Color(0,0,0));
        optionGauche.setOpaque(true);

        optionDroit = new JPanel();
        //optionDroit.setLayout(null);
        optionDroit.setBackground(new Color(0,0,0));
        optionDroit.setOpaque(true);

        Droit = createButton("  >  ", "Suivant", 4);
        Gauche = createButton("  <  ", "Revenir", 4);

        optionDroit.add(Droit);
        optionGauche.add(Gauche);

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
            hauteurPanel = (getHauteur());
            frame.remove(pan);
            pan.setPreferredSize(new Dimension(largeurPanel, hauteurPanel));

            int largeurBouton = (int)Math.round(largeurPanel * 0.5);
            int hauteurBouton = (int)Math.round(hauteurPanel * 0.18);
            int yBouton = (int)Math.round(hauteurPanel * 0.05);
            int xBouton = (int)Math.round(largeurPanel * 0.25);

            System.out.println(hauteurPanel);
            System.out.println(largeurPanel);

            Niveau.setBounds(xBouton, yBouton, largeurBouton, hauteurBouton);
            Charger.setBounds(xBouton, (yBouton*2)+hauteurBouton, largeurBouton, hauteurBouton);
            Option.setBounds(xBouton, ((yBouton*3) + (hauteurBouton*2)), largeurBouton, hauteurBouton);
            Quitter.setBounds(xBouton, ((yBouton*4) + (hauteurBouton*3)), largeurBouton, hauteurBouton);

            frame.add(pan, BorderLayout.WEST);
            frame.revalidate();
        }
    }

    public void InterfaceOption(){
        System.out.println("Salut 1 ");
        if(niv.Option){
            niv().compteur = 0;
            System.out.println("Salut 2 ");
            frame.add(optionGauche, BorderLayout.WEST);
            frame.add(optionDroit, BorderLayout.EAST);
            frame.revalidate();
        }else{
            System.out.println("Salut 3 ");
            frame.remove(optionGauche);
            frame.remove(optionDroit);
            frame.revalidate();
        }
    }

    public void changeBackground(boolean b1, boolean b2, boolean b3){
        niv.changeBackground(b1, b2, b3);
        BoutonMenu();
        InterfaceOption();
    }

    private JLabel createLabel(String s) {
        JLabel lab = new JLabel(s);
        lab.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lab;
    }

    private JButton createButton(String s, String c, int i) {
        JButton but = new JButton(s);
        but.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                but.setBackground(new Color(255, 222, 06));
                but.setBorder(BorderFactory.createBevelBorder(0, Color.BLACK, Color.BLACK));
                but.setForeground(Color.BLACK);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                but.setBackground(Color.BLACK);
                but.setBorder(BorderFactory.createBevelBorder(0, new Color(255, 222, 06), new Color(255, 222, 06)));
                but.setForeground(new Color(255, 222, 06));
            }
        });
        but.setFont(new Font("Segoe UI Black", Font.BOLD, 18));
        but.addActionListener(new AdaptateurCommande(control, c));
        but.setAlignmentX(Component.CENTER_ALIGNMENT);
        but.setForeground(new Color(255, 222, 06));
        but.setBackground(Color.BLACK);
        but.setBorder(BorderFactory.createBevelBorder(0, new Color(255, 222, 06), new Color(255, 222, 06)));
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

    public void tracerRegles() {
        niv.tracerRegles();
    }
}
