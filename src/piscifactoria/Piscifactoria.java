package piscifactoria;

import java.util.ArrayList;

import tanque.Tanque;

public class Piscifactoria {
    boolean rio;
    ArrayList<Tanque<Pez>> tanques = new ArrayList<>();
    int ctanques=0;
    int almacen;
    int almacenMax;

    public Piscifactoria(boolean rio){
        this.rio=rio;
        if(this.rio){
            this.tanques.add(new Tanque<Pez>(25));
            this.ctanques = this.tanques.size();
            this.almacen = 25;
            this.almacenMax = 25;
        }else{
            this.tanques.add(new Tanque<Pez>(100));
            this.ctanques = this.tanques.size();
            this.almacen = 100;
            this.almacenMax = 100;
        }
    }
}
