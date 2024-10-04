package peces;

import java.util.Random;

import almacenCentral.AlmacenCentral;
import piscifactoria.Piscifactoria;
import tanque.Tanque;

public class Omnivoro extends Pez{
    private boolean noComer(){
        Random r = new Random();
        return r.nextInt(1,4) == 1;
    }

    @Override
    public void grow(Tanque tanque, Piscifactoria piscifactoria, Boolean almacenCentral){
        if(!this.noComer()){
            this.edad++;
        }else{
            super.grow(tanque, piscifactoria, almacenCentral);
        }
    }

    @Override
    public void comer(Tanque tanque, Piscifactoria piscifactoria, Boolean almacenCentral){
        Random r = new Random();

        if(this.alimentado == false){
            if(tanque.hasDead()){
                this.alimentado = true;
                if(r.nextBoolean()){
                    tanque.removeDead();
                }
            }else{
                if(piscifactoria.getAlmacen() != 0){
                    this.alimentado = true;
                    piscifactoria.setAlmacen(piscifactoria.getAlmacen()-1);
                }else if (almacenCentral) {
                    AlmacenCentral.getInstance().setCapacidad(almacenCentral.getInstance().getCapacidad() - 1);
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
