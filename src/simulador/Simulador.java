package simulador;

import java.util.ArrayList;
import java.util.Scanner;

import almacenCentral.AlmacenCentral;
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
import piscifactoria.Piscifactoria;
import propiedades.AlmacenPropiedades;

public class Simulador {
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
    private ArrayList<Piscifactoria> piscifactorias = new ArrayList<>();
    private boolean almacenCentral = false;
    private static Scanner sc = new Scanner(System.in);
    private int dias = 0;


    public static void main(String[] args) {
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
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

    /**
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

    public void nuevoDia(int dias){
        for(int i=0; i < dias; i++){
            for(Piscifactoria piscifactoria : piscifactorias){
                //Añadir metodo para pasar días en la piscifactoria
                piscifactoria.nuevoDia();
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

    public void venderPeces(){
        for(Piscifactoria piscifactoria : piscifactorias){
            //Añadir método para vender los peces adultos de las piscifactorias
            piscifactoria.venderAdultos();
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
                continue; // Continúa el bucle si la opción es inválida
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
                this.piscifactorias.add(new Piscifactoria(tipo, nombrePisci))
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
