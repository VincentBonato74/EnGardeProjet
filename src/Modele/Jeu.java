package Modele;

import Patterns.Observable;

import java.util.ArrayList;

public class Jeu extends Observable {
    Partie courant;
    public CarteIHM selectedCarte;
    public SelectionCaseIHM selectedCase;

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

    public Coup determinerCoup(int type, int[] valeurs, int[] grilleJeu, int typeAction)
    {
        return courant.manche().joue(type, valeurs, grilleJeu,typeAction );
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
        ArrayList<SelectionCaseIHM> CaseIHM = new ArrayList<>();
        CaseIHM = partie().manche().CaseIHM;
        for (int i = 0; i < CaseIHM.size(); i++){
            partie().manche().CaseIHM.get(i).updateEtat(0);
        }
        selectedCarte = new CarteIHM(id, val, x, y, l, h);
        miseAJour();
    }
}
