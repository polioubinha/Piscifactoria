package peces.alimentacion;

import java.util.Random;

import almacenCentral.AlmacenCentral;
import peces.Pez;
import piscifactoria.Piscifactoria;
import tanque.Tanque;

public class Filtrador extends Pez{
    
    @Override
    public void grow(Tanque tanque, Piscifactoria piscifactoria, Boolean almacenCentral){
        Random r = new Random();
        if(r.nextBoolean()){
            this.edad++;
        }else{
            super.grow(tanque, piscifactoria, almacenCentral);
        }
    }

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
