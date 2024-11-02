package simulador;

import java.util.ArrayList;
import almacenCentral.AlmacenCentral;
import monedero.Monedas;
import helpers.InputHelper;
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
    /** Piscifactorías del simulador */
    private ArrayList<Piscifactoria> piscifactorias = new ArrayList<>();
    /** Almacen de la piscifactoria */
    private boolean almacenCentral = false;
    /** Dias transcurridos */
    private int dias = 0;
    /** Nombre de la empresa */
    private String nombreEmpresa = "";
    /** InputHelper para la entrada de datos */
    private InputHelper inputHelper;

    /**
     * Constructor de la clase 
     */
    public Simulador() {
        this.inputHelper = new InputHelper();
    }

    /**
     * Main que lleva a cabo la lógica del programa
     * @param args
     */
    public static void main(String[] args) {
        Simulador simulador = new Simulador();
        int salida = 0;
    
        simulador.init();
        do {
            System.out.println("\n===============================");
            System.out.println("            Día: " + simulador.getDias());
            System.out.println("===============================\n");
            
            System.out.println("¿Qué desea hacer?");
            System.out.println("\n--- Menú ---");
            System.out.println("1. Mostrar estado general");
            System.out.println("2. Estado Tanques");
            System.out.println("3. Mostrar estadísticas");
            System.out.println("4. Mostrar ictio");
            System.out.println("5. Avanzar un día");
            System.out.println("6. Agregar comida");
            System.out.println("7. Agregar pez");
            System.out.println("8. Vender");
            System.out.println("9. Limpiar tanque");
            System.out.println("10. Vaciar tanque");
            System.out.println("11. Mejorar instalaciones");
            System.out.println("12. Avanzar varios días");
            System.out.println("13. Salir");
            salida = simulador.inputHelper.getIntInput("Introduce tu opción: ", 1, 99);

            switch (salida) {
                case 1: simulador.showGeneralStatus(); break;
                case 2: simulador.menuPisc(); break;
                case 3: Stats.getInstancia().mostrar(); break;
                case 4: simulador.showIctio(); break;
                case 5: simulador.nextDay(1); break;
                case 6: simulador.addFood(); break;
                case 7: simulador.addFish(); break;
                case 8: simulador.sell(); break;
                case 9: simulador.cleanTank(); break;
                case 10: simulador.emptyTank(); break;
                case 11: simulador.upgrade(); break;
                case 12:
                    int avanzarDias = simulador.inputHelper.getIntInput("¿Cuántos días quieres avanzar? ", 1, 100);
                    simulador.nextDay(avanzarDias);
                    break;
                case 13:
                    System.out.println("\n¡Gracias por jugar! ¡Hasta la próxima!");
                    break;
                    
                
                
                
                
                
                
                
                    case 99: Monedas.getInstance().añadirMonedas(1000); break;
            }
        } while (salida != 13);
    }
    
    /**
     * Inicializa el programa
     */
    public void init(){
        this.nombreEmpresa = inputHelper.getStringInput("Introduce el nombre de la empresa: ");
        String nombrePisci = nombrePiscifactoria();
        Monedas.getInstance();
        Stats.getInstancia(peces);
        this.piscifactorias.add(new Piscifactoria(true, nombrePisci));
    }

    /**
     * Devuelve el nombre de la piscifactoria 
     * @return nombre piscifactoria
     */
    private String nombrePiscifactoria() {
        return inputHelper.getStringInput("Introduce el nombre de la piscifactoría: ");
    }

    /**
     * Obtiene los días
     * @return dias
     */
    public int getDias() {
        return dias;
    }

    /**
     * Modifica los dias 
     * @param dias a modificar
     */
    public void setDias(int dias) {
        this.dias = dias;
    }

    /**
     * Devuelve el nombre de la empresa
     * @return nombre de la empresa
     */
    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    /**
     * Cambia el nombre de la empresa 
     * @param nombreEmpresa
     */
    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    /**
     * Muestra el estado general de la simulación
     */
    public void showGeneralStatus(){
        for(Piscifactoria piscifactoria : piscifactorias){
            piscifactoria.showStatus();
        }

        System.out.println("Día: " + this.dias);
        System.out.println(Monedas.getInstance().getCantidad() + " cantidad de monedas.");
        if(almacenCentral){
            System.out.println("Almacen central: " + AlmacenCentral.getInstance().getCapacidad() + "/" 
                + AlmacenCentral.getInstance().getCapacidadMax() + " (" + 
                piscifactorias.get(0).porcentaje(AlmacenCentral.getInstance().getCapacidad(), 
                AlmacenCentral.getInstance().getCapacidadMax()) + "%)"
            );
        }
    }

    /**
     * Menú para seleccionar una piscifactoría
     */
    public void menuPisc() {
        System.out.println("Seleccione una opción:");
        for (int i = 0; i < piscifactorias.size(); i++) {
            System.out.println((i + 1) + ". " + piscifactorias.get(i).getNombre());
        }
        int indice = inputHelper.getIntInput("Selecciona una piscifactoría: ", 1, piscifactorias.size());
        piscifactorias.get(indice - 1).showStatus();
    }

    /**
     * Añade comida a la piscifactoría
     */
    public void addFood() {
        if(!almacenCentral){
            System.out.println("Opciones de comida:");
            System.out.println("1. Añadir 5");
            System.out.println("2. Añadir 10");
            System.out.println("3. Añadir 25");
            System.out.println("4. Llenar");
            System.out.println("5. Salir");
            int opc = inputHelper.getIntInput("Elige una opción: ", 1, 5);

            switch(opc){
                case 1: piscifactorias.get(0).addComida(5); break;
                case 2: piscifactorias.get(0).addComida(10); break;
                case 3: piscifactorias.get(0).addComida(25); break;
                case 4: piscifactorias.get(0).addComida(piscifactorias.get(0).getAlmacenMax() - piscifactorias.get(0).getAlmacen()); break;
                case 5: break;
                default: System.out.println("Selecciona una opción válida");
            }
        } else {
            System.out.println("Opciones de comida:");
            System.out.println("1. Añadir 5");
            System.out.println("2. Añadir 10");
            System.out.println("3. Añadir 25");
            System.out.println("4. Llenar");
            System.out.println("5. Salir");
            int opcion = inputHelper.getIntInput("Elige una opción: ", 1, 5);

            switch (opcion) {
                case 1: AlmacenCentral.getInstance().comprarComida(5); break;
                case 2: AlmacenCentral.getInstance().comprarComida(10); break;
                case 3: AlmacenCentral.getInstance().comprarComida(25); break;
                case 4: AlmacenCentral.getInstance().comprarComida(AlmacenCentral.getInstance().getCapacidadMax() - AlmacenCentral.getInstance().getCapacidad()); break;
                case 5: break;
                default: System.out.println("Selecciona una opción válida.");
            }
        }
    }

    /**
     * Añade un pez a la piscifactoria
     */
    public void addFish() {
        this.menuPisc();
        int piscifactoria = inputHelper.getIntInput("Seleccione la piscifactoría a la que agregar el pez: ", 1, piscifactorias.size());
        piscifactorias.get(piscifactoria - 1).newFish();
    }

    /**
     * Limpia los tanques
     */
    public void cleanTank() {
        for(Piscifactoria piscifactoria : piscifactorias){
            piscifactoria.limpiarTanques();
        }
    }

    /**
     * Vacía los tanques
     */
    public void emptyTank() {
        for(Piscifactoria piscifactoria : piscifactorias){
            piscifactoria.vaciarTanques();
        }
    }

    /**
     * Avanza un día
     * @param dias
     */
    public void nextDay(int dias) {
        for (int i = 0; i < dias; i++) {
            for (Piscifactoria piscifactoria : piscifactorias) {
                piscifactoria.nextDay(almacenCentral);
            }
            this.dias++;
        }
    }

    /**
     * Muestra los datos de una especie de pez
     */
    public void showIctio() {
        int opcion;
        do {
            for (int i = 0; i < peces.length; i++) {
                System.out.println((i + 1) + ". " + peces[i]);
            }
            opcion = inputHelper.getIntInput("Elige un pez para ver sus datos o 0 para salir: ", 0, peces.length);
            if (opcion > 0 && opcion <= peces.length) {
                switch (opcion) {
                    case 1: Dorada.datos(); break;
                    case 2: TruchaArcoiris.datos(); break;
                    case 3: ArenqueDelAtlantico.datos(); break;
                    case 4: Besugo.datos(); break;
                    case 5: Caballa.datos(); break;
                    case 6: Sargo.datos(); break;
                    case 7: Robalo.datos(); break;
                    case 8: Carpa.datos(); break;
                    case 9: CarpaPlateada.datos(); break;
                    case 10: Pejerrey.datos(); break;
                    case 11: SalmonChinook.datos(); break;
                    case 12: TilapiaDelNilo.datos(); break;
                }
            }
        } while (opcion != 0);
    }

    /**
     * Vende los peces adultos
     */
    public void sell() {
        for(Piscifactoria piscifactoria : piscifactorias){
            piscifactoria.venderAdultos();
        }
    }

    /**
     * Realiza mejoras, comprando un edificio o mejorando alguno
     */
    public void upgrade() {
        int opcion;
        do {
            System.out.println("====== Mejoras disponibles =====");
            System.out.println("1. Comprar edificios");
            System.out.println("2. Mejorar edificios");
            System.out.println("3. Cancelar");
            opcion = inputHelper.getIntInput("Elige una opción: ", 1, 3);

            switch (opcion) {
                case 1: comprarEdificio(); break;
                case 2: mejorarEdificio(); break;
                case 3: break;
            }
        } while (opcion != 3);
    }

    /**
     * Compra un edificio
     */
    private void comprarEdificio() {
        System.out.println("===== Comprar edificios =====");
        System.out.println("1. Piscifactoría");
        if (!almacenCentral) {
            System.out.println("2. Almacén central");
        }
        
        int opcion = inputHelper.getIntInput("Elige una opción: ", 1, almacenCentral ? 1 : 2);
        
        if (opcion == 1) {
            boolean tipo = tipoPiscifactoria();
            nuevaPiscifactoria(tipo);
        } else if (opcion == 2 && !almacenCentral) {
            comprarAlmacen();
        }
    }

    /**
     * Mejora un edificio
     */
    private void mejorarEdificio() {
        System.out.println("===== Mejorar edificios =====");
        System.out.println("1. Piscifactoría");
        if (almacenCentral) {
            System.out.println("2. Almacén central");
        }

        int opcion = inputHelper.getIntInput("Elige una opción: ", 1, almacenCentral ? 2 : 1);

        if (opcion == 1) {
            mejorarPiscifactoria();
        } else if (opcion == 2 && almacenCentral) {
            AlmacenCentral.getInstance().upgrade();
        }
    }

    /**
     * Mejora la piscifactoría
     */
    private void mejorarPiscifactoria() {
        int pisc;
        do {
            this.menuPisc();
            pisc = inputHelper.getIntInput("Seleccione la piscifactoría para mejorar: ", 1, piscifactorias.size());

            if (pisc < 1 || pisc > piscifactorias.size()) {
                System.out.println("Inserta un valor válido");
            } else {
                int mejoraOpcion;
                do {
                    System.out.println("===== Mejorar piscifactoría =====");
                    System.out.println("1. Comprar tanque");
                    System.out.println("2. Aumentar almacén");
                    System.out.println("3. Cancelar");
                    mejoraOpcion = inputHelper.getIntInput("Elige una opción: ", 1, 3);

                    switch (mejoraOpcion) {
                        case 1: piscifactorias.get(pisc - 1).comprarTanque(); break;
                        case 2: piscifactorias.get(pisc - 1).upgradeFood(); break;
                        case 3: break;
                    }
                } while (mejoraOpcion != 3);
            }
        } while (pisc < 1 || pisc > piscifactorias.size());
    }

    /**
     * Devuelve el tipo de la nueva piscifactoria
     * @return tipo
     */
    private boolean tipoPiscifactoria() {
        System.out.println("Selecciona el tipo de piscifactoría:");
        System.out.println("1. Río");
        System.out.println("2. Mar");
        int opcion = inputHelper.getIntInput("Elige una opción: ", 1, 2);
        return opcion == 1;
    }

    /**
     * Devuelve el numero de piscifactorias de mar 
     * @return
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
     * Devuelve el numero de piscifactorias de rio
     * @return
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
     * Adquiere una nueva piscifactoría
     * @param tipo de piscifactoría
     */
    private void nuevaPiscifactoria(boolean tipo) {
        if (tipo) {
            if (Monedas.getInstance().comprobarCompra(this.rio() * 500)) {
                Monedas.getInstance().compra(this.rio() * 500);
                String nombrePisci = nombrePiscifactoria();
                piscifactorias.add(new Piscifactoria(tipo, nombrePisci));
            } else {
                System.out.println("No tienes las suficientes monedas para realizar la compra.");
            }
        } else {
            if (this.mar() == 0) {
                if (Monedas.getInstance().comprobarCompra(1 * 500)) {
                    Monedas.getInstance().compra(500);
                    String nombrePisci = nombrePiscifactoria();
                    piscifactorias.add(new Piscifactoria(tipo, nombrePisci));
                } else {
                    System.out.println("No tienes las suficientes monedas para realizar la compra.");
                }
            } else {
                if (Monedas.getInstance().comprobarCompra(this.mar() * 500)) {
                    Monedas.getInstance().compra(this.mar() * 500);
                    String nombrePisci = nombrePiscifactoria();
                    piscifactorias.add(new Piscifactoria(tipo, nombrePisci));
                } else {
                    System.out.println("No tienes las suficientes monedas para realizar la compra.");
                }
            }
        }
    }

    /**
     * Compra un almacen 
     */
    private void comprarAlmacen() {
        if (Monedas.getInstance().comprobarCompra(2000)) {
            Monedas.getInstance().compra(2000);
            AlmacenCentral.getInstance();
            this.almacenCentral = true;
        } else {
            System.out.println("No tienes las suficientes monedas para realizar la compra.");
        }
    }
}
