package com.sanus.sanus.Data;

/**
 * Created by Mireya on 28/02/2018.
 */

public class DatosDoctor {

    String nombre, especialidad, cv, cedula;
    String avatar;

    public DatosDoctor(String nombre, String especialidad, String cv, String cedula, String avatar) {
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.cv = cv;
        this.cedula = cedula;
        this.avatar = avatar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getAvatar() {return avatar;}

    public void setAvatar(String avatar) {this.avatar = avatar;}

}
