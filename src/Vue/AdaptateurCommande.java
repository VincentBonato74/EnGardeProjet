package Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurCommande implements ActionListener {
    CollecteurEvenements control;
    String commande;

    AdaptateurCommande(CollecteurEvenements c, String nom){
        control = c;
        commande = nom;
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        control.commande(commande);
    }
}