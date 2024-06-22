package com.proyecto.encuentranos.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
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

}
