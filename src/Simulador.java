import java.io.Console;

public class Simulador {
    private int numeroDias = 0;
    private int numeroPiscifactorias = 0;
    private String nombreEmpresa = "";

    public static void main(String[] args) {
        mostrarMenu();
    }

    public int getNumeroDias() {
        return numeroDias;
    }

    public void setNumeroDias(int numeroDias) {
        this.numeroDias = numeroDias;
    }

    public int getNumeroPiscifactorias() {
        return numeroPiscifactorias;
    }

    public void setNumeroPiscifactorias(int numeroPiscifactorias) {
        this.numeroPiscifactorias = numeroPiscifactorias;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public static void mostrarMenu() {
        System.out.println("Menú:");
        System.out.println("1. Estado general");
        System.out.println("2. Estado piscifactoría");
        System.out.println("3. Estado tanques");
        System.out.println("4. Informes");
        System.out.println("5. Ictiopedia");
        System.out.println("6. Pasar día");
        System.out.println("7. Comprar comida");
        System.out.println("8. Comprar peces");
        System.out.println("9. Vender peces");
        System.out.println("10. Limpiar tanques");
        System.out.println("11. Vaciar tanque");
        System.out.println("12. Mejorar");
        System.out.println("13. Pasar varios días");
        System.out.println("14. Salir");

        Console c = System.console();
        int opt= Integer.parseInt(c.readLine());

        switch (opt) {
            case 1:
                System.out.println();
                break;
            case 2:
                System.out.println();
                break;
            case 3:
                System.out.println();
                break;
            case 4:
                System.out.println();
                break;
            case 5:
                System.out.println();
                break;
            case 6:
                System.out.println();
                break;
            case 7:
                System.out.println();
                break;
            case 8:
                System.out.println();
                break;
            case 9:
                System.out.println();
                break;
            case 10:
                System.out.println();
                break;
            case 11:
                System.out.println();
                break;
            case 12:
                System.out.println();
                break;
            case 13:
                System.out.println();
                break;
            case 14:
                System.out.println("Hasta pronto!");
                break;
            default:
                System.out.println("Opción no válida");
                mostrarMenu();
        }
    }

    public static void init() {
        // Este método inicializa el sistema y pide los datos
    }

}
