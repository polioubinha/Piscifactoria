package monedero;

public class Banco {
    private int monedas;
    
    public Banco(int monedas) {
        this.monedas = monedas;
    }

    public int getMonedas() {
        return monedas;
    }

    public void setMonedas(int monedas) {
        this.monedas = monedas;
    }

    /**
     * Comprobación de si tiene dinero para hacer compra o no
     * @param cantidad coste de una acción a realizar
     * @return si tiene las suficientes monedas para realizar la compra
     */
    public boolean comprobarMonedero(int cantidad){
        if(this.monedas >= cantidad){
            return true;
        }
        return false;
    }       
}
