package Modele;

import Controller.JoueurHumain;
import Patterns.Observable;

public class Partie {
    boolean J1Gagnant, J2Gagnant;
    //int J1Point, J2Point;
    Jeu jeu;
    Manche courant;
    JoueurHumain joueur1, joueur2;
    //int[] PointJ1;
    //int[] PointJ2;

    public Partie(Jeu j){
        jeu = j;
        initialisePartie();
    }

    public void initialisePartie(){

        joueur1 = new JoueurHumain(jeu);
        joueur2 = new JoueurHumain(jeu);
        joueur1.vie = 5;
        joueur2.vie = 5;
        courant = new Manche(this);
        J1Gagnant = false;
        J2Gagnant = false;
        /*J1Point = 0;
        J2Point = 0;
        PointJ1 = new int[5];
        PointJ2 = new int[5];*/
    }

    public void initialiseManche(){
        courant = null;
        courant = new Manche(this);
    }

    public boolean aGagner(){
        if(joueur1.vie == 0){
            J2Gagnant = true;
            return true;
        }else if(joueur2.vie == 0){
            J1Gagnant = true;
            return true;
        }
        return false;
    }

    public void jouerCoup(Coup cp) {
        courant.jouerCoup(cp);
    }

    /*public int getJ1Point(){
        return J1Point;
    }

    public int getJ2Point(){
        return J2Point;
    }*/

    public JoueurHumain Joueur(int numJoueur)
    {
        if(numJoueur == 1)
        {
            return joueur1;
        }
        else
        {
            return joueur2;
        }
    }

    public Manche manche()
    {
        return  courant;
    }

    /*public void ajoutePoint(int tourJoueur)
    {
        if(tourJoueur == 1)
        {
            J1Point +=1;
        }
        else
        {
            J2Point +=1;
        }
    }*/

}
