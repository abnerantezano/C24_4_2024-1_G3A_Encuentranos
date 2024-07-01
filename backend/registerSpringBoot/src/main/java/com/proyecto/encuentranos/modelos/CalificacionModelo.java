package com.proyecto.encuentranos.modelos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "calificacion")
public class CalificacionModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_calificacion")
    private int idCalificacion;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    private ClienteModelo cliente;

    @Column(name = "calificacion")
    private int calificacion;

    @Lob
    @Column(name = "comentario")
    private String comentario;

    @Column(name = "fh_creacion", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fhCreacion;
}
