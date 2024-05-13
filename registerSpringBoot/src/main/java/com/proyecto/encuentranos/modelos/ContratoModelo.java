package com.proyecto.encuentranos.modelos;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name="contrato")
public class ContratoModelo {
	
	@ManyToOne
	@JoinColumn(name = "id_proveedor", referencedColumnName = "id_proveedor")
	private ProveedorModelo idProveedor;
	
	@ManyToOne
	@JoinColumn(name = "id_servicio", referencedColumnName = "id_servicio")
	private ServicioModelo idServicio;
	
	@ManyToOne
	@JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
	private ClienteModelo idCliente;
	
	@Column(name= "fecha_inicio")
	private LocalDate fechaInicio;
	
	@Column(name= "fecha_fin")
	private LocalDate fechaFin;
	
	@Column(name = "activo")
	private boolean activo;

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

	public ClienteModelo getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(ClienteModelo idCliente) {
		this.idCliente = idCliente;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	
}
