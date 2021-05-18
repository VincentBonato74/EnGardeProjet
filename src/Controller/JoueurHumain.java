package Controller;

import Modele.*;

import java.util.ArrayList;
import java.util.List;

public class JoueurHumain {
    Jeu jeu;
    public ArrayList<Integer> main= new ArrayList<>();
    public int position;
    public int direction;

    public JoueurHumain(Jeu j){
        jeu = j;

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
            System.out.println("Salut");
            //main.add(pioche(JoueurHumain));
        }
    }
}
