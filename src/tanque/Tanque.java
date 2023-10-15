package tanque;

import peces.Pez;

public class Tanque<T extends Pez> {
    public int capacidad;
    public int muertos=0;    

    public Tanque(int capacidad){
        this.capacidad=capacidad;
    }
}
