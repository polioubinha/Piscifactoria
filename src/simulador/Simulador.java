package simulador;

import java.util.ArrayList;
import java.util.List;
import helpers.InputHelper;
import helpers.MenuHelper;
import monedero.Monedas;
import peces.especies.dobles.*;
import peces.especies.mar.*;
import peces.especies.rio.*;
import piscifactoria.Piscifactoria;
import stats.Stats;
import almacenCentral.AlmacenCentral;
import propiedades.AlmacenPropiedades;

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
    /** Días transcurridos */
    private int dias = 0;
    private String nombreEmpresa = "";

    public static void main(String[] args) {
        InputHelper inputHelper = new InputHelper();
        MenuHelper menuHelper = new MenuHelper(inputHelper.getScanner());

        Simulador simulador = new Simulador();
        int salida;

        try {
            simulador.init(inputHelper);
            do {
                System.out.println("\n===============================");
                System.out.println("            Día: " + simulador.getDias());
                System.out.println("===============================\n");

                if (simulador.getDias() == 0) {
                    System.out.println("¡Bienvenido a " + simulador.getNombreEmpresa() + "!");
                } else {
                    System.out.println("¡Bienvenido de nuevo a " + simulador.getNombreEmpresa() + "!");
                }
                System.out.println("¿Qué desea hacer?");

                // Menú principal
                salida = menuHelper.showMenu(
                    new ArrayList<>(List.of(
                        "Mostrar estado general",
                        "Gestionar piscina",
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
                    )), "Menú Principal");

                System.out.println("\n-------------------------------");

                switch (salida) {
                    case 1:
                        simulador.showGeneralStatus();
                        break;
                    case 2:
                        simulador.menuPisc(menuHelper, inputHelper);
                        break;
                    case 3:
                        Stats.getInstancia().mostrar();
                        break;
                    case 4:
                        simulador.showIctio(menuHelper);
                        break;
                    case 5:
                        simulador.nextDay(1);
                        break;
                    case 6:
                        simulador.addFood(menuHelper, inputHelper);
                        break;
                    case 7:
                        simulador.addFish(menuHelper, inputHelper);
                        break;
                    case 8:
                        simulador.sell();
                        break;
                    case 9:
                        simulador.cleanTank();
                        break;
                    case 10:
                        simulador.emptyTank();
                        break;
                    case 11:
                        simulador.upgrade(menuHelper, inputHelper);
                        break;
                    case 12:
                        int avanzarDias = inputHelper.getIntInput("¿Cuántos días quieres avanzar? ", 1, 365);
                        simulador.nextDay(avanzarDias);
                        break;
                    case 13:
                        System.out.println("\n¡Gracias por jugar! ¡Hasta la próxima!");
                        break;
                    default:
                        System.out.println("\nOpción no válida. Inténtalo de nuevo.");
                        break;
                }
            } while (salida != 13);
        } catch (Exception e) {
            System.out.println("\nError inesperado: " + e.getMessage());
        } finally {
            inputHelper.closeScanner();
        }
    }

    public void init(InputHelper inputHelper) {
        this.nombreEmpresa = inputHelper.getStringInput("Introduce el nombre de la empresa: ");
        String nombrePisci = nombrePiscifactoria(inputHelper);
        Monedas.getInstance();
        Stats.getInstancia(peces);
        this.piscifactorias.add(new Piscifactoria(true, nombrePisci));
    }

    public int getDias() {
        return dias;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void showGeneralStatus() {
        for (Piscifactoria piscifactoria : piscifactorias) {
            piscifactoria.showStatus();
        }

        System.out.println("Día: " + this.dias);
        System.out.println(Monedas.getInstance().getCantidad() + " cantidad de monedas.");
        if (almacenCentral) {
            System.out.println("Almacen central: " + AlmacenCentral.getInstance().getCapacidad() + "/" +
                AlmacenCentral.getInstance().getCapacidadMax() + " (" +
                this.piscifactorias.get(0).porcentaje(AlmacenCentral.getInstance().getCapacidad(),
                AlmacenCentral.getInstance().getCapacidadMax()) + "%)");
        }
    }

    public void menuPisc(MenuHelper menuHelper, InputHelper inputHelper) {
        int piscifactoriaIndex = menuHelper.showMenu(
            piscifactorias.stream().map(Piscifactoria::getNombre).toList(),
            "Seleccione una piscifactoría"
        ) - 1;

        if (piscifactoriaIndex >= 0 && piscifactoriaIndex < piscifactorias.size()) {
            piscifactorias.get(piscifactoriaIndex).showStatus();
        } else {
            System.out.println("Opción no válida.");
        }
    }

    public void showIctio(MenuHelper menuHelper) {
        int opcion = menuHelper.showMenu(
            new ArrayList<>(List.of(peces)),
            "Seleccione un pez para ver su información"
        );

        switch (opcion) {
            case 1 -> Dorada.datos();
            case 2 -> TruchaArcoiris.datos();
            case 3 -> ArenqueDelAtlantico.datos();
            case 4 -> Besugo.datos();
            case 5 -> Caballa.datos();
            case 6 -> Robalo.datos();
            case 7 -> Sargo.datos();
            case 8 -> Carpa.datos();
            case 9 -> CarpaPlateada.datos();
            case 10 -> Pejerrey.datos();
            case 11 -> SalmonChinook.datos();
            case 12 -> TilapiaDelNilo.datos();
            default -> System.out.println("Opción no válida.");
        }
    }

    public void addFood(MenuHelper menuHelper, InputHelper inputHelper) {
        if (!almacenCentral) {
            int piscifactoriaIndex = menuHelper.showMenu(
                piscifactorias.stream().map(Piscifactoria::getNombre).toList(),
                "Seleccione la piscifactoría para añadir comida"
            ) - 1;

            if (piscifactoriaIndex >= 0 && piscifactoriaIndex < piscifactorias.size()) {
                piscifactorias.get(piscifactoriaIndex).addComida(25);
            } else {
                System.out.println("Opción no válida.");
            }
        } else {
            AlmacenCentral.getInstance().comprarComida(25);
        }
    }

    public void addFish(MenuHelper menuHelper, InputHelper inputHelper) {
        int piscifactoriaIndex = menuHelper.showMenu(
            piscifactorias.stream().map(Piscifactoria::getNombre).toList(),
            "Seleccione la piscifactoría para añadir pez"
        ) - 1;

        if (piscifactoriaIndex >= 0 && piscifactoriaIndex < piscifactorias.size()) {
            piscifactorias.get(piscifactoriaIndex).newFish(menuHelper, inputHelper);
        } else {
            System.out.println("Opción no válida.");
        }
    }

    public void cleanTank() {
        for (Piscifactoria piscifactoria : piscifactorias) {
            piscifactoria.limpiarTanques();
        }
    }

    public void emptyTank() {
        for (Piscifactoria piscifactoria : piscifactorias) {
            piscifactoria.vaciarTanques();
        }
    }

    public void nextDay(int dias) {
        for (int i = 0; i < dias; i++) {
            for (Piscifactoria piscifactoria : piscifactorias) {
                piscifactoria.nextDay(almacenCentral);
            }
            this.dias++;
        }
    }

    public void sell() {
        for (Piscifactoria piscifactoria : piscifactorias) {
            piscifactoria.venderAdultos();
        }
    }

    public void upgrade(MenuHelper menuHelper, InputHelper inputHelper) {
        int piscifactoriaIndex = menuHelper.showMenu(
            piscifactorias.stream().map(Piscifactoria::getNombre).toList(),
            "Seleccione la piscifactoría para mejorar"
        ) - 1;

        if (piscifactoriaIndex >= 0 && piscifactoriaIndex < piscifactorias.size()) {
            piscifactorias.get(piscifactoriaIndex).upgradeFood();
        } else {
            System.out.println("Opción no válida.");
        }
    }

    private String nombrePiscifactoria(InputHelper inputHelper) {
        return inputHelper.getStringInput("Introduce el nombre de la piscifactoría: ");
    }
}
