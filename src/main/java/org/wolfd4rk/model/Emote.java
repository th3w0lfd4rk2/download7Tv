package org.wolfd4rk.model;

public class Emote {

    public Emote() {
    }

    public Emote(String nombre, String urlImagen) {
        this.nombre = nombre;
        this.urlImagen = urlImagen;
    }

    private String nombre;

    private String urlImagen;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }


}
