package piscifactoria;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.Arrays;

import helpers.InputHelper;
import helpers.MenuHelper;
import monedero.Monedas;
import peces.Pez;
import peces.especies.dobles.Dorada;
import peces.especies.dobles.TruchaArcoiris;
import peces.especies.mar.ArenqueDelAtlantico;
import peces.especies.mar.Besugo;
import peces.especies.mar.Caballa;
import peces.especies.mar.Robalo;
import peces.especies.mar.Sargo;
import peces.especies.rio.Carpa;
import peces.especies.rio.CarpaPlateada;
import peces.especies.rio.Pejerrey;
import peces.especies.rio.SalmonChinook;
import peces.especies.rio.TilapiaDelNilo;
import tanque.Tanque;

public class Piscifactoria {
    /** Piscifactoria de rio o de mar */
    private final boolean rio;
    /** Nombre de la piscifactoria */
    private String nombre = "";
    /** Cantidad del almacén */
    private int almacen;
    /** Cantidad maxima del almacen */
    private int almacenMax;
    /** Tanques de la piscifactoria */
    private ArrayList<Tanque<Pez>> tanques = new ArrayList<>();

    /**
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
            this.almacenMax = 25;
        }else{
            this.nombre = nombre;
            this.tanques.add(new Tanque<Pez>(100));
            this.almacen = 100;
            this.almacenMax = 100;
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

    public int getAlmacenMax() {
        return almacenMax;
    }

    public void setAlamacenMax(int alamacenMax) {
        this.almacenMax = alamacenMax;
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

    /**
     * Muestra el estado actual del sistema de piscifactoría, incluyendo la cantidad de tanques, 
     * ocupación, cantidad de peces vivos, alimentados y adultos, así como la distribución de 
     * sexos y el estado del almacen de comida.
     */
    public void showStatus() {
        System.out.println("==========" + this.nombre + "==========");
        System.out.println("Tanques: " + this.tanques.size());
        System.out.println("Ocupacion: " + this.totalPeces() + "/" + this.capacidadTotal() + " ("
                + this.porcentaje(this.totalPeces(), this.capacidadTotal()) + "%)");
        System.out.println("Peces vivos: " + this.pecesVivos() + "/" + this.totalPeces() + " ("
                + this.porcentaje(this.pecesVivos(), this.totalPeces()) + "%)");
        System.out.println("Peces alimentados: " + this.totalAlimentados() + "/" + this.pecesVivos() + " ("
                + this.porcentaje(this.totalAlimentados(), this.pecesVivos()) + "%)");
        System.out.println("Peces adultos: " + this.adultosTotales() + "/" + this.pecesVivos() + " ("
                + this.porcentaje(this.adultosTotales(), this.pecesVivos()) + "%)");
        System.out.println("Hembras/Machos: " + this.totalHembras() + "/" + this.totalMachos());
        System.out.println("Almacen de comida actual: " + this.almacen + "/" + this.almacenMax + " ("
                + this.porcentaje(this.almacen, this.almacenMax) + "%)");
    }


    /**
     * Obtiene la cantidad total de peces adultos de la piscifactoria
     * 
     * @return cantidad total de peces adultos
     */
    public int adultosTotales(){
        int cantidad = 0;

        for(Tanque<Pez> tanque: tanques){
            cantidad += tanque.adultos();
        }

        return cantidad;
    }

    /*
     * Obtiene la cantidad total de hembras de la piscifactoria
     * 
     * @return cantidad total de hembras
     */
    public int totalHembras(){
        int cantidad = 0;

        for(Tanque<Pez> tanque: tanques){
            cantidad += tanque.hembras();
        }

        return cantidad;
    }

