package Controller;

import Modele.CarteIHM;
import Modele.Jeu;
import Modele.SelectionCaseIHM;
import Structures.Iterateur;
import Structures.Sequence;
import Structures.SequenceListe;
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
		joueurCourant = 1; //jeu.partie().getTourJoueur();
		Joueur1 = jeu.partie().Joueur(1);
		Joueur2 = jeu.partie().Joueur(2);
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

					 jeu.SelectionCarte(i, c.getValeur(), c.getCoordX(), c.getCoordY(), c.getLargeur(), c.getHauteur());

					 jeu.partie().manche().listerCoups(jeu.partie().Joueur(jeu.partie().manche().getTourJoueur()), jeu.selectedCarte);


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
						Coup cp = jeu.determinerCoup(c.getId(), valeurs,jeu.partie().manche().grilleJeu);
						jeu.jouerCoup(cp);
						jeu.selectedCarte.reset();
						jeu.partie().manche().remplirMain(jeu.partie().Joueur(jeu.partie().manche().getTourJoueur()));
					} else if (c.getEtat() == 2){
						jeu.partie().manche().attaque(jeu.partie().manche().getTourJoueur());
						jeu.selectedCarte = null;
						jeu.partie().initialiseManche();

					}
				}
			}
		}
	}

	public void clickChange(int x, int y){
		if((y >= inter.niv().yBouton && y <= (inter.niv().yBouton + inter.niv().tailleBouton))){
			System.out.println("Salut y");
			if((x >= inter.niv().xBouton1 && x <= (inter.niv().xBouton1 + inter.niv().tailleBouton))) {
				System.out.println("Salut x1");
				if(inter.niv().compteurJ1 == 0){
					inter.niv().compteurJ1 = 1;
				}else{
					inter.niv().compteurJ1--;
				}
			}else if((x >= inter.niv().xBouton2 && x <= (inter.niv().xBouton2 + inter.niv().tailleBouton))) {
				System.out.println("Salut x2");
				inter.niv().compteurJ1 = (inter.niv().compteurJ1+1)%2;
			}else if((x >= inter.niv().xBouton3 && x <= (inter.niv().xBouton3 + inter.niv().tailleBouton))){
				System.out.println("Salut x3");
				if(inter.niv().compteurJ2 == 0){
					inter.niv().compteurJ2 = 1;
				}else{
					inter.niv().compteurJ2--;
				}
			}else if((x >= inter.niv().xBouton4 && x <= (inter.niv().xBouton4 + inter.niv().tailleBouton))){
				System.out.println("Salut x4");
				inter.niv().compteurJ2 = (inter.niv().compteurJ2+1)%2;
			}else if((x >= inter.niv().xBouton5 && x <= (inter.niv().xBouton5 + inter.niv().tailleBouton))){
				System.out.println("Salut x5");
				if(inter.niv().compteurMap == 0){
					inter.niv().compteurMap = 7;
				}else{
					inter.niv().compteurMap--;
				}
			}else if((x >= inter.niv().xBouton6 && x <= (inter.niv().xBouton6 + inter.niv().tailleBouton))){
				System.out.println("Salut x6");
				inter.niv().compteurMap = (inter.niv().compteurMap+1)%8;
			}
		}
		System.out.println(inter.niv().compteurJ1);
		System.out.println(inter.niv().compteurJ2);
		System.out.println(inter.niv().compteurMap);
		inter.niv().metAJour();
	}

	public void avancer() {
		int[] valeurs= new int[5];
		valeurs[0] = jeu.selectedCarte.getValeur();
		Coup cp = jeu.determinerCoup(1, valeurs,jeu.partie().manche().grilleJeu);
		jeu.jouerCoup(cp);
		jeu.selectedCarte.reset();

	}

	public void reculer() {
		int[] valeurs= new int[5];
		valeurs[0] = jeu.selectedCarte.getValeur();
		Coup cp = jeu.determinerCoup(2, valeurs,jeu.partie().manche().grilleJeu);
		jeu.jouerCoup(cp);
		jeu.selectedCarte.reset();
	}



	public void tictac(){
		if(inter.getMenu()){
			inter.MAJPanelMenu();
		}
		if (inter.getRegles()) {
			inter.MAJPanelRegle();
		}
		if(inter.getNewPartie()){
			inter.MAJPanelNewPartie();
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
    public boolean commande(String c) {
        switch (c) {
			case "NewPartie":
				inter.changeBackground(false, false, false, true);
				break;
			case "Regles":
				inter.changeBackground(false, false, true, false);
				break;
			case "RetourMenu":
				inter.changeBackground(true, false, false, false);
				break;
			case "PartieLance":
				Jeu jeu = new Jeu();
				inter.changeBackground(false, true, false, false);
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
