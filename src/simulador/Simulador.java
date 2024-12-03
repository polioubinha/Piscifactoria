package simulador;

import peces.especies.dobles.*;
import peces.especies.mar.*;
import peces.especies.rio.*;
import almacenCentral.*;
import registro.*;
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
        registro = new Registro("transcripciones", "logs", "partida1");
        try {
            init();
        } catch (IllegalArgumentException e) {
            System.out.println("Error durante la inicialización: " + e.getMessage());
        }
    }

    private void init() {
        String nombreEmpresa = menuHelper.pedirTexto("Introduce el nombre de la empresa: ");
        registro.registrarAccion("Nombre de la empresa establecido: " + nombreEmpresa);
        String nombrePiscifactoria = menuHelper.pedirTexto("Introduce el nombre de la piscifactoría inicial: ");
        registro.registrarAccion("Nombre de la piscifactoría inicial establecido: " + nombrePiscifactoria);
        piscifactorias.add(new Piscifactoria(true, nombrePiscifactoria));
        try {
            Stats.getInstancia(new String[]{"Dorada", "Trucha Arcoiris", "Arenque del Atlántico", "Besugo", "Caballa", "Sargo", "Robalo", "Carpa", "Carpa Plateada", "Pejerrey", "Salmón Chinook", "Tilapia del Nilo"});
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error al inicializar las estadísticas: " + e.getMessage());
        }
       Monedas.getInstance();
        registro.registrarAccion("Inicialización completada: Empresa - " + nombreEmpresa + ", Piscifactoría - " + nombrePiscifactoria);
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
            registro.registrarAccion("Opción seleccionada en el menú: " + opcion);

            switch (opcion) {
                case 13:
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
                    registro.registrarAccion("Mostrar estadísticas");
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
                case 12:
                    int dias = menuHelper.pedirNumero("Cuántos días quieres avanzar? ", 1, 100);
                    registro.registrarAccion("Avanzar varios días: " + dias);
                    nextDay(dias);
                    break;
                case 14:
                    salir = true;
                    registro.registrarAccion("Salir del simulador");
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
        registro.registrarAccion("Añadir monedas ocultas: +1000");
    }

    private void showGeneralStatus() {
        for (Piscifactoria piscifactoria : piscifactorias) {
            piscifactoria.showStatus();
        }
        System.out.println("Día: " + dias);
        System.out.println(Monedas.getInstance().getCantidad() + " cantidad de monedas.");
        registro.registrarAccion("Mostrar estado general");
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
            for (Piscifactoria piscifactoria : piscifactorias) {
                piscifactoria.nextDay(almacenCentral);
                for (Tanque tanque : piscifactorias.get(0).getTanques()) {
                    tanque.nextFood(piscifactorias.get(0), almacenCentral);
                }
            }
            this.dias++;
            registro.registrarAccion("Avanzar un día: Día " + this.dias);
        }
    }

    private void addFood() {
        if (!almacenCentral) {
            int opcion = menuHelper.mostrarMenu(new String[]{"Agregar 5", "Agregar 10", "Agregar 25", "Llenar"},true);
            switch (opcion) {
                case 1: piscifactorias.get(0).addComida(5); registro.registrarAccion("Agregar 5 unidades de comida"); break;
                case 2: piscifactorias.get(0).addComida(10); registro.registrarAccion("Agregar 10 unidades de comida"); break;
                case 3: piscifactorias.get(0).addComida(25); registro.registrarAccion("Agregar 25 unidades de comida"); break;
                case 4: piscifactorias.get(0).addComida(piscifactorias.get(0).getAlmacenMax() - piscifactorias.get(0).getAlmacen()); registro.registrarAccion("Llenar almacén de comida"); break;
                case 0: break;
                default: System.out.println("Selecciona una opción válida");
            }
        } else {
            int opcion = menuHelper.mostrarMenu(new String[]{"Agregar 5", "Agregar 10", "Agregar 25", "Llenar"},true);
            switch (opcion) {
                case 1: AlmacenCentral.getInstance().comprarComida(5); registro.registrarAccion("Comprar 5 unidades de comida"); break;
                case 2: AlmacenCentral.getInstance().comprarComida(10); registro.registrarAccion("Comprar 10 unidades de comida"); break;
                case 3: AlmacenCentral.getInstance().comprarComida(25); registro.registrarAccion("Comprar 25 unidades de comida"); break;
                case 4: AlmacenCentral.getInstance().comprarComida(AlmacenCentral.getInstance().getCapacidadMax() - AlmacenCentral.getInstance().getCapacidad()); registro.registrarAccion("Llenar almacén central de comida"); break;
                case 0: break;
                default: System.out.println("Selecciona una opción válida.");
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
            piscifactoria.venderAdultos();
            for (Tanque tanque : piscifactoria.getTanques()) {
                tanque.venderOptimos();
            }
        }
        registro.registrarAccion("Venta de peces adultos realizada.");
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
            }
        }
        registro.registrarAccion("Vaciado de tanques realizado.");
    }

    private void upgrade() {
        int pisc = menuHelper.pedirNumero("Seleccione la piscifactoría para mejorar: ", 1, piscifactorias.size());
        int mejoraOpcion = menuHelper.mostrarMenu(new String[]{"Comprar tanque", "Aumentar almacén"},true);
        switch (mejoraOpcion) {
            case 1: piscifactorias.get(pisc - 1).comprarTanque(); registro.registrarAccion("Compra de tanque realizada en piscifactoría: " + pisc); break;
            case 2: piscifactorias.get(pisc - 1).upgradeFood(); registro.registrarAccion("Almacén mejorado en piscifactoría: " + pisc); break;
            case 0: break;
        }
    }

    public static void main(String[] args) {
        Simulador simulador = new Simulador();
        simulador.mainLoop();
    }
}
