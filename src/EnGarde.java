import Controller.ControllerMediateur;
import Modele.Jeu;
import Modele.Manche;
import Modele.Partie;
import Vue.InterfaceGraphique;

public class EnGarde {

    public static void main(String [] args) {
        try {
            Jeu j = new Jeu();
            Partie p = new Partie();
            Manche m = new Manche();
            ControllerMediateur c = new ControllerMediateur(j);
            InterfaceGraphique.demarrer(j, c);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
