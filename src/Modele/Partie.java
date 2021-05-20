package Modele;

import Patterns.Observable;

public class Partie {
    boolean J1Gagnant, J2Gagnant;
    int J1Point, J2Point;
    Manche courant;
    int[] PointJ1;
    int[] PointJ2;

    public Partie(){
        initialisePartie();
    }

    public void initialisePartie(){
        courant = new Manche();
        J1Gagnant = false;
        J2Gagnant = false;
        J1Point = 0;
        J2Point = 0;
        PointJ1 = new int[5];
        PointJ2 = new int[5];
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
        return J2Point;
    }

    public boolean estRougeJ1(int nb){
        if(PointJ1[nb] == 1){
            return true;
        }
        return false;
    }

    public boolean estRougeJ2(int nb){
        if(PointJ2[nb] == 1){
            return true;
        }
        return false;
    }
}
