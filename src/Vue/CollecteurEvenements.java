package Vue;

public interface CollecteurEvenements {
    void tictac();
    void clickCarte(int x, int y);
    void clickDeplacement(int x, int y);
    boolean commande(String c);
    void fixerInterfaceGraphique(InterfaceGraphique i);
}
