package Vue;

import Controller.JoueurHumain;
import Modele.Jeu;
import Modele.SelectionCaseIHM;
import Patterns.Observateur;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class NiveauGraphique extends JComponent implements Observateur {
    Jeu jeu;
    Image fondMenu, fond, joueur1, joueur2, sol, map, teteJ1, teteJ2, TiretBleu, TiretRouge, NomJ1, NomJ2;
    int dimensionTete, xTeteDroite, xTeteGauche, yTete, etape, largeur, hauteur, nbColonnes, largeurCase, hauteurNom, largeurNom;
    int hauteurCase, hauteurLuke, hauteurVador, largeurVador, yPoint, xPointGauche, xPointDroit, hauteurTiret, largeurTiret, yNom, xNom;
    int hauteur2, largeur2;
    Image[] joueurs1;
    Image[] joueurs2;
    Image carte1, carte2, carte3, carte4, carte5, carte0;
    Image carte1_select, carte2_select, carte3_select, carte4_select, carte5_select;
    Image carte1_disabled, carte2_disabled, carte3_disabled, carte4_disabled, carte5_disabled;
    Image ButtonChangeTour;
    Random r;
    Clip clip;
    Graphics2D drawable;
    boolean Menu, Partie, Option, PartieSet, MenuSet, OptionSet;
    public int compteur;
    Image[] cartes = {};
    Image[] cartesSel = {};
    Image[] cartesDisabled = {};

    //Fonction Permettant de charger une image
    private Image chargeImage(String nom){
        Image img = null;
        try{
            InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(nom + ".png");
            if(in != null){
                img = ImageIO.read(in);
            }
        }catch(Exception e ){
            System.err.println("Erreur lors du chargement de l'image : " + e);
            System.exit(1);
        }
        return img;
    }

    //Fonction qui choisi un décors au hasard et choisi le sol et la musique associée.
    public void randomDecors(){
        Random r = new Random();
        int nb = r.nextInt(8);
        int nb2 = nb%4;
        map = chargeImage("Map/Map"+nb);
        sol = chargeImage("Sol/Sol"+nb2);
        //Gestion Musique
        try {
            AudioInputStream input = AudioSystem.getAudioInputStream(new File("res/Music/Duel"+nb+".wav"));
            clip = AudioSystem.getClip();
            clip.open(input);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-30.0f);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            //gainControl.setValue(0.0f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public NiveauGraphique(Jeu j){
        jeu = j;
        nbColonnes = 23;

        //Chargement des images
        teteJ1 = chargeImage("Luke/Head");
        teteJ2 = chargeImage("Vador/Head");
        TiretBleu = chargeImage("Partie/TiretBleu");
        TiretRouge = chargeImage("Partie/TiretRouge");
        fondMenu = chargeImage("Menu/MenuDroit");
        fond = chargeImage("Menu/Menu");
        NomJ1 = chargeImage("Partie/NomJ1");
        NomJ2 = chargeImage("Partie/NomJ2");

        carte0 = chargeImage("Carte/Card_0");
        carte1 = chargeImage("Carte/Card_1");
        carte2 = chargeImage("Carte/Card_2");
        carte3 = chargeImage("Carte/Card_3");
        carte4 = chargeImage("Carte/Card_4");
        carte5 = chargeImage("Carte/Card_5");
        cartes = new Image[] { carte0, carte1, carte2, carte3, carte4, carte5};

        carte1_select = chargeImage("Carte/Card_1_selected");
        carte2_select = chargeImage("Carte/Card_2_selected");
        carte3_select = chargeImage("Carte/Card_3_selected");
        carte4_select = chargeImage("Carte/Card_4_selected");
        carte5_select = chargeImage("Carte/Card_5_selected");
        cartesSel = new Image[] {carte1_select, carte2_select, carte3_select, carte4_select, carte5_select};

        carte1_disabled = chargeImage("Carte/Card_1_disabled");
        carte2_disabled = chargeImage("Carte/Card_2_disabled");
        carte3_disabled = chargeImage("Carte/Card_3_disabled");
        carte4_disabled = chargeImage("Carte/Card_4_disabled");
        carte5_disabled = chargeImage("Carte/Card_5_disabled");
        cartesDisabled = new Image[] {carte1_disabled, carte2_disabled, carte3_disabled, carte4_disabled, carte5_disabled};

        ButtonChangeTour = chargeImage("Partie/ChangeTour");

        //Chargement des images pour Animations
        joueurs1 = new Image[4];
        joueurs2 = new Image[4];
        for(int i = 0; i < 4; i++){
            joueurs1[i] = chargeImage("Luke/stand_0"+i);
            joueurs2[i] = chargeImage("Vador/stand_0"+i);
        }

        etape = 0;
        joueur1 = joueurs1[etape];
        joueur2 = joueurs2[etape];

        //initialisation des booléens pour savoir dans quel page on est.
        Menu = true;
        Partie = false;
        Option = false;

        //initialisation de booléen pour savoir quand est ce qu'il faut démarrez ou arrêter la musique
        PartieSet = false;
        MenuSet = false;
    }

    public void paintComponent(Graphics g){
        drawable = (Graphics2D) g;
        largeur = getSize().width;
        hauteur = getSize().height;


        drawable.clearRect(0, 0, largeur, hauteur);
        if(!Menu && Partie && !Option){

            tracerPartie();
        }else if(Menu && !Partie && !Option){
            tracerMenu();
        }else if(!Menu && !Partie && Option){
            tracerRegles();
        }
    }

    //Fonction qui démarre la musique du menu principal
    public void startMusique(){
        try {
            AudioInputStream input = AudioSystem.getAudioInputStream(new File("res/Music/Menu.wav"));
            clip = AudioSystem.getClip();
            clip.open(input);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-80.0f);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Fonction qui permet de stopper la musique en cours
    public void stopMusique(){
        if(MenuSet || PartieSet){
            clip.stop();
        }
    }

    //Fonction qui trace les différents éléments graphique du menu
    public void tracerMenu(){
        if(!MenuSet){
            stopMusique();
            startMusique();
        }
        PartieSet = false;
        MenuSet = true;

        largeurNom = (int)Math.round(largeur*0.30);
        hauteurNom = (int)Math.round(hauteur*0.25);
        xNom = (int)Math.round(largeur*0.35);
        yNom = (int)Math.round(hauteur*0.05);

        drawable.clearRect(0, 0, largeur, hauteur);
        drawable.drawImage(fondMenu, 0,0,largeur, hauteur, null);
    }

    //Fonction qui trace les différents éléments graphique des règles du jeu
    public void tracerRegles(){
        largeurNom = (int)Math.round(largeur*0.30);
        hauteurNom = (int)Math.round(hauteur*0.25);
        xNom = (int)Math.round(largeur*0.35);
        yNom = (int)Math.round(hauteur*0.05);

        drawable.clearRect(0, 0, largeur, hauteur);
        drawable.drawImage(fond, 0,0,largeur, hauteur, null);

        if(compteur == 0){
            drawable.drawImage(teteJ1, 0, 0, 150,150, null);
        }
    }

    //Fonction qui trace les différents éléments graphique de la partie.
    public void tracerPartie(){
        if(!PartieSet){
            stopMusique();
            randomDecors();
        }

        PartieSet = true;
        MenuSet = false;

        largeurCase = largeur / nbColonnes;
        hauteurCase = (int)Math.round(largeurCase*0.35);
        hauteurLuke = (int)Math.round(largeurCase * 2.10);
        hauteurVador = (int)Math.round(largeurCase * 1.94);
        largeurVador = (int)Math.round(largeurCase * 1.50);
        xTeteGauche = (int)Math.round(largeur*0.05);
        xTeteDroite = (int)Math.round((largeur*.95)-(largeurCase*1.75));
        yTete = (int)Math.round(hauteur*0.05);
        xPointGauche = (xTeteGauche+dimensionTete);
        xPointDroit = xTeteDroite;
        yPoint = (int)Math.round(yTete+dimensionTete*0.75);
        dimensionTete = (int)Math.round(largeurCase*1.75);
        hauteurTiret = (int)Math.round(hauteur*0.01);
        largeurTiret = (int)Math.round(largeur*0.025);
        hauteurNom = (int)Math.round(dimensionTete / 2);
        largeurNom = (int)Math.round(dimensionTete * 3);
        yNom = (int)Math.round(hauteur*0.08);

        int[] grilleJeu = jeu.partie().manche().grilleJeu;

        drawable.clearRect(0, 0, largeur, hauteur);
        drawable.drawImage(map, 0, 0, largeur, hauteur, null);
        //affichage de la tete et du nom des deux joueurs
        drawable.drawImage(teteJ1, xTeteGauche, yTete, dimensionTete ,dimensionTete,null);
        drawable.drawImage(teteJ2, xTeteDroite, yTete, dimensionTete ,dimensionTete,null);
        drawable.drawImage(NomJ1, xPointGauche+(1*xTeteGauche), yNom, largeurNom, hauteurNom, null);
        drawable.drawImage(NomJ2, xPointDroit-(5*xTeteGauche), yNom, largeurNom, hauteurNom, null);

        // affichage des barres de vie à partir de la santé de chaque joueur
        int joueur1Vie = jeu.partie().manche().joueur1.vie;
        int joueur2Vie = jeu.partie().manche().joueur2.vie;
        for(int i=0; i<joueur1Vie;i++){
            drawable.drawImage(TiretBleu, xPointGauche+((i+1)*xTeteGauche), yPoint, largeurTiret, hauteurTiret, null);
        }
        for(int i=joueur1Vie; i<5;i++){
            drawable.drawImage(TiretRouge, xPointGauche+((i+1)*xTeteGauche), yPoint, largeurTiret, hauteurTiret, null);
        }
        for(int i=0; i<joueur2Vie;i++){
            drawable.drawImage(TiretBleu, xPointDroit-((i+1)*xTeteGauche), yPoint, largeurTiret, hauteurTiret, null);
        }
        for(int i=joueur2Vie; i<5;i++){
            drawable.drawImage(TiretRouge, xPointDroit-((i+1)*xTeteGauche), yPoint, largeurTiret, hauteurTiret, null);
        }

        for(int c = 0; c < jeu.partie().manche().NOMBRE_CASES; c++){
            int x = c * largeurCase;
            int y = (int) Math.round(hauteur * 0.62);

            if (jeu.partie().manche().getCaseIHM().size() < jeu.partie().manche().NOMBRE_CASES){
                jeu.partie().manche().initCaseIHM(c, grilleJeu[c], x, y - hauteurCase*3, largeurCase, hauteurCase*4, 0);
            } else {
                jeu.partie().manche().updateCaseIHM(c, grilleJeu[c], x, y - hauteurCase*3, largeurCase, hauteurCase*4);
            }


            drawable.drawImage(sol, x, y, largeurCase, hauteurCase, null);
            if(grilleJeu[c] == 1){
                drawable.drawImage(joueur1, x, (int)Math.round(y-hauteurVador+(hauteurCase*0.5)), largeurVador, hauteurVador, null);
            }else if(grilleJeu[c] == 2){
                drawable.drawImage(joueur2, x+largeurCase, (int)Math.round(y-hauteurVador+(hauteurCase*0.5)), -largeurVador, hauteurVador, null);
            }

            affichePossibilites(drawable);
        }

        afficheMainJoueur(jeu.partie().Joueur(jeu.partie().manche().getTourJoueur()), drawable);

        if(jeu.selectedCarte != null && jeu.selectedCarte.getId() != -1)      {
            selectCarte(jeu.selectedCarte.getValeur(), jeu.selectedCarte.getCoordX(), jeu.selectedCarte.getCoordY(), jeu.selectedCarte.getLargeur(), jeu.selectedCarte.getHauteur(), drawable);
        }

        afficheBoutonChangeTour();

    }

    @Override
    public void metAJour() {
        repaint();
    }

    //Fontion permettant d'animer les joueurs à l'arret.
    public void animJoueur() {
        etape = (etape+1)%4;
        joueur1 = joueurs1[etape];
        joueur2 = joueurs2[etape];
        metAJour();
    }

    public void selectCarte(int val ,int x, int y, int l, int h, Graphics2D drawable){
        drawable.drawImage(cartesSel[val-1],x,y,l,h,null);
    }

    public void afficheMainJoueur(JoueurHumain j, Graphics2D drawable){
        int nbCartes = j.main.size();
        //System.out.print("joueur: "+ j.main + "\n");

        int largeurCarte = (int) Math.round(largeur * 0.30)/5;
        int hauteurCarte = (int) Math.round(hauteur * 0.15);


        int x = (largeur/2)-((nbCartes*largeurCarte)/2)-largeurCarte;
        int y = (int) Math.round(hauteur * 0.80);


        for(int i = 0; i < j.main.size(); i++){

            int valeurCarte = j.main.get(i);


            x = x+largeurCarte;


            if(j.getCarteI().size() < 5){
                j.initCarteI(i, valeurCarte, x, y, largeurCarte, hauteurCarte);
            } else {
                j.updateCarteI(i, valeurCarte, x, y, largeurCarte, hauteurCarte);
            }

            if (j.getCarteI().get(i).getEtat() == 1){
                drawable.drawImage(cartesDisabled[valeurCarte-1], x , y, largeurCarte, hauteurCarte, null);
            } else {
                drawable.drawImage(cartes[valeurCarte], x , y, largeurCarte, hauteurCarte, null);
            }

            if (jeu.selectedCarte != null && jeu.selectedCarte.getId() == i){

                jeu.selectedCarte.update(jeu.selectedCarte.getId(), jeu.selectedCarte.getValeur(), x, y, largeurCarte, hauteurCarte);
            }

        }
    }

    public void affichePossibilites(Graphics2D drawable){
        ArrayList<SelectionCaseIHM> CaseIHM = new ArrayList<>();
        CaseIHM = jeu.partie().manche().CaseIHM;;
        for(int i = 0; i< CaseIHM.size(); i++)
        {
            int etat = CaseIHM.get(i).getEtat();

            switch(etat){
                case 1:
                    Color cBlue = new Color(100, 250, 255, 20);
                    drawable.setColor(cBlue);
                    drawable.fillRect(CaseIHM.get(i).getX(), CaseIHM.get(i).getY(), CaseIHM.get(i).getLargeur(), CaseIHM.get(i).getHauteur());

                    break;
                case 2:
                    drawable.setColor(Color.RED);
                    drawable.fillRect(CaseIHM.get(i).getX(), CaseIHM.get(i).getY(), CaseIHM.get(i).getLargeur(), CaseIHM.get(i).getHauteur());
                    break;
                default:
                    break;

            }

        }

    }

    //Fonction qui met à jour les booléens pour changer l'affichage de la fenêtre en fonction de la page
    //que l'on veut afficher
    public void changeBackground(boolean b1, boolean b2, boolean b3) {
        Menu = b1;
        Partie = b2;
        Option = b3;
        metAJour();
    }
}
