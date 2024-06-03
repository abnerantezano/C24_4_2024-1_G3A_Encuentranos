package com.proyecto.encuentranos.modelos;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "mensaje")
public class MensajeModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensaje")
    private int idMensaje;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private UsuarioModelo idUsuario;

    @ManyToOne
    @JoinColumn(name = "id_chat", referencedColumnName = "id_chat")
    private ChatModelo idChat;

    @Lob
    @Column(name = "mensaje")
    private String mensaje;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_envio")
    private Date fechaEnvio;

    @Temporal(TemporalType.TIME)
    @Column(name = "hora_envio")
    private Date horaEnvio;

	public int getIdMensaje() {
		return idMensaje;
	}

	public void setIdMensaje(int idMensaje) {
		this.idMensaje = idMensaje;
	}

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

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public Date getHoraEnvio() {
		return horaEnvio;
	}

	public void setHoraEnvio(Date horaEnvio) {
		this.horaEnvio = horaEnvio;
	}
    

}

