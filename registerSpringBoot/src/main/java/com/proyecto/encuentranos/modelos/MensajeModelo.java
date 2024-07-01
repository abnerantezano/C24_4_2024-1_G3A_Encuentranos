package com.proyecto.encuentranos.modelos;

import com.google.type.DateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Date;

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

    @Column( name = "mensaje", nullable = false)
    private String mensaje;

    @Column(name = "fh_creacion")
    @CreatedDate
    private LocalDateTime fechaCreacion;
}
