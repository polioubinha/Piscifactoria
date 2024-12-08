package simulador;

import peces.Pez;
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

            int opcion = menuHelper.mostrarMenu(opciones);
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
            opcion = menuHelper.mostrarMenu(peces);
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
    
                    if (tanque.esDeRio()) { 
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
            registro.registrarLog("INFO", "Día " + this.dias + " finalizado con éxito. Peces Río: " +
            pecesRio + ", Peces Mar: " + pecesMar + ", Monedas totales: " + Monedas.getInstance().getCantidad());
        }
    
        registro.registrarAccion("Avanzados " + dias + " días.");
    }

    private void addFood() {
        if (!almacenCentral) {
            int opcion = menuHelper.mostrarMenu(new String[]{"Agregar 5", "Agregar 10", "Agregar 25", "Llenar", "Salir"});
            switch (opcion) {
                case 1: 
                    piscifactorias.get(0).addComida(5);
                    registro.registrarLog("INFO", "5 unidades de comida añadidas a la piscifactoría " + piscifactorias.get(0).getNombre());
                    break;
                case 2: 
                    piscifactorias.get(0).addComida(10);
                    registro.registrarLog("INFO", "10 unidades de comida añadidas a la piscifactoría " + piscifactorias.get(0).getNombre());
                    break;
                case 3: 
                    piscifactorias.get(0).addComida(25);
                    registro.registrarLog("INFO", "25 unidades de comida añadidas a la piscifactoría " + piscifactorias.get(0).getNombre());
                    break;
                case 4: 
                    int cantidad = piscifactorias.get(0).getAlmacenMax() - piscifactorias.get(0).getAlmacen();
                    piscifactorias.get(0).addComida(cantidad);
                    registro.registrarLog("INFO", "Almacén de comida llenado con " + cantidad + " unidades en la piscifactoría " + piscifactorias.get(0).getNombre());
                    break;
                case 5:
                    registro.registrarLog("INFO", "Se salió del menú de agregar comida sin realizar cambios.");
                    break;
                default:
                    registro.registrarLog("WARNING", "Selección inválida al intentar agregar comida.");
                    System.out.println("Selecciona una opción válida");
            }
        } else {
            int opcion = menuHelper.mostrarMenu(new String[]{"Agregar 5", "Agregar 10", "Agregar 25", "Llenar", "Salir"});
            switch (opcion) {
                case 1: 
                    AlmacenCentral.getInstance().comprarComida(5);
                    registro.registrarLog("INFO", "5 unidades de comida compradas para el almacén central.");
                    break;
                case 2: 
                    AlmacenCentral.getInstance().comprarComida(10);
                    registro.registrarLog("INFO", "10 unidades de comida compradas para el almacén central.");
                    break;
                case 3: 
                    AlmacenCentral.getInstance().comprarComida(25);
                    registro.registrarLog("INFO", "25 unidades de comida compradas para el almacén central.");
                    break;
                case 4: 
                    int cantidad = AlmacenCentral.getInstance().getCapacidadMax() - AlmacenCentral.getInstance().getCapacidad();
                    AlmacenCentral.getInstance().comprarComida(cantidad);
                    registro.registrarLog("INFO", "Almacén central llenado con " + cantidad + " unidades de comida.");
                    break;
                case 5:
                    registro.registrarLog("INFO", "Se salió del menú de agregar comida sin realizar cambios.");
                    break;
                default:
                    registro.registrarLog("WARNING", "Selección inválida al intentar agregar comida al almacén central.");
                    System.out.println("Selecciona una opción válida.");
            }
        }
    }
    
    

   private void addFish() {
    // Solicitar al usuario la piscifactoría a la que desea agregar el pez
    int piscifactoriaIndex = menuHelper.pedirNumero("Seleccione la piscifactoría a la que agregar el pez: ", 1, piscifactorias.size())-1;
    Piscifactoria piscifactoria = piscifactorias.get(piscifactoriaIndex);

    try {
        // Agregar el pez usando el método `newFish` de Piscifactoria
        piscifactoria.newFish();

        // Obtener el último tanque modificado y el pez añadido
        Tanque tanque = piscifactoria.getTanques().get(piscifactoria.getTanques().size() - 1);
        Pez pez = tanque.getPeces().get(tanque.getPeces().size() - 1); // Último pez añadido

        // Preparar el mensaje para los logs
        String mensajeLog = String.format(
            "[%s] %s (%s) comprado. Añadido al tanque de capacidad %d de la piscifactoría %s.",
            java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
            pez.getDatos().getNombre(),
            pez.isSexo() ? "M" : "H",
            tanque.getCapacidad(),
            piscifactoria.getNombre()
        );

        // Registrar en logs y transcripciones
        registro.registrarLog("INFO", mensajeLog);
        registro.registrarTranscripcion(mensajeLog);

        System.out.println("Pez añadido correctamente: " + mensajeLog);
    } catch (Exception e) {
        String mensajeError = "Error al añadir pez a la piscifactoría " + piscifactoria.getNombre();
        registro.registrarLog("ERROR", mensajeError);
        registro.registrarError(mensajeError + " - " + e.getMessage());
        System.out.println("No se pudo añadir el pez. Verifique los tanques y monedas disponibles.");
    }
}

