package com.proyecto.encuentranos.modelos;

import java.io.Serializable;
import java.util.Objects;

public class DetalleCalificacionModeloId implements Serializable {
    private int idProveedor;
    private int idServicio;
    private int idCalificacion;
    
    
    
	public DetalleCalificacionModeloId(int idProveedor, int idServicio, int idCalificacion) {
		super();
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

    
    // Override equals and hashCode methods
}
