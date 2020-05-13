package track.android.citt.appbicicletas.model;

public class Registro {
    private String alumno;
    private String entrada;
    private String salida;

    public Registro() {

    }

    public Registro(String alumno, String entrada, String salida) {
        this.alumno = alumno;
        this.entrada = entrada;
        this.salida = salida;
    }

    public String getAlumno() {
        return alumno;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
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

