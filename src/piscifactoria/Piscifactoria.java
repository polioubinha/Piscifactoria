package piscifactoria;

import java.io.Console;
import java.util.ArrayList;
import monedero.Monedas;
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
    private ArrayList<Tanque> tanques = new ArrayList<>();

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
            this.tanques.add(new Tanque(25));
            this.almacen = 25;
            this.almacenMax = 25;
        }else{
            this.nombre = nombre;
            this.tanques.add(new Tanque(100));
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

    public ArrayList<Tanque> getTanques() {
        return tanques;
    }

    public void setTanques(ArrayList<Tanque> tanques) {
        this.tanques = tanques;
    }

    public void añadirTanque(){
        if (this.rio) {
            this.tanques.add(new Tanque(25));
        }else{
            this.tanques.add(new Tanque(100));
        }
    }

    public void nextDay(Boolean almacenCentral){
        for(int i=0; i < this.tanques.size(); i++){
            if(this.almacen != 0){
                this.tanques.get(i).nextFood(this, almacenCentral);
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

        for (Tanque tanque : tanques) {
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

        for(Tanque tanque: tanques){
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

        for(Tanque tanque: tanques){
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

        for(Tanque tanque: tanques){
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

        for (Tanque tanque : tanques){
            cantidad += tanque.getPeces().size();
        }
        return cantidad;
    }

    /*
     * @return La cantidad total de peces vivos en la piscifactoria
     */
    public int pecesVivos(){
        int cantidad = 0;

        for(Tanque tanque : tanques){
            cantidad += tanque.vivos();
        }

        return cantidad;
    }

    /*
     * @return Cantidad total de peces alimentados de la piscifactoría
     */
    public int totalAlimentados(){
        int cantidad = 0;

        for(Tanque tanque : tanques){
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

        for(Tanque tanque : tanques){
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
                System.out.println(i + ". Tanque vacío");
            }else{
                System.out.println(i + ". Pez: " + this.tanques.get(i).getPeces().get(0).getDatos().getNombre());
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
                    this.tanques.add(new Tanque(25));
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
                    this.tanques.add(new Tanque(100));
                }else{
                    System.out.println("No se puede comprar un tanque nuevo. Has alcanzado el límite.");
                }
            }else{
                System.out.println("No tienes dinero suficiente");
            }
        }
    }

    public void limpiarTanques() {
        for(Tanque tanque: tanques){
            tanque.limpiarTanque();
        }      
  
    }

    public void vaciarTanques() {
        for(Tanque tanque: tanques){
            tanque.vaciarTanque();
        }      
    }

    public void opcionPez(){
        if(this.rio){
            System.out.println("====== PECES RIO ======");
            System.out.println("1.- Carpa");
            System.out.println("2.- Carpa Plateada");
            System.out.println("3.- Pejerrey");
            System.out.println("4.- Salmon Chinook");
            System.out.println("5.- Tilapia Del Nilo");
            System.out.println("6.- Dorada");
            System.out.println("7.- Trucha Arcoiris");
        }else{
            System.out.println("====== PECES MAR ======");
            System.out.println("1.- Arenque del Atlántico");
            System.out.println("2.- Besugo");
            System.out.println("3.- Caballa");
            System.out.println("4.- Robalo");
            System.out.println("5.- Sargo");
            System.out.println("6.- Dorada");
            System.out.println("7.- Trucha Arcoiris");
        }
    }   

    public void venderAdultos() {
        for(Tanque tanque : tanques){
            tanque.venderAdultos();
        }
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
    public void opcionPez() {
        Scanner sc = new Scanner(System.in);
    
        System.out.println("Seleccione un tanque para añadir el pez:");
        for (int i = 0; i < tanques.size(); i++) {
            System.out.println((i + 1) + ". Tanque " + (i + 1));  
        }
        int tanque = sc.nextInt() - 1; 
        sc.nextLine();  
    
        System.out.println("Selecciona el tipo de pez para añadir:");
        System.out.println("1. Carpa");
        System.out.println("2. Carpa Plateada");
        System.out.println("3. Pejerrey");
        System.out.println("4. Salmón Chinook");
        System.out.println("5. Tilapia del Nilo");
        System.out.println("6. Dorada");
        System.out.println("7. Trucha Arcoiris");
    
        int tipoPez = sc.nextInt();
        sc.nextLine();  
    
        // Validación de selección de tanque y tipo de pez
        if (tanque >= 0 && tanque < tanques.size() && tipoPez >= 1 && tipoPez <= 7) {
            addFish(tanque, tipoPez);  // Llama al método addFish con los parámetros enteros
        } else {
            System.out.println("Opción de tanque o tipo de pez no válida.");
        }
        
        sc.close();
    }
    
    
    
    public void newFish() {
        Console c = System.console();
        int opcion = 0;
        int pez = 0;
        boolean salida = false;

        try {
            do {
                this.listTanks();
                System.out.print("Selecciona un tanque: ");
                try {
                    opcion = Integer.parseInt(c.readLine());

                    if (opcion < 0 || opcion >= this.tanques.size()) {
                        System.out.println("Opción no válida, introduce uno de los valores mostrados.");
                    } else {
                        if (!this.tanques.get(opcion).getPeces().isEmpty()) {
                            try {
                                this.tanques.get(opcion).comprarPez();
                                salida = true;
                            } catch (Exception e) {
                                System.out.println("Error al comprar pez: " + e.getMessage());
                            }
                        } else {
                            do {
                                this.opcionPez();
                                System.out.print("Selecciona el tipo de pez (1-7): ");
                                try {
                                    pez = Integer.parseInt(c.readLine());

                                    if (pez > 0 && pez < 8) {
                                        this.addFish(opcion, pez);
                                        salida = true;
                                    } else {
                                        System.out.println("Opción no válida, introduce una de las opciones mostradas.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Opción no válida, introduce una de las opciones mostradas.");
                                }
                            } while (pez < 1 || pez > 7);
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Opción no válida, introduce un número.");
                }
            } while (!salida);
        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado: " + e.getMessage());
        } finally {
            scanner.close();
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
