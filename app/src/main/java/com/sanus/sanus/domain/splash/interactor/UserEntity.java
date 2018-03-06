package com.sanus.sanus.domain.splash.interactor;



public class UserEntity {

    public String apellido;
    public String avatar;
    public String completo;
    public String edad;
    public String nombre;
    public String sexo;
    public String tipo;

    public UserEntity() {
    }

    public UserEntity(String apellido, String avatar, String completo, String edad, String nombre, String sexo, String tipo) {
        this.apellido = apellido;
        this.avatar = avatar;
        this.completo = completo;
        this.edad = edad;
        this.nombre = nombre;
        this.sexo = sexo;
        this.tipo = tipo;
    }
}


