package com.proyecto.encuentranos.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "tipo_usuario")
public class TipoUsuarioModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column( name = "id_tipo")
    private int idTipo;

	@Column( name = "nombre")
    private String nombre;

}
