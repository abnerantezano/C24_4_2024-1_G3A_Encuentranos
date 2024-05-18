package com.proyecto.encuentranos.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

//ESTE ES EL ID EMBEBIDO DE CALIFICACIONPROVEEDORMODELO
@Embeddable
public class CalificacionProveedorPk {
	
	//ATRIBUTOS
    @Column(name = "id_proveedor")
    private Integer idProveedor;
    
    @Column(name = "id_cliente")
    private Integer idCliente;
    
    //CONSTRUCTORES, GETTERS Y SETTERS
    
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
