package com.sanus.sanus.Data;

/**
 * Created by Mireya on 09/02/2018.
 */

public class BusquedaDoctor{
    String nombre;
    String especialidad;

    public BusquedaDoctor (String nombre, String especialidad){
        this.nombre = nombre;
        this.especialidad = especialidad;
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

}
