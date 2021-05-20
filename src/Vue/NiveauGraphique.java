package Vue;

import Modele.Jeu;
import Patterns.Observateur;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Random;

public class NiveauGraphique extends JComponent implements Observateur {
    Jeu jeu;
    Image fond, nom, joueur1, joueur2, sol, map, teteJ1, teteJ2, TiretBleu, TiretRouge, NomJ1, NomJ2;
    int dimensionTete, xTeteDroite, xTeteGauche, yTete, etape, largeur, hauteur, nbColonnes, largeurCase, hauteurNom, largeurNom;
    int hauteurCase, hauteurLuke, hauteurVador, largeurVador, yPoint, xPointGauche, xPointDroit, hauteurTiret, largeurTiret, yNom, xNom;
    int hauteur2, largeur2;
    Image[] joueurs1;
    Image[] joueurs2;
    Random r;
    Clip clip;
    Graphics2D drawable;
    boolean Menu, Partie, PartieSet, MenuSet;

    private Image chargeImage(String nom){
        Image img = null;
        try{
            InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(nom + ".png");
            if(in != null){
                img = ImageIO.read(in);
            }
        }catch(Exception e ){
            System.err.println("Erreur lors du chargement de l'image : " + e);
            System.exit(1);
        }
        return img;
    }

