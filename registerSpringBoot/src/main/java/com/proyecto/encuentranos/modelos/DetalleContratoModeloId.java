package com.proyecto.encuentranos.modelos;
import java.io.Serializable;
import java.util.Objects;

public class DetalleContratoModeloId implements Serializable {
    private int idProveedor;
    private int idServicio;
    private int idContrato;
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
	public int getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(int idContrato) {
		this.idContrato = idContrato;
	}
	public DetalleContratoModeloId(int idProveedor, int idServicio, int idContrato) {
		super();
		this.idProveedor = idProveedor;
		this.idServicio = idServicio;
		this.idContrato = idContrato;
	}

    
}
