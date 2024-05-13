package com.proyecto.encuentranos.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
@Embeddable
public class ContratoPk {

    @Column(name = "id_proveedor")
    private Integer idProveedor;
    
    @Column(name = "id_servicio")
    private Integer idServicio;

    @Column(name = "id_cliente")
    private Integer idCliente;
    
    public ContratoPk() {
    	
    }

	public ContratoPk(Integer idProveedor, Integer idServicio, Integer idCliente) {
		this.idProveedor = idProveedor;
		this.idServicio = idServicio;
		this.idCliente = idCliente;
	}

	public Integer getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(Integer idProveedor) {
		this.idProveedor = idProveedor;
	}

	public Integer getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(Integer idServicio) {
		this.idServicio = idServicio;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
    
}