    /*
     * Obtiene la cantidad total de machos de la piscifactoria
     * 
     * @return cantidad total de machos
     */
    public int totalMachos(){
        int cantidad = 0;

        for(Tanque<Pez> tanque: tanques){
            cantidad += tanque.machos();
        }

        return cantidad;
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
     * @return La cantidad total de peces vivos en la piscifactoria
     */
    public int pecesVivos(){
        int cantidad = 0;

        for(Tanque<Pez> tanque : tanques){
            cantidad += tanque.vivos();
        }

        return cantidad;
    }

    /*
     * @return Cantidad total de peces alimentados de la piscifactoría
     */
    public int totalAlimentados(){
        int cantidad = 0;

        for(Tanque<Pez> tanque : tanques){
            cantidad += tanque.alimentados();
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

    /**
     * Calcula un porcentaje 
     * @param cantidad 
     * @param total 
     * @return porcentaje calculado
     */
    public double porcentaje(int cantidad, int total){
        if(total == 0){
            return 0.0;
        }

        double porcentaje = (double) cantidad/total * 100;
        porcentaje = Math.round(porcentaje * 100) / 10.0;
        return porcentaje;
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

    public void limpiarTanques() {
        for(Tanque<Pez> tanque: tanques){
            tanque.limpiarTanque();
        }      
  
    }

    public void vaciarTanques() {
        for(Tanque<Pez> tanque: tanques){
            tanque.vaciarTanque();
        }      
    }

   

    public void venderAdultos() {
        int totalVendidos = 0;
        int totalGanancias = 0;
        
        for (Tanque<Pez> tanque : tanques) {
            tanque.venderAdultos(); 
            totalVendidos += tanque.getVendidos();
            totalGanancias += tanque.getGanancias();
        }
        
        System.out.println("Piscifactoría " + nombre + ": " + totalVendidos + " peces adultos vendidos por " + totalGanancias + " monedas.");
    }

    public void upgradeFood() {
        if(this.rio){
            if(Monedas.getInstance().comprobarCompra(100)){
                if(this.almacenMax < 250){
                    Monedas.getInstance().compra(100);
                    this.almacenMax += 25;
                    System.out.println("Se ha mejorado la piscifactoría " + 
                        this.nombre +". La capacidad se ha aumentado hasta " + 
                        this.almacenMax + " por 100 monedas."
                    );
                }else{
                    System.out.println("No se puede aumentar la capacidad de la piscifactoría.");
                }
            }else{
                System.out.println("No tienes las monedas suificientes para realizar la mejora.");
            }
        }else{
            if(Monedas.getInstance().comprobarCompra(200)){
                if(this.almacenMax < 1000){
                    Monedas.getInstance().compra(200);
                    this.almacenMax += 100;
                    System.out.println("Se ha mejorado la piscifactoría " + 
                        this.nombre +". La capacidad se ha aumentado hasta " + 
                        this.almacenMax + " por 200 monedas."
                    );
                }else{
                    System.out.println("No se puede aumentar la capacidad de la piscifactoría.");
                }
            }else{
                System.out.println("No tienes las monedas suificientes para realizar la mejora.");
            }
        }
    }
   public void addFish(int tanque, int pez){
        if(this.rio){
            if(this.tanques.get(tanque).getPeces().size() < this.tanques.get(tanque).getCapacidad()){
                switch(pez){
                    case 1:
                        this.tanques.get(tanque).comprarPez(new Carpa(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    case 2:
                        this.tanques.get(tanque).comprarPez(new CarpaPlateada(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    case 3:
                        this.tanques.get(tanque).comprarPez(new Pejerrey(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    case 4:
                        this.tanques.get(tanque).comprarPez(new SalmonChinook(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    case 5:
                        this.tanques.get(tanque).comprarPez(new TilapiaDelNilo(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    case 6:
                        this.tanques.get(tanque).comprarPez(new Dorada(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    case 7:
                        this.tanques.get(tanque).comprarPez(new TruchaArcoiris(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    default:
                        break;
                }
            }else{
                System.out.println("El tanque está completo. No se pueden añadir más peces.");
            }
        }else{
            if(this.tanques.get(tanque).getPeces().size() < this.tanques.get(tanque).getCapacidad()){
                switch (pez) {
                    case 1:
                        this.tanques.get(tanque).comprarPez(new ArenqueDelAtlantico(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    case 2:
                        this.tanques.get(tanque).comprarPez(new Besugo(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    case 3:
                        this.tanques.get(tanque).comprarPez(new Caballa(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    case 4:
                        this.tanques.get(tanque).comprarPez(new Robalo(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    case 5:
                        this.tanques.get(tanque).comprarPez(new Sargo(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    case 6:
                        this.tanques.get(tanque).comprarPez(new Dorada(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    case 7:
                        this.tanques.get(tanque).comprarPez(new TruchaArcoiris(this.tanques.get(tanque).sexoNuevoPez()));
                        break;
                    default:
                        break;
                }
            }else{
                System.out.println("El tanque está completo. No se pueden añadir más peces.");
            }
        }
    }
    public void opcionPez(MenuHelper menuHelper, InputHelper inputHelper) {
    List<String> tanqueOptions = new ArrayList<>();
    for (int i = 0; i < tanques.size(); i++) {
        tanqueOptions.add("Tanque " + (i + 1));
    }

    int tanque = menuHelper.showMenu(tanqueOptions, "Seleccione un tanque para añadir el pez") - 1;

    List<String> pezOptions;
    if (rio) {
        pezOptions = Arrays.asList("Carpa", "Carpa Plateada", "Pejerrey", "Salmón Chinook", "Tilapia del Nilo", "Dorada", "Trucha Arcoiris");
    } else {
        pezOptions = Arrays.asList("Arenque del Atlántico", "Besugo", "Caballa", "Robalo", "Sargo", "Dorada", "Trucha Arcoiris");
    }

    int tipoPez = menuHelper.showMenu(pezOptions, "Seleccione el tipo de pez para añadir");

    if (tanque >= 0 && tanque < tanques.size() && tipoPez >= 1 && tipoPez <= pezOptions.size()) {
        addFish(tanque, tipoPez);
    } else {
        System.out.println("Selección de tanque o tipo de pez no válida.");
    }
}

    
    
    
    
public void newFish(MenuHelper menuHelper, InputHelper inputHelper) {
    boolean salida = false;

    try {
        do {
            // Mostrar lista de tanques
            this.listTanks();

            // Selección del tanque
            System.out.println("Seleccione el tanque donde desea añadir el pez:");
            int opcion = menuHelper.showMenu(new ArrayList<>(Arrays.asList("Seleccionar tanque", "Cancelar")), "Seleccionar tanque");

            if (opcion >= 0 && opcion < this.tanques.size()) {
                if (this.tanques.get(opcion).getPeces().isEmpty()) {
                    this.opcionPez(menuHelper, inputHelper);
                    salida = true;
                } else {
                    // Confirmación de compra de pez adicional en tanque existente
                    boolean confirmarCompra = inputHelper.getYesNoInput("¿Desea añadir un pez en el tanque seleccionado?");
                    if (confirmarCompra) {
                        this.tanques.get(opcion).comprarPez();
                    }
                    salida = true;
                }
            } else {
                System.out.println("Opción de tanque no válida, introduce una opción válida.");
            }
        } while (!salida);
    } catch (Exception e) {
        System.out.println("Ocurrió un error inesperado: " + e.getMessage());
    }
}

    public void addComida(int cantidad){
        int coste;
        if(cantidad <= 25){
            coste = cantidad;
        }else{
            coste = cantidad - (cantidad / 25) *5;
        }

        if(Monedas.getInstance().comprobarCompra(coste)){
            this.almacen += cantidad;
            Monedas.getInstance().compra(coste);

            if(this.almacen > this.almacenMax){
                this.almacen = this.almacenMax;
            }
            System.out.println("Añadida " + cantidad + " de comida.");
        }else{
            System.out.println("No tienes las suficientes monedas para realizar la compra.");
        }
    }


}
