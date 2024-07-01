package com.proyecto.encuentranos.modelos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "detalle_contrato")
public class DetalleContratoModelo {
	
    @EmbeddedId
    private DetalleContratoModeloId id;
    
    @ManyToOne
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id_proveedor", insertable = false, updatable = false)
    private ProveedorModelo idProveedor;

    @ManyToOne
    @JoinColumn(name = "id_servicio", referencedColumnName = "id_servicio", insertable = false, updatable = false)
    private ServicioModelo idServicio;

    @ManyToOne
    @JoinColumn(name = "id_contrato", referencedColumnName = "id_contrato", insertable = false, updatable = false)
    private ContratoModelo idContrato;

    @Column(name = "precio_actual")
    private double precioActual;

    @Temporal(TemporalType.DATE)
    @Column( name = "fecha_aprovacion")
    @CreatedDate
    private Date fechaAprobacion;

}
