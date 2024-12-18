package almacenCentral;

import monedero.Monedas;

/** Clase que representa el almacén central */
public class AlmacenCentral {
    /** Capacidad del almacén */
    private int capacidad = 0;
    /** Capacidad maxima del almacén */
    private int capacidadMax = 0;
    /** Tipo de comida del almacén */
    private String tipoComida;

    /** Instancias de almacenes por tipo */
    private static AlmacenCentral almacenAnimal;
    private static AlmacenCentral almacenVegetal;

    /**
     * Método para obtener la instancia, si no existe, la crea
     * @param tipoComida tipo de comida del almacén
     * @return instancia de AlmacenCentral
     */
    public static AlmacenCentral getInstance(String tipoComida) {
        if (tipoComida.equalsIgnoreCase("animal")) {
            if (almacenAnimal == null) {
                almacenAnimal = new AlmacenCentral("animal");
            }
            return almacenAnimal;
        } else if (tipoComida.equalsIgnoreCase("vegetal")) {
            if (almacenVegetal == null) {
                almacenVegetal = new AlmacenCentral("vegetal");
            }
            return almacenVegetal;
        } else {
            throw new IllegalArgumentException("Tipo de comida no válido. Debe ser 'animal' o 'vegetal'.");
        }
    }

    /**
     * Constructor de la clase, inicializamos la capacidad y la capacidad máxima y el tipo de comida
     */
    private AlmacenCentral(String tipoComida) {
        this.capacidad = 200;
        this.capacidadMax = 200;
        this.tipoComida = tipoComida.toLowerCase();
    }

    /**
     * Obtenemos la capacidad del almacen
     * @return capacidad del almacen
     */
    public int getCapacidad() {
        return capacidad;
    }

    /**
     * Establecemos la capacidad del almacen
     * @param capacidad del almacen
     */
    public void setCapacidad(int capacidad) {
        if (capacidad > this.capacidadMax) {
            this.capacidad = this.capacidadMax;
        } else if (capacidad < 0) {
            this.capacidad = 0;
        } else {
            this.capacidad = capacidad;
        }
    }

    /**
     * Obtenemos la capacidad máxima del almacen
     * @return capacidad máxima del almacen
     */
    public int getCapacidadMax() {
        return capacidadMax;
    }

    /**
     * Establecemos la capacidad máxima del almacen
     * @param capacidad máxima del almacen
     */
    public void setCapacidadMax(int capacidadMax) {
        if (capacidadMax < this.capacidad) {
            throw new IllegalArgumentException("Capacidad máxima no puede ser menor a la capacidad actual.");
        }
        this.capacidadMax = capacidadMax;
    }

    /**
     * Obtenemos el tipo de comida del almacén
     * @return tipo de comida (animal o vegetal)
     */
    public String getTipoComida() {
        return tipoComida;
    }

    /**
     * Aumentamos la capacidad máxima del almacen
     * @param cantidad a aumentar
     */
    public void aumentarCapacidad(int cantidad) {
        this.capacidadMax += cantidad;
    }

    /**
     * Agregamos comida al almacen
     * @param cantidad de comida a agregar
     */
    public void agregarComida(int cantidad) {
        if (capacidad + cantidad > capacidadMax) {
            capacidad = capacidadMax;
        } else {
            capacidad += cantidad;
        }
    }

    /**
     * Mejora el almacen aumentando la capacidad máxima
     * Para realizar la mejora se necesita 200 monedas
     */
    public void upgrade() {
        if (Monedas.getInstance().comprobarCompra(200)) {
            Monedas.getInstance().compra(200);
            this.aumentarCapacidad(50);
        } else {
            System.out.println("No tienes suficientes monedas");
        }
    }

    /**
     * Compramos una cantidad de comida para el almacen
     * @param cantidad de comida a comprar
     */
    public void comprarComida(int cantidad) {
        int coste = (cantidad <= 25) ? cantidad : cantidad - (cantidad / 25) * 5;

        if (Monedas.getInstance().comprobarCompra(coste)) {
            if (this.capacidad + cantidad > this.capacidadMax) {
                cantidad = this.capacidadMax - this.capacidad;
            }
            this.capacidad += cantidad;
            Monedas.getInstance().compra(coste);
            System.out.println("Has comprado " + cantidad + " de comida.");
        } else {
            System.out.println("No tienes suficientes monedas. El coste es: " + coste + ".");
        }
    }
}