package simulador;

import java.util.ArrayList;
import java.util.Scanner;

import almacenCentral.AlmacenCentral;
import monedero.Monedas;
import peces.especies.dobles.*;
import peces.especies.mar.*;
import peces.especies.rio.*;
import piscifactoria.Piscifactoria;
import propiedades.AlmacenPropiedades;
import stats.Stats;

public class Simulador {
    /** Array con los nombres de los peces */
    private final String[] peces = {
        AlmacenPropiedades.DORADA.getNombre(),
        AlmacenPropiedades.TRUCHA_ARCOIRIS.getNombre(),
        AlmacenPropiedades.ARENQUE_ATLANTICO.getNombre(),
        AlmacenPropiedades.BESUGO.getNombre(),
        AlmacenPropiedades.CABALLA.getNombre(),
        AlmacenPropiedades.SARGO.getNombre(),
        AlmacenPropiedades.ROBALO.getNombre(),
        AlmacenPropiedades.CARPA.getNombre(),
        AlmacenPropiedades.CARPA_PLATEADA.getNombre(),
        AlmacenPropiedades.PEJERREY.getNombre(),
        AlmacenPropiedades.SALMON_CHINOOK.getNombre(),
        AlmacenPropiedades.TILAPIA_NILO.getNombre()
    };
    /** Piscifactorías del sistema */
    private ArrayList<Piscifactoria> piscifactorias = new ArrayList<>();
    /** Almacen central */
    private boolean almacenCentral = false;
    /** Scanner para pedir cosas por teclado */
    private static Scanner sc = new Scanner(System.in);
    /** Días transcurridos */
    private int dias = 0;
    private String nombreEmpresa = "";

    public static void main(String[] args) {
        Simulador simulador = new Simulador();
        int salida = 0;
    
        try {
            simulador.init();
            do {
                salida = 0;
                System.out.println("\n===============================");
                System.out.println("            Día: " + simulador.getDias());
                System.out.println("===============================\n");
                
                if (simulador.getDias() == 0) {
                    System.out.println("¡Bienvenido a " + simulador.getNombreEmpresa() + "!");
                } else {
                    System.out.println("¡Bienvenido de nuevo a " + simulador.getNombreEmpresa() + "!");
                }
                System.out.println("¿Qué desea hacer?");
                
                
                System.out.println("\n--- Menú ---");
                System.out.println("1. Mostrar estado general");
                System.out.println("2. Gestionar piscina");
                System.out.println("3. Opción 3...");
                System.out.println("4. Mostrar estadísticas");
                System.out.println("5. Mostrar ictio");
                System.out.println("6. Avanzar un día");
                System.out.println("7. Agregar comida");
                System.out.println("8. Agregar pez");
                System.out.println("9. Vender");
                System.out.println("10. Limpiar tanque");
                System.out.println("11. Vaciar tanque");
                System.out.println("12. Mejorar instalaciones");
                System.out.println("13. Avanzar varios días");
                System.out.println("14. Salir");
                System.out.print("\nIntroduce tu opción: ");
                
                try {
                    salida = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("\n¡Error! Introduce un valor numérico válido.");
                    continue;
                }
    
                System.out.println("\n-------------------------------");
                switch (salida) {
                    case 1:
                        simulador.showGeneralStatus();
                        break;
                    case 2:
                        simulador.menuPisc(salida);
                        break;
                    case 3:
                        simulador.menuPisc(salida);
                        break;
                    case 4:
                        Stats.getInstancia().mostrar();
                        break;
                    case 5:
                        simulador.showIctio();
                        break;
                    case 6:
                        simulador.nextDay(1);
                        break;
                    case 7:
                        simulador.addFood();
                        break;
                    case 8:
                        simulador.addFish();
                        break;
                    case 9:
                        simulador.sell();
                        break;
                    case 10:
                        simulador.cleanTank();
                        break;
                    case 11:
                        simulador.emptyTank();
                        break;
                    case 12:
                        simulador.upgrade();
                        break;
                    case 13:
                        int avanzarDias = 1;
                        System.out.print("¿Cuántos días quieres avanzar? ");
                        try {
                            avanzarDias = Integer.parseInt(sc.nextLine());
                            simulador.nextDay(avanzarDias);
                        } catch (NumberFormatException e) {
                            System.out.println("\nIntroduce un valor numérico válido.");
                        }
                        break;
                    case 14:
                        System.out.println("\n¡Gracias por jugar! ¡Hasta la próxima!");
                        break;
                    case 99:
                        Monedas.getInstance().añadirMonedas(1000);
                        System.out.println("Añadidas 1000 monedas a la cuenta con la opción oculta.");
                    default:
                        System.out.println("\nOpción no válida. Inténtalo de nuevo.");
                        break;
                }
            } while (salida != 14);
        } catch (IllegalStateException e) {
            System.out.println("\n" + e.getMessage());
        } finally {
            sc.close();
        }
    }
    
