package Controller;

import Modele.*;

import java.util.ArrayList;
import java.util.List;

public class JoueurHumain {
    Jeu jeu;
    public ArrayList<Integer> main= new ArrayList<>();
    public int position;
    public int direction;
    public ArrayList<CarteIHM> carteI = new ArrayList<>();

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

    public void initCarteI(int id, int valeur, int Cx, int Cy, int l, int h){
        CarteIHM c1 = new CarteIHM(id, valeur, Cx, Cy, l, h);
        carteI.add(c1);
    }

    public void updateCarteI(int id, int valeur, int Cx, int Cy, int l, int h){
        CarteIHM c1 = carteI.get(id);
        c1.update(id, valeur, Cx, Cy, l, h);

    }

    public ArrayList<CarteIHM> getCarteI() {
        return carteI;
    }


    public List<Integer> getMain(){
        return main;
    }
}
