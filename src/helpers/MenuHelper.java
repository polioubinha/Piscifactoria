package helpers;
import java.util.Scanner;
import java.util.List;

public class MenuHelper {
    private Scanner scanner;

    public MenuHelper() {
        this.scanner = new Scanner(System.in);
    }

    
    /**
     * Muestra un menú y obtiene la opción seleccionada
     * @param options
     * @param title
     * @return
     */
    public int showMenu(List<String> options, String title) {
        System.out.println("\n" + title);
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        System.out.println("0. Cancelar");

        return getValidOption(options.size());
    }

    /**
     * Valida que la opción seleccionada sea válida
     * @param maxOption
     * @return
     */
    private int getValidOption(int maxOption) {
        int choice;
        while (true) {
            try {
                System.out.print("Seleccione una opción: ");
                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 0 && choice <= maxOption) {
                    return choice;
                } else {
                    System.out.println("Opción no válida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número.");
            }
        }
    }
}
