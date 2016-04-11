package com.hackro.tutorials.retrofitrestheader;

/**
 * Created by david on 11/04/16.
 */

//Recuerda que este es un ejemplo,tu entidad debera cambiar dependiendo de tu response
public class Disponibilidad {

    private boolean ServicioDisponible;
    private boolean HorarioMantenimiento;
    private String Mensaje;

    public Disponibilidad(boolean servicioDisponible, boolean horarioMantenimiento, String mensaje) {
        ServicioDisponible = servicioDisponible;
        HorarioMantenimiento = horarioMantenimiento;
        Mensaje = mensaje;
    }

    public Disponibilidad() {
    }

    public boolean isServicioDisponible() {
        return ServicioDisponible;
    }

    public void setServicioDisponible(boolean servicioDisponible) {
        ServicioDisponible = servicioDisponible;
    }

    public boolean isHorarioMantenimiento() {
        return HorarioMantenimiento;
    }

    public void setHorarioMantenimiento(boolean horarioMantenimiento) {
        HorarioMantenimiento = horarioMantenimiento;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }
}
