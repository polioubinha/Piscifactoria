package simulador;

import peces.especies.dobles.*;
import peces.especies.mar.*;
import peces.especies.rio.*;
import almacenCentral.*;
import registro.Registro;
import helpers.MenuHelper;
import monedero.Monedas;
import piscifactoria.Piscifactoria;
import stats.Stats;
import tanque.Tanque;
import java.util.ArrayList;

public class Simulador {
    private MenuHelper menuHelper;
    private ArrayList<Piscifactoria> piscifactorias;
    private int dias;
    private boolean almacenCentral;
    private Registro registro;

    public Simulador() {
        menuHelper = new MenuHelper();
        piscifactorias = new ArrayList<>();
        dias = 0;
        almacenCentral = false;
        registro = Registro.getInstance("src/transcripciones", "src/logs", "Partida1");
        try{    
        init();
        } catch (IllegalArgumentException e) {
            System.out.println("Error durante la inicialización: " + e.getMessage());
        }
    }

    private void init() {
        String nombreEmpresa = menuHelper.pedirTexto("Introduce el nombre de la empresa: ");
        String nombrePiscifactoria = menuHelper.pedirTexto("Introduce el nombre de la piscifactoría inicial: ");

        registro.registrarTranscripcion("Inicialización completada: Empresa - " + nombreEmpresa + ", Piscifactoría - " + nombrePiscifactoria);
        registro.registrarTranscripcion("Peces iniciales disponibles:\nRío: Dorada, Trucha Arcoiris\nMar: Tilapia del Nilo, Salmón Chinook");

        // registro.registrarLog("Inicialización completada");
        piscifactorias.add(new Piscifactoria(true, nombrePiscifactoria));
        try {
            Stats.getInstancia(new String[]{"Dorada", "Trucha Arcoiris", "Arenque del Atlántico", "Besugo", "Caballa", "Sargo", "Robalo", "Carpa", "Carpa Plateada", "Pejerrey", "Salmón Chinook", "Tilapia del Nilo"});
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error al inicializar las estadísticas: " + e.getMessage());
        }
       Monedas.getInstance();
    }

    public void mainLoop() {
        String[] opciones = {
            "Mostrar estado general",
            "Estado Tanques",
            "Mostrar estadísticas",
            "Mostrar ictio",
            "Avanzar un día",
            "Agregar comida",
            "Agregar pez",
            "Vender",
            "Limpiar tanque",
            "Vaciar tanque",
            "Mejorar instalaciones",
            "Avanzar varios días",
            "Añadir truco monedas",
            "Salir",
        };

        boolean salir = false;
        while (!salir) {
            System.out.println("\n===============================");
            System.out.println("            Menú             ");
            System.out.println("===============================\n");

            int opcion = menuHelper.mostrarMenu(opciones,false);
            registro.registrarTranscripcion("Opción seleccionada en el menú: " + opcion);

            switch (opcion) {
                case 99:
                    addHiddenCoins();
                    break;
                case 1:
                    showGeneralStatus();
                    break;
                case 2:
                    menuPisc();
                    break;
                case 3:
                    Stats.getInstancia().mostrar();
                    registro.registrarTranscripcion("Mostrar stats");
                   // registro.registrarLog("Mostrar stats");

                    break;
                case 4:
                    showIctio();
                    break;
                case 5:
                    nextDay(1);
                    break;
                case 6:
                    addFood();
                    break;
                case 7:
                    addFish();
                    break;
                case 8:
                    sell();
                    break;
                case 9:
                    cleanTank();
                    break;
                case 10:
                    emptyTank();
                    break;
                case 11:
                    upgrade();
                    break;
                case 13:
                    int dias = menuHelper.pedirNumero("Cuántos días quieres avanzar? ", 1, 100);
                    nextDay(dias);
                    registro.registrarTranscripcion("Se han avanzado: " + dias + "dias");
                    
                    //registro.registrarLog("Se han avanzado: " + dias + "dias");

                    
                    break;
                case 14:
                    salir = true;
                    registro.registrarTranscripcion("Salir del simulador");
                   // registro.registrarLog("Salir del simulador");

                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }

        menuHelper.cerrarScanner();
        registro.cerrarRegistro();
    }

    public void addHiddenCoins() {
        Monedas.getInstance().añadirMonedas(1000);
        System.out.println("1000 monedas añadidas exitosamente.");
        System.out.println("Total de monedas actuales: " + Monedas.getInstance().getCantidad());
        registro.registrarTranscripcion("Añadir monedas ocultas: +1000");
       // registro.registrarLog("Añadir monedas ocultas");
    }

    private void showGeneralStatus() {
        for (Piscifactoria piscifactoria : piscifactorias) {
            piscifactoria.showStatus();
        }
        System.out.println("Día: " + dias);
        System.out.println(Monedas.getInstance().getCantidad() + " cantidad de monedas.");
        registro.registrarTranscripcion("Mostrar estado general");
        registro.registrarTranscripcion(
    "Estado general:\n" +
    "Piscifactorías activas: " + piscifactorias.size() + "\n" +
    "Monedas disponibles: " + Monedas.getInstance().getCantidad()
);
    }

    private void menuPisc() {
        for (Piscifactoria piscifactoria : piscifactorias) {
            piscifactoria.listTanks();
            for (Tanque tanque : piscifactoria.getTanques()) {
                tanque.showStatus();
            }
        }
        registro.registrarAccion("Mostrar estado de tanques");
    }

    private void showIctio() {
        String[] peces = {
            "Dorada", "Trucha Arcoiris", "Arenque del Atlántico", "Besugo", "Caballa", "Sargo", "Robalo", "Carpa", "Carpa Plateada", "Pejerrey", "Salmón Chinook", "Tilapia del Nilo"
        };
        int opcion;
        do {
            opcion = menuHelper.mostrarMenu(peces,true);
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
                registro.registrarAccion("Mostrar información de: " + peces[opcion - 1]);
            }
        } while (opcion != 0);
    }

