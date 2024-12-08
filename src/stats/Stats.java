package stats;

import estadisticas.Estadisticas;

public class Stats extends Estadisticas {

    /**
     * Instancia única de la clase Stats.
     */
    private static Stats instance = null;

    /**
     * Constructor privado que crea una instancia de Stats con los nombres
     * de las características especificadas.
     * 
     * @param nombres Array que representa los nombres de las estadísticas.
     */
    private Stats(String[] nombres) {
        super(nombres);
    }

    /**
     * Obtiene la instancia de Stats. Si no existe, crea una nueva.
     * 
     * @param nombres Array que representa los nombres de las estadísticas.
     * @return La instancia única de Stats.
     */
    public static Stats getInstancia(String[] nombres) {
        if (instance == null) {
            instance = new Stats(nombres);
            System.out.println("Stats inicializado correctamente.");
        }
        return instance;
    }
    

    /**
     * Obtiene la instancia de Stats.
     * 
     * @return La instancia de Stats, si no existe, lanza una excepción.
     */
    public static Stats getInstancia() {
        if (instance == null) {
            throw new IllegalStateException("Stats no ha sido inicializado. Llama primero a getInstancia(String[] nombres).");
        }
        return instance;
    }
}
