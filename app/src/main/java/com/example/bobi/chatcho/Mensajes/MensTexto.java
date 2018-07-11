package com.example.bobi.chatcho.Mensajes;

/**
 * Created by Bobi on 08/12/2017.
 */

public class MensTexto {
    private String id;
    private String mensaje;
    private String horaMensaje;
    private int tipoMensaje; //es receptor o emisor 1 => Emisor 2 => Receptor

    public MensTexto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getHoraMensaje() {
        return horaMensaje;
    }

    public void setHoraMensaje(String horaMensaje) {
        this.horaMensaje = horaMensaje;
    }

    public int getTipoMensaje() {
        return tipoMensaje;
    }

    public void setTipoMensaje(int tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }
}
