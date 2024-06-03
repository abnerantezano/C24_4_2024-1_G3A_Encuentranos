package com.proyecto.encuentranos.modelos;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "chat")
public class ChatModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column( name = "id_chat")
    private int idChat;

    @Temporal(TemporalType.DATE)
	@Column( name = "fecha_creacion")
    private Date fechaCreacion;
    
    // Getters and Setters

	public int getIdChat() {
		return idChat;
	}

	public void setIdChat(int idChat) {
		this.idChat = idChat;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

}
