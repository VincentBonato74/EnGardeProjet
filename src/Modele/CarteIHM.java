package Modele;

public class CarteIHM {
    int id;
    int valeur;
    int x, y;
    int largeur, hauteur;


    public CarteIHM(int num, int value){
        id = num;
        valeur = value;
    }



    public CarteIHM(int num, int value, int Cx, int Cy, int l, int h){
        id = num;
        valeur = value;
        x = Cx;
        y = Cy;
        largeur = l;
        hauteur = h;
    }

    public CarteIHM(int value, int Cx, int Cy, int l, int h){
        valeur = value;
        x = Cx;
        y = Cy;
        largeur = l;
        hauteur = h;
    }

    public void update(int num, int value, int Cx, int Cy, int l, int h){
        id = num;
        valeur = value;
        x = Cx;
        y = Cy;
        largeur = l;
        hauteur = h;
    }

    public int getCoordX(){
        return x;
    }

    public int getCoordY(){
        return y;
    }

    public int getId() {
        return id;
    }

    public int getValeur() {
        return valeur;
    }

    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }

    @Override
    public String toString() {
        return "CarteIHM{" +
                "id=" + id +
                ", valeur=" + valeur +
                ", x=" + x +
                ", y=" + y +
                ", largeur=" + largeur +
                ", hauteur=" + hauteur +
                '}';
    }
}
