package Controller;

import Modele.CarteIHM;
import Modele.Jeu;
import Modele.SelectionCaseIHM;
import Structures.Iterateur;
import Structures.Sequence;
import Structures.SequenceListe;
import Vue.ButtonIHM;
import Vue.CollecteurEvenements;
import Vue.InterfaceGraphique;
import Vue.NiveauGraphique;
import Modele.Coup;

import java.util.ArrayList;

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

		JoueurHumain joueur = jeu.partie().Joueur(jeu.partie().manche().getTourJoueur());
		for(int i = 0; i < joueur.getCarteI().size(); i++){
			CarteIHM c = joueur.getCarteI().get(i);
			if(x >= c.getCoordX() && x <= (c.getCoordX() + c.getLargeur()) && c.getEtat() != 1){
				if((y >= c.getCoordY() && y <= (c.getCoordY() + c.getHauteur()))){

					 jeu.SelectionCarte(i, c.getValeur(), c.getCoordX(), c.getCoordY(), c.getLargeur(), c.getHauteur());
					 jeu.partie().manche().listerCoups(joueur, jeu.selectedCarte);

				}
			}
		}
	}

	public void clickDeplacement(int x, int y){
		ArrayList<SelectionCaseIHM> CaseIHM = new ArrayList<>();
		CaseIHM = jeu.partie().manche().getCaseIHM();

		for(int i = 0; i < CaseIHM.size(); i++){
			SelectionCaseIHM c = CaseIHM.get(i);
			if(c.getEtat() != 0 && x >= c.getX() && x <= (c.getX() + c.getLargeur())){
				if((y >= c.getY() && y <= (c.getY() + c.getHauteur()))){


					if (c.getEtat() == 1){
						int[] valeurs= new int[5];
						valeurs[0] = jeu.selectedCarte.getValeur();
						Coup cp = jeu.determinerCoup(c.getId(), valeurs,jeu.partie().manche().grilleJeu, 1);
						jeu.jouerCoup(cp);
						jeu.selectedCarte.reset();

					} else if (c.getEtat() == 2){
						int[] valeurs = new int[5];
						valeurs[0] = jeu.selectedCarte.getValeur();
						Coup cp = jeu.determinerCoup(c.getId(), valeurs, jeu.partie().manche().grilleJeu, 2);
						jeu.jouerCoup(cp);

						jeu.selectedCarte = null;

						//jeu.partie().manche().updateAll();
						jeu.partie().manche().changeTourJoueur(jeu.partie().manche().getTourJoueur());
						jeu.partie().initialiseManche();

					}
				}
			}
		}
	}

	public void clickChangeTour(int x, int y)
	{
		if(jeu.partie().manche().boutonChangeTour != null)
		{
			ButtonIHM but = jeu.partie().manche().boutonChangeTour;
			if (x >= but.getX() && x < but.getX() + but.getLargeur()){
				if (y >= but.getY() && y < but.getY() + but.getHauteur()){
					jeu.partie().manche().changeTourJoueur(jeu.partie().manche().tourJoueur);
					jeu.selectedCarte = null;
					jeu.partie().manche().updateAll();
					System.out.println("Je change le tour");
				}
			}
		}

	}



	public void tictac(){
		if(inter.getMenu()){
			inter.MAJPanelMenu();
		}
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
			case "Niveau":
				inter.changeBackground(false, true, false);
				break;
			case "Option":
				inter.changeBackground(false, false, true);
				break;
			case "Regle":
				break;
			case "Suivant":
				if(inter.niv().compteur < 10){
					inter.niv().compteur++;
				}
				break;
			case "Revenir":
				if(inter.niv().compteur > 0){
					inter.niv().compteur--;
				}
				break;
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
