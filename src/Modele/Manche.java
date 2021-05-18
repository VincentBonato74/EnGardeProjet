package Modele;
import java.util.*;
import Controller.*;
//import

public class Manche {
    Partie partie;
    ArrayList<Integer> piocheCartes = new ArrayList<>();
    int[] grilleJeu;
    int tourJoueur;
    JoueurHumain joueur1, joueur2;


    public Manche(Partie p){

        partie = p;

        //Les joueurs de la partie associés à la manche
        joueur1 = partie.Joueur(1);
        joueur2 = partie.Joueur(2);

        //Initialiser la pioche de la manche
        initialiserPioche();

        grilleJeu = new int [23];
        //Situation du joueur 1 au début de la partie
        grilleJeu[0] = 1;
        joueur1.position = 0;
        joueur1.direction = 1;
        //Situation du joueur 2 au début de la partie
        grilleJeu[22] = 2;
        joueur2.position = 22;
        joueur2.direction = -1;
        remplirMain(joueur1);
        remplirMain(joueur2);
        System.out.println("Pioche complete : " + piocheCartes);
        listerCoups(joueur1);
        listerCoups(joueur2);

        tourJoueur = 1;

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
    public void remplirMain(JoueurHumain j){
        while(j.main.size() < 5){
            int carte = pioche();
            j.main.add(carte);

        }
        System.out.println("main complete joueur : " + j.main);
    }

    public int pioche(){ // le joueur récupère une carte dans l
        int res;
        res = piocheCartes.get(0);
        piocheCartes.remove(0);
        return res;
    }

    public void listerCoups(JoueurHumain j){
        int newPos;
        int dir = j.direction;
        System.out.println("main complete joueur : " + j.main);
        for(int i=0;i<j.main.size();i++){
            int valeurCarte = j.main.get(i);
            System.out.println("carte : " + valeurCarte);
            if(j.position >= valeurCarte){
                newPos = j.position - (dir * valeurCarte);
                System.out.println("peut reculer en " + newPos);
            }
            if(j.position + valeurCarte < 22 && grilleJeu[j.position+valeurCarte] == 0){
                newPos = j.position + (dir * valeurCarte);
                System.out.println("peut avancer en " + newPos);
            }
        }
    }

    public void jouerCoup(Coup cp)
    {
        cp.fixerManche(this);
        //nouveau(cp)


    }

    public Coup joue()
    {
        return null;
    }

    public int getTourJoueur(){ return tourJoueur;}

    public boolean estJ1(int nb){
        return grilleJeu[nb] == 1;
    }

    public boolean estJ2(int nb){
        return grilleJeu[nb] == 2;
    }

    public boolean estVide(int nb){
        return grilleJeu[nb] == 0;
    }

    public JoueurHumain Joueur(int numJoueur)
    {
        if(numJoueur == 1)
        {
            return joueur1;
        }
        else
        {
            return joueur2;
        }
    }

}
