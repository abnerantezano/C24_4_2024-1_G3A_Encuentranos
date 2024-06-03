package com.proyecto.encuentranos.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
//CREANDO EL MODELO distrito
@Entity
@Table(name="distrito")
public class DistritoModelo {
	
	//ATRIBUTOS
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column( name = "id_distrito")
    private int idDistrito;

	@Column( name = "nombre")
    private String nombre;
    
    // Getters and Setters

	public int getIdDistrito() {
		return idDistrito;
	}

	public void setIdDistrito(int idDistrito) {
		this.idDistrito = idDistrito;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
    
}
