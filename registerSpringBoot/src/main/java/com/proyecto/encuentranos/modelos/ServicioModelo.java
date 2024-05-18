package com.proyecto.encuentranos.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
//CREANDO EL MODELO servicio
@Entity
@Table(name="servicio")
public class ServicioModelo {
	
	//ATRIBUTOS
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column( name = "id_servicio")
	private Integer id;
	
	@NotEmpty
	@Column(name = "nombre")
	private String nombre;
	
	@NotEmpty
	@Column(name = "descripcion")
	private String descripcion;

	//GETTERS Y SETTERS
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
