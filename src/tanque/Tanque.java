package tanque;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import monedero.Monedas;
import peces.Pez;
import piscifactoria.Piscifactoria;
import stats.Stats;

/** Clase que gestiona los tanques de la piscifactoría */
public class Tanque extends Pez{
    /** Peces del tanque */
    private ArrayList<Pez> peces = new ArrayList<>();
    /** Capacidad del tanque */
    private int capacidad;
    /** Peces muertos */
    private ArrayList<Integer> pecesMuertos = new ArrayList<>();
    /** Cantidad de peces vendidos */
    private int vendidos = 0;
    /** Cantidad de peces muertos */
    private int muertos = 0;
    /** Cantidad de ganancias */
    private int ganancias = 0;
    /*Si es de Rio o de Mar */
    private boolean esDeRio;

   /**
     * Constructor de la clase tanque
     * @param capacidad capacidad del tanque
     * @param esDeRio indica si el tanque es de río
     */
    public Tanque(int capacidad, boolean esDeRio) {
        this.capacidad = capacidad;
        this.esDeRio = esDeRio;
    }

    /**
     * Devuelve la capacidad del tanque
     * @return capacidad del tanque 
     */
    public int getCapacidad(){
        return capacidad;
    }

    /**
     * Devuelve las ganancias del tanque 
     * @return ganancias del tanque
     */
    public int getGanancias(){
        return ganancias;
    }

    /**
     * Devuelve la cantidad de peces vendidos
     * @return cantidad de peces vendidos
     */
    public int getVendidos(){
        return vendidos;
    }
 /**
     * Indica si el tanque es de río
     * @return true si es de río, false si es de mar
     */
    public boolean esDeRio() {
        return esDeRio;
    }
   
    /**
     * Devuelve los peces del tanque 
     * @return peces del tanque 
     */
    public ArrayList<Pez> getPeces(){
        return peces;
    }

    /**
     * Método para mostrar el estado del tanque
     */
    public void showStatus() {
        System.out.println("Ocupación: " + this.peces.size() + "/" + this.capacidad + " ("
                + this.porcentaje(this.peces.size(), this.capacidad) + "%)");
        System.out.println("Peces vivos: " + this.vivos() + "/" + this.peces.size() + " ("
                + this.porcentaje(this.vivos(), this.peces.size()) + "%)");
        System.out.println("Peces alimentados: " + this.alimentados() + "/" + this.vivos() + " ("
                + this.porcentaje(this.alimentados(), this.vivos()) + "%)");
        System.out.println("Peces adultos: " + this.adultos() + "/" + this.vivos() + " ("
                + this.porcentaje(this.adultos(), this.vivos()) + "%)");
        System.out.println("Hembras/Machos: " + this.hembras() + "/" + this.machos());
    }

    /**
     * Estado de cada pez en el tanque
     */
    public void showFishStatus(){
        for(Pez pez : peces){
            pez.showStatus();
        }
    }

    /**
     * Calcula un porcentaje 
     * @param cantidad 
     * @param total 
     * @return porcentaje calculado
     */
    public double porcentaje(int cantidad, int total) {
        if (total == 0) {
            return 0.0;
        }
        double porcentaje = (double) cantidad / total * 100;
        porcentaje = Math.round(porcentaje * 100.0) / 100.0;
        
        return porcentaje;
    }

    /**
     * Devuelve numero de peces vivos
     * @return cantidad de peces vivos
     */
    public int vivos() {
        int cantidad = 0;
        for (Pez pez : peces) {
            if (pez.isVivo()) {
                cantidad++;
            }
        }
        return cantidad;
    }

    /**
     * Devuelve el numero de peces alimentados
     * @return cantidad de peces alimentados
     */
    public int alimentados() {
        int cantidad = 0;
        for (Pez pez : peces) {
            if (pez.isAlimentado() && pez.isVivo()) {
                cantidad++;
            }
        }
        return cantidad;
    }

    /**
     * Devuelve el número de peces adultos
     * @return cantidad de peces adultos
     */
    public int adultos() {
        int cantidad = 0;
        for (Pez pez : peces) {
            if (pez.isMaduro() && pez.isVivo()) {
                cantidad++;
            }
        }
        return cantidad;
    }

    /**
     * Verifica si hay peces muestos en el tanque
     * 
     * @return true si hay peces muertos, false si no los hay
     */
    public boolean hasDead(){
        if(muertos != 0){
            this.pecesMuertos.removeAll(pecesMuertos);
        }

        for(int i = 0; i < peces.size(); i++){
            if(!peces.get(i).isVivo()){
                pecesMuertos.add(i);
            }
        }

        if (this.pecesMuertos != null){
            return !pecesMuertos.isEmpty();
        }else{
            return false;
        }
    }

    /**
     * Método para eliminar un pez muerto
     * 
     * @return true si se ha eliminado un pez, false si no
     */
    public boolean eliminarMuerto() {
        for (Iterator<Pez> it = peces.iterator(); it.hasNext();) {
            if (!it.next().isVivo()) {
                it.remove();
                return true;
            }
        }
        return false;
    }    

    /**
     * Retorna la cantidad de machos del tanque
     * 
     * @return Cantidad de machos vivos en el tanque
     */
    public int machos(){
        int machos = 0;

        for(Pez pez : peces){
            if(pez.isSexo() && pez.isVivo()){
                machos++;
            }
        }
        return machos;
    }

    /**
     * Retorna la cantidad de hembras del tanque
     * 
     * @return Cantidad de hembras vivas en el tanque
     */
    public int hembras(){
        int hembras = 0;

        for(Pez pez: peces){
            if(!pez.isSexo() && pez.isVivo()){
                hembras++;
            }
        }

        return hembras;
    }

