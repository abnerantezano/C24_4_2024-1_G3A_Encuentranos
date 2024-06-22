package com.proyecto.encuentranos.modelos;

import com.google.type.DateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.sql.Time;

@Setter
@Getter
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

    @Temporal(TemporalType.DATE)
    @Column(name = "fh_creacion")
    private Date fh_creacion;
}
