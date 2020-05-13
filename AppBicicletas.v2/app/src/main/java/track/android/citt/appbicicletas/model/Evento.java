package track.android.citt.appbicicletas.model;

import java.util.Date;

public class Evento {

    private String entrada;
    private String salida;

    public Evento() {

    }

    public Evento(String entrada, String salida) {
        this.entrada = entrada;
        this.salida = salida;
    }

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public String getSalida() {
        return salida;
    }

    public void setSalida(String salida) {
        this.salida = salida;
    }
}
