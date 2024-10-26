package peces.alimentacion;

import java.util.Random;

import almacenCentral.AlmacenCentral;
import peces.Pez;
import piscifactoria.Piscifactoria;
import tanque.Tanque;

public class Carnivoro extends Pez{

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
    public boolean reproducirse() {
        return super.reproducirse();
    }    
}
