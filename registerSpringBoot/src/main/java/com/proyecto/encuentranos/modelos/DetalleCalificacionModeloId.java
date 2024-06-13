package com.proyecto.encuentranos.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class DetalleCalificacionModeloId implements Serializable {
	@Column(name = "id_proveedor")
	private int idProveedor;

	@Column(name = "id_servicio")
	private int idServicio;

	@Column(name = "id_calificacion")
	private int idCalificacion;

	public DetalleCalificacionModeloId() {}

	public DetalleCalificacionModeloId(int idProveedor, int idServicio, int idCalificacion) {
		this.idProveedor = idProveedor;
		this.idServicio = idServicio;
		this.idCalificacion = idCalificacion;
	}
	public int getIdProveedor() {
		return idProveedor;
	}
	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}
	public int getIdServicio() {
		return idServicio;
	}
	public void setIdServicio(int idServicio) {
		this.idServicio = idServicio;
	}
	public int getIdCalificacion() {
		return idCalificacion;
	}
	public void setIdCalificacion(int idCalificacion) {
		this.idCalificacion = idCalificacion;
	}
}
