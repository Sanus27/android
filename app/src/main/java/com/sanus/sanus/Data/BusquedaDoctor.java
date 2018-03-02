package com.sanus.sanus.Data;

/**
 * Created by Mireya on 09/02/2018.
 */

public class BusquedaDoctor {
    //String nombre;
    String especialidad;


    String avatar;

    public BusquedaDoctor (String especialidad, String avatar){
        //this.nombre = nombre;
        this.especialidad = especialidad;
        this.avatar = avatar;
    }

    /*public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }*/

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }



}
