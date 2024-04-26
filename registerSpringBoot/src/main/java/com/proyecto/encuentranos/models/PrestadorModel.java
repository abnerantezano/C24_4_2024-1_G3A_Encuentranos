package com.proyecto.encuentranos.models;

import jakarta.persistence.*;

@Entity
@Table(name="prestadores")
public class PrestadorModel {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id_prestador")
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
	private UsuarioModel usuario;

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
	
	
}
