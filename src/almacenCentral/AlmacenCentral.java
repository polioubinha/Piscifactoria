package almacenCentral;

import monedero.Monedas;

public class AlmacenCentral {

    int capacidad = 0;
    int capacidadMax = 0;

    /**
     * Singleton para que solo haya una instancia de AlmacenCentral
     */
    public static AlmacenCentral instance;

    /**
     * Método para obtener la instancia, si no existe, la crea
     * 
     * @return instancia de AlmacenCentral
     */
    public static AlmacenCentral getInstance(){
        if(instance==null){
            instance = new AlmacenCentral();
        }
        return instance;
    }

    /**
     * inicializamos la capacidad y la capacidad máxima
     */
    private AlmacenCentral(){
        this.capacidad = 200;
        this.capacidadMax = 200;
    }

    /**
     * Obtenemos la capacidad del almacen
     * 
     * @return capacidad del almacen
     */
    public int getCapacidad(){
        return capacidad;
    }

    /**
     * Establecemos la capacidad del almacen
     * 
     * @param capacidad del almacen
     */
    public void setCapacidad(int capacidad){
        this.capacidad = capacidad;
    }

    /**
     * Obtenemos la capacidad máxima del almacen
     * 
     * @return capacidad máxima del almacen
     */
    public int getCapacidadMax(){
        return capacidadMax;
    }

    /**
     * Establecemos la capacidad máxima del almacen
     * 
     * @param capacidad máxima del almacen
     */
    public void setCapacidadMax(int capacidadMax){
        this.capacidadMax = capacidadMax;
    }

    /**
     * Añadimos capacidad al almacen
     * 
     * @param capacidad a añadir
     */
    public void añadirCapacidad(int capacidad){
        this.capacidad += capacidad;
    }

    /**
     * Aumentamos la capacidad máxima del almacen
     * 
     * @param cantidad a aumentar
     */
    public void aumentarCapacidad(int cantidad){
        this.capacidadMax += cantidad;
    }

    /**
     * Agregamos comida al almacen
     * 
     * @param cantidad de comida a agregar
     */
    public void agregarComida(int cantidad){
        this.capacidad += cantidad;
    }

    /**
     * Mejora el almacen aumentando la capacidad máxima
     * 
     * Para realizar la mejora se necesita 100 monedas
     */
    public void upgrade(){
        if(Monedas.getInstance().comprobarCompra(200)){
            Monedas.getInstance().compra(200);
            this.aumentarCapacidad(50);
        }else{
            System.out.println("No tienes suficientes monedas");
        }
    }

    /**
     * Compramos una cantidad de  comida para el almacen
     * 
     * @param cantidad de comida a comprar
     */
    public void comprarComida(int cantidad){
        int coste;
        if(cantidad <= 25){
            coste = cantidad;
        }else{
            coste = cantidad - (cantidad / 25) * 5;
        }

        if(Monedas.getInstance().comprobarCompra(coste)){
            this.capacidad -= cantidad;
            Monedas.getInstance().compra(coste);
            if(this.capacidad > this.capacidadMax){
                this.capacidad = this.capacidadMax;
            }
            System.out.println("Has comprado " + cantidad + " de comida");
        }else{
            System.out.println("No tienes suficientes monedas");
        }
    }
}
