package org.wolfd4rk.model;

import java.io.Serializable;
import java.util.Objects;

public class Emote implements Serializable {

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

    @Override
    public String toString() {
        return "Emote{" +
                "nombre='" + nombre + '\'' +
                ", urlImagen='" + urlImagen + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emote emote = (Emote) o;
        return nombre.equals(emote.nombre) && urlImagen.equals(emote.urlImagen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, urlImagen);
    }
}
