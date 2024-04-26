package com.proyecto.encuentranos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="direcciones")
public class DireccionModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_direccion")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="id_usuario", referencedColumnName="id_usuario")
	private UsuarioModel usuario;
	
	@NotEmpty
	@Column(name = "region")
	private String region;
	
	@NotEmpty
	@Column(name = "provincia")
	private String provincia;
	
	@NotEmpty
	@Column(name = "distrito")
	private String distrito;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UsuarioModel getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioModel usuario) {
		this.usuario = usuario;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getDistrito() {
		return distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}
	
	

}
