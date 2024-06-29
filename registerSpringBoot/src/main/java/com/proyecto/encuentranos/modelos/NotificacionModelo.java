package com.proyecto.encuentranos.modelos;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notificacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion")
    private int idNotificacion;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente", insertable = false, updatable = false)
    private ClienteModelo idCliente;

    @ManyToOne
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id_proveedor", insertable = false, updatable = false)
    private ProveedorModelo idProveedor;

    @ManyToOne
    @JoinColumn(name = "id_contrato", referencedColumnName = "id_contrato", insertable = false, updatable = false)
    private ContratoModelo idContrato;

    @Column(name = "tipo", length = 50)
    private String tipo;

    @Column(name = "mensaje", columnDefinition = "TEXT")
    private String mensaje;

    @Column(name = "estado", length = 50)
    private String estado;

    @Column(name = "fh_creacion", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fhCreacion;
}