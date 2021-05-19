package Modele;


import Structures.SequenceListe;

public class Historique<E extends Commande> {
    SequenceListe<Coup> CoupFait, CoupAnnuler;

    Historique(){
        CoupFait = new SequenceListe<>();
        CoupAnnuler = new SequenceListe<>();
    }

    void nouveau(Coup c){
        CoupFait.insereTete(c);
        //c.execute(c);
        while(!CoupAnnuler.estVide()){
            CoupAnnuler.extraitTete();
        }
    }

    public boolean peutRefaire(){
        return !CoupAnnuler.estVide();
    }

    public boolean peutAnnuler(){
        return !CoupFait.estVide();
    }

    Coup refaire(){
        if(peutRefaire()){

            Coup c = CoupAnnuler.extraitTete();

            CoupFait.insereTete(c);
            return c;
        }else{
            return null;
        }
    }

    Coup annuler(){
        if(peutAnnuler()){
            Coup c = CoupFait.extraitTete();
            c.desexecute(c);

            CoupAnnuler.insereTete(c);
            return c;
        }else{
            return null;
        }
    }

}