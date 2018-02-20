package com.sanus.sanus.Data;

/**
 * Created by Mireya on 16/02/2018.
 */

public class ComentarioDoctor {
    String usuario;
    String cometario;
    String fecha;

    public ComentarioDoctor(String usuario, String cometario, String fecha) {
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





}
