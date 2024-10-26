package peces.alimentacion;

import java.util.Random;

import almacenCentral.AlmacenCentral;
import peces.Pez;
import piscifactoria.Piscifactoria;
import tanque.Tanque;

public class Omnivoro extends Pez{
    private boolean noComer(){
        Random r = new Random();
        return r.nextInt(1,4) == 1;
    }

    @Override
    public void grow(Tanque<? extends Pez> tanque, Piscifactoria piscifactoria, Boolean almacenCentral) {
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
    public void comer(Tanque<? extends Pez> tanque, Piscifactoria piscifactoria, boolean almacenCentral) {
        Random r = new Random();

        if(this.alimentado == false){
            if(tanque.hasDead()){
                this.alimentado = true;
                if(r.nextBoolean()){
                    tanque.eliminarMuerto();
                }
            }else{
                if(piscifactoria.getAlmacen() != 0){
                    this.alimentado = true;
                    piscifactoria.setAlmacen(piscifactoria.getAlmacen()-1);
                }else if (almacenCentral) {
                    AlmacenCentral.getInstance().setCapacidad(AlmacenCentral.getInstance().getCapacidad() - 1);
                    this.alimentado = true;
                }
            }
        }
    }

    @Override
    public boolean reproducirse(){
        return super.reproducirse();
    }
}
