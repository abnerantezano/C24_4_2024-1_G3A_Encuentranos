package com.proyecto.encuentranos.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

//ESTE ES EL ID EMBEBIDO DE SALAUSUARIOMODELO
@Embeddable
public class SalaUsuarioPk {

	//ATRIBUTOS
	
    @Column(name = "id_usuario")
    private Integer idUsuario;
    
    @Column(name = "id_sala")
    private Integer idSala;

    //CONSTRUCTORES, GETTERS Y SETTERS
    
	public SalaUsuarioPk(Integer idUsuario, Integer idSala) {
		super();
		this.idUsuario = idUsuario;
		this.idSala = idSala;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdSala() {
		return idSala;
	}

	public void setIdSala(Integer idSala) {
		this.idSala = idSala;
	}
    
}
