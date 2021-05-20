package Modele;

import Controller.JoueurHumain;
import Patterns.Observable;

public class Partie {
    boolean J1Gagnant, J2Gagnant;
    int J1Point, J2Point;
    Jeu jeu;
    Manche courant;
    JoueurHumain joueur1, joueur2;

    public Partie(Jeu j){
        jeu = j;
        initialisePartie();

    }

    public void initialisePartie(){

        joueur1 = new JoueurHumain(jeu);
        joueur2 = new JoueurHumain(jeu);
        courant = new Manche(this);
        J1Gagnant = false;
        J2Gagnant = false;
        J1Point = 0;
        J2Point = 0;

    }

    public boolean aGagner(){
        if(J1Point == 5){
            J1Gagnant = true;
            return true;
        }else if(J2Point == 5){
            J2Gagnant = true;
            return true;
        }
        return false;
    }

    public void jouerCoup(Coup cp) {
        courant.jouerCoup(cp);
    }

    public int getJ1Point(){
        return J1Point;
    }

    public int getJ2Point(){
        return J2Point;}

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



}
