import Modele.*;
import Controller.ControllerMediateur;
import Vue.InterfaceGraphique;

public class EnGarde {

    public static void main(String [] args) {
        try {
            Jeu j = new Jeu();
            Manche m = new Manche();
            ControllerMediateur c = n
            ControllerMediateur(j);
            InterfaceGraphique.demarrer(j, c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
