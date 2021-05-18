package Modele;

import Patterns.Observable;

public class Jeu extends Observable {
    Partie courant;

    public Jeu()
    {
        initialisePartie();
    }

    public void initialisePartie(){
        courant = new Partie();
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
}
