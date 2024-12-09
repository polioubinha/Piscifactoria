package peces.alimentacion;

import java.util.Random;

import almacenCentral.AlmacenCentral;
import peces.Pez;
import piscifactoria.Piscifactoria;
import tanque.Tanque;
/** Clase para gestionar la alimentacion de un carnivoro */
public class Carnivoro extends Pez{
    /**
     * Método para alimentar al pez
     * 
     * @param tanque tanque en el que se encuentra el pez
     * @param piscifactoria piscifactoria en la que se encuentra el pez
     * @param almacenCentral si el pez se alimenta del almacen central
     */
    @Override
    public void comer(Tanque tanque, Piscifactoria piscifactoria, Boolean usarAlmacenCentral) {
        Random r = new Random();

        if(!this.alimentado){
            if(tanque.hasDead()){
                this.alimentado = true;
                if(r.nextBoolean()){
                    tanque.eliminarMuerto();
                }
            }else{
                if(piscifactoria.getAlmacen() != 0){
                    this.alimentado = true;
                    piscifactoria.setAlmacen(piscifactoria.getAlmacen()-1);
                }else if (usarAlmacenCentral) {
                    AlmacenCentral almacenAnimal = AlmacenCentral.getInstance("animal");
                    if(almacenAnimal.getCapacidad() > 0){
                        almacenAnimal.setCapacidad(almacenAnimal.getCapacidad()-1);
                        this.alimentado = true;
                    } else {
                        System.out.println("No hay suficiente comida animal en el almacén central");
                    }
                }
            }
        }
    }

    /**
     * Realiza la acción de reprocirse de un pez
     */
    @Override
    public boolean reproducirse() {
        return super.reproducirse();
    }    

    /**
     * Devuelve el tipo de comida del que se alimentan
     * @return tipo de comida
     */
    public static String getTipoComida() {
        return "animal";
    }
}
