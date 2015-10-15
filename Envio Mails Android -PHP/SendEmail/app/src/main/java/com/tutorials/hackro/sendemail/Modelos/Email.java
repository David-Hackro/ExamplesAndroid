package com.tutorials.hackro.sendemail.Modelos;

/**
 * Created by hackro on 14/10/15.
 */
public class Email {

    private String emisor,receptor,asunto,mensaje;

    public Email(String emisor, String receptor, String asunto, String mensaje) {
        this.emisor = emisor;
        this.receptor = receptor;
        this.asunto = asunto;
        this.mensaje = mensaje;
    }

    public Email() {
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
