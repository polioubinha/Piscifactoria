package piscifactoria;

import java.util.ArrayList;

import monedero.Monedas;
import peces.Pez;
import tanque.Tanque;

public class Piscifactoria {
    private final boolean rio;
    private String nombre = "";
    private int almacen;
    private int alamacenMax;
    private ArrayList<Tanque<Pez>> tanques = new ArrayList<>();

    /*
     * Constructor de la piscifactoria
     * 
     * @param rio true si es un rio, false si es de mar
     * @param nombre nombre de la piscifactoria
     */
    public Piscifactoria(boolean rio, String nombre) {
        this.rio = rio;
        if(this.rio){
            this.nombre = nombre;
            this.tanques.add(new Tanque<Pez>(25));
            this.almacen = 25;
            this.alamacenMax = 25;
        }else{
            this.nombre = nombre;
            this.tanques.add(new Tanque<Pez>(100));
            this.almacen = 100;
            this.alamacenMax = 100;
        }
    }

    public boolean isRio() {
        return rio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAlmacen() {
        return almacen;
    }

    public void setAlmacen(int almacen) {
        this.almacen = almacen;
    }

    public int getAlamacenMax() {
        return alamacenMax;
    }

    public void setAlamacenMax(int alamacenMax) {
        this.alamacenMax = alamacenMax;
    }

    public ArrayList<Tanque<Pez>> getTanques() {
        return tanques;
    }

    public void setTanques(ArrayList<Tanque<Pez>> tanques) {
        this.tanques = tanques;
    }

    public void añadirTanque(){
        if (this.rio) {
            this.tanques.add(new Tanque<Pez>(25));
        }else{
            this.tanques.add(new Tanque<Pez>(100));
        }
    }

    public void nextDay(Boolean almCentral){
        for(int i=0; i < this.tanques.size(); i++){
            if(this.almacen != 0){
                this.tanques.get(i).nextFood(this, almCentral);
                this.tanques.get(i).nextDayReproduccion();
            }
            this.tanques.get(i).venderOptimos();
            System.out.println("Piscifactoria " + this.nombre + ": " + this.tanques.get(i).getVendidos() + " peces vendidos por " + this.tanques.get(i).getGanancias() + " monedas");
        }
        this.gananciasDiarias();
    }

    private void gananciasDiarias() {
        int ganancias = 0;
        int cantidad = 0;

        for (Tanque<Pez> tanque : tanques) {
            cantidad += tanque.getVendidos();
            ganancias += tanque.getGanancias();
        }
        System.out.println("Piscifactoria " + this.nombre + ": " + cantidad + " peces vendidos por " + ganancias
        + " monedas totales");
    }

    public void showStatus(){
        System.out.println("===============" + this.nombre + "===============");
        System.out.println("Tanques: " + this.tanques.size());
        System.out.println("Ocupacion: " + this.totalPeces() + "/" + this.capacidadTotal() + " (" + this.porcentaje(this.totalPeces(), this.capacidadTotal()) + "%)");
    }


    /*
     * Devuelve la cantidad total de peces en la piscifactoria
     * 
     * @return cantidad total de peces
     */
    public int totalPeces(){
        int cantidad = 0;

        for (Tanque<Pez> tanque : tanques){
            cantidad += tanque.getPeces().size();
        }
        return cantidad;
    }

    /*
     * Devuelve la capacidad total de la piscifactoria
     * 
     * @return capacidad total de la piscifactoria
     */
    public int capacidadTotal(){
        int cantidad = 0;

        for(Tanque<Pez> tanque : tanques){
            cantidad += tanque.getCapacidad();
        }

        return cantidad;
    }

    public double porcentaje(int cantidad, int total){
        return (cantidad * 100) / total;
    }

    /*
     * Se muestra la lista de tanques de la piscifactoría
     */
    public void listTanks(){
        for(int i = 0; i < this.tanques.size(); i++){
            if(this.tanques.get(i).getPeces().size() == 0){
                System.out.println(i + " tanque vacío");
            }else{
                System.out.println(i + ", pez: " + this.tanques.get(i).getPeces().get(0).getDatos().getNombre());
            }
        }
    }

    /*
     * Compra de un nuevo tanque de peces en la piscifactoria
     */
    public void comprarTanque(){
        if(this.rio){
            if(Monedas.getInstance().comprobarCompra(150 * this.tanques.size())){
                if(this.tanques.size() < 10){
                    Monedas.getInstance().compra(150 * this.tanques.size());
                    this.tanques.add(new Tanque<Pez>(25));
                }else{
                    System.out.println("No se puede comprar un tanque nuevo. Has alcanzado el límite.");
                }
            }else{
                System.out.println("No tienes dinero suficiente");
            }
        }else{
            if(Monedas.getInstance().comprobarCompra(600 * this.tanques.size())){
                if(this.tanques.size() < 10){
                    Monedas.getInstance().compra(600 * this.tanques.size());
                    this.tanques.add(new Tanque<Pez>(100));
                }else{
                    System.out.println("No se puede comprar un tanque nuevo. Has alcanzado el límite.");
                }
            }else{
                System.out.println("No tienes dinero suficiente");
            }
        }
    }
}
