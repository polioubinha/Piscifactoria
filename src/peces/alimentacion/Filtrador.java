package peces.alimentacion;

import java.util.Random;

import almacenCentral.AlmacenCentral;
import peces.Pez;
import piscifactoria.Piscifactoria;
import tanque.Tanque;

public class Filtrador extends Pez{
    
    /**
     * Realiza la acción de crecer de un pez
     */
    @Override
    public void grow(Tanque tanque, Piscifactoria piscifactoria, Boolean almacenCentral){
        Random r = new Random();
        if(r.nextBoolean()){
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
    public void comer(Tanque tanque, Piscifactoria piscifactoria, Boolean almacenCentral){
        
        if(this.alimentado == false){
            
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
