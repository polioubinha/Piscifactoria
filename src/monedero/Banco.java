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

    public boolean comprobarMonedero(int cantidad){
        if(this.monedas >= cantidad){
            monedas -= cantidad;
            System.out.println("Gastaste " + cantidad + " monedas y ahora tienes " + monedas + " monedas.");
            return true;
        }else{
            monedas += cantidad;
            System.out.println("Ganaste " + cantidad + " monedas y ahora tienes " + monedas + " monedas.");
            return false;
        }
    }       
}
