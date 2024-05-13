package com.proyecto.encuentranos.modelos;

import jakarta.persistence.Column;

public class CalificacionProveedorPk {
	
    @Column(name = "id_proveedor")
    private Integer idProveedor;
    
    @Column(name = "id_cliente")
    private Integer idCliente;
    
    public CalificacionProveedorPk() {
    	
    }

	public CalificacionProveedorPk(Integer idProveedor, Integer idCliente) {
		this.idProveedor = idProveedor;
		this.idCliente = idCliente;
	}

	public Integer getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(Integer idProveedor) {
		this.idProveedor = idProveedor;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
	
}
