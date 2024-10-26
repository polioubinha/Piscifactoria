package peces;

import java.util.Random;
import piscifactoria.Piscifactoria;
import propiedades.PecesDatos;
import tanque.Tanque;

public abstract class Pez {
    protected PecesDatos datos;
    protected int ciclo;
    protected int edad;
    protected boolean vivo = true;
    protected boolean sexo = false;
    protected boolean alimentado = true;
    protected boolean maduro = false;

    public PecesDatos getDatos(){
        return datos;
    }

    /**
     * Comprobamos si el pez esta alimentado
     * 
     * @return true si está alimentado, false si no
     */
    public boolean isAlimentado() {
        return alimentado;
    }

    /**
     * Obtenemos la edad del pez
     * 
     * @return edad del pez
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Establecemos la edad del pez
     * 
     * @param edad del pez
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * Comprobamos que el pez está vivo
     * 
     * @return true si está vivo, false si no
     */
    public boolean isVivo() {
        return vivo;
    }

    /**
     * Establecemos si el pez está vivo
     * 
     * @param vivo true si está vivo, false si no
     */
    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    /**
     * Comprobamos si el pez está maduro
     * 
     * @return true si está maduro, false si no
     */
    public boolean isMaduro() {
        return maduro;
    }

    /**
     * Establecemos si el pez está maduro
     * 
     * @return true si está maduro, false si no
     */
    public void setMaduro(boolean fertil) {
        this.maduro = fertil;
    }

    /**
     * Comprobamos si el pez es macho o hembra
     * 
     * @return true si es macho, false si es hembra
     */
    public boolean isSexo() {
        return sexo;
    }

    /**
     * Establecemos si el pez es macho o hembra
     * 
     * @param sexo true si es macho, false si es hembra
     */
    public String getSexo() {
        if (this.sexo) {
            return "Macho";
        } else {
            return "Hembra";
        }
    }

    /**
     * Comprobación de madurez del pez
     * 
     * @return true si es maduro, false si no lo es
     */
    public boolean comprobacionMadurez(int edad){
        if(edad >= this.datos.getMadurez()){
            this.setMaduro(true);
        } else {
            this.setMaduro(false);
        }
        return alimentado;
    }

    /**
     * Comprobación de crecimiento del pez en base a la comida
     * 
     * @param tanque El tanque al que pertecene el pez
     * @param piscifactoria Piscifactoria a la que pertenece el pez
     */
    public void grow(Tanque tanque, Piscifactoria piscifactoria, Boolean almacenCentral){
        Random r = new Random();
        if (this.vivo) {
            comer(tanque, piscifactoria, almacenCentral);

            if (!this.maduro && r.nextDouble() < 0.05) {
                this.vivo = false;
            }

            if(!this.alimentado){
                this.muerte();
            }

            if (this.vivo) {
                this.edad++;
                System.out.println(this.edad);
                this.comprobacionMadurez(this.edad);
            }
        }
        this.alimentado = false;
    }

    /**
     * El pez tiene un 50% de posibilidades de morir
     */
    public void muerte(){
        Random muerte = new Random();

        if(muerte.nextBoolean()){
            this.setVivo(false);
        }
    }


    /**
     * Comprueba si el pez es optimo para reproducirse
     * 
     * @return true si el pez está optimo para reproducirse, false si no lo está.
     */
    public boolean reproducirse() {
        boolean machoPresente = this.sexo;
        if (this.maduro && this.ciclo <= 0) {
            if (!this.sexo && machoPresente)  {
                this.ciclo = this.datos.getCiclo();
                    return true;
            } else {
                    return false;
                }
            } else {
                this.ciclo--; // Decrementamos el ciclo para que pueda reproducirse en el futuro
            return false; // esto es si es macho no se reproduce
            }
        } 

    /**
     * Comprobar si el pez es óptimo para vender
     * 
     * @return true si es optimo, false si no lo es
     */
    public boolean isOptimo() {
        return this.edad == this.datos.getOptimo();
    }
   

    /**
     * Se muestra el estado actual del pez
     */
    public void showStatus(){}
    
    /**
     * Verifica si el pez pudo comer
     */
    public void comer(Tanque tanque, Piscifactoria pisc, Boolean almacenCen) {
    }

    
}