package com.tarea1.mallk.data;

/**
 * Created by javigm on 22/11/13.
 * Guarda los comentarios.
 * idComentario: autoincrement identificador comentario
 * idOrigen: identificador de tienda / foto / etc..
 * origen: indica la tabla referencia (Foto,Tienda,etc...)
 * comentario: Texto
 */
public class Comment {
    private int idComentario;
    private int idOrigen;
    private String origen;
    private String comentario;

    /*CONSTRUCTOR*/
    public Comment(int idComentario, int idOrigen, String origen, String comentario) {
        this.idComentario = idComentario;
        this.idOrigen = idOrigen;
        this.origen = origen;
        this.comentario = comentario;
    }

    public Comment(String comentario, String origen, int idOrigen) {
        this.comentario = comentario;
        this.origen = origen;
        this.idOrigen = idOrigen; //Referencia tienda/imagen
        this.idComentario = 0; //Autoincrement
    }

    public Comment(String comentario) {
        this.comentario = comentario;
        this.idComentario = 0; //Autoincrement
        this.idOrigen = 0; //Referencia tienda/imagen
        this.origen = "";
    }

    /*GETTER SETTER*/
    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public int getIdOrigen() {
        return idOrigen;
    }

    public void setIdOrigen(int idOrigen) {
        this.idOrigen = idOrigen;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /*TOSTRING*/
    @Override
    public String toString() {
        return "Comment{" +
                "idComentario=" + idComentario +
                ", idOrigen=" + idOrigen +
                ", origen='" + origen + '\'' +
                ", comentario='" + comentario + '\'' +
                '}';
    }
}
