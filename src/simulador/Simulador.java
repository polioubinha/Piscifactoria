package simulador;

import peces.Pez;
import peces.especies.dobles.*;
import peces.especies.mar.*;
import peces.especies.rio.*;
import almacenCentral.*;
import registro.Registro;
import rewards.Crear;
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
            // Comprobar si existen archivos de guardado en la carpeta "saves/"
            File saves = new File("saves");
            if (saves.exists() && saves.isDirectory() && saves.listFiles().length > 0) {
                File[] files = saves.listFiles();
                System.out.println("---------- Partidas disponibles ---------");
                System.out.println("0. Nueva Partida");

                // Mostrar lista de partidas
                for (int ind = 0; ind < files.length; ind++) {
                    System.out.println((ind + 1) + ". " + files[ind].getName());
                }

                // Preguntar al usuario qué partida quiere cargar
                System.out.println("¿Qué partida quieres cargar?");
                int opcion = menuHelper.pedirNumero("Selecciona una opción (0 para nueva partida)", 0, files.length);

                if (opcion > 0) {
                    // Cargar la partida seleccionada
                    String partida = files[opcion - 1].getName();
                    this.load(partida);
                } else {
                    // Crear nueva partida
                    this.newGame();
                }
            } else {
                // No hay partidas guardadas, crear una nueva partida
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
        nombreEmpresa = menuHelper.pedirTexto("Introduce el nombre de la empresa: ");
        String nombrePiscifactoria = menuHelper.pedirTexto("Introduce el nombre de la piscifactoría inicial: ");

        registro.registrarTranscripcion(
                "Inicialización completada: Empresa - " + nombreEmpresa + ", Piscifactoría - " + nombrePiscifactoria);
        registro.registrarTranscripcion(
                "Peces iniciales disponibles:\nRío: Dorada, Trucha Arcoiris\nMar: Tilapia del Nilo, Salmón Chinook");

        // Agregar la primera piscifactoría
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

        // Inicializar la instancia de Monedas
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
                "Salir"
        };

        boolean salir = false;
        while (!salir) {
            System.out.println("\n===============================");
            System.out.println("            Menú             ");
            System.out.println("===============================\n");

            int opcion = menuHelper.mostrarMenu(opciones);
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
                    int dias = menuHelper.pedirNumero("Cuántos días quieres avanzar? ", 1, 100);
                    nextDay(dias);
                    registro.registrarTranscripcion("Se han avanzado: " + dias + " días");
                    break;
                case 13:
                    registro.registrarTranscripcion("Salir del simulador");
                    save();

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
            opcion = menuHelper.mostrarMenu(peces);
            if (opcion > 0 && opcion <= peces.length) {
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
                        Sargo.datos();
                        break;
                    case 7:
                        Robalo.datos();
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
            int opcion = menuHelper
                    .mostrarMenu(new String[] { "Agregar 5", "Agregar 10", "Agregar 25", "Llenar", "Salir" });
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
            int opcion = menuHelper
                    .mostrarMenu(new String[] { "Agregar 5", "Agregar 10", "Agregar 25", "Llenar", "Salir" });
            switch (opcion) {
                case 1:
                    AlmacenCentral.getInstance().comprarComida(5);
                    registro.registrarAccion("Comprar 5 unidades de comida");
                    registro.registrarTranscripcion("5 unidades de comida añadidas al almacén central.");
                    break;
                case 2:
                    AlmacenCentral.getInstance().comprarComida(10);
                    registro.registrarAccion("Comprar 10 unidades de comida");
                    registro.registrarTranscripcion("10 unidades de comida añadidas al almacén central.");
                    break;
                case 3:
                    AlmacenCentral.getInstance().comprarComida(25);
                    registro.registrarAccion("Comprar 25 unidades de comida");
                    registro.registrarTranscripcion("25 unidades de comida añadidas al almacén central.");
                    break;
                case 4:
                    int cantidad = AlmacenCentral.getInstance().getCapacidadMax()
                            - AlmacenCentral.getInstance().getCapacidad();
                    AlmacenCentral.getInstance().comprarComida(cantidad);
                    registro.registrarAccion("Llenar almacén central de comida");
                    registro.registrarTranscripcion("Almacén central lleno con " + cantidad + " unidades de comida.");
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Selecciona una opción válida.");
            }
        }
    }

    private void addFish() {
        int piscifactoria = menuHelper.pedirNumero("Seleccione la piscifactoría a la que agregar el pez: ", 1,
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
        int mejoraOpcion = menuHelper.mostrarMenu(new String[] { "Comprar tanque", "Aumentar almacén", "Cancelar" });
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

    public void save() {
        guardarPartida();
        System.out.println("Guardado de partida");
        return; // Se permite que el programa termine de forma natural
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
