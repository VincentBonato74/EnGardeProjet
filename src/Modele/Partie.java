package Modele;

public class Partie {
    boolean J1Gagnant, J2Gagnant;
    int J1Point, J2Point;
    int[] grilleJeu;
    int tourJoueur;

    Partie(){
        initialisePartie();
    }

    public void initialisePartie(){
        grilleJeu = new int[23];
        J1Gagnant = false;
        J2Gagnant = false;
        J1Point = 0;
        J2Point = 0;
        tourJoueur = 1;
        //Situation du joueur 1 au début de la partie
        grilleJeu[0] = 1;
        //Situation du joueur 2 au début de la partie
        grilleJeu[22] = 2;
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

    public int getJ1Point(){
        return J1Point;
    }

    public int getJ2Point(){
        return J2Point;
    }

    public boolean estJ1(int nb){
        return grilleJeu[nb] == 1;
    }

    public boolean estJ2(int nb){
        return grilleJeu[nb] == 2;
    }

    public boolean estVide(int nb){
        return grilleJeu[nb] == 0;
    }

    public int getTourJoueur(){
        return tourJoueur;
    }
}
