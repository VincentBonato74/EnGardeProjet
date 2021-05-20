package Modele;
import java.util.HashMap;
import java.util.Map;

public class Coup extends Commande {
    Manche manche;
    int[] mapAvant;
    Action action;

    //HashMap <String, Integer> action = new HashMap <String, Integer>();
    /*action.put("avance",3);
    action.put("attaque", 5);
    action.put("attaque",5);*/

    public Coup(int []grilleJeu, Action action){
        mapAvant = grilleJeu;
        this.action = action;
    }

    public Action GetAction(){
        return this.action;
    }

    void fixerManche(Manche m)
    {
        manche = m;
    }

    public void execute(Coup cp) {

    }

    public void desexecute(Coup cp) {

    }


}
