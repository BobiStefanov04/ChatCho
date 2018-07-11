package com.example.bobi.chatcho.ActividadUsuarios.Amigos;

/**
 * Created by Bobi on 20/06/2018.
 */

public class Amigo {

    private int fotoAmigo;
    private String nombre;
    private String mensaje;
    private String hora;
    private String id;

    public Amigo(){

    }

    public int getFotoAmigo() {
        return fotoAmigo;
    }

    public void setFotoAmigo(int fotoAmigo) {
        this.fotoAmigo = fotoAmigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
