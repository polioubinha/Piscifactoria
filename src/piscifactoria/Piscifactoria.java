package piscifactoria;

import tanque.Tanque;

public class Piscifactoria {
    private final boolean rio;
    private String nombre = "";
    private int almacen;
    private int alamacenMax;
    private ArrayList<Tanque<Pez>> tanques = new ArrayList<>();

    /*
     * Constructor de la piscifactoria
     * 
     * @param rio true si es un rio, false si es de mar
     * @param nombre nombre de la piscifactoria
     */
    public Piscifactoria(boolean rio, String nombre) {
        this.rio = rio;
        if(this.rio){
            this.nombre = nombre;
            this.tanques.add(new Tanque<Pez>(25));
            this.almacen = 25;
            this.alamacenMax = 25;
        }else{
            this.nombre = nombre;
            this.tanques.add(new Tanque<Pez>(100));
            this.almacen = 100;
            this.alamacenMax = 100;
        }
    }

    public boolean isRio() {
        return rio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAlmacen() {
        return almacen;
    }

    public void setAlmacen(int almacen) {
        this.almacen = almacen;
    }

    public int getAlamacenMax() {
        return alamacenMax;
    }

    public void setAlamacenMax(int alamacenMax) {
        this.alamacenMax = alamacenMax;
    }

    public ArrayList<Tanque<Pez>> getTanques() {
        return tanques;
    }

    public void setTanques(ArrayList<Tanque<Pez>> tanques) {
        this.tanques = tanques;
    }

    public void añadirTanque(){
        if (this.rio) {
            this.tanques.add(new Tanque<Pez>(25));
        }else{
            this.tanques.add(new Tanque<Pez>(100));
        }
    }

    public void showStatus(){
        System.out.println("===============" + this.nombre + "===============");
        System.out.println("Tanques: " + this.tanques.size());
        System.out.println("Ocupacion: " + this.pecesTotales() + "/" + this.capacidadTotal() + " (" + this.porcentaje(this.pecesTotales(), this.capacidadTotal()) + "%)");
    }

    public int capacidadTotal(){
        int cantidad = 0;

        for(Tanque<Pez> tanque : tanques){
            cantidad += tanque.getCapacidad();
        }

        return cantidad;
    }
}
