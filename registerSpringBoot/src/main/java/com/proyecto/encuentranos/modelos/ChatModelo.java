package com.proyecto.encuentranos.modelos;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "chat")
public class ChatModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idChat;

    @Column(nullable = false)
    private boolean eliminado;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    // Getters and Setters

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
