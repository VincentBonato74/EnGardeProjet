package Modele;

import Patterns.Observable;

public class Jeu extends Observable {
    Partie courant;
    public CarteIHM selectedCarte;

    public Jeu()
    {
        initialisePartie();
    }

    public void initialisePartie(){
        courant = new Partie(this);
        miseAJour();
    }

    public Partie partie() {
        return courant;
    }

    public void jouerCoup(Coup cp) {
        if (cp == null) {
            System.out.println("Salut");
            //Configuration.instance().logger().info("Déplacement impossible");
        } else {
            courant.jouerCoup(cp);
            miseAJour();
        }
    }

    public void SelectionCarte(int id, int val ,int x, int y, int l, int h) {
        selectedCarte = new CarteIHM(id, val, x, y, l, h);
        miseAJour();
    }
}
