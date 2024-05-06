package com.proyecto.encuentranos.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name="tipo_usuario")
public class TipoUsuarioModelo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column( name = "id_tipo")
	Long id;
	
	@NotEmpty
	@Column(name = "nombre")
	private String nombre;
	
    public TipoUsuarioModelo() {
    }
    
    public TipoUsuarioModelo(Long id) {
        this.id = id;
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public TipoUsuarioModelo orElse(Object object) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
