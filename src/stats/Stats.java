package stats;

import estadisticas.Estadisticas;

public class Stats extends Estadisticas{

    /*
     * Instacia única de la clase Stats
     */
    private static Stats instance = null;

    /*
     * Constructor privado que crea una instancia de Stats con los nombres
     * de las características especificadas
     * 
     * @param nombres Array que representa los nombres de las estadísticas
     */
    private Stats(String[] nombres) {
        super(nombres);
    }


    /*
     * Obtiene la instancia de Stats. Si no existe, crea una nueva
     * 
     * @param nombres Array que representa los nombres de las estadísticas
     */
    public static Stats getInstancia(String[] nombres) {
        if (instance == null) {
            instance = new Stats(nombres);
        }
        return instance;
    }


    /*
     * Obtiene la instancia de Stats
     * 
     * @return La instancia de Stats, si no existe devuelve null
     */
    public static Stats getInstancia() {
        return instance;
    }
}
