package com.tutorials.hackro.recyclerviewandcardview.Models;

/**
 * Created by hackro on 16/10/15.
 */
//Esta es mi clase modelo :3
public class Persona {

    private String nombre;
    private String usuario;
    private String twitter;

    public Persona(String nombre, String usuario, String twitter) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.twitter = twitter;
    }

    public Persona() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }
}
