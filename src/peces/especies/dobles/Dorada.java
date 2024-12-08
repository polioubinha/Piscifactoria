package peces.especies.dobles;

import peces.alimentacion.Omnivoro;
import propiedades.AlmacenPropiedades;
import propiedades.PecesDatos;

public class Dorada extends Omnivoro{
    /** Datos del pez */
    private final PecesDatos datos = AlmacenPropiedades.DORADA;

    /**
     * Devuelve los datos del pez
     */
    public PecesDatos getDatos(){
        return datos;
    }

    /**
     * Constructor de la clase
     * @param sexo
     */
    public Dorada(boolean sexo){
        this.sexo=sexo;
        this.ciclo=this.datos.getCiclo();
    }

    /**
     * Devuelve si el pez está alimentado
     */
    public boolean isAlimentado(){
        return alimentado;
    }

    /**
     * Devuelve la edad del pez
     */
    public int getEdad(){
        return edad;
    }

    /**
     * Modifica la edad del pez
     */
    public void setEdad(int edad){
        this.edad=edad;
    }

    /**
     * Devuelve si el pez está vivo
     */
    public boolean isVivo() {
        return vivo;
    }

    /**
     * Modifica el estado de vida del pez
     */
    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    /**
     * Devuelve si el pez es maduro
     */
    public boolean isMaduro() {
        return maduro;
    }

    /**
     * Modifica el estado de madurez del pez
     */
    public void setMaduro(boolean maduro) {
        this.maduro = maduro;
    }

    /**
     * Devuelve el sexo del pez
     */
    public boolean isSexo() {
        return sexo;
    }

    /**
     * Devuelve un String del sexo del pez
     */
    public String getSexo() {
        return this.sexo ? "Macho" : "Hembra";
    }

    /**
     * Muestra los datos del pez
     */
    public static void datos(){
        PecesDatos datos = AlmacenPropiedades.DORADA;
        System.out.println("------------");
        System.out.println("Nombre común: " + datos.getNombre());
        System.out.println("Nombre científico: " + datos.getCientifico());
        System.out.println("Tipo: " + datos.getTipo());
        System.out.println("Coste: " + datos.getCoste());
        System.out.println("Precio venta: " + datos.getMonedas());
        System.out.println("Huevos: " + datos.getHuevos());
        System.out.println("Ciclo: "+ datos.getCiclo());
        System.out.println("Madurez: " + datos.getMadurez());
        System.out.println("Óptimo: " + datos.getOptimo());
        System.out.println("Tipo de comida: " + getTipoComida());
    }

    /**
     * Muestra el estado del pez
     */
    public void showStatus(){
        System.out.println("------" + this.datos.getNombre() + "------");
        System.out.println("Edad: " + this.edad + " días");
        System.out.println("Sexo: " + (this.sexo ? "Macho" : "Hembra"));
        System.out.println("Vivo: " + (this.vivo ? "Si" : "No"));
        System.out.println("Adulto: " + (this.maduro ? "Si" : "No"));
        System.out.println("Fertil: " + (this.ciclo == 0 ? "Si" : "No"));
        System.out.println("Alimentado: " + (this.alimentado ? "Si" : "No"));
    }

    /**
     * Comprueba la madurez del pez
     */
    public void comprobacionMadurez(int edad){
        this.setMaduro(this.edad >= this.datos.getMadurez());
    }

    /**
     * Devuelve si el pez es optimo
     */
    public boolean isOptimo(){
        return this.edad==this.datos.getOptimo();
    }

    /**
     * Devuelve si el pez puede reproducirse
     */
    public boolean reproducirse(){
        if(this.maduro && this.edad % this.datos.getCiclo()==0 && !this.sexo){
            this.ciclo=this.datos.getCiclo();
            return true;
        }
        this.ciclo--;
        return false;
    }
}
