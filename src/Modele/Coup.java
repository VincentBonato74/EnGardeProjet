package Modele;

public class Coup extends Commande {
    Manche manche;
    int[] mapAvant;

    public Coup(int []grilleJeu)
    {
        mapAvant = grilleJeu;
    }

    void fixerManche(Manche m)
    {
        manche = m;
    }

    public void execute(Coup cp)
    {

    }

    public void desexecute(Coup cp)
    {

    }


}
