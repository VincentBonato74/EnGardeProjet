package Modele;

public class Coup {
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

    public void execute()
    {

    }

    public void desexecute()
    {

    }


}
