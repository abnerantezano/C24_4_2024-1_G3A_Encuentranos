package com.proyecto.encuentranos.modelos;

import java.util.Date;

import com.google.type.DateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    private int numero;

    @Lob
    @Column(name = "comentario")
    private String comentario;

    @Column(name = "fh_creacion")
    @Temporal(TemporalType.DATE)
    private DateTime fh_creacion;
}
