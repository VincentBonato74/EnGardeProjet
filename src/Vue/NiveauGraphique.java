package Vue;

import Modele.Jeu;
import Patterns.Observateur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class NiveauGraphique extends JComponent implements Observateur {
    Jeu jeu;
    Image j1, j2, sol;
    int largeur, hauteur, nbColonnes, largeurCase, hauteurCase, hauteurLuke, hauteurVador, largeurVador;

    private Image chargeImage(String nom){
        Image img = null;

        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(nom + ".png");
        try{
            img = ImageIO.read(in);
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
        j1 = chargeImage("Luke_1_0");
        j2 = chargeImage("Vador_1_0");
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

        for(int c = 0; c < 23; c++){
            int x = c * largeurCase;
            int y = (int) Math.round(hauteur * 0.5);
            drawable.drawImage(sol, x, y, largeurCase, hauteurCase, null);
            if(c == 0){
                drawable.drawImage(j1, x, (int)Math.round(y-hauteurLuke+(hauteurCase*0.5)), largeurCase, hauteurLuke, null);
            }else if(c == 22){
                drawable.drawImage(j2, x, (int)Math.round(y-hauteurVador+(hauteurCase*0.5)), largeurVador, hauteurVador, null);
            }
        }

    }


    @Override
    public void metAJour() {
        repaint();
    }
}