    /**
     * Calcula el sexo del nuevo pez en relación a la cantidad del tanque
     * 
     * @return true si se tiene que crear un macho, false si tiene que ser hembra
     */
    public boolean sexoNuevoPez(){
        int machos = this.machos();
        int hembras = this.hembras();

        if(machos == 0 && hembras == 0){
            return true;
        }

        return machos < hembras;
    }

    /**
     * Hace crecer al pez si está vivo
     * @param piscifactoria
     * @param almacenCentral
     */
    public void nextFood(Piscifactoria piscifactoria, Boolean almacenCentral) {
        for(Pez pez :peces){
            if(pez.isVivo()){
                pez.grow(this, piscifactoria, almacenCentral);
            }
        }
    }

    /**
     * Realiza la compra de un pez
     */
    public void comprarPez(){
        Pez nuevoPez = this.createNewInstance(this.peces.get(0).getClass());
        if(Monedas.getInstance().comprobarCompra(nuevoPez.getDatos().getCoste())){
            Monedas.getInstance().compra(nuevoPez.getDatos().getCoste());
            this.peces.add(nuevoPez);
        }else{
            System.out.println("No tienes monedas suficientes para realizar la compra.");
        }
    }

    /**
     * Se crea una instancia de un pez
     * 
     * @param tipoPez Clase del pez a crear
     * @return La instancia del pez indicado
     */
    public Pez createNewInstance(Class<? extends Pez> tipoPez){
        try {
            Constructor<? extends Pez> contructor = tipoPez.getDeclaredConstructor(boolean.class);
            return contructor.newInstance(this.sexoNuevoPez());
        } catch (Exception e) {
            System.out.println("Error al generar un nuevo pez");
            return null;
        }
    }

    /**
     * Realiza la logica de la reproduccion de los peces
     */
    public void nextDayReproduccion() {
        int capacidadDisponible = this.capacidad - this.peces.size();
        List<Pez> nuevosPeces = new ArrayList<>();

        for(Pez pez : peces){
            if(pez.isVivo() && capacidadDisponible > 0){
                if(pez.reproducirse()){
                    int huevos = pez.getDatos().getHuevos();

                    if(huevos <= capacidadDisponible){
                        for(int i= 0; i < huevos; i++){
                            Pez nuevoPez = this.createNewInstance(pez.getClass());
                            nuevosPeces.add(nuevoPez);
                            Stats.getInstancia().registrarNacimiento(nuevoPez.getDatos().getNombre());
                            capacidadDisponible++;
                        }
                    }else{
                        for(int i = 0; i< capacidadDisponible;i++){
                            Pez nPez = this.createNewInstance(pez.getClass());
                            nuevosPeces.add(nPez);
                            Stats.getInstancia().registrarNacimiento(nPez.getDatos().getNombre());
                            capacidadDisponible--;
                        }
                    }
                }
            }
        }
        peces.addAll(nuevosPeces);
    }

    /**
     * Venta de peces optimos del tanque
     */
    public void venderOptimos() {
        Iterator<Pez> iterator = this.peces.iterator();
        this.vendidos = 0;
        this.ganancias = 0;

        while(iterator.hasNext()){
            Pez pez = iterator.next();
            if(pez.isOptimo() && pez.isVivo()){
                Monedas.getInstance().venta(pez.getDatos().getMonedas());
                this.vendidos++;
                this.ganancias += pez.getDatos().getMonedas();
                iterator.remove();
            }
        }
    }

    /**
     * Venta de los peces adultos del tanque
     */
    public void venderAdultos(){
        Iterator<Pez> iterator = this.peces.iterator();
        this.vendidos = 0;
        this.ganancias = 0;

        while(iterator.hasNext()){
            Pez pez = iterator.next();
            if(pez.isMaduro() && pez.isVivo()){
                Monedas.getInstance().venta(pez.getDatos().getMonedas()/2);
                this.vendidos++;
                this.ganancias += pez.getDatos().getMonedas();
                iterator.remove();
            }
        }
        System.out.println("Vendidos " + this.vendidos + " peces por ");
    }

    /**
     * Compra un pez
     * @param pez comprado
     */
    public void comprarPez(Pez pez){
        if(Monedas.getInstance().comprobarCompra(pez.getDatos().getCoste())){
            Monedas.getInstance().compra(pez.getDatos().getCoste());;
            this.peces.add(pez);
            System.out.println("- Se ha comprado " + pez.getDatos().getNombre() +
             "(" + pez.getSexo() + ") por " + pez.getDatos().getCoste() +
              " monedas y se ha añadido al tanque en la piscifactoria");
        }else{
            System.out.println("No se ha podido realizar la comprar, no tienes las suficientes monedas.");
        }
    }

    /**
     * Vacía un tanque 
     */
    public void vaciarTanque() {
        this.peces.removeAll(peces);
    }

    /**
     * Limpia el tanque
     */
    public void limpiarTanque() {
        if (this.hasDead()) {
            Iterator<Integer> iterator = pecesMuertos.iterator();
            while (iterator.hasNext()) {
                int muerto = iterator.next();
                this.peces.remove((int) muerto);
                iterator.remove();
            }
        }
    }

    /**
     * Realiza la logica de alimentación del nuevo día
     * @param piscifactoria
     * @param almacenCentral
     */
    public void nuevoDiaComer(Piscifactoria piscifactoria, Boolean almacenCentral) {
        for(Pez pez : peces){
            if(pez.isVivo()){
                pez.grow(null, piscifactoria, almacenCentral);
            }
        }
    }

}
