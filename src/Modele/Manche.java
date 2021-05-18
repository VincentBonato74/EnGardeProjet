package Modele;
import java.util.*;
import Controller.*;
//import

public class Manche {
    Partie partie;
    ArrayList<Integer> piocheCartes = new ArrayList<>();
    int[] grilleJeu;
    int tourJoueur;
    JoueurHumain joueur1,joueur2;


    public Manche(){

        initialiserPioche();

        grilleJeu = new int [23];
        //Situation du joueur 1 au début de la partie
        grilleJeu[0] = 1;
        //Situation du joueur 2 au début de la partie
        grilleJeu[22] = 2;
        remplirMain(joueur1);
        remplirMain(joueur2);

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
        /*while(j.main.size() < 5){
            int carte = pioche(j);
            j.main.add(carte);
            System.out.println("ajout de carte " + carte + "dans la main du joueur");
        }
        System.out.println("main complete joueur : " + j.main);
*/
    }

    public int pioche(JoueurHumain j){ // le joueur récupère une carte dans la pioche
        int taille = j.main.size();
        int res = 0;
        if(taille <5){
            res = piocheCartes.get(0);
            piocheCartes.remove(0);

        }
        return res;
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
