package peces.alimentacion;

import java.util.Random;

import almacenCentral.AlmacenCentral;
import peces.Pez;
import piscifactoria.Piscifactoria;
import tanque.Tanque;

/** Clase para gestionar la alimentacion de un omnivoro */
public class Omnivoro extends Pez{
    /**
     * Probabilidad de que el pez no coma
     * @return probabilidad
     */
    private boolean noComer(){
        Random r = new Random();
        return r.nextInt(4) == 0; // 25% de probabilidad de no comer
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
     * 
     * @return true si se ha alimentado, false si no
     */
    @Override
    public void comer(Tanque tanque, Piscifactoria piscifactoria, Boolean usarAlmacenCentral) {
        Random r = new Random();

        if (!this.alimentado) {
            if (tanque.hasDead()) {
                this.alimentado = true;
                if (r.nextBoolean()) {
                    tanque.eliminarMuerto();
                }
            } else {
                if (piscifactoria.getAlmacen() > 0) {
                    this.alimentado = true;
                    piscifactoria.setAlmacen(piscifactoria.getAlmacen() - 1);
                } else if (usarAlmacenCentral) {
                    boolean alimentoConsumido = false;

                    AlmacenCentral almacenAnimal = AlmacenCentral.getInstance("animal");
                    if (almacenAnimal.getCapacidad() > 0) {
                        almacenAnimal.setCapacidad(almacenAnimal.getCapacidad() - 1);
                        alimentoConsumido = true;
                    }

                    if (!alimentoConsumido) {
                        AlmacenCentral almacenVegetal = AlmacenCentral.getInstance("vegetal");
                        if (almacenVegetal.getCapacidad() > 0) {
                            almacenVegetal.setCapacidad(almacenVegetal.getCapacidad() - 1);
                            alimentoConsumido = true;
                        }
                    }

                    if (alimentoConsumido) {
                        this.alimentado = true;
                    } else {
                        System.out.println("No hay suficiente comida disponible en el almacén central.");
                    }
                }
            }
        }
    }

    /**
     * Realiza la acción de reproducirse de un pez
     */
    @Override
    public boolean reproducirse(){
        return super.reproducirse();
    }

    /**
     * Devuelve el tipo de comida del que se alimentan
     * @return tipo de comida
     */
    public static String getTipoComida() {
        return "animal y vegetal";
    }
}
