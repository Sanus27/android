package com.sanus.sanus.Data;

/**
 * Created by Mireya on 10/02/2018.
 */

public class Citas {
    String doctor;
    String fecha;
    String hospital;
    String hora;

    public Citas(String doctor, String fecha, String hospital, String hora) {
        this.doctor = doctor;
        this.fecha = fecha;
        this.hospital = hospital;
        this.hora = hora;
    }
    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }


}
