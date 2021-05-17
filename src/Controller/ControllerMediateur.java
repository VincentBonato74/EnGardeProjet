package Controller;

import Modele.Jeu;
import Vue.CollecteurEvenements;

public class ControllerMediateur implements CollecteurEvenements {
	Jeu jeu;
	JoueurHumain Joueur1, Joueur2;
	final int lenteurAttente = 50;
	int joueurCourant;
	int decompte;

	public ControllerMediateur(Jeu j){
		jeu = j;
		Joueur1 = new JoueurHumain(jeu);
		Joueur2 = new JoueurHumain(jeu);
		joueurCourant = jeu.partie().getTourJoueur();
	}

	public void tictac(){
		if(!jeu.partie().aGagner()){
			if(decompte == 0){
				if(joueurCourant == 2){
					//Faire jouer le joueur 2
					changeJoueur();
				}else{
					decompte = lenteurAttente;
				}
			}else{
				decompte--;
			}
		}else{
			joueurCourant = 1;
		}
	}

	public void changeJoueur(){
		if(joueurCourant == 1){
			joueurCourant = 2;
		}else{
			joueurCourant = 1;
		}
	}

    @Override
    public boolean commande(String c)
    {
        switch (c) {

			case "quit":
				System.exit(0);
				break;
			case "fullscreen":
				break;
			default:
				return false;
		}
		return true;
    }
}
