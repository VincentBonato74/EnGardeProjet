package Controller;

import Modele.CarteIHM;
import Modele.Jeu;
import Structures.Iterateur;
import Structures.Sequence;
import Structures.SequenceListe;
import Vue.CollecteurEvenements;
import Vue.InterfaceGraphique;
import Vue.NiveauGraphique;
import Modele.Coup;

public class ControllerMediateur implements CollecteurEvenements {
	Jeu jeu;
	JoueurHumain Joueur1, Joueur2;
	final int lenteurAttente = 50;
	int joueurCourant;
	int decompte;
	Sequence<Animation> animations;
	InterfaceGraphique inter;


	public ControllerMediateur(Jeu j){
		jeu = j;
		animations = new SequenceListe<>();
		Joueur1 = j.partie().Joueur(1);
		Joueur2 = j.partie().Joueur(2);
		joueurCourant = 1; //jeu.partie().getTourJoueur();
	}

	public void fixerInterfaceGraphique(InterfaceGraphique i){
		inter = i;
		animations.insereQueue(new AnimationJoueur(inter));
	}


	public void clickCarte(int x, int y){
		//System.out.print("joueur: " + jeu.partie().Joueur(joueurCourant).carteI + "\n");
		//System.out.println("test : " + jeu.partie().Joueur(joueurCourant).getCarteI().size());
		for(int i = 0; i < jeu.partie().Joueur(jeu.partie().manche().getTourJoueur()).getCarteI().size(); i++){
			CarteIHM c = jeu.partie().Joueur(jeu.partie().manche().getTourJoueur()).getCarteI().get(i);
			if((x >= c.getCoordX() && x <= (c.getCoordX() + c.getLargeur()))){
				if((y >= c.getCoordY() && y <= (c.getCoordY() + c.getHauteur()))){

					System.out.print("Carte: " + c.getValeur() + "\n");

					 jeu.SelectionCarte(i, c.getValeur(), c.getCoordX(), c.getCoordY(), c.getLargeur(), c.getHauteur());

					 jeu.partie().manche().listerCoups(jeu.partie().Joueur(jeu.partie().manche().getTourJoueur()), jeu.selectedCarte);


				}
			}
		}
	}

	public void avancer()
	{
		int[] valeurs= new int[5];
		valeurs[0] = jeu.selectedCarte.getValeur();
		Coup cp = jeu.determinerCoup(1, valeurs,jeu.partie().manche().grilleJeu);
		System.out.println(jeu.partie().manche().getTourJoueur());
		jeu.jouerCoup(cp);
		jeu.partie().Joueur(jeu.partie().manche().getTourJoueur()).supprMain(jeu.selectedCarte.getId());
		jeu.selectedCarte.reset();




		//System.out.println("Tour du Joueur (1) : " + jeu.partie().manche().tourJoueur);
	}

	public void reculer()
	{
		int[] valeurs= new int[5];
		valeurs[0] = jeu.selectedCarte.getValeur();
		Coup cp = jeu.determinerCoup(2, valeurs,jeu.partie().manche().grilleJeu);
		jeu.jouerCoup(cp);
		jeu.partie().Joueur(jeu.partie().manche().getTourJoueur()).supprMain(jeu.selectedCarte.getId());
		jeu.selectedCarte.reset();
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
			case "avancer":
				avancer();

				//System.out.println("Tour du Joueur (3) :" + jeu.partie().manche().getTourJoueur());
				break;
			case "reculer":
				reculer();
				break;
			default:
				return false;
		}

		return true;
    }
}
