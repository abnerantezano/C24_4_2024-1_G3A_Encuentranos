package com.proyecto.encuentranos.modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "chat_usuario")
@IdClass(ChatUsuarioModeloId.class)
public class ChatUsuarioModelo {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private UsuarioModelo idUsuario;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_chat", referencedColumnName = "id_chat")
    private ChatModelo idChat;

	public UsuarioModelo getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(UsuarioModelo idUsuario) {
		this.idUsuario = idUsuario;
	}

	public ChatModelo getIdChat() {
		return idChat;
	}

	public void setIdChat(ChatModelo idChat) {
		this.idChat = idChat;
	}

    
}