    public void init(){
        System.out.print("Introduce el nombre de la empresa: ");
        String nombre = sc.nextLine();
        String nombrePisci = nombrePiscifactoria();
        Monedas.getInstance();
        Stats.getInstancia(peces);
        this.setNombreEmpresa(nombre);
        this.piscifactorias.add(new Piscifactoria(true, nombrePisci));
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }
    
    /**
     * Menú principal del simulador
     */
    public void menu() {
        System.out.println("******Menú******");
        System.out.println("1. Estado general");
        System.out.println("2. Estado piscifactoría");
        System.out.println("3. Estado tanques");
        System.out.println("4. Informes");
        System.out.println("5. Ictiopedia");
        System.out.println("6. Pasar día");
        System.out.println("7. Comprar comida");
        System.out.println("8. Comprar peces");
        System.out.println("9. Vender peces");
        System.out.println("10. Limpiar tanques");
        System.out.println("11. Vaciar tanques");
        System.out.println("12. Mejorar");
        System.out.println("13. Pasar varios días");
        System.out.println("14. Salir");
    }

    public void showGeneralStatus(){
        for(Piscifactoria piscifactoria : piscifactorias){
            piscifactoria.showStatus();
        }

        System.out.println("Día: " + this.dias);
        System.out.println(Monedas.getInstance().getCantidad() + " cantidad de monedas.");
        if(almacenCentral == true){
            System.out.println("Almacen central: " + AlmacenCentral.getInstance().getCapacidad() + "/" 
                + AlmacenCentral.getInstance().getCapacidadMax() + " (" + 
                this.piscifactorias.get(0).porcentaje(AlmacenCentral.getInstance().getCapacidad(), 
                AlmacenCentral.getInstance().getCapacidadMax()) + "%)"
            );
        }
    }

