package peces;

import propiedades.AlmacenPropiedades;
import propiedades.PecesDatos;

public class Sargo extends Omnivoro{
    private final PecesDatos datos = AlmacenPropiedades.SARGO;

    public PecesDatos getDatos() {
        return datos;
    }

    public Sargo(boolean sexo) {
        this.sexo = sexo;
        this.ciclo = this.datos.getCiclo();
    }

    public boolean isAlimentado() {
        return alimentado;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public boolean isMaduro() {
        return maduro;
    }

    public void setMaduro(boolean maduro) {
        this.maduro = maduro;
    }

    public boolean isSexo() {
        return sexo;
    }

    public String getSexo() {
        return this.sexo ? "Macho" : "Hembra";
    }
    

    public void showStatus(){
        System.out.println("------" + this.datos.getNombre() + "------");
        System.out.println("Edad: " + this.edad + "dias");
        if(this.sexo){
            System.out.println("Sexo: Macho");
        }else{
            System.out.println("Sexo: Hembra");
        }

        if(this.vivo){
            System.out.println("Vivo: Si");
        }else{
            System.out.println("Vivo: No");
        }

        if(this.maduro){
            System.out.println("Adulto: Si");
        }else{
            System.out.println("Adulto: No");
        }

        if(this.ciclo == 0){
            System.out.println("Fertil: Si");
        }else{
            System.out.println("Fertil: No");
        }

        if(this.alimentado){
            System.out.println("Alimentado: Si");
        }else{
            System.out.println("Alimentado: No");
        }
    }
    
    public void comprobarMadurez(int edad) {
        this.setMaduro(this.edad >= this.datos.getMadurez());
    }
    

    public boolean esOptimo() {
        return this.edad == this.datos.getOptimo();
    }
    

    public boolean reproducirse() {
        if (this.maduro && this.edad % this.datos.getCiclo() == 0 && !this.sexo) {
            this.ciclo = this.datos.getCiclo();
            return true;
        }
        
        this.ciclo--;
        return false;
    }
    
}
