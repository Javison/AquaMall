package com.tarea1.mallk.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by javigm on 10/11/13.
 */
public class Store implements Serializable{
    private int idStore;
    private String sector;
    private String nombre;
    private String descripcion;
    private String direccion;
    private String horario;
    private String telefono;
    private String webSite;
    private String email;
    private String gpsLocation;
    private String imgStore;
    private int favoritos;

    public Store(int idStore, String sector, String nombre, String descripcion, String direccion, String horario, String telefono, String webSite, String email, String gpsLocation, String imgStore, int favoritos) {
        this.idStore = idStore;
        this.sector = sector;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.horario = horario;
        this.telefono = telefono;
        this.webSite = webSite;
        this.email = email;
        this.gpsLocation = gpsLocation;
        this.imgStore = imgStore;
        this.favoritos = favoritos;
    }

    public int getIdStore() {
        return idStore;
    }

    public void setIdStore(int idStore) {
        this.idStore = idStore;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGpsLocation() {
        return gpsLocation;
    }

    public void setGpsLocation(String gpsLocation) {
        this.gpsLocation = gpsLocation;
    }

    public String getImgStore() {
        return imgStore;
    }

    public void setImgStore(String imgStore) {
        this.imgStore = imgStore;
    }

    public int getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(int favoritos) {
        this.favoritos = favoritos;
    }


    @Override
    public String toString() {
        return "Store{" +
                "idStore=" + idStore +
                ", sector='" + sector + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", direccion='" + direccion + '\'' +
                ", horario='" + horario + '\'' +
                ", telefono='" + telefono + '\'' +
                ", webSite='" + webSite + '\'' +
                ", email='" + email + '\'' +
                ", gpsLocation='" + gpsLocation + '\'' +
                ", imgStore='" + imgStore + '\'' +
                ", favoritos=" + favoritos +
                '}';
    }

}
