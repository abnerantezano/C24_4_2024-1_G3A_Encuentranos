package com.proyecto.encuentranos.modelos;

import java.io.Serializable;
import java.util.Objects;

public class ServicioProveedorModeloId implements Serializable {
	private int idServicio;
    private int idProveedor;
	public ServicioProveedorModeloId(int idServicio, int idProveedor) {
		super();
		this.idServicio = idServicio;
		this.idProveedor = idProveedor;
	}
	public int getIdServicio() {
		return idServicio;
	}
	public void setIdServicio(int idServicio) {
		this.idServicio = idServicio;
	}
	public int getIdProveedor() {
		return idProveedor;
	}
	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}
    
    

    // Override equals and hashCode methods
}
