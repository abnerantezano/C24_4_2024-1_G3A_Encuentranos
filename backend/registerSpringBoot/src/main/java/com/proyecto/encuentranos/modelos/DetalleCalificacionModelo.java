package com.proyecto.encuentranos.modelos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "detalle_calificacion")
public class DetalleCalificacionModelo {

    @EmbeddedId
    private DetalleCalificacionModeloId id;

    @ManyToOne
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id_proveedor", insertable = false, updatable = false)
    private ProveedorModelo idProveedor;

    @ManyToOne
    @JoinColumn(name = "id_servicio", referencedColumnName = "id_servicio", insertable = false, updatable = false)
    private ServicioModelo idServicio;

    @ManyToOne
    @JoinColumn(name = "id_calificacion", referencedColumnName = "id_calificacion", insertable = false, updatable = false)
    private CalificacionModelo idCalificacion;

    public void setEmbeddedId(DetalleCalificacionModeloId id) {
        this.id = id;
        this.idProveedor = new ProveedorModelo();
        this.idProveedor.setIdProveedor(id.getIdProveedor());
        this.idServicio = new ServicioModelo();
        this.idServicio.setIdServicio(id.getIdServicio());
        this.idCalificacion = new CalificacionModelo();
        this.idCalificacion.setIdCalificacion(id.getIdCalificacion());
    }
}
