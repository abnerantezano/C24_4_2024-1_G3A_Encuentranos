package com.proyecto.encuentranos.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name="usuario")
public class UsuarioModelo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column( name = "id_usuario")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_tipo", referencedColumnName = "id_tipo")
	private TipoUsuarioModelo idTipo;
	
	@NotEmpty
	@Column(name = "correo")
	private String correo;
	
	@NotEmpty
	@Column(name = "contrasena")
	private String contrasena;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoUsuarioModelo getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(TipoUsuarioModelo idTipo) {
		this.idTipo = idTipo;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	
}
