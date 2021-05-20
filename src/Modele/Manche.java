package Modele;
import java.util.*;
import Controller.*;
//import

public class Manche {
    Partie partie;
    ArrayList<Integer> piocheCartes = new ArrayList<>();
    public int[] grilleJeu;
    int tourJoueur;
    JoueurHumain joueur1, joueur2;


    public Manche(Partie p){

        partie = p;
        //int sel = p.jeu.selectedCarte;

        //Les joueurs de la partie associés à la manche
        joueur1 = partie.Joueur(1);
        joueur2 = partie.Joueur(2);

        //Initialiser la pioche de la manche
        initialiserPioche();

        grilleJeu = new int [23];
        //Situation du joueur 1 au début de la partie
        grilleJeu[10] = 1;
        joueur1.position = 10;
        joueur1.direction = 1;
        //Situation du joueur 2 au début de la partie
        grilleJeu[14] = 2;
        joueur2.position = 14;
        joueur2.direction = -1;
        remplirMain(joueur1);
        remplirMain(joueur2);
        System.out.println("Pioche complete : " + piocheCartes);

        tourJoueur = 1;

    }
    public void clickCarte(){
        //listerCoups(joueur1, p.jeu.selectedCarte);

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

    public void listerCoups(JoueurHumain j, CarteIHM carte){
        int newPos;
        int dir = j.direction;
        int valeurCarte = carte.getValeur();
        System.out.println("carte : " + valeurCarte);
        if(dir == 1) { // joueur à gauche
            if(j.position >= valeurCarte){
                newPos = j.position - valeurCarte;
                System.out.println("peut reculer en " + newPos);
            }
            newPos = j.position + valeurCarte;
            if(newPos <= 22){
                if(newPos == joueur2.position ){
                    System.out.println("peut attaquer le joueur avec carte " + valeurCarte);
                }else if(newPos < joueur2.position){
                    System.out.println("peut avancer en " + newPos);
                }else{
                    System.out.println("bloqué par joueur");
                }
            }
        }

        if(dir == -1){ // joueur à droite
            if(j.position +valeurCarte <= 22){
                newPos = j.position + valeurCarte;
                System.out.println("peut reculer en " + newPos);
            }
            newPos = j.position + valeurCarte;
            if(newPos >= 0){
                if(newPos == joueur1.position ){
                    System.out.println("peut attaquer le joueur avec carte " + valeurCarte);
                }else if(newPos > joueur2.position){
                    System.out.println("peut avancer en " + newPos);
                }else{
                    System.out.println("bloqué par joueur");
                }
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
