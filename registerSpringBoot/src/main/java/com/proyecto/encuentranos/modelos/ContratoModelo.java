package com.proyecto.encuentranos.modelos;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
//CREANDO EL MODELO CONTRATO PARA LA TABLA contrato
@Entity
@Table(name="contrato")
public class ContratoModelo {
	
	//SE ESTA USANDO COMO ID UN ID EMBEBIDO
	//ESTE ID SE ESTA PONIENDO PORQUE NECESITAMOS QUE SEA UNA ENTIDAD LA TABLA contrato
	
	//ATRIBUTOS
	@EmbeddedId
    private ContratoPk id;
	
	@ManyToOne
	@JoinColumn(name = "id_proveedor")
	@MapsId("idProveedor")
	private ProveedorModelo idProveedor;
	
	@ManyToOne
	@JoinColumn(name = "id_servicio")
	@MapsId("idServicio")
	private ServicioModelo idServicio;
	
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	@MapsId("idCliente")
	private ClienteModelo idCliente;
	
	@Column(name= "fecha_inicio")
	private LocalDate fechaInicio;
	
	@Column(name= "fecha_fin")
	private LocalDate fechaFin;
	
	@Column(name = "activo")
	private boolean activo;

	//GETTERS Y SETTERS
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
