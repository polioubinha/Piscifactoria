package peces.alimentacion;

import java.util.Random;

import almacenCentral.AlmacenCentral;
import peces.Pez;
import piscifactoria.Piscifactoria;
import tanque.Tanque;

public class Filtrador extends Pez{
    /**
     * Probabilidad de que el pez no coma
     * @return probabilidad
     */
    private boolean noComer(){
        Random r = new Random();
        return r.nextBoolean();
    }
    /**
     * Realiza la acción de crecer de un pez
     */
    @Override
    public void grow(Tanque tanque, Piscifactoria piscifactoria, Boolean almacenCentral){
        if(!this.noComer()){
            this.edad++;
        }else{
            super.grow(tanque, piscifactoria, almacenCentral);
        }
    }

    /**
     * Método para alimentar al pez
     * 
     * @param tanque tanque en el que se encuentra el pez
     * @param piscifactoria piscifactoria en la que se encuentra el pez
     * @param almacenCentral si el pez se alimenta del almacen central
     */
    @Override
    public void comer(Tanque tanque, Piscifactoria piscifactoria, Boolean usarAlmacenCentral) {
        if (!this.alimentado) {
            if (piscifactoria.getAlmacen() > 0) {
                this.alimentado = true;
                piscifactoria.setAlmacen(piscifactoria.getAlmacen() - 1);
            } else if (usarAlmacenCentral) {
                AlmacenCentral almacenVegetal = AlmacenCentral.getInstance("vegetal");
                if (almacenVegetal.getCapacidad() > 0) {
                    almacenVegetal.setCapacidad(almacenVegetal.getCapacidad() - 1);
                    this.alimentado = true;
                } else {
                    System.out.println("No hay suficiente comida vegetal en el almacén central.");
                }
            }
        }
    }

    public static String getTipoComida() {
        return "vegetal";
    }
}
