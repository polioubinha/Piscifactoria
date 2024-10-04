package tanque;

import java.util.ArrayList;
import java.util.Iterator;

import peces.Pez;

public class Tanque<T extends Pez> {
    ArrayList<Pez> peces = new ArrayList<>();
    int capacidad;
    int vendidos = 0;
    int muertos = 0;
    int ganancias = 0;
    ArrayList<Integer> pecesMuertos = new ArrayList<>();

    public Tanque(int capacidad){
        this.capacidad = capacidad;
    }

    public int getCapacidad(){
        return capacidad;
    }

    public int getGanancias(){
        return ganancias;
    }

    public int getVendidos(){
        return vendidos;
    }

    public ArrayList<Pez> getPeces(){
        return peces;
    }

    public void showStatus() {
        System.out.println("Ocupación: " + this.peces.size() + "/" + this.capacidad + " ("
                + this.porcentaje(this.peces.size(), this.capacidad) + "%)");
        System.out.println("Peces vivos: " + this.vivos() + "/" + this.peces.size() + " ("
                + this.porcentaje(this.vivos(), this.peces.size()) + "%)");
        System.out.println("Peces alimentados: " + this.alimentados() + "/" + this.vivos() + " ("
                + this.porcentaje(this.alimentados(), this.vivos()) + "%)");
        System.out.println("Peces adultos: " + this.adultos() + "/" + this.vivos() + " ("
                + this.porcentaje(this.adultos(), this.vivos()) + "%)");
        System.out.println("Hembras/Machos: " + this.hembras() + "/" + this.machos());
        System.out.println("Fértiles:" + this.fertiles() + "/" + this.vivos() + " ("
                + this.porcentaje(this.fertiles(), this.vivos()) + "%)");
    }

    public void fishStatus(){
        for(Pez pez : peces){
            pez.showStatus();
        }
    }

    public boolean hasDead(){
        if(muertos != 0){
            this.pecesMuertos.removeAll(pecesMuertos);
        }

        for(int i = 0; i < peces.size(); i++){
            if(!peces.get(i).isVivo()){
                pecesMuertos.add(i);
            }
        }

        if (this.pecesMuertos != null){
            return !pecesMuertos.isEmpty();
        }else{
            return false;
        }
    }

    public boolean eliminarMuerto(){
        Iterator<Pez> it = this.peces.iterator();

        while(it.hasNext()){
            Pez pez = it.next();
            if(!pez.isVivo() == false){
                it.remove();
                return true;
            }
        }
        return false;
    }

}
