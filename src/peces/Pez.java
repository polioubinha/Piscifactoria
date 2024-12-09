package peces;

import java.util.Random;

import piscifactoria.Piscifactoria;
import propiedades.PecesDatos;
import tanque.Tanque;

/** Clase que gestiona los peces */
public abstract class Pez {
    /** Datos del pez */
    protected PecesDatos datos;
    /** Ciclo del pez */
    protected int ciclo = 0;
    /** Edad del pez */
    protected int edad = 0;
    /** True si el pez está vivo, false en caso contrario */
    protected boolean vivo = true;
    /** True si el pez es macho, false si es hembra */
    protected boolean sexo = false;
    /** True si el pez está alimentado, false en caso contrario */
    protected boolean alimentado = true;
    /** True si el pez está maduro, false en caso contrario */
    protected boolean maduro = false;

    /**
     * Devuelve los datos del pez
     * @return datos del pez
     */
    public PecesDatos getDatos(){
        return datos;
    }

    /**
     * Comprobamos si el pez esta alimentado
     * @return true si está alimentado, false si no
     */
    public boolean isAlimentado() {
        return alimentado;
    }

    /**
     * Devuelve la edad del pez
     * @return edad del pez
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Establecemos la edad del pez
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
     * @param vivo true si está vivo, false si no
     */
    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    /**
     * Comprobamos si el pez está maduro
     * @return true si está maduro, false si no
     */
    public boolean isMaduro() {
        return maduro;
    }

    /**
     * Establecemos si el pez está maduro
     * @return true si está maduro, false si no
     */
    public void setMaduro(boolean fertil) {
        this.maduro = fertil;
    }

    /**
     * Comprobamos si el pez es macho o hembra
     * @return true si es macho, false si es hembra
     */
    public boolean isSexo() {
        return sexo;
    }

    /**
     * Establecemos si el pez es macho o hembra
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
     * @return true si es maduro, false si no lo es
     */
    public void comprobacionMadurez(int años) {
        if (años >= this.datos.getMadurez()) {
            this.setMaduro(true);

        } else {
            this.setMaduro(false);
        }
    }

    /**
     * Comprobación de crecimiento del pez en base a la comida
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
     * Elimina un pez
     * @return 
     */
    public boolean eliminarPez() {
        Random comer = new Random();
        if (comer.nextBoolean()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Comprueba si el pez es optimo para reproducirse
     * @return true si el pez está optimo para reproducirse, false si no lo está.
     */
    public boolean reproducirse() {
        if (this.maduro && this.ciclo <= 0) {
            if (!this.sexo)  {
                this.ciclo = this.datos.getCiclo();
                    return true;
            } else {
                    return false;
                }
            } else {
                this.ciclo--; 
                return false;
            }
        } 

    /**
     * Comprobar si el pez es óptimo para vender
     * @return true si es optimo, false si no lo es
     */
    public boolean isOptimo() {
        if (this.edad == this.datos.getOptimo()) {
            return true;
        } else {
            return false;
        }
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