    private void nextDay(int dias) {
        for (int i = 0; i < dias; i++) {
            int pecesRio = 0;
            int pecesMar = 0;
    
            for (Piscifactoria piscifactoria : piscifactorias) {
                piscifactoria.nextDay(almacenCentral);
                for (Tanque tanque : piscifactoria.getTanques()) {
                    tanque.nextFood(piscifactoria, almacenCentral);
    
                    // Contar peces según tipo de tanque
                    if (tanque.esDeRio()) { // Supongo que hay un método `esDeRio()` para determinar el tipo de tanque
                        pecesRio += tanque.getPeces().size();
                    } else {
                        pecesMar += tanque.getPeces().size();
                    }
                }
            }
            this.dias++;
    
            // Transcripción para el final del día
            registro.registrarTranscripcion(
                "Día " + this.dias + " finalizado.\n" +
                "Peces actuales:\n" +
                "Río: " + pecesRio + "\n" +
                "Mar: " + pecesMar + "\n" +
                "Monedas totales: " + Monedas.getInstance().getCantidad() + "\n" +
                "-------------------------"
            );
        }
    
        registro.registrarAccion("Avanzados " + dias + " días.");
    }

    private void addFood() {
        if (!almacenCentral) {
            int opcion = menuHelper.mostrarMenu(new String[]{"Agregar 5", "Agregar 10", "Agregar 25", "Llenar"},true);
            switch (opcion) {
                case 1: 
                    piscifactorias.get(0).addComida(5); 
                    registro.registrarAccion("Agregar 5 unidades de comida");
                    registro.registrarTranscripcion("5 unidades de comida añadidas a la piscifactoría " + piscifactorias.get(0).getNombre() + ".");
                    break;
                case 2: 
                    piscifactorias.get(0).addComida(10); 
                    registro.registrarAccion("Agregar 10 unidades de comida");
                    registro.registrarTranscripcion("10 unidades de comida añadidas a la piscifactoría " + piscifactorias.get(0).getNombre() + ".");
                    break;
                case 3: 
                    piscifactorias.get(0).addComida(25); 
                    registro.registrarAccion("Agregar 25 unidades de comida");
                    registro.registrarTranscripcion("25 unidades de comida añadidas a la piscifactoría " + piscifactorias.get(0).getNombre() + ".");
                    break;
                case 4: 
                    int cantidad = piscifactorias.get(0).getAlmacenMax() - piscifactorias.get(0).getAlmacen();
                    piscifactorias.get(0).addComida(cantidad); 
                    registro.registrarAccion("Llenar almacén de comida");
                    registro.registrarTranscripcion("Almacén de la piscifactoría " + piscifactorias.get(0).getNombre() + " lleno con " + cantidad + " unidades de comida.");
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Selecciona una opción válida");
            }
        } else {
        int tipoComida = menuHelper.mostrarMenu(new String[]{"Comida Animal", "Comida Vegetal"}, true);
        if (tipoComida < 1 || tipoComida > 2) {
            System.out.println("Selecciona un tipo de comida válido.");
            return;
        }

        String tipo = tipoComida == 1 ? "animal" : "vegetal";
        AlmacenCentral almacen = AlmacenCentral.getInstance(tipo);

        int opcion = menuHelper.mostrarMenu(new String[]{"Agregar 5", "Agregar 10", "Agregar 25", "Llenar"}, true);
        switch (opcion) {
            case 1:
                almacen.comprarComida(5);
                registro.registrarAccion("Comprar 5 unidades de comida " + tipo);
                registro.registrarTranscripcion("5 unidades de comida " + tipo + " añadidas al almacén central.");
                break;
            case 2:
                almacen.comprarComida(10);
                registro.registrarAccion("Comprar 10 unidades de comida " + tipo);
                registro.registrarTranscripcion("10 unidades de comida " + tipo + " añadidas al almacén central.");
                break;
            case 3:
                almacen.comprarComida(25);
                registro.registrarAccion("Comprar 25 unidades de comida " + tipo);
                registro.registrarTranscripcion("25 unidades de comida " + tipo + " añadidas al almacén central.");
                break;
            case 4:
                int cantidad = almacen.getCapacidadMax() - almacen.getCapacidad();
                almacen.comprarComida(cantidad);
                registro.registrarAccion("Llenar almacén central de comida " + tipo);
                registro.registrarTranscripcion("Almacén central de comida " + tipo + " lleno con " + cantidad + " unidades.");
                break;
            case 5:
                break;
            default:
                System.out.println("Selecciona una opción válida.");
        }
        }
    }
    

