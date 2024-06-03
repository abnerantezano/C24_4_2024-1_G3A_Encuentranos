package com.proyecto.encuentranos.modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "servicio")
public class ServicioModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column( name = "id_servicio")
    private int idServicio;

	@Column( name = "nombre")
    private String nombre;

    @Lob
	@Column( name = "descripcion")
    private String descripcion;

    // Getters and Setters

	public int getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(int idServicio) {
		this.idServicio = idServicio;
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
