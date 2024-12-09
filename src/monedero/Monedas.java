package monedero;

/** Clase para gestionar las monedas */
public class Monedas{
    /** Cantidad de monedas */
    private int cantidad;
    /** Singleton */
    private static Monedas instance=null;

    /**
     * Constructor privado que crea una instancia de Monedas
     * 
     * @param cantidad cantidad de monedas
     */
    private Monedas(int cantidad){
        this.cantidad = cantidad;
    }

    /**
     * Método para obtener la instancia, si no existe, la crea
     * @return instance
     */
    public static Monedas getInstance(){
        if(instance == null){
            instance = new Monedas(100);
        }
        return instance;
    }

    /**
     * Obtiene la cantidad de monedas
     * @return cantidad de monedas
     */
    public int getCantidad(){
        return cantidad;
    }

    /**
     * Modifica la cantidad de monedas
     * @param cantidad cantidad de monedas a modificar
     */
    public void setCantidad(int cantidad){
        this.cantidad = cantidad;
    }

    /**
     * Comprueba que se tenga la cantidad de monedas suficientes para realizar la compra
     * @param precio precio de la compra
     * @return boolean true si se puede realizar la compra, false en caso contrario
     */
    public boolean comprobarCompra(int precio) {
        return cantidad >= precio;
    }
    
    /**
     * Realiza una compra, restandole su valor a la cantidad de monedas
     * @param precio precio de la compra
     */
    public void compra(int precio){
        this.cantidad -= precio;
    }

    /**
     * Realiza un venta, sumandole su valor a la cantidad de monedas
     * @param precio precio de la venta
     */
    public void venta(int precio){
        this.cantidad += precio;
    }

    /**
     * Añade monedas
     * @param cantidad cantidad de monedas a añadir
     */
    public void añadirMonedas(int cantidad){
        this.cantidad += cantidad;
    }
}