package com.proyecto.encuentranos.modelos;

import jakarta.persistence.*;
import java.util.Date;
import java.sql.Time;

@Entity
@Table(name = "mensaje")
public class MensajeModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMensaje;

    @ManyToOne
    @JoinColumn(name = "id_emisor", nullable = false)
    private UsuarioModelo idEmisor;

    @ManyToOne
    @JoinColumn(name = "id_receptor", nullable = false)
    private UsuarioModelo idReceptor;

    @ManyToOne
    @JoinColumn(name = "id_chat", nullable = false)
    private ChatModelo idChat;

    @Column(nullable = false)
    private String mensaje;

    @Column(name = "fecha_envio", nullable = false)
    private Date fechaEnvio;

    @Column(name = "hora_envio", nullable = false)
    private Time horaEnvio;

    // Getters and Setters

    public int getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(int idMensaje) {
        this.idMensaje = idMensaje;
    }

    public UsuarioModelo getIdEmisor() {
        return idEmisor;
    }

    public void setIdEmisor(UsuarioModelo idEmisor) {
        this.idEmisor = idEmisor;
    }

    public UsuarioModelo getIdReceptor() {
        return idReceptor;
    }

    public void setIdReceptor(UsuarioModelo idReceptor) {
        this.idReceptor = idReceptor;
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

    public Time getHoraEnvio() {
        return horaEnvio;
    }

    public void setHoraEnvio(Time horaEnvio) {
        this.horaEnvio = horaEnvio;
    }
}
