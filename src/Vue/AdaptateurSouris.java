package Vue;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdaptateurSouris extends MouseAdapter {
    NiveauGraphique niv;
    CollecteurEvenements control;

    AdaptateurSouris(NiveauGraphique n, CollecteurEvenements c) {
        niv = n;
        control = c;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int l = e.getY();
        int c = e.getX();
        System.out.println(l + " " + c);
        //control.clicSouris(l, c);
    }

}