private void sell() {
    for (Piscifactoria piscifactoria : piscifactorias) {
        int pecesVendidos = 0;
        int gananciasTotales = 0;

        // Vender peces adultos de la piscifactoría
        piscifactoria.venderAdultos();
        registro.registrarTranscripcion(
            "Vendidos peces adultos de la piscifactoría " + piscifactoria.getNombre() + "."
        );

        // Procesar la venta de peces óptimos por tanque
        for (Tanque tanque : piscifactoria.getTanques()) {
            int pecesAntes = tanque.getPeces().size();
            int gananciasAntes = tanque.getGanancias();

            tanque.venderOptimos();

            pecesVendidos += (pecesAntes - tanque.getPeces().size());
            gananciasTotales += (tanque.getGanancias() - gananciasAntes);

            registro.registrarTranscripcion(
                "Vendidos " + (pecesAntes - tanque.getPeces().size()) + 
                " peces óptimos del tanque (Capacidad: " + tanque.getCapacidad() + 
                ") de la piscifactoría " + piscifactoria.getNombre() +
                " por " + (tanque.getGanancias() - gananciasAntes) + " monedas."
            );
        }

        // Registrar log de venta
        if (pecesVendidos > 0) {
            registro.registrarLog(
                "INFO", 
                "Vendidos " + pecesVendidos + " peces de la piscifactoría " + piscifactoria.getNombre() + 
                " de forma manual por un total de " + gananciasTotales + " monedas."
            );
        } else {
            registro.registrarLog(
                "INFO", 
                "No se vendieron peces en la piscifactoría " + piscifactoria.getNombre() + "."
            );
        }
    }

    registro.registrarAccion("Venta de peces completada.");
}

    
    

private void cleanTank() {
    for (Piscifactoria piscifactoria : piscifactorias) {
        piscifactoria.limpiarTanques();

        for (int i = 0; i < piscifactoria.getTanques().size(); i++) {
            Tanque tanque = piscifactoria.getTanques().get(i);
            tanque.limpiarTanque();

            registro.registrarLog(
                "INFO", 
                "Tanque " + (i + 1) + " limpiado en la piscifactoría " + piscifactoria.getNombre()
            );

            registro.registrarTranscripcion(
                "Tanque " + (i + 1) + " de la piscifactoría " + piscifactoria.getNombre() + " ha sido limpiado."
            );
        }
    }

    registro.registrarAccion("Limpieza de todos los tanques completada.");
}


private void emptyTank() {
    for (Piscifactoria piscifactoria : piscifactorias) {
        piscifactoria.vaciarTanques();

        for (int i = 0; i < piscifactoria.getTanques().size(); i++) {
            Tanque tanque = piscifactoria.getTanques().get(i);
            tanque.vaciarTanque();

            registro.registrarLog(
                "INFO", 
                "Tanque " + (i + 1) + " vaciado en la piscifactoría " + piscifactoria.getNombre()
            );

            registro.registrarTranscripcion(
                "Tanque " + (i + 1) + " de la piscifactoría " + piscifactoria.getNombre() + " ha sido vaciado."
            );
        }
    }

    registro.registrarAccion("Vaciado de todos los tanques completado.");
}


private void upgrade() {
    int pisc = menuHelper.pedirNumero("Seleccione la piscifactoría para mejorar: ", 1, piscifactorias.size());
    int mejoraOpcion = menuHelper.mostrarMenu(new String[]{"Comprar tanque", "Aumentar almacén", "Cancelar"});
    Piscifactoria piscifactoriaSeleccionada = piscifactorias.get(pisc - 1);

    switch (mejoraOpcion) {
        case 1: 
            piscifactoriaSeleccionada.comprarTanque();
            registro.registrarLog(
                "INFO",
                "Mejorada la piscifactoría " + piscifactoriaSeleccionada.getNombre() + " añadiendo un nuevo tanque."
            );
            registro.registrarTranscripcion(
                "Se ha añadido un nuevo tanque a la piscifactoría " + piscifactoriaSeleccionada.getNombre() + "."
            );
            break;
        case 2: 
            int capacidadAntes = piscifactoriaSeleccionada.getAlmacenMax();
            int coste = piscifactoriaSeleccionada.isRio() ? 100 : 200; 

            piscifactoriaSeleccionada.upgradeFood();

            int capacidadDespues = piscifactoriaSeleccionada.getAlmacenMax();
            registro.registrarLog(
                "INFO",
                "Mejorada la piscifactoría " + piscifactoriaSeleccionada.getNombre() + " aumentando su capacidad de comida."
            );
            registro.registrarTranscripcion(
                "Mejorada la piscifactoría " + piscifactoriaSeleccionada.getNombre() +
                " aumentando su capacidad de comida hasta un total de " + capacidadDespues +
                " por " + coste + " monedas. (Capacidad anterior: " + capacidadAntes + ")."
            );
            break;
        case 3: 
            registro.registrarLog("INFO", "Mejora cancelada en la piscifactoría " + piscifactoriaSeleccionada.getNombre() + ".");
            registro.registrarTranscripcion("Se ha cancelado la mejora en la piscifactoría " + piscifactoriaSeleccionada.getNombre() + ".");
            break;
        default:
            registro.registrarLog("WARNING", "Selección inválida en el menú de mejoras.");
            System.out.println("Opción no válida");
    }
}


    public static void main(String[] args) {
        Simulador simulador = new Simulador();
        simulador.mainLoop();
    }
}
