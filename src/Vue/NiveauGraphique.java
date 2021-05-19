package Vue;

import Controller.JoueurHumain;
import Modele.CarteIHM;
import Modele.Jeu;
import Patterns.Observateur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class NiveauGraphique extends JComponent implements Observateur {
    Jeu jeu;
    Image joueur1, joueur2, sol, map, teteJ1, teteJ2;
    int dimensionTete, xTeteDroite, xTeteGauche, yTete, etape, largeur, hauteur, nbColonnes, largeurCase, hauteurCase, hauteurLuke, hauteurVador, largeurVador;
    Image[] joueurs1;
    Image[] joueurs2;
    Image carte1, carte2, carte3, carte4, carte5;
    Image carte1_select, carte2_select, carte3_select, carte4_select, carte5_select;
    Random r;

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
        int nb = r.nextInt(7);
        int nb2 = nb%4;
        map = chargeImage("Map"+nb);
        sol = chargeImage("Sol"+nb2);
    }

    public NiveauGraphique(Jeu j){
        jeu = j;
        nbColonnes = 23;
        randomDecors();
        teteJ1 = chargeImage("Luke_Head");
        teteJ2 = chargeImage("Vador_Head");

        carte1 = chargeImage("Card_1");
        carte2 = chargeImage("Card_2");
        carte3 = chargeImage("Card_3");
        carte4 = chargeImage("Card_4");
        carte5 = chargeImage("Card_5");

        carte1_select = chargeImage("Card_1_selected");
        carte2_select = chargeImage("Card_2_selected");
        carte3_select = chargeImage("Card_3_selected");
        carte4_select = chargeImage("Card_4_selected");
        carte5_select = chargeImage("Card_5_selected");


        joueurs1 = new Image[4];
        joueurs2 = new Image[4];
        for(int i = 0; i < 4; i++){
            joueurs1[i] = chargeImage("luke_stand_0"+i);
            joueurs2[i] = chargeImage("vador_stand_0"+i);
        }

        etape = 0;
        joueur1 = joueurs1[etape];
        joueur2 = joueurs2[etape];
    }

    public void paintComponent(Graphics g){
        Graphics2D drawable = (Graphics2D) g;
        largeur = getSize().width;
        hauteur = getSize().height;

        drawable.clearRect(0, 0, largeur, hauteur);

        largeurCase = largeur / nbColonnes;
        hauteurCase = (int)Math.round(largeurCase*0.35);
        hauteurLuke = (int)Math.round(largeurCase * 2.10);
        hauteurVador = (int)Math.round(largeurCase * 1.94);
        largeurVador = (int)Math.round(largeurCase * 1.50);
        xTeteDroite = (int)Math.round(largeur*0.05);
        xTeteGauche = (int)Math.round((largeur*.95)-(largeurCase*1.75));
        yTete = (int)Math.round(hauteur*0.05);
        dimensionTete = (int)Math.round(largeurCase*1.75);

        drawable.drawImage(map, 0, 0, largeur, hauteur, null);
        drawable.drawImage(teteJ1, xTeteDroite, yTete, dimensionTete ,dimensionTete,null);
        drawable.drawImage(teteJ2, xTeteGauche, yTete, dimensionTete ,dimensionTete,null);
        for(int c = 0; c < 23; c++){
            int x = c * largeurCase;
            int y = (int) Math.round(hauteur * 0.62);
            drawable.drawImage(sol, x, y, largeurCase, hauteurCase, null);
            if(c == 0){
                drawable.drawImage(joueur1, x, (int)Math.round(y-hauteurLuke+(hauteurCase*0.5)), largeurCase, hauteurLuke, null);
            }else if(c == 22){
                drawable.drawImage(joueur2, x, (int)Math.round(y-hauteurVador+(hauteurCase*0.5)), largeurVador, hauteurVador, null);
            }
        }

        if(jeu.partie().manche().getTourJoueur() == 1)
        {
            afficheMainJoueur(jeu.partie().manche().Joueur(1), drawable);
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

    public void selectCarte(int val ,int x, int y, int l, int h, Graphics2D drawable){


        switch(val) {
            case 1:
                drawable.drawImage(carte1_select, x , y, l, h, null);
                break;
            case 2:
                drawable.drawImage(carte2_select, x , y, l, h, null);
                break;
            case 3:
                drawable.drawImage(carte3_select, x , y, l, h, null);
                break;
            case 4:
                drawable.drawImage(carte4_select, x , y, l, h, null);
                break;
            case 5:
                drawable.drawImage(carte5_select, x , y, l, h, null);
                break;

        }
    }

    public void afficheMainJoueur(JoueurHumain j, Graphics2D drawable){

        int largeurCarte = (int) Math.round(largeur * 0.30)/5;
        int hauteurCarte = (int) Math.round(hauteur * 0.15);

        int nbCartes = j.main.size();

        int x = (largeur/2)-((nbCartes*largeurCarte)/2)-largeurCarte;
        int y = (int) Math.round(hauteur * 0.75);


        for(int i = 0; i < j.main.size(); i++){

            int valeurCarte = j.main.get(i);


            x = x+largeurCarte;

            switch(valeurCarte) {
                case 1:
                    drawable.drawImage(carte1, x , y, largeurCarte, hauteurCarte, null);
                    break;
                case 2:
                    drawable.drawImage(carte2, x , y, largeurCarte, hauteurCarte, null);
                    break;
                case 3:
                    drawable.drawImage(carte3, x, y, largeurCarte, hauteurCarte, null);
                    break;
                case 4:
                    drawable.drawImage(carte4, x, y, largeurCarte, hauteurCarte, null);
                    break;
                case 5:
                    drawable.drawImage(carte5, x, y, largeurCarte, hauteurCarte, null);
                    break;

            }
            if(j.getCarteI().size() < 5){
                j.initCarteI(i, valeurCarte, x, y, largeurCarte, hauteurCarte);
            } else {
                j.updateCarteI(i, valeurCarte, x, y, largeurCarte, hauteurCarte);
            }

            CarteIHM carte = j.carteI.get(i);

            if(jeu.selectedCarte != null && j.carteI.get(i).getId() == i){


                jeu.selectedCarte.update(i, valeurCarte, x, y, largeurCarte, hauteurCarte);
            }
        }
    }
}
