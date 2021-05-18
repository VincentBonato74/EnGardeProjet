package Vue;

import Modele.Jeu;
import Patterns.Observateur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class NiveauGraphique extends JComponent implements Observateur {
    Jeu jeu;
    Image joueur1, joueur2, sol, tatooine;
    int etape, largeur, hauteur, nbColonnes, largeurCase, hauteurCase, hauteurLuke, hauteurVador, largeurVador;
    Image[] joueurs1;
    Image[] joueurs2;

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

    public NiveauGraphique(Jeu j){
        jeu = j;
        nbColonnes = 23;
        sol = chargeImage("Sol");
        joueur2 = chargeImage("Vador_1_0");
        tatooine = chargeImage("tatooine");

        joueurs1 = new Image[4];
        //joueurs2 = new Image[4];
        for(int i = 0; i < 4; i++){
            joueurs1[i] = chargeImage("Luke_0_"+i);
            //joueurs2[i] = chargeImage("Vador_1_"+i);
        }

        etape = 0;
        joueur1 = joueurs1[etape];
        //joueur2 = joueurs2[etape];
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

        drawable.drawImage(tatooine, 0, 0, largeur, hauteur, null);

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

    }

    @Override
    public void metAJour() {
        repaint();
    }

    public void animJoueur() {
        etape = (etape+1)%4;
        joueur1 = joueurs1[etape];
        //joueur2 = joueurs2[etape];
        metAJour();
    }
}
