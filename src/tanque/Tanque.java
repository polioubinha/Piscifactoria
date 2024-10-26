package tanque;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import monedero.Monedas;
import peces.Pez;
import piscifactoria.Piscifactoria;
import stats.Stats;

public class Tanque<T extends Pez> {
    ArrayList<Pez> peces = new ArrayList<>();
    int capacidad;
    ArrayList<Integer> pecesMuertos = new ArrayList<>();
    int vendidos = 0;
    int muertos = 0;
    int ganancias = 0;

    public Tanque(int capacidad){
        this.capacidad = capacidad;
    }

    public int getCapacidad(){
        return capacidad;
    }

    public int getGanancias(){
        return ganancias;
    }

    public int getVendidos(){
        return vendidos;
    }

    public ArrayList<Pez> getPeces(){
        return peces;
    }

    /*
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

    /*
     * Estado de cada pez en el tanque
     */
    public void showFishStatus(){
        for(Pez pez : peces){
            pez.showStatus();
        }
    }

    /**
     * Calcula el porcentaje en función de dos números enteros.
     *
     * @param numero1 El primer número.
     * @param numero2 El segundo número (denominador).
     * @return El porcentaje calculado, redondeado a un decimal.
     */
    public double porcentaje(int numero1, int numero2) {
        if (numero2 == 0) {
            return 0.0; // Evitamos la división por cero
        }

        return Math.round(((double) numero1 / numero2) * 1000) / 10.0;
    }

    public int vivos() {
        int cantidad = 0;
        for (Pez pez : peces) {
            if (pez.isVivo()) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public int alimentados() {
        int cantidad = 0;
        for (Pez pez : peces) {
            if (pez.isAlimentado() && pez.isVivo()) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public int adultos() {
        int cantidad = 0;
        for (Pez pez : peces) {
            if (pez.isMaduro() && pez.isVivo()) {
                cantidad++;
            }
        }
        return cantidad;
    }

    /*
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

    /*
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

    /*
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

    /*
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

    /*
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

    public void nextFood(Piscifactoria piscifactoria, Boolean almacenCentral) {
        for(Pez pez :peces){
            if(pez.isVivo()){
                pez.grow(this, piscifactoria, almacenCentral);
            }
        }
    }

    public void comprarPez(){
        Pez nuevoPez = this.createNewInstance(this.peces.get(0).getClass());
        if(Monedas.getInstance().comprobarCompra(nuevoPez.getDatos().getCoste())){
            Monedas.getInstance().compra(nuevoPez.getDatos().getCoste());
            this.peces.add(nuevoPez);
        }else{
            System.out.println("No tienes monedas suficientes para realizar la compra.");
        }
    }

    /*
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
            e.printStackTrace();
            return null;
        }
    }

    public void nextDayReproduccion() {
        int capacidadDisponible = this.capacidad - this.peces.size();
        List<Pez> nuevosPeces = new ArrayList<>();

        for(Pez pez : peces){
            if(pez.isVivo() && capacidadDisponible > 0){
                if(pez.isMaduro() && pez.reproducirse(null)){
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

    /*
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

    /*
     * Venta de los peces adultos del tanque
     */
    public void venderAdultos(){
        Iterator<Pez> iterator = this.peces.iterator();
        this.vendidos = 0;
        this.ganancias = 0;

        while(iterator.hasNext()){
            Pez pez = iterator.next();
            if(pez.isMaduro() && pez.isVivo()){
                Monedas.getInstance().venta(pez.getDatos().getMonedas());
                this.vendidos++;
                this.ganancias += pez.getDatos().getMonedas();
                iterator.remove();
            }
        }
    }
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
    public void vaciarTanque() {
        this.peces.removeAll(peces);
    }
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
    public void nuevoDiaComer(Piscifactoria piscifactoria, Boolean almacenCentral) {
        for(Pez pez : peces){
            if(pez.isVivo()){
                pez.grow(null, piscifactoria, almacenCentral);
            }
        }
    }

}
