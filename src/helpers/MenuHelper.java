package helpers;

import java.util.Scanner;

public class MenuHelper {
    private Scanner scanner;

    public MenuHelper() {
        scanner = new Scanner(System.in);
    }

    /**
     * Muestra un menú con las opciones dadas y devuelve la opción seleccionada por el usuario
     * 
     * @param opciones Array de opciones del menú
     * @return La opción seleccionada por el usuario (1 a n)
     */
    public int mostrarMenu(String[] opciones, boolean cancelar) {
        for (int i = 0; i < opciones.length; i++) {
            System.out.println((i + 1) + ". " + opciones[i]);
        }
        if(cancelar){
            System.out.println("0. Cancelar"); 
        } 
        int seleccion;
        do {
            System.out.print("Introduce tu opción: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Por favor, introduce un número: ");
                scanner.next();
            }
            seleccion = scanner.nextInt();
        } while (seleccion < 0 || seleccion > opciones.length);
        return seleccion;
    }

    /**
     * Pide al usuario que introduzca un número entero dentro de un rango.
     * 
     * @param mensaje Mensaje para pedir el número
     * @param min Valor mínimo
     * @param max Valor máximo
     * @return El número introducido por el usuario
     */
    public int pedirNumero(String mensaje, int min, int max) {
        int numero;
        do {
            System.out.print(mensaje);
            while (!scanner.hasNextInt()) {
                System.out.print("Por favor, introduce un número: ");
                scanner.next();
            }
            numero = scanner.nextInt();
        } while (numero < min || numero > max);
        return numero;
    }

    /**
     * Muestra un mensaje para pedir una entrada de texto.
     * 
     * @param mensaje Mensaje para pedir el texto
     * @return El texto introducido por el usuario
     */
    public String pedirTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.next();
    }

    /**
     * Cierra el scanner (debe llamarse al final del programa)
     */
    public void cerrarScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}