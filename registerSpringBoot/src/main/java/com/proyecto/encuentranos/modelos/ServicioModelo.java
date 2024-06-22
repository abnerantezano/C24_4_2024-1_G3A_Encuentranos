package com.proyecto.encuentranos.modelos;

import com.google.type.DateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
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

	@Column(name = "imagen_url")
	private String imagenUrl;

	@Temporal(TemporalType.DATE)
	@Column(name = "fh_creacion")
	private Date fh_creacion;
}