    public void menuPisc(int salida) {
        int contador = 1;
        System.out.println("Seleccione una opción:");
        System.out.println("--------------------------- Piscifactorías ---------------------------");
        System.out.println("[Peces vivos / Peces totales / Espacio total]");
        
        // Listado de piscifactorías con el formato deseado
        for (Piscifactoria piscifactoria : piscifactorias) {
            System.out.println(contador + ".- " + piscifactoria.getNombre() + " [" + piscifactoria.pecesVivos() + "/"
                    + piscifactoria.totalPeces() + "/" + piscifactoria.capacidadTotal() + "]");
            contador++;
        }
    
        // Opción para cancelar
        System.out.println("0.- Cancelar");
    
        try {
            int indice = Integer.parseInt(sc.nextLine());
    
            if (indice == 0) {
                System.out.println("Operación cancelada. Retornando al menú principal...");
                return; // Termina el método y regresa al menú principal
            } else if (indice < 1 || indice > this.piscifactorias.size()) {
                System.out.println("Opción inválida, retornando al menú principal");
            } else {
                if (salida == 2) {
                    this.piscifactorias.get(indice - 1).showStatus();
                } else if (salida == 3) {
                    this.piscifactorias.get(indice - 1).listTanks();
                    int tanque = Integer.parseInt(sc.nextLine());
                    if (tanque < 0 || tanque > this.piscifactorias.get(indice - 1).getTanques().size()) {
                        System.out.println("Opción no válida, retornando al menú principal");
                    } else {
                        System.out.println("=============== Tanque " + tanque + "===============");
                        this.piscifactorias.get(indice - 1).getTanques().get(tanque).showStatus();
                        this.piscifactorias.get(indice - 1).getTanques().get(tanque).showFishStatus();
                    }
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Opción no válida");
        }
    }
    

    /*
     * Limpia los peces muertos de los tanques de las piscifactorias
     */
    public void cleanTank(){
        for(Piscifactoria piscifactoria : piscifactorias){
            //Añadir metodo para limpiar los tanques de la piscifactoría
            piscifactoria.limpiarTanques();
        }
    }

    /**
     * Vacía los tanques de las piscifactorias
     */
    public void emptyTank(){
        for(Piscifactoria piscifactoria : piscifactorias){
            //Añadir metodo para vaciar los tanques de la piscifactoría
            piscifactoria.vaciarTanques();
        }
    }

    public void nextDay(int dias){
        for(int i=0; i < dias; i++){
            for(Piscifactoria piscifactoria : piscifactorias){
                //Añadir metodo para pasar días en la piscifactoria
                piscifactoria.nextDay(this.almacenCentral);
            }
            this.dias++;
        }
    }

    /**
     * Proporciona al usuario la posibilidad de obtener datos
     * de los peces disponibles
     */
    public void showIctio(){
        int opcion=0;
        do {
            for(int i=0; i < peces.length; i++){
                System.out.println((i + 1) + ". " + peces[i]);
            }
            try {
                opcion = Integer.parseInt(sc.nextLine());
                if (opcion < 1 || opcion > peces.length) {
                    System.out.println("Opción incorrecta.");
                }else{
                    switch (opcion) {
                        case 1:
                            Dorada.datos();
                            break;
                        case 2:
                            TruchaArcoiris.datos();
                            break;
                        case 3:
                            ArenqueDelAtlantico.datos();
                            break;
                        case 4:
                            Besugo.datos();
                            break;
                        case 5:
                            Caballa.datos();
                            break;
                        case 6:
                            Robalo.datos();
                            break;
                        case 7:
                            Sargo.datos();
                            break;
                        case 8:
                            Carpa.datos();
                            break;
                        case 9:
                            CarpaPlateada.datos();
                            break;
                        case 10:
                            Pejerrey.datos();
                            break;
                        case 11:
                            SalmonChinook.datos();
                            break;
                        case 12:
                            TilapiaDelNilo.datos();
                            break;
                        default:
                            break;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Opción incorrecta.");
            }
        } while (opcion < 1 || opcion > peces.length);
    }

    public void sell(){
        for(Piscifactoria piscifactoria : piscifactorias){
            //Añadir método para vender los peces adultos de las piscifactorias
            piscifactoria.venderAdultos();
        }
    }

    //Metodo para añadir comida
    public void addFood(){
        if(!almacenCentral){
            this.selecPisc();
            int piscifactoria = Integer.parseInt(sc.nextLine());
            this.piscifactorias.get(piscifactoria).getAlmacen();

            System.out.println("Opciones de comida:");
            System.out.println("1. Añadir 5");
            System.out.println("2. Añadir 10");
            System.out.println("3. Añadir 25");
            System.out.println("4. Llenar");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");

            int opc = Integer.parseInt(sc.nextLine());

            switch(opc){
                case 1:
                    this.piscifactorias.get(piscifactoria - 1).addComida(5);
                    break;
                case 2:
                    this.piscifactorias.get(piscifactoria - 1).addComida(10);
                    break;
                case 3:
                    this.piscifactorias.get(piscifactoria - 1).addComida(25);
                    break;
                case 4:
                    this.piscifactorias.get(piscifactoria - 1).addComida(this.piscifactorias.get(piscifactoria - 1).getAlmacenMax()
                    - this.piscifactorias.get(piscifactoria - 1).getAlmacen());
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Selecciona una opción válida");

            }
        }else{
            System.out.println("Opciones de comida:");
            System.out.println("1. Añadir 5");
            System.out.println("2. Añadir 10");
            System.out.println("3. Añadir 25");
            System.out.println("4. Llenar");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");

            int opcion = Integer.parseInt(sc.nextLine());
            switch (opcion) {
                case 1:
                    AlmacenCentral.getInstance().comprarComida(5);
                    break;
                case 2:
                    AlmacenCentral.getInstance().comprarComida(10);
                    break;
                case 3:
                    AlmacenCentral.getInstance().comprarComida(25);
                    break;
                case 4:
                    AlmacenCentral.getInstance().comprarComida(AlmacenCentral.getInstance().getCapacidadMax()
                    - AlmacenCentral.getInstance().getCapacidad());
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Selecciona una opción válida.");
            }
        }
    }

    //Permite al usuario seleccionar una piscifactoria y añadir un pez a un tanque de esa misma
    public void addFish(){
        int piscifactoria = 0;
        boolean salida = false;
        do{
            System.out.println("====== PISCIFACTORÍAS ======");
            this.selecPisc();
            System.out.print("Selecciona una piscifactoría: ");
            piscifactoria = Integer.parseInt(sc.nextLine());
            if (piscifactoria < 0 || piscifactoria > this.piscifactorias.size()) {
                System.out.println("Índice incorrecto, inserta un valor de los indicados");
            } else {
                this.piscifactorias.get(piscifactoria - 1).newFish();
                salida = true;
            }
        }while(!salida);
    }


    /*
     * Metodo para 
     */
    public void newDay(int días){
        for(int i=0; i < dias; i++){
            for(Piscifactoria piscifactoria : piscifactorias){
                piscifactoria.nextDay(this.almacenCentral);
            }
            this.dias++;
        }
    }

    /**
     * Proporciona un menú para mejorar edificios o comprar nuevos edificios en la piscifactoría.
     */
    public void upgrade() {
        int opcion = 0;
        System.out.println("====== Mejoras disponibles =====");
        System.out.println("1. Comprar edificios");
        System.out.println("2. Mejorar edificios");
        System.out.println("3. Cancelar");

        opcion = obtenerOpcion();

        switch (opcion) {
            case 1:
                comprarEdificio();
                break;
            case 2:
                mejorarEdificio();
                break;
            case 3:
                break; // Cancelar
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    /**
     * Maneja la compra de nuevos edificios.
     */
    private void comprarEdificio() {
        System.out.println("===== Comprar edificios =====");
        System.out.println("1. Piscifactoría");
        if (!almacenCentral) {
            System.out.println("2. Almacén central");
        }
        
        int opcion = obtenerOpcion();
        
        switch (opcion) {
            case 1:
                boolean tipo = tipoPiscifactoria();
                nuevaPiscifactoria(tipo);
                break;
            case 2:
                if (!almacenCentral) {
                    comprarAlmacen();
                } else {
                    System.out.println("Opción no válida.");
                }
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    /**
     * Maneja la mejora de edificios existentes.
     */
    private void mejorarEdificio() {
        System.out.println("===== Mejorar edificios =====");
        System.out.println("1. Piscifactoría");
        if (almacenCentral) {
            System.out.println("2. Almacén central");
        }

        int opcion = obtenerOpcion();

        switch (opcion) {
            case 1:
                mejorarPiscifactoria();
                break;
            case 2:
                if (almacenCentral) {
                    AlmacenCentral.getInstance().upgrade();
                } else {
                    System.out.println("Opción no válida.");
                }
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    /**
     * Maneja la mejora de una piscifactoría específica.
     */
    private void mejorarPiscifactoria() {
        int pisc;
        do {
            this.selecPisc();
            pisc = obtenerOpcion(); // Obtener opción de piscifactoría

            if (pisc < 1 || pisc > this.piscifactorias.size()) {
                System.out.println("Inserta un valor válido");
                continue; 
            }

            int mejoraOpcion;
            do {
                System.out.println("===== Mejorar piscifactoría =====");
                System.out.println("1. Comprar tanque");
                System.out.println("2. Aumentar almacén");
                System.out.println("3. Cancelar");
                mejoraOpcion = obtenerOpcion();

                switch (mejoraOpcion) {
                    case 1:
                        this.piscifactorias.get(pisc - 1).comprarTanque();
                        break;
                    case 2:
                        this.piscifactorias.get(pisc - 1).upgradeFood();
                        break;
                    case 3:
                        return; // Cancelar
                    default:
                        System.out.println("Opción no válida.");
                        break;
                }
            } while (mejoraOpcion != 3); // Repite hasta cancelar
        } while (pisc < 1 || pisc > this.piscifactorias.size()); // Repite hasta que la opción sea válida
    }

    /**
     * Obtiene una opción del usuario con manejo de excepciones.
     * 
     * @return Opción seleccionada por el usuario.
     */
    private int obtenerOpcion() {
        int opcion = 0;
        while (true) {
            try {
                opcion = Integer.parseInt(sc.nextLine());
                return opcion; 
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida, por favor introduce un número.");
            }
        }
    }

    /**
     * Proporciona las piscifactorías disponibles
     */
    private void selecPisc() {
        for(int i = 0; i < this.piscifactorias.size(); i++){
            System.out.println((i + 1) + ". " + this.piscifactorias.get(i).getNombre());
        }
    }

    private void comprarAlmacen() {
        if(Monedas.getInstance().comprobarCompra(2000)){
            Monedas.getInstance().compra(2000);
            AlmacenCentral.getInstance();
            this.almacenCentral = true;
        }else{
            System.out.println("No tienes las suficentes monedas para realizar la compra.");
        }
    }

    /**
     * Proporciona al usuario un menu para seleccionar el tipo de piscifactoría
     * 
     * @return true si es piscifactoria de rio, false si es de mar
     */
    private boolean tipoPiscifactoria() {
        int opcion = 0;
        boolean tipo = true;
        do {
            System.out.println("Selecciona el tipo de piscifactoría:");
            System.out.println("1. Río");
            System.out.println("2. Mar");
            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opción no válida, por favor introduce un número.");
            }

            if(opcion == 1){
                tipo = true;
            }else if(opcion == 2){
                tipo = false;
            }else{
                System.out.println("Opción no válida, introduce un valor válido.");
            }
        } while (opcion < 1 || opcion > 2);
        return tipo;
    }

    /**
     * Calcula el número de piscifactorías de tipo "mar".
     *
     * @return Número de piscifactorías de tipo "mar".
     */
    public int mar() {
        int numero = 0;
        for (Piscifactoria piscifactoria : piscifactorias) {
            if (!piscifactoria.isRio()) {
                numero++;
            }
        }
        return numero;
    }

    /**
     * Calcula el número de piscifactorías de tipo "río".
     *
     * @return Número de piscifactorías de tipo "río".
     */
    public int rio() {
        int numero = 0;
        for (Piscifactoria piscifactoria : piscifactorias) {
            if (piscifactoria.isRio()) {
                numero++;
            }
        }
        return numero;
    }

    /**
     * Crea una nueva piscifactoria (rio o mar)
     * Si no tiene ninguna de mar, se añade un costo de 500 monedas
     * 
     * @param tipo true si es piscifactoria de rio, false si es de mar
     */
    private void nuevaPiscifactoria(boolean tipo) {
        if(tipo){
            if(Monedas.getInstance().comprobarCompra(this.rio() * 500)){
                Monedas.getInstance().compra(this.rio() * 500);
                String nombrePisci = nombrePiscifactoria();
                this.piscifactorias.add(new Piscifactoria(tipo, nombrePisci));
            }else{
                System.out.println("No tienes las suficientes monedas para realizar la compra.");
            }
        }else{
            if(this.mar() == 0){
                if(Monedas.getInstance().comprobarCompra(1 * 500)){
                    Monedas.getInstance().compra(this.mar() * 500);
                    String nombrePisci = nombrePiscifactoria();
                    this.piscifactorias.add(new Piscifactoria(tipo, nombrePisci));
                }else{
                    System.out.println("No tienes las suficientes monedas para realizar la compra.");
                }
            }else{
                if(Monedas.getInstance().comprobarCompra(this.mar() * 500)){
                    Monedas.getInstance().compra(this.mar() * 500);
                    String nombrePisci = nombrePiscifactoria();
                    this.piscifactorias.add(new Piscifactoria(tipo, nombrePisci));
                }else{
                    System.out.println("No tienes las suficientes monedas para realizar la compra.");
                }
            }
        }
    }

    /**
     * Se le pide al usuario introducir el nombre de la piscifactoría
     * 
     * @return El nombre de la piscifactoría
     */
    private String nombrePiscifactoria() {
        System.out.print("Introduce el nombre de la piscifactoría: ");
        return sc.nextLine();
    }
}