    public void randomDecors(){
        Random r = new Random();
        int nb = r.nextInt(8);
        int nb2 = nb%4;
        map = chargeImage("Map"+nb);
        sol = chargeImage("Sol"+nb2);
        //Gestion Musique
        try {
            AudioInputStream input = AudioSystem.getAudioInputStream(new File("res/Music/Duel"+nb+".wav"));
            clip = AudioSystem.getClip();
            clip.open(input);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public NiveauGraphique(Jeu j){
        jeu = j;
        nbColonnes = 23;
        teteJ1 = chargeImage("Luke_Head");
        teteJ2 = chargeImage("Vador_Head");
        TiretBleu = chargeImage("TiretBleu");
        TiretRouge = chargeImage("TiretRouge");
        fond = chargeImage("Menu");
        nom = chargeImage("NomSW");
        NomJ1 = chargeImage("NomJ1");
        NomJ2 = chargeImage("NomJ2");

        joueurs1 = new Image[4];
        joueurs2 = new Image[4];
        for(int i = 0; i < 4; i++){
            joueurs1[i] = chargeImage("luke_stand_0"+i);
            joueurs2[i] = chargeImage("vador_stand_0"+i);
        }

        etape = 0;
        joueur1 = joueurs1[etape];
        joueur2 = joueurs2[etape];

        Menu = true;
        Partie = false;
        PartieSet = false;
        MenuSet = false;
    }

    public void paintComponent(Graphics g){
        drawable = (Graphics2D) g;
        largeur = getSize().width;
        hauteur = getSize().height;

        drawable.clearRect(0, 0, largeur, hauteur);
        if(!Menu && Partie){
            tracerPartie();
        }else if(Menu && !Partie){
            tracerMenu();
        }
    }

    public void startMusique(){
        try {
            AudioInputStream input = AudioSystem.getAudioInputStream(new File("res/Music/Menu.wav"));
            clip = AudioSystem.getClip();
            clip.open(input);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopMusique(){
        if(MenuSet || PartieSet){
            clip.stop();
        }
    }

    public void tracerMenu(){
        if(!MenuSet){
            //stopMusique();
            //startMusique();
        }

        PartieSet = false;
        MenuSet = true;

        largeurNom = (int)Math.round(largeur*0.30);
        hauteurNom = (int)Math.round(hauteur*0.25);
        xNom = (int)Math.round(largeur*0.35);
        yNom = (int)Math.round(hauteur*0.05);

        drawable.clearRect(0, 0, largeur, hauteur);
        drawable.drawImage(fond, 0,0,largeur, hauteur, null);
        drawable.drawImage(nom, xNom, yNom, largeurNom, hauteurNom ,null);
    }


    public void tracerPartie(){
        if(!PartieSet){
            //stopMusique();
            randomDecors();
        }

        PartieSet = true;
        MenuSet = false;

        largeurCase = largeur / nbColonnes;
        hauteurCase = (int)Math.round(largeurCase*0.35);
        hauteurLuke = (int)Math.round(largeurCase * 2.10);
        hauteurVador = (int)Math.round(largeurCase * 1.94);
        largeurVador = (int)Math.round(largeurCase * 1.50);
        xTeteGauche = (int)Math.round(largeur*0.05);
        xTeteDroite = (int)Math.round((largeur*.95)-(largeurCase*1.75));
        yTete = (int)Math.round(hauteur*0.05);
        xPointGauche = (xTeteGauche+dimensionTete);
        xPointDroit = xTeteDroite;
        yPoint = (int)Math.round(yTete+dimensionTete*0.75);
        dimensionTete = (int)Math.round(largeurCase*1.75);
        hauteurTiret = (int)Math.round(hauteur*0.01);
        largeurTiret = (int)Math.round(largeur*0.025);
        hauteurNom = (int)Math.round(dimensionTete / 2);
        largeurNom = (int)Math.round(dimensionTete * 3);
        yNom = (int)Math.round(hauteur*0.08);

        drawable.clearRect(0, 0, largeur, hauteur);
        drawable.drawImage(map, 0, 0, largeur, hauteur, null);
        drawable.drawImage(teteJ1, xTeteGauche, yTete, dimensionTete ,dimensionTete,null);
        drawable.drawImage(teteJ2, xTeteDroite, yTete, dimensionTete ,dimensionTete,null);
        for(int i = 0; i < 5; i++){
            if(!jeu.partie().estRougeJ1(i)){
                drawable.drawImage(TiretBleu, xPointGauche+((i+1)*xTeteGauche), yPoint, largeurTiret, hauteurTiret, null);
            }else{
                drawable.drawImage(TiretRouge, xPointGauche+((i+1)*xTeteGauche), yPoint, largeurTiret, hauteurTiret, null);
            }
            if(!jeu.partie().estRougeJ2(i)){
                drawable.drawImage(TiretBleu, xPointDroit-((i+1)*xTeteGauche), yPoint, largeurTiret, hauteurTiret, null);
            }else{
                drawable.drawImage(TiretRouge, xPointDroit-((i+1)*xTeteGauche), yPoint, largeurTiret, hauteurTiret, null);
            }
            if(i == 0){
                drawable.drawImage(NomJ1, xPointGauche+((i+1)*xTeteGauche), yNom, largeurNom, hauteurNom, null);
            }
            if(i==4){
                drawable.drawImage(NomJ2, xPointDroit-((i+1)*xTeteGauche), yNom, largeurNom, hauteurNom, null);
            }
        }

        for(int c = 0; c < 23; c++){
            int x = c * largeurCase;
            int y = (int) Math.round(hauteur * 0.62);
            drawable.drawImage(sol, x, y, largeurCase, hauteurCase, null);
            if(c == 0){
                drawable.drawImage(joueur1, x, (int)Math.round(y-hauteurVador+(hauteurCase*0.5)), largeurVador, hauteurVador, null);
            }else if(c == 22){
                drawable.drawImage(joueur2, x+largeurCase, (int)Math.round(y-hauteurVador+(hauteurCase*0.5)), -largeurVador, hauteurVador, null);
            }
        }
    }

    @Override
    public void metAJour() {
        repaint();
    }

    public void animJoueur() {
        etape = (etape+1)%4;
        joueur1 = joueurs1[etape];
        joueur2 = joueurs2[etape];
        metAJour();
    }

    public void changeBackground() {
        if(MenuSet && !PartieSet){
            Menu = false;
            Partie = true;
            metAJour();
        }else{
            Menu = true;
            Partie = false;
            metAJour();
        }
    }
}
