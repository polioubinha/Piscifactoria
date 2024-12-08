package simulador;

import peces.Pez;
import peces.especies.dobles.*;
import peces.especies.mar.*;
import peces.especies.rio.*;
import almacenCentral.*;
import registro.Registro;
import rewards.Crear;
import rewards.Eliminar;
import helpers.MenuHelper;
import monedero.Monedas;
import piscifactoria.Piscifactoria;
import propiedades.AlmacenPropiedades;
import stats.Stats;
import tanque.Tanque;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Simulador {
    private MenuHelper menuHelper;
    private ArrayList<Piscifactoria> piscifactorias;
    private static String nombreEmpresa = "";
    private int dias;
    private boolean almacenCentral;
    private Registro registro;

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        Simulador.nombreEmpresa = nombreEmpresa;
    }

    public Simulador() {
        menuHelper = new MenuHelper();
        piscifactorias = new ArrayList<>();
        dias = 0;
        almacenCentral = false;
        registro = Registro.getInstance("src/transcripciones", "src/logs", "Partida1");
        try {
            init();
        } catch (IllegalArgumentException e) {
            System.out.println("Error durante la inicialización: " + e.getMessage());
        }
    }

    /**
     * Inicializa el simulador configurando el nombre de la empresa, cargando una
     * partida existente
     * o creando una nueva partida.
     */
    private void init() {
        try {
            File saves = new File("saves");
            if (saves.exists() && saves.isDirectory() && saves.listFiles().length > 0) {
                File[] files = saves.listFiles();
                System.out.println("---------- Partidas disponibles ---------");
                System.out.println("0. Nueva Partida");

                for (int ind = 0; ind < files.length; ind++) {
                    System.out.println((ind + 1) + ". " + files[ind].getName());
                }

                System.out.println("¿Qué partida quieres cargar?");
                int opcion = MenuHelper.pedirNumero("Selecciona una opción (0 para nueva partida)", 0, files.length);

                if (opcion > 0) {
                    // Cargar la partida seleccionada
                    String partida = files[opcion - 1].getName();
                    this.load(partida);
                } else {
                    this.newGame();
                }
            } else {
                this.newGame();
            }

        } catch (SecurityException e) {
            registro.registrarTranscripcion("Error en el acceso a los archivos de guardado: " + e.getMessage());
        }
    }

    /**
     * Crea una nueva partida solicitando al usuario el nombre de la empresa y la
     * primera piscifactoría.
     */
    public void newGame() {
        nombreEmpresa = MenuHelper.pedirTexto("Introduce el nombre de la empresa: ");
        String nombrePiscifactoria = MenuHelper.pedirTexto("Introduce el nombre de la piscifactoría inicial: ");

        registro.registrarTranscripcion(
                "Inicialización completada: Empresa - " + nombreEmpresa + ", Piscifactoría - " + nombrePiscifactoria);
        registro.registrarTranscripcion(
                "Peces iniciales disponibles:\nRío: Dorada, Trucha Arcoiris\nMar: Tilapia del Nilo, Salmón Chinook");

        piscifactorias.add(new Piscifactoria(true, nombrePiscifactoria));

        try {
            Stats.getInstancia(new String[] {
                    "Dorada", "Trucha Arcoiris", "Arenque del Atlántico",
                    "Besugo", "Caballa", "Sargo", "Robalo",
                    "Carpa", "Carpa Plateada", "Pejerrey",
                    "Salmón Chinook", "Tilapia del Nilo"
            });
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
                "Cobrar recompensas",
                "Salir"
        };

        boolean salir = false;
        while (!salir) {
            System.out.println("\n===============================");
            System.out.println("            Menú             ");
            System.out.println("===============================\n");

            int opcion = menuHelper.mostrarMenu(opciones,false);
            registro.registrarTranscripcion("Opción seleccionada en el menú: " + opcion);

            switch (opcion) {
                case 1:
                    showGeneralStatus();
                    break;
                case 2:
                    menuPisc();
                    break;
                case 3:
                    Stats.getInstancia().mostrar();
                    registro.registrarTranscripcion("Mostrar stats");
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
                    int dias = MenuHelper.pedirNumero("Cuántos días quieres avanzar? ", 1, 100);
                    nextDay(dias);
                    registro.registrarTranscripcion("Se han avanzado: " + dias + " días");
                    break;
                case 13:
                    cobrarRecompensas();
                    break;
                case 14:
                    registro.registrarTranscripcion("Salir del simulador");
                    save();
                    salir = true;
                    break;
                case 97:
                    Crear.addAlmacen("A");
                    Crear.addAlmacen("B");
                    Crear.addAlmacen("C");
                    Crear.addAlmacen("D");
                    Crear.darComida(1);
                    Crear.darComida(2);
                    Crear.darComida(3);
                    Crear.darComida(4);
                    Crear.darComida(5);
                    Crear.darMonedas(1);
                    Crear.darMonedas(2);
                    Crear.darMonedas(3);
                    Crear.darMonedas(4);
                    Crear.darMonedas(5);
                    Crear.addPisci("A", "m");
                    Crear.addPisci("B", "m");
                    Crear.addPisci("A", "r");
                    Crear.addPisci("B", "r");
                    Crear.addTanque("m");
                    Crear.addTanque("r");
                case 98:
                    break;
                case 99:
                    addHiddenCoins();
                    registro.registrarTranscripcion("Se ha activado la opción oculta: Añadir monedas");
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
                        "Monedas disponibles: " + Monedas.getInstance().getCantidad());
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
                "Dorada", "Trucha Arcoiris", "Arenque del Atlántico", "Besugo", "Caballa", "Sargo", "Robalo", "Carpa",
                "Carpa Plateada", "Pejerrey", "Salmón Chinook", "Tilapia del Nilo"
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
                            "-------------------------");
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
                    registro.registrarTranscripcion("5 unidades de comida añadidas a la piscifactoría "
                            + piscifactorias.get(0).getNombre() + ".");
                    break;
                case 2:
                    piscifactorias.get(0).addComida(10);
                    registro.registrarAccion("Agregar 10 unidades de comida");
                    registro.registrarTranscripcion("10 unidades de comida añadidas a la piscifactoría "
                            + piscifactorias.get(0).getNombre() + ".");
                    break;
                case 3:
                    piscifactorias.get(0).addComida(25);
                    registro.registrarAccion("Agregar 25 unidades de comida");
                    registro.registrarTranscripcion("25 unidades de comida añadidas a la piscifactoría "
                            + piscifactorias.get(0).getNombre() + ".");
                    break;
                case 4:
                    int cantidad = piscifactorias.get(0).getAlmacenMax() - piscifactorias.get(0).getAlmacen();
                    piscifactorias.get(0).addComida(cantidad);
                    registro.registrarAccion("Llenar almacén de comida");
                    registro.registrarTranscripcion("Almacén de la piscifactoría " + piscifactorias.get(0).getNombre()
                            + " lleno con " + cantidad + " unidades de comida.");
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
        int piscifactoria = MenuHelper.pedirNumero("Seleccione la piscifactoría a la que agregar el pez: ", 1,
                piscifactorias.size());
        piscifactorias.get(piscifactoria - 1).newFish();
        registro.registrarAccion("Agregar pez a la piscifactoría: " + piscifactoria);
    }

    private void sell() {
        for (Piscifactoria piscifactoria : piscifactorias) {
            piscifactoria.venderAdultos();
            registro.registrarTranscripcion(
                    "Vendidos peces adultos de la piscifactoría " + piscifactoria.getNombre() + ".");

            for (Tanque tanque : piscifactoria.getTanques()) {
                int pecesAntes = tanque.getPeces().size();
                int gananciasAntes = tanque.getGanancias();

                tanque.venderOptimos();

                int pecesVendidos = pecesAntes - tanque.getPeces().size();
                int gananciasObtenidas = tanque.getGanancias() - gananciasAntes;

                registro.registrarTranscripcion(
                        "Vendidos " + pecesVendidos + " peces óptimos del tanque (Capacidad: " +
                                tanque.getCapacidad() + ") de la piscifactoría " + piscifactoria.getNombre() +
                                " por " + gananciasObtenidas + " monedas.");
            }
        }

        registro.registrarAccion("Venta de peces completada.");
        // registro.registrarLog("Se completó la venta de peces en todas las
        // piscifactorías.");
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
                        "Tanques vaciados en la piscifactoría " + piscifactoria.getNombre() + ".");
            }
        }
    }

    private void upgrade() {
        int pisc = menuHelper.pedirNumero("Seleccione la piscifactoría para mejorar: ", 1, piscifactorias.size());
        int mejoraOpcion = menuHelper.mostrarMenu(new String[]{"Comprar tanque", "Aumentar almacén"},true);
        switch (mejoraOpcion) {
            case 1:
                piscifactorias.get(pisc - 1).comprarTanque();
                registro.registrarTranscripcion(
                        "Tanque añadido a la piscifactoría " + piscifactorias.get(pisc - 1).getNombre() + ".");
                break;
            case 2:
                piscifactorias.get(pisc - 1).upgradeFood();
                registro.registrarTranscripcion("Capacidad del almacén aumentada en la piscifactoría "
                        + piscifactorias.get(pisc - 1).getNombre() + ".");
                break;
            case 3:
                registro.registrarTranscripcion("Mejora cancelada.");
                break;
        }
    }

    /**
     * Muestra las opciones de selección de piscifactorías disponibles.
     */
    public void selecPisc() {
        for (int i = 0; i < this.piscifactorias.size(); i++) {
            System.out.println((i + 1) + ". " + this.piscifactorias.get(i).getNombre());
        }
    }

    /**
     * Verifica la existencia de archivos de recompensas y muestra los resultados.
     * Se crean valores booleanos en un array para cada tipo de recompensa,
     * indicando si el archivo correspondiente existe o no.
     * Luego, se muestra el estado de las recompensas en la consola.
     */
    public void cobrarRecompensas() {

        boolean[] recompensas = new boolean[20];

        recompensas[0] = new File("recompensas/almacen_a.xml").exists();
        recompensas[1] = new File("recompensas/almacen_b.xml").exists();
        recompensas[2] = new File("recompensas/almacen_c.xml").exists();
        recompensas[3] = new File("recompensas/almacen_d.xml").exists();
        recompensas[4] = new File("recompensas/comida_1.xml").exists();
        recompensas[5] = new File("recompensas/comida_2.xml").exists();
        recompensas[6] = new File("recompensas/comida_3.xml").exists();
        recompensas[7] = new File("recompensas/comida_4.xml").exists();
        recompensas[8] = new File("recompensas/comida_5.xml").exists();
        recompensas[9] = new File("recompensas/monedas_1.xml").exists();
        recompensas[10] = new File("recompensas/monedas_2.xml").exists();
        recompensas[11] = new File("recompensas/monedas_3.xml").exists();
        recompensas[12] = new File("recompensas/monedas_4.xml").exists();
        recompensas[13] = new File("recompensas/monedas_5.xml").exists();
        recompensas[14] = new File("recompensas/pisci_m_a.xml").exists();
        recompensas[15] = new File("recompensas/pisci_m_b.xml").exists();
        recompensas[16] = new File("recompensas/pisci_r_a.xml").exists();
        recompensas[17] = new File("recompensas/pisci_r_b.xml").exists();
        recompensas[18] = new File("recompensas/tanque_m.xml").exists();
        recompensas[19] = new File("recompensas/tanque_r.xml").exists();

        mostrar(recompensas);

    }

        /**
     * Muestra el estado de las recompensas en la consola y proporciona opciones
     * para reclamar cada recompensa.
     * 
     * @param recompensas Un array de booleanos que indica la existencia de cada
     *                    tipo de recompensa.
     */
    public void mostrar(boolean[] recompensas) {

        String almacenA = "x";
        String almacenB = "x";
        String almacenC = "x";
        String almacenD = "x";

        String PiscMA = "x";
        String PiscMB = "x";

        String PiscRA = "x";
        String PiscRB = "x";

        almacenA = (recompensas[0]) ? "A" : almacenA;
        almacenB = (recompensas[1]) ? "B" : almacenB;
        almacenC = (recompensas[2]) ? "C" : almacenC;
        almacenD = (recompensas[3]) ? "D" : almacenD;

        PiscMA = (recompensas[14]) ? "A" : PiscMA;
        PiscMB = (recompensas[15]) ? "B" : PiscMB;

        PiscRA = (recompensas[16]) ? "A" : PiscRA;
        PiscRB = (recompensas[17]) ? "B" : PiscRB;

        if (almacenA.contains("x") || almacenB.contains("x") || almacenC.contains("x") || almacenD.contains("x")) {
            System.out.println("Faltan partes de almacén central: " + almacenA + almacenB + almacenC + almacenD);
        } else {
            System.out.println("1. Reclamar almacén " + almacenA + almacenB + almacenC + almacenD + ":");
        }
        System.out.print((recompensas[4]) ? "2. Reclamar comida I\n" : "");
        System.out.print((recompensas[5]) ? "3. Reclamar comida II\n" : "");
        System.out.print((recompensas[6]) ? "4. Reclamar comida III\n" : "");
        System.out.print((recompensas[7]) ? "5. Reclamar comida IV\n" : "");
        System.out.print((recompensas[8]) ? "6. Reclamar comida V\n" : "");
        System.out.print((recompensas[9]) ? "7. Reclamar monedas I\n" : "");
        System.out.print((recompensas[10]) ? "8. Reclamar monedas II\n" : "");
        System.out.print((recompensas[11]) ? "9. Reclamar monedas III\n" : "");
        System.out.print((recompensas[12]) ? "10. Reclamar monedas IV\n" : "");
        System.out.print((recompensas[13]) ? "11. Reclamar monedas V" : "");
        if (PiscMA.contains("x") || PiscMB.contains("x")) {
            System.out.println("Faltan partes de la piscifactoría marina: " + PiscMA + PiscMB);
        } else {
            System.out.println("12. Reclamar piscifactoría de mar " + PiscMA + PiscMB + ":");
        }
        if (PiscRA.contains("x") || PiscRB.contains("x")) {
            System.out.print("Faltan partes de la piscifactoria fluvial: " + PiscRA + PiscRB);
        } else {
            System.out.println("13. Reclamar piscifactoría de río " + PiscRA + PiscRB + ":");
        }
        System.out.print((recompensas[18]) ? "14. Reclamar tanque de mar" : "");
        System.out.print((recompensas[19]) ? "15. Reclamar tanque de río" : "");

        seleccionarOpcion(recompensas);

    }
    