    private void addFish() {
        int piscifactoria = menuHelper.pedirNumero("Seleccione la piscifactoría a la que agregar el pez: ", 1, piscifactorias.size());
        piscifactorias.get(piscifactoria - 1).newFish();
        registro.registrarAccion("Agregar pez a la piscifactoría: " + piscifactoria);
    }

    private void sell() {
        for (Piscifactoria piscifactoria : piscifactorias) {
            // Procesar la venta de peces adultos en la piscifactoría
            piscifactoria.venderAdultos();
            registro.registrarTranscripcion(
                "Vendidos peces adultos de la piscifactoría " + piscifactoria.getNombre() + "."
            );
    
            for (Tanque tanque : piscifactoria.getTanques()) {
                // Procesar la venta de peces óptimos por tanque
                int pecesAntes = tanque.getPeces().size();
                int gananciasAntes = tanque.getGanancias();
    
                tanque.venderOptimos();
    
                int pecesVendidos = pecesAntes - tanque.getPeces().size();
                int gananciasObtenidas = tanque.getGanancias() - gananciasAntes;
    
                registro.registrarTranscripcion(
                    "Vendidos " + pecesVendidos + " peces óptimos del tanque (Capacidad: " +
                    tanque.getCapacidad() + ") de la piscifactoría " + piscifactoria.getNombre() +
                    " por " + gananciasObtenidas + " monedas."
                );
            }
        }
    
        registro.registrarAccion("Venta de peces completada.");
       // registro.registrarLog("Se completó la venta de peces en todas las piscifactorías.");
    }
    
    

    private void cleanTank() {
        for (Piscifactoria piscifactoria : piscifactorias) {
            piscifactoria.limpiarTanques();
            for (Tanque tanque : piscifactorias.get(0).getTanques()) {
                tanque.limpiarTanque();
            }
        }
        registro.registrarAccion("Limpiar tanques realizada.");
    }

    private void emptyTank() {
        for (Piscifactoria piscifactoria : piscifactorias) {
            piscifactoria.vaciarTanques();
            for (Tanque tanque : piscifactorias.get(0).getTanques()) {
                tanque.vaciarTanque();
                registro.registrarTranscripcion(
                    "Tanques vaciados en la piscifactoría " + piscifactoria.getNombre() + "."
                );
            }
        }
    }

    private void upgrade() {
        int pisc = menuHelper.pedirNumero("Seleccione la piscifactoría para mejorar: ", 1, piscifactorias.size());
        int mejoraOpcion = menuHelper.mostrarMenu(new String[]{"Comprar tanque", "Aumentar almacén"},true);
        switch (mejoraOpcion) {
            case 1: 
                piscifactorias.get(pisc - 1).comprarTanque();
                registro.registrarTranscripcion("Tanque añadido a la piscifactoría " + piscifactorias.get(pisc - 1).getNombre() + ".");
                break;
            case 2: 
                piscifactorias.get(pisc - 1).upgradeFood();
                registro.registrarTranscripcion("Capacidad del almacén aumentada en la piscifactoría " + piscifactorias.get(pisc - 1).getNombre() + ".");
                break;
            case 3: 
                registro.registrarTranscripcion("Mejora cancelada.");
                break;
        }
    }

    public static void main(String[] args) {
        Simulador simulador = new Simulador();
        simulador.mainLoop();
    }
}
