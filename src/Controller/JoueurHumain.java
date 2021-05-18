package Controller;

import Modele.*;

import java.util.ArrayList;
import java.util.List;

public class JoueurHumain {
    Jeu jeu;
    Manche manche;
    public List main;

    JoueurHumain(Jeu j){
        jeu = j;
        main = new ArrayList();
    }

    public boolean joue(int l, int c){
        /*if(jeu.niveau().estJouable(l, c)){
            Coup cp = jeu.determineCoup(l, c);
            jeu.jouerCoup(cp);
            return true;
        }else{
            System.out.println("Coup Non Jouable");
            return false;
        }*/


        return false;
    }

    public void completeMain(){
        while(main.size() != 5){
            //main.add(pioche(JoueurHumain));
        }
    }
}
