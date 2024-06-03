package com.proyecto.encuentranos.modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle_calificacion")
@IdClass(DetalleCalificacionModeloId.class)
public class DetalleCalificacionModelo {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id_proveedor")
    private ProveedorModelo idProveedor;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_servicio", referencedColumnName = "id_servicio")
    private ServicioModelo idServicio;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_calificacion", referencedColumnName = "id_calificacion")
    private CalificacionModelo idCalificacion;

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
