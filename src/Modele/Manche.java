package Modele;
import java.util.*;
import Controller.*;

public class Manche {
    List piocheCartes = new ArrayList();


    public Manche(){
        initialiserPioche();
    }

    public void initialiserPioche(){
        for(int i =1; i<=5;i++){
            for(int j=1; j<=5;j++){
                piocheCartes.add(i);
            }
        }
        System.out.println("Pioche non triee : " + piocheCartes);
        Collections.shuffle(piocheCartes);
        System.out.println("Pioche melangee :" + piocheCartes);


    }

    public int pioche(JoueurHumain j){ // le joueur récupère une carte dans la pioche
        int taille = j.main.size();
        int res = 0;
        if(taille <5){
            res = (int)piocheCartes.get(0);
            piocheCartes.remove(0);

        }
        return res;
    }



}
