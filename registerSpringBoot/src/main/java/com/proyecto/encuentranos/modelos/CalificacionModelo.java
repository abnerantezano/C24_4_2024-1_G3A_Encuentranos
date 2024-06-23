package com.proyecto.encuentranos.modelos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Column(name = "numero")
    private Integer numero;

    @Lob
    @Column(name = "comentario")
    private String comentario;

    @Column(name = "fh_creacion")
    @Temporal(TemporalType.DATE)
    private Date fhCreacion;
}
