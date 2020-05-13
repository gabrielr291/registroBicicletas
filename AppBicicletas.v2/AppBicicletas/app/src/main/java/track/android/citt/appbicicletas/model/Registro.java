package track.android.citt.appbicicletas.model;

public class Registro {
    private String rutAlumno;
    private String nombreAlumno;
    private String horaLLegada;
    private String horaSalida;
    private String comentario;

    public Registro() {

    }

    public Registro(String rutAlumno, String nombreAlumno, String horaLLegada, String horaSalida, String comentario) {
        this.rutAlumno = rutAlumno;
        this.nombreAlumno = nombreAlumno;
        this.horaLLegada = horaLLegada;
        this.horaSalida = horaSalida;
        this.comentario = comentario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getRutAlumno() {
        return rutAlumno;
    }

    public void setRutAlumno(String rutAlumno) {
        this.rutAlumno = rutAlumno;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public String getHoraLLegada() {
        return horaLLegada;
    }

    public void setHoraLLegada(String horaLLegada) {
        this.horaLLegada = horaLLegada;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }
}
