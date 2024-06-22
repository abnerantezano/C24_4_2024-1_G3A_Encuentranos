package com.proyecto.encuentranos.modelos;

import java.sql.Time;
import java.util.Date;

import com.google.type.DateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "contrato")
public class ContratoModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contrato")
    private int idContrato;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    private ClienteModelo idCliente;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_fin")
    private Date fechaFin;

    @Column(name = "estado")
    private String estado;

    @Column(name = "precio_final")
    private Double precioFinal;  // Cambiado a Double

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "hi_servicio")
    private Date hiServicio;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "hf_servicio")
    private Date hfServicio;

    @Temporal(TemporalType.DATE)
    @Column(name = "fh_creacion")
    private Date fh_creacion;
}
