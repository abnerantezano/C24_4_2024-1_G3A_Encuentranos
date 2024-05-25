package com.proyecto.encuentranos.modelos;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

//CREANDO EL MODELO SALAUSUARIO PARA LA TABLA sala_usuario
@Entity
@Table(name="sala_usuario")
public class SalaUsuarioModelo {

	//ATRIBUTOS
	
	//SE ESTA USANDO COMO ID UN ID EMBEBIDO
	//ESTE ID SE ESTA PONIENDO PORQUE NECESITAMOS QUE SEA UNA ENTIDAD LA TABLA 
	//sala_usuario
    @EmbeddedId
    private SalaUsuarioPk id;

    //CONSTRUCTOR, GETTERS Y SETTERS
    
    public SalaUsuarioModelo() {}

	public SalaUsuarioPk getId() {
		return id;
	}

	public void setId(SalaUsuarioPk id) {
		this.id = id;
	}
      
}
