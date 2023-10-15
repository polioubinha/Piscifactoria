package peces;

import java.util.Random;

public class Pez<T> {
    private String nombreComun;
    private String nombreCientifico;
    private int edad = 0;
    private boolean sexo = false;
    private boolean fertil = false;
    private boolean vivo = true;
    private boolean alimentado = true;

    public Pez(String nombreComun, String nombreCientifico, int edad, boolean sexo, boolean fertil, boolean vivo, boolean alimentado) {
        this.nombreComun = nombreComun;
        this.nombreCientifico = nombreCientifico;
        this.edad = edad;
        this.sexo = sexo;
        this.fertil = fertil;
        this.vivo = true;
        this.alimentado = false;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public boolean isSexo() {
        return sexo;
    }

    public void setSexo(boolean sexo) {
        this.sexo = sexo;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public boolean isFertil() {
        return fertil;
    }

    public void setFertil(boolean fertil) {
        this.fertil = fertil;
    }
    
    public void showStatus() {
        System.out.println("--------------- " + nombreComun + " ---------------");
        System.out.println("--------------- " + nombreCientifico + " ---------------");
        System.out.println("Edad: " + edad + " días");
        System.out.println("Sexo: " + sexo);
        System.out.println("Vivo: " + (vivo ? "Si" : "No"));
        System.out.println("Alimentado: " + (alimentado ? "Si" : "No"));
        System.out.println("Fértil: " + (fertil ? "Si" : "No"));
    }

    public boolean alimentacion(){
        Random alimentar = new Random();
        if(alimentar.nextBoolean()){
            return true;
        }else{
            return false;
        }
    }

    public void grow(){
        Random muerte = new Random();
        if(this.alimentacion()){
            if(muerte.nextBoolean()){
                this.vivo=false;
            }
        }
        if(this.vivo==true){
            this.edad++;
        }
    }

    public void reset(){
        this.edad = 0;
        this.fertil=false;
        this.vivo=true;
        this.sexo=false;
    }

}
