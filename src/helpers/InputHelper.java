package helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputHelper {
    private BufferedReader reader;

    public InputHelper() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Obtiene un input de tipo String
     * @param prompt 
     * @return 
     */
    public String getStringInput(String prompt) {
        String input = null;
        while (true) {
            try {
                System.out.print(prompt);
                input = reader.readLine();
                if (input != null && !input.isEmpty()) {
                    return input;
                } else {
                    System.out.println("La entrada no puede estar vacía. Intente nuevamente.");
                }
            } catch (IOException e) {
                System.out.println("Error al leer la entrada. Intente nuevamente.");
            }
        }
    }

    /**
     * Obtiene un input de tipo int
     * @param prompt
     * @param minValue
     * @param maxValue
     * @return
     */
    public int getIntInput(String prompt, int minValue, int maxValue) {
        int value = 0;
        while (true) {
            try {
                System.out.print(prompt);
                String input = reader.readLine();
                value = Integer.parseInt(input);
                if (value >= minValue && value <= maxValue) {
                    return value;
                } else {
                    System.out.println("Por favor, ingrese un número entre " + minValue + " y " + maxValue + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número.");
            } catch (IOException e) {
                System.out.println("Error al leer la entrada. Intente nuevamente.");
            }
        }
    }

    /**
     * Obtiene un input de tipo Si/No
     * @param prompt
     * @return
     */
    public boolean getYesNoInput(String prompt) {
        String input = null;
        while (true) {
            try {
                System.out.print(prompt + " (S/N): ");
                input = reader.readLine().trim().toLowerCase();
                if (input.equals("s")) {
                    return true;
                } else if (input.equals("n")) {
                    return false;
                } else {
                    System.out.println("Entrada no válida. Por favor, ingrese 'S' para Sí o 'N' para No.");
                }
            } catch (IOException e) {
                System.out.println("Error al leer la entrada. Intente nuevamente.");
            }
        }
    }
}