/**
     * Permite al usuario seleccionar una opción para reclamar una recompensa
     * específica.
     * 
     * @param recompensas Un array de booleanos que indica la existencia de cada
     *                    tipo de recompensa.
     */
    public void seleccionarOpcion(boolean[] recompensas) {

        boolean control = true;
        int opcion;

        do {
            System.out.print("Selecciona una opción: ");
            opcion = MenuHelper.pedirNumero("Selecciona una opción: ",0, 15);

            switch (opcion) {
                case 0:
                    break;
                case 1:
                    if (Eliminar.obtenerDatos("recompensas/almacen_a.xml") >= 1
                            && Eliminar.obtenerDatos("recompensas/almacen_b.xml") >= 1
                            && Eliminar.obtenerDatos("recompensas/almacen_c.xml") >= 1
                            && Eliminar.obtenerDatos("recompensas/almacen_d.xml") >= 1) {
                        System.out.println("Reclamando almacén...");
                        if (AlmacenCentral.getInstance(null) == null) {
                            Eliminar.dismnuirRecompensas("recompensas/almacen_a.xml");
                            Eliminar.dismnuirRecompensas("recompensas/almacen_b.xml");
                            Eliminar.dismnuirRecompensas("recompensas/almacen_c.xml");
                            Eliminar.dismnuirRecompensas("recompensas/almacen_d.xml");
                            Eliminar.obtenerDatos("recompensas/almacen_a.xml");
                            if (Eliminar.obtenerDatos("recompensas/almacen_a.xml") < 1) {
                                Eliminar.borrarXML("recompensas/almacen_a.xml");
                            }
                            if (Eliminar.obtenerDatos("recompensas/almacen_b.xml") < 1) {
                                Eliminar.borrarXML("recompensas/almacen_b.xml");
                            }
                            if (Eliminar.obtenerDatos("recompensas/almacen_c.xml") < 1) {
                                Eliminar.borrarXML("recompensas/almacen_c.xml");
                            }
                            if (Eliminar.obtenerDatos("recompensas/almacen_d.xml") < 1) {
                                Eliminar.borrarXML("recompensas/almacen_d.xml");
                            }
                        } else {
                            System.out.println("Ya tienes el almacén central");
                        }
                    }
                    control = false;
                    break;
                case 2:
                    if (Eliminar.obtenerDatos("recompensas/comida_1.xml") >= 1) {
                        System.out.println("Reclamando comida I...");
                        int restante1 = AlmacenCentral.getInstance().getCapacidadMax()
                                - AlmacenCentral.getInstance().getCapacidad();
                        if (restante1 < 50) {
                            AlmacenCentral.getInstance()
                                    .agregarComida((AlmacenCentral.getInstance().getCapacidad() + restante1));
                        } else {
                            AlmacenCentral.getInstance()
                                    .agregarComida((AlmacenCentral.getInstance().getCapacidad() + 50));
                        }
                        Eliminar.dismnuirRecompensas("recompensas/comida_1.xml");
                        if (Eliminar.obtenerDatos("recompensas/comida_1.xml") < 1) {
                            Eliminar.borrarXML("recompensas/comida_1.xml");
                        }
                    }
                    control = false;
                    break;
                case 3:
                    if (Eliminar.obtenerDatos("recompensas/comida_2.xml") >= 1) {
                        System.out.println("Reclamando comida II...");
                        int restante2 = AlmacenCentral.getInstance().getCapacidadMax()
                                - AlmacenCentral.getInstance().getCapacidad();
                        if (restante2 < 100) {
                            AlmacenCentral.getInstance()
                                    .agregarComida((AlmacenCentral.getInstance().getCapacidad() + restante2));
                        } else {
                            AlmacenCentral.getInstance()
                                    .agregarComida((AlmacenCentral.getInstance().getCapacidad() + 100));
                        }
                        Eliminar.dismnuirRecompensas("recompensas/comida_2.xml");
                        if (Eliminar.obtenerDatos("recompensas/comida_2.xml") < 1) {
                            Eliminar.borrarXML("recompensas/comida_2.xml");
                        }
                    }
                    control = false;
                    break;
                case 4:
                    if (Eliminar.obtenerDatos("recompensas/comida_3.xml") >= 1) {
                        System.out.println("Reclamando comida III...");
                        int restante2 = AlmacenCentral.getInstance().getCapacidadMax()
                                - AlmacenCentral.getInstance().getCapacidad();
                        if (restante2 < 100) {
                            AlmacenCentral.getInstance()
                                    .agregarComida((AlmacenCentral.getInstance().getCapacidad() + restante2));
                        } else {
                            AlmacenCentral.getInstance()
                                    .agregarComida((AlmacenCentral.getInstance().getCapacidad() + 250));
                        }
                        Eliminar.dismnuirRecompensas("recompensas/comida_3.xml");
                        if (Eliminar.obtenerDatos("recompensas/comida_3.xml") < 1) {
                            Eliminar.borrarXML("recompensas/comida_3.xml");
                        }
                    }
                    control = false;
                    break;
                case 5:
                    if (Eliminar.obtenerDatos("recompensas/comida_4.xml") >= 1) {
                        System.out.println("Reclamando comida IV...");
                        int restante2 = AlmacenCentral.getInstance().getCapacidadMax()
                                - AlmacenCentral.getInstance().getCapacidad();
                        if (restante2 < 100) {
                            AlmacenCentral.getInstance()
                                    .agregarComida((AlmacenCentral.getInstance().getCapacidad() + restante2));
                        } else {
                            AlmacenCentral.getInstance()
                                    .agregarComida((AlmacenCentral.getInstance().getCapacidad() + 500));
                        }
                        Eliminar.dismnuirRecompensas("recompensas/comida_4.xml");
                        if (Eliminar.obtenerDatos("recompensas/comida_4.xml") < 1) {
                            Eliminar.borrarXML("recompensas/comida_4.xml");
                        }
                    }
                    control = false;
                    break;
                case 6:
                    if (Eliminar.obtenerDatos("recompensas/comida_5.xml") >= 1) {
                        System.out.println("Reclamando comida V...");
                        int restante2 = AlmacenCentral.getInstance().getCapacidadMax()
                                - AlmacenCentral.getInstance().getCapacidad();
                        if (restante2 < 100) {
                            AlmacenCentral.getInstance()
                                    .agregarComida((AlmacenCentral.getInstance().getCapacidad() + restante2));
                        } else {
                            AlmacenCentral.getInstance()
                                    .agregarComida((AlmacenCentral.getInstance().getCapacidad() + 1000));
                        }
                        Eliminar.dismnuirRecompensas("recompensas/comida_5.xml");
                        if (Eliminar.obtenerDatos("recompensas/comida_5.xml") < 1) {
                            Eliminar.borrarXML("recompensas/comida_5.xml");
                        }
                    }
                    control = false;
                    break;
                case 7:
                    if (Eliminar.obtenerDatos("recompensas/monedas_1.xml") >= 1) {
                        System.out.println("Reclamando monedas I...");
                        Monedas.getInstance().setCantidad(Monedas.getInstance().getCantidad() + 100);
                    }
                    Eliminar.dismnuirRecompensas("recompensas/monedas_1.xml");
                    if (Eliminar.obtenerDatos("recompensas/monedas_1.xml") < 1) {
                        Eliminar.borrarXML("recompensas/monedas_1.xml");
                    }
                    control = false;
                    break;
                case 8:
                    if (Eliminar.obtenerDatos("recompensas/monedas_2.xml") >= 1) {
                        System.out.println("Reclamando monedas II...");
                        Monedas.getInstance().setCantidad(Monedas.getInstance().getCantidad() + 300);
                    }
                    Eliminar.dismnuirRecompensas("recompensas/monedas_2.xml");
                    if (Eliminar.obtenerDatos("recompensas/monedas_2.xml") < 1) {
                        Eliminar.borrarXML("recompensas/monedas_2.xml");
                    }
                    control = false;
                    break;
                case 9:
                    if (Eliminar.obtenerDatos("recompensas/monedas_3.xml") >= 1) {
                        System.out.println("Reclamando monedas III...");
                        Monedas.getInstance().setCantidad(Monedas.getInstance().getCantidad() + 500);
                    }
                    Eliminar.dismnuirRecompensas("recompensas/monedas_3.xml");
                    if (Eliminar.obtenerDatos("recompensas/monedas_3.xml") < 1) {
                        Eliminar.borrarXML("recompensas/monedas_3.xml");
                    }
                    control = false;
                    break;
                case 10:
                    if (Eliminar.obtenerDatos("recompensas/monedas_4.xml") >= 1) {
                        System.out.println("Reclamando monedas IV...");
                        Monedas.getInstance().setCantidad(Monedas.getInstance().getCantidad() + 750);
                    }
                    Eliminar.dismnuirRecompensas("recompensas/monedas_4.xml");
                    if (Eliminar.obtenerDatos("recompensas/monedas_4.xml") < 1) {
                        Eliminar.borrarXML("recompensas/monedas_4.xml");
                    }
                    control = false;
                    break;
                case 11:
                    if (Eliminar.obtenerDatos("recompensas/monedas_5.xml") >= 1) {
                        System.out.println("Reclamando monedas V...");
                        Monedas.getInstance().setCantidad(Monedas.getInstance().getCantidad() + 1000);
                    }
                    Eliminar.dismnuirRecompensas("recompensas/monedas_5.xml");
                    if (Eliminar.obtenerDatos("recompensas/monedas_5.xml") < 1) {
                        Eliminar.borrarXML("recompensas/monedas_5.xml");
                    }
                    control = false;
                    break;
                case 12:
                    if (Eliminar.obtenerDatos("recompensas/pisci_m_a.xml") >= 1
                            && Eliminar.obtenerDatos("recompensas/pisci_m_b.xml") >= 1) {
                        System.out.println("Reclamando piscifactoría de mar...");
                        System.out.print("Nombre de la Piscifactoria: ");
                        String nombrePiscMar = MenuHelper.pedirTexto("");
                        piscifactorias.add(new Piscifactoria(false, nombrePiscMar));
                    }
                    Eliminar.dismnuirRecompensas("recompensas/pisci_m_a.xml");
                    Eliminar.dismnuirRecompensas("recompensas/pisci_m_b.xml");
                    if (Eliminar.obtenerDatos("recompensas/pisci_m_a.xml") < 1) {
                        Eliminar.borrarXML("recompensas/pisci_m_a.xml");
                    }
                    if (Eliminar.obtenerDatos("recompensas/pisci_m_b.xml") < 1) {
                        Eliminar.borrarXML("recompensas/pisci_m_b.xml");
                    }
                    control = false;
                    break;
                case 13:
                    if (Eliminar.obtenerDatos("recompensas/pisci_r_a.xml") >= 1
                            && Eliminar.obtenerDatos("recompensas/pisci_r_b.xml") >= 1) {
                        System.out.println("Reclamando piscifactoría de río...");
                        System.out.print("Nombre de la Piscifactoria: ");
                        String nombrePiscMar = MenuHelper.pedirTexto("");
                        piscifactorias.add(new Piscifactoria(true, nombrePiscMar));
                    }
                    Eliminar.dismnuirRecompensas("recompensas/pisci_r_a.xml");
                    Eliminar.dismnuirRecompensas("recompensas/pisci_r_b.xml");
                    if (Eliminar.obtenerDatos("recompensas/pisci_r_a.xml") < 1) {
                        Eliminar.borrarXML("recompensas/pisci_r_a.xml");
                    }
                    if (Eliminar.obtenerDatos("recompensas/pisci_r_b.xml") < 1) {
                        Eliminar.borrarXML("recompensas/pisci_r_b.xml");
                    }
                    control = false;
                    break;
                case 14:
                    if (Eliminar.obtenerDatos("recompensas/tanque_m.xml") >= 1) {
                        System.out.println("Reclamando tanque de mar...");

                        this.selecPisc();
                        Piscifactoria pisc = this.piscifactorias
                                .get(MenuHelper.pedirNumero("",0, piscifactorias.size()));
                        if (pisc.getTanques().size() < 10) {
                            pisc.añadirTanque();
                        }
                    }
                    Eliminar.dismnuirRecompensas("recompensas/tanque_m.xml");
                    if (Eliminar.obtenerDatos("recompensas/tanque_m.xml") < 1) {
                        Eliminar.borrarXML("recompensas/tanque_m.xml");
                    }
                    control = false;
                    break;
                case 15:
                    if (Eliminar.obtenerDatos("recompensas/tanque_r.xml") >= 1) {
                        System.out.println("Reclamando tanque de río...");

                        this.selecPisc();
                        Piscifactoria pisc = this.piscifactorias
                                .get(MenuHelper.pedirNumero("",0, piscifactorias.size()));
                        if (pisc.getTanques().size() < 10) {
                            pisc.añadirTanque();
                        }
                    }
                    Eliminar.dismnuirRecompensas("recompensas/tanque_r.xml");
                    if (Eliminar.obtenerDatos("recompensas/tanque_r.xml") < 1) {
                        Eliminar.borrarXML("recompensas/tanque_r.xml");
                    }
                    control = false;
                    break;
                default:
                    break;
            }
        } while (control);
    }

    public void save() {
        guardarPartida();
        System.out.println("Guardado de partida");
        return;
    }

    public void guardarPartida() {
        JsonObject estructuraJson = new JsonObject();

        // Crear la lista de peces implementados
        JsonArray pecesImplementados = new JsonArray();
        String[] nombresPeces = {
                AlmacenPropiedades.TILAPIA_NILO.getNombre(),
                AlmacenPropiedades.LUCIO_NORTE.getNombre(),
                AlmacenPropiedades.CORVINA.getNombre(),
                AlmacenPropiedades.SALMON_CHINOOK.getNombre(),
                AlmacenPropiedades.PEJERREY.getNombre(),
                AlmacenPropiedades.LENGUADO_EUROPEO.getNombre(),
                AlmacenPropiedades.CABALLA.getNombre(),
                AlmacenPropiedades.ROBALO.getNombre(),
                AlmacenPropiedades.LUBINA_EUROPEA.getNombre(),
                AlmacenPropiedades.BESUGO.getNombre(),
                AlmacenPropiedades.ARENQUE_ATLANTICO.getNombre(),
                AlmacenPropiedades.SALMON_ATLANTICO.getNombre()
        };

        for (String pez : nombresPeces) {
            pecesImplementados.add(pez);
        }

        estructuraJson.add("implementados", pecesImplementados);
        estructuraJson.addProperty("empresa", this.getNombreEmpresa());
        estructuraJson.addProperty("dia", dias);
        estructuraJson.addProperty("monedas", Monedas.getInstance().getCantidad());

        // Crear la estructura de edificios
        JsonObject edificiosObjeto = new JsonObject();
        JsonObject almacenObjeto = new JsonObject();
        JsonObject comidaObjeto = new JsonObject();
        AlmacenCentral almacenCentral = AlmacenCentral.getInstance();
        if (almacenCentral != null) {
            almacenObjeto.addProperty("disponible", true);
            almacenObjeto.addProperty("capacidad", almacenCentral.getCapacidadMax());
            comidaObjeto.addProperty("general", almacenCentral.getCapacidad());
        } else {
            almacenObjeto.addProperty("disponible", false);
            almacenObjeto.addProperty("capacidad", 200);
            comidaObjeto.addProperty("general", 0);
        }
        almacenObjeto.add("comida", comidaObjeto);
        edificiosObjeto.add("almacen", almacenObjeto);
        estructuraJson.add("edificios", edificiosObjeto);

        // Piscifactorias
        JsonArray piscifactoriasArray = new JsonArray();
        for (Piscifactoria pisc : piscifactorias) {
            JsonObject piscifactoriaObjeto = new JsonObject();
            piscifactoriaObjeto.addProperty("nombre", pisc.getNombre());
            piscifactoriaObjeto.addProperty("tipo", pisc instanceof Piscifactoria);
            piscifactoriaObjeto.addProperty("capacidad", pisc.getAlmacenMax());
            piscifactoriaObjeto.addProperty("general", pisc.getAlmacen());

            JsonArray tanquesArray = new JsonArray();
            for (Tanque tanque : pisc.getTanques()) {
                JsonObject tanqueObjeto = new JsonObject();
                tanqueObjeto.addProperty("pez",
                        tanque.getPeces().isEmpty() ? "" : tanque.getPeces().get(0).getDatos().getNombre());
                tanqueObjeto.addProperty("num", tanque.getPeces().size());
                tanqueObjeto.addProperty("vivos", tanque.vivos());
                tanqueObjeto.addProperty("maduros", tanque.adultos());

                JsonArray pecesArray = new JsonArray();
                for (Pez pez : tanque.getPeces()) {
                    JsonObject pezObjeto = new JsonObject();
                    pezObjeto.addProperty("edad", pez.getEdad());
                    pezObjeto.addProperty("sexo", pez.isSexo());
                    pezObjeto.addProperty("vivo", pez.isVivo());
                    pezObjeto.addProperty("maduro", pez.isMaduro());
                    pecesArray.add(pezObjeto);
                }
                tanqueObjeto.add("peces", pecesArray);
                tanquesArray.add(tanqueObjeto);
            }
            piscifactoriaObjeto.add("tanques", tanquesArray);
            piscifactoriasArray.add(piscifactoriaObjeto);
        }

        estructuraJson.add("piscifactorias", piscifactoriasArray);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(estructuraJson);
        String nombreArchivo = "saves" + File.separator + this.getNombreEmpresa() + ".save";

        // Crear la carpeta 'saves' si no existe
        File archivo = new File(nombreArchivo);
        archivo.getParentFile().mkdirs(); // Crea las carpetas necesarias

        try (FileWriter writer = new FileWriter(archivo)) {
            writer.write(json);
            System.out.println("Partida guardada en: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al guardar la partida: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void load(String partida) {
        JsonReader reader = null;
        JsonObject jsonObject = null;
        File file = new File("saves/" + partida);

        try {
            reader = new JsonReader(new BufferedReader(new FileReader(file)));
            jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

            // Cargar valores principales
            if (jsonObject.has("empresa")) {
                Simulador.nombreEmpresa = jsonObject.get("empresa").getAsString();
            }
            this.dias = jsonObject.has("dia") ? jsonObject.get("dia").getAsInt() : 0;
            int monedas = jsonObject.has("monedas") ? jsonObject.get("monedas").getAsInt() : 0;
            Monedas.getInstance().setCantidad(monedas);

            // Extraer y cargar datos de edificios
            if (jsonObject.has("edificios")) {
                JsonObject edificiosObjeto = jsonObject.getAsJsonObject("edificios");
                if (edificiosObjeto.has("almacen")) {
                    JsonObject almacenObjeto = edificiosObjeto.getAsJsonObject("almacen");
                    if (almacenObjeto.get("disponible").getAsBoolean()) {
                        AlmacenCentral.getInstance().setCapacidadMax(almacenObjeto.get("capacidad").getAsInt());
                        AlmacenCentral.getInstance()
                                .setCapacidad(almacenObjeto.getAsJsonObject("comida").get("general").getAsInt());
                    }
                }
            }

            // Extraer y cargar piscifactorías
            JsonArray piscifactoriasArray = jsonObject.getAsJsonArray("piscifactorias");
            for (JsonElement elemento : piscifactoriasArray) {
                JsonObject piscifactoriaObjeto = elemento.getAsJsonObject();
                String nombrePiscifactoria = piscifactoriaObjeto.get("nombre").getAsString();
                Piscifactoria piscAux = new Piscifactoria(true, nombrePiscifactoria);
                piscifactorias.add(piscAux);
            }

            System.out.println("Sistema restaurado");

        } catch (FileNotFoundException ex) {
            System.out.println("Error al cargar la partida");

        } catch (IOException e) {
            System.out.println("Error de entrada/salida");

        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                System.out.println("Error al cerrar el lector");
            }
        }
    }

    public static void main(String[] args) {
        Simulador simulador = new Simulador();
        simulador.mainLoop();
    }
}
