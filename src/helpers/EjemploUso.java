

import java.util.ArrayList;
import java.util.List;

public class EjemploUso {
    public static void main(String[] args) {
        MenuHelper menuHelper = new MenuHelper();
        InputHelper inputHelper = new InputHelper();

        // Opciones del menú principal
        List<String> mainMenuOptions = new ArrayList<>();
        mainMenuOptions.add("Estado general");
        mainMenuOptions.add("Estado piscifactoría");
        mainMenuOptions.add("Estado tanques");
        mainMenuOptions.add("Informes");
        mainMenuOptions.add("Ictiopedia");
        mainMenuOptions.add("Pasar día");
        mainMenuOptions.add("Comprar comida");
        mainMenuOptions.add("Comprar peces");
        mainMenuOptions.add("Vender peces");
        mainMenuOptions.add("Limpiar tanques");
        mainMenuOptions.add("Vaciar tanque");
        mainMenuOptions.add("Mejorar");
        mainMenuOptions.add("Pasar varios días");
        mainMenuOptions.add("Salir");

        // Esto son pruebas para los helpers NO ES CODIGO UTIL, solo para hacer pruebas.
        while (true) {
            int option = menuHelper.showMenu(mainMenuOptions, "Menú Principal");

            switch (option) {
                case 0:
                    System.out.println("Saliendo del programa.");
                    return;
                case 1:
                    System.out.println("Mostrando estado general...");
                    break;
                case 7:
                    
                    int cantidad = inputHelper.getIntInput("Ingrese la cantidad de comida a comprar (5, 10, 25): ", 5, 25);
                    System.out.println("Has comprado " + cantidad + " unidades de comida.");
                    break;
                case 13:
                    int dias = inputHelper.getIntInput("¿Cuántos días quieres pasar?: ", 1, 100);
                    System.out.println("Pasando " + dias + " días...");
                    break;
                default:
                    System.out.println("Opción no implementada.");
                    break;
            }
        }
    }
}
