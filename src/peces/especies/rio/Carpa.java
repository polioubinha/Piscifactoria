package peces.especies.rio;

import java.util.Random;

import almacenCentral.AlmacenCentral;
import peces.Pez;
import peces.alimentacion.Omnivoro;
import piscifactoria.Piscifactoria;
import propiedades.AlmacenPropiedades;
import propiedades.PecesDatos;
import tanque.Tanque;

public class Carpa extends Omnivoro{
    private final PecesDatos datos = AlmacenPropiedades.CARPA;

    public PecesDatos getDatos(){
        return datos;
    }

    public Carpa(boolean sexo){
        this.sexo=sexo;
        this.ciclo=this.datos.getCiclo();
    }

    public boolean isAlimentado(){
        return alimentado;
    }

    public int getEdad(){
        return edad;
    }

    public void setEdad(int edad){
        this.edad=edad;
    }

    public boolean isVivo(){
        return vivo;
    }

    public void setVivo(boolean vivo){
        this.vivo=vivo;
    }

    public boolean isMaduro(){
        return maduro;
    }

    public void setMaduro(boolean maduro){
        this.maduro=maduro;
    }

    public boolean isSexo(){
        return sexo;
    }

    public String getSexo(){
        return this.sexo ? "Macho" : "Hembra";
    }

    public static void datos(){
        PecesDatos datos = AlmacenPropiedades.CARPA;
        System.out.println("------------");
        System.out.println("Nombre común: " + datos.getNombre());
        System.out.println("Nombre científico: " + datos.getCientifico());
        System.out.println("Tipo: " + datos.getTipo());
        System.out.println("Coste: " + datos.getCoste());
        System.out.println("Precio venta: " + datos.getMonedas());
        System.out.println("Huevos: " + datos.getHuevos());
        System.out.println("Ciclo: " + datos.getCiclo());
        System.out.println("Madurez: " + datos.getMadurez());
        System.out.println("Óptimo: " + datos.getOptimo());
    }

    public void showStatus(){
        System.out.println("------" + this.datos.getNombre() + "------");
        System.out.println("Edad: " + this.edad + " días");
        System.out.println("Sexo: " + (this.sexo ? "Macho" : "Hembra"));
        System.out.println("Vivo: " + (this.vivo ? "Si" : "No"));
        System.out.println("Adulto: " + (this.maduro ? "Si" : "No"));
        System.out.println("Fertil: " + (this.ciclo == 0 ? "Si" : "No"));
        System.out.println("Alimentado: " + (this.alimentado ? "Si" : "No"));
    }

    public void comprobarMadurez(int edad){
        this.setMaduro(this.edad >= this.datos.getMadurez());
    }

    public boolean esOptimo(){
        return this.edad==this.datos.getOptimo();
    }

    public boolean reproducirse(){
        if(this.maduro && this.edad % this.datos.getCiclo()==0 && !this.sexo){
            this.ciclo=this.datos.getCiclo();
            return true;
        }
        this.ciclo--;
        return false;
    }

    //REVISAR METODO COMER

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
                    piscifactoria.setAlmacen(piscifactoria.getAlmacen()-2);
                }else if (almacenCentral) {
                    AlmacenCentral.getInstance().setCapacidad(AlmacenCentral.getInstance().getCapacidad() - 2);
                    this.alimentado = true;
                }
            }
        }
    }
}
