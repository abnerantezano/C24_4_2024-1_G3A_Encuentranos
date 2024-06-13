package com.proyecto.encuentranos.modelos;

import jakarta.persistence.*;

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

	public DetalleCalificacionModeloId getId() {
		return id;
	}

	public ProveedorModelo getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(ProveedorModelo idProveedor) {
		this.idProveedor = idProveedor;
	}

	public ServicioModelo getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(ServicioModelo idServicio) {
		this.idServicio = idServicio;
	}

	public CalificacionModelo getIdCalificacion() {
		return idCalificacion;
	}

	public void setIdCalificacion(CalificacionModelo idCalificacion) {
		this.idCalificacion = idCalificacion;
	}

	public void setId(DetalleCalificacionModeloId id) {
		this.idProveedor = new ProveedorModelo();
        this.idProveedor.setIdProveedor(id.getIdProveedor());
        this.idServicio = new ServicioModelo();
        this.idServicio.setIdServicio(id.getIdServicio());
        this.idCalificacion = new CalificacionModelo();
        this.idCalificacion.setIdCalificacion(id.getIdCalificacion());
	}

}
