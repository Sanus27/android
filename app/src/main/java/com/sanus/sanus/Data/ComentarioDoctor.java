package com.sanus.sanus.Data;

/**
 * Created by Mireya on 28/02/2018.
 */

public class ComentarioDoctor {
    String usuario;
    String cometario;
    String fecha;
    String calificacion;

    public ComentarioDoctor(String usuario, String cometario, String fecha, String calificacion) {
        this.usuario = usuario;
        this.cometario = cometario;
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCometario() {
        return cometario;
    }

    public void setCometario(String cometario) {
        this.cometario = cometario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCalificacion() {return calificacion;}

    public void setCalificacion(String calificacion) {this.calificacion = calificacion;}

}
