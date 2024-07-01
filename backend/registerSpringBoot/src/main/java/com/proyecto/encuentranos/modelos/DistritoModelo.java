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
@Table(name="distrito")
public class DistritoModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column( name = "id_distrito")
    private int idDistrito;

	@Column( name = "nombre")
    private String nombre;
}
