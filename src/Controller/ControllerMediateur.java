package Controller;

import Modele.CarteIHM;
import Modele.Jeu;
import Structures.Iterateur;
import Structures.Sequence;
import Structures.SequenceListe;
import Vue.CollecteurEvenements;
import Vue.InterfaceGraphique;
import Vue.NiveauGraphique;

public class ControllerMediateur implements CollecteurEvenements {
	Jeu jeu;
	JoueurHumain Joueur1, Joueur2;
	final int lenteurAttente = 50;
	int joueurCourant;
	int decompte;
	Sequence<Animation> animations;
	InterfaceGraphique inter;
	NiveauGraphique niv;

	public ControllerMediateur(Jeu j){
		jeu = j;
		animations = new SequenceListe<>();
		Joueur1 = j.partie().Joueur(1);
		Joueur2 = j.partie().Joueur(2);
		joueurCourant = 0; //jeu.partie().getTourJoueur();
	}

	public void fixerInterfaceGraphique(InterfaceGraphique i){
		inter = i;
		animations.insereQueue(new AnimationJoueur(inter));
	}


	public void clickCarte(int x, int y){

		for(int i = 0; i < Joueur1.getCarteI().size(); i++){
			CarteIHM c = Joueur1.getCarteI().get(i);
			if((x >= c.getCoordX() && x <= (c.getCoordX() + c.getLargeur()))){
				if((y >= c.getCoordY() && y <= (c.getCoordY() + c.getHauteur()))){

					 System.out.print("Carte: " + c.getValeur() + "\n");

					 //inter.SelectionCarte(c.getValeur(), c.getCoordX(), c.getCoordY(), c.getLargeur(), c.getHauteur());
					 jeu.SelectionCarte(i, c.getValeur(), c.getCoordX(), c.getCoordY(), c.getLargeur(), c.getHauteur());
				}
			}
		}
	}

	public void tictac(){
		Iterateur<Animation> it = animations.iterateur();
		while(it.aProchain()){
			Animation anim = it.prochain();
			anim.tictac();
		}
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
