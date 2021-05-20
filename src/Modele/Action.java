package Modele;

public class Action {
    int id;
    int[] valeurs;

    public Action(int type, int[] valeurs){
        this.id = type;
        this.valeurs = valeurs;
    }
}
