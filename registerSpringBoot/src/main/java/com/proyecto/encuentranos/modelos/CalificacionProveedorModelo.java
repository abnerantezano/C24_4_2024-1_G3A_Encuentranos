package com.proyecto.encuentranos.modelos;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="calificacion_proveedor")
public class CalificacionProveedorModelo {

    @EmbeddedId
    private CalificacionProveedorPk id;
    
	@ManyToOne
	@JoinColumn(name = "id_proveedor")
	@MapsId("idProveedor")
	private UsuarioModelo idProveedor;
	
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	@MapsId("idCliente")
	private UsuarioModelo idCliente;
	
	@NotNull
	@Column(name = "calificacion")
	private double calificacion;
	
	@NotEmpty
	@Column(name = "comentario")
	private String comentario;

	@Column(name= "fecha_calificacion")
	private LocalDate fechaCalificacion;

	public CalificacionProveedorPk getId() {
		return id;
	}

	public void setId(CalificacionProveedorPk id) {
		this.id = id;
	}

	public UsuarioModelo getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(UsuarioModelo idProveedor) {
		this.idProveedor = idProveedor;
	}

	public UsuarioModelo getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(UsuarioModelo idCliente) {
		this.idCliente = idCliente;
	}

	public double getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(double calificacion) {
		this.calificacion = calificacion;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public LocalDate getFechaCalificacion() {
		return fechaCalificacion;
	}

	public void setFechaCalificacion(LocalDate fechaCalificacion) {
		this.fechaCalificacion = fechaCalificacion;
	}
	
	
}
