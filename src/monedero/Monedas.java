package monedero;

public class Monedas{
    private int cantidad;

    // Singleton
    private static Monedas instance=null;

    // Constructor privado para que no se pueda instanciar
    private Monedas(int cantidad){
        this.cantidad = cantidad;
    }

    // Método para obtener la instancia, si no existe, la crea
    public static Monedas getInstance(){
        if(instance == null){
            instance = new Monedas(1000);
        }
        return instance;
    }

    // Método para obtener la cantidad de monedas
    public int getCantidad(){
        return cantidad;
    }

    // Método para establecer la cantidad de monedas
    public void setCantidad(int cantidad){
        this.cantidad = cantidad;
    }

    //Metodo para comprobar si hay se puede realizar la compra con la cantidad indicada
    public boolean comprobarCompra(int precio) {
        return cantidad >= precio;
    }
    
    // Método para realizar la compra, resta el precio de la cantidad de monedas
    public void compra(int precio){
        this.cantidad -= precio;
    }

    // Método para realizar la venta, suma el precio a la cantidad de monedas
    public void venta(int precio){
        this.cantidad += precio;
    }

    // Método para añadir monedas
    public void añadirMonedas(int cantidad){
        this.cantidad += cantidad;
    }
}