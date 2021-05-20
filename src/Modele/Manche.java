package Modele;
import java.util.*;
import Controller.*;
//import

public class Manche {

    static final int AVANCER = 1;
    static final int RECULER = 2;
    static final int ATTAQUER = 3;
    static final int ATTAQUE_INDIRECTE = 4;
    static final int BLOQUER = 5;

    Partie partie;
    ArrayList<Integer> piocheCartes = new ArrayList<>();
    public int[] grilleJeu;
    int tourJoueur;
    JoueurHumain joueur1, joueur2, joueurCourant;


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
        //System.out.println("carte : " + valeurCarte);
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

    public void jouerCoup(Coup cp) {
        cp.fixerManche(this);
        nouveau(cp);

        changeTourJoueur(tourJoueur);

    }

    public void miseAJourGrille(int oldPosJ1, int oldPosJ2, int posJ1, int posJ2){
        // Les conditions sont codées de manière à ce que si un joueur bouge, l'autre ne bouge pas
        // Si les deux conditions ne sont pas respectées, cela veut dire qu'aucun joueur n'a bougé
        if(posJ1 != oldPosJ1){
            this.grilleJeu[oldPosJ1] = 0;
            this.grilleJeu[posJ1] = 1;
        } else if (posJ2 != oldPosJ2) {
            this.grilleJeu[oldPosJ2] = 0;
            this.grilleJeu[posJ2] = 2;
        } else {
            System.out.println("Aucun joueur n'a bougé");
        }
    }


    public Coup joue(int type, int[] valeurs, int[] grilleJeu){
        JoueurHumain joueurCourant;
        Action action = new Action(type, valeurs);
        Coup coupCourrant = new Coup(grilleJeu, action);

        //_____________________  Recupérer le joueur courant
        System.out.println("Tour du joueur (2) : " + tourJoueur);
        joueurCourant = Joueur(tourJoueur);

        //======================================================================= CAS ACTION EST UN DPLACEMENT
        if(type == AVANCER || type == RECULER) {

            //_____________________ Détermine de combien de cases on va se déplacer
            int nbDeplacement = valeurs[0];

            //_____________________ Récuperation des positions courantes
            int target = 0;
            int oldPosJ1 = this.joueur1.getPosition();
            int oldPosJ2 = this.joueur2.getPosition();

            //======================================================================= CAS ACTION = AVANCER
            if (type == AVANCER) {
                target = joueurCourant.targetAvant(nbDeplacement);
            }
            //======================================================================= CAS ACTION = RECULER
            else if (type == RECULER) {
                target = joueurCourant.targetArriere(nbDeplacement);
            }
            if (target > 0 && target < grilleJeu.length && estVide(this.grilleJeu[target])) {

                //_____________________  On met à jour le joueur courant
                joueurCourant.deplace(target);
                if (tourJoueur == 1) {
                    this.joueur1 = joueurCourant;
                } else {
                    this.joueur2 = joueurCourant;
                }

                //_____________________ On met à jour les infos générales du jeu.
                miseAJourGrille(oldPosJ1, oldPosJ2, this.joueur1.getPosition(), this.joueur2.getPosition());
                //joueurCourant.supprMain(partie.jeu.selectedCarte.getId());
                System.out.println("main joueur 1 : " + joueur1.main);
                System.out.println("main joueur 2 :" + joueur2.main);

                /*if ((tourJoueur % 2) + 1 == 1) {
                    this.joueur1.supprMain(partie.jeu.selectedCarte.getId());
                } else {
                    this.joueur2.supprMain(partie.jeu.selectedCarte.getId());
                }*/

            return coupCourrant;
        } else {
            System.out.println("Déplacement impossible, personnage sur la case destination ou destination hors map");
                return null;
            }
        }
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
