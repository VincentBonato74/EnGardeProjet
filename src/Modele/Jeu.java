package Modele;

import Patterns.Observable;

public class Jeu extends Observable {
    Partie courant;

    public Jeu()
    {
        initializePartie();
    }

    public void initializePartie(){
        courant = new Partie();
        miseAJour();
    }

    public Partie partie() {
        return courant;
    }
}
