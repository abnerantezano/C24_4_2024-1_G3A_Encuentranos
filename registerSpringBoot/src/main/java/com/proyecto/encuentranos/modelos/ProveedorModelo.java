package com.proyecto.encuentranos.modelos;

import java.util.Date;

import com.google.type.DateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "proveedor")
public class ProveedorModelo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column( name = "id_proveedor")
	private int idProveedor;

	@ManyToOne
	@JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
	private UsuarioModelo idUsuario;

	@ManyToOne
	@JoinColumn(name = "id_distrito", referencedColumnName = "id_distrito")
	private DistritoModelo idDistrito;

	@Column( name = "nombre")
	private String nombre;

	@Column( name = "apellido_paterno")
	private String apellidoPaterno;

	@Column( name = "apellido_materno")
	private String apellidoMaterno;

	@Column(name = "dni", length = 8, unique = true)
	private String dni;

	@Column(name = "celular", length = 9, unique = true)
	private String celular;

	@Column( name = "sexo")
	private String sexo;

	@Temporal(TemporalType.DATE)
	@Column( name = "fecha_nacimiento")
	private Date fechaNacimiento;

	@Lob
	@Column( name = "descripcion")
	private String descripcion;

	@Column( name = "calificacion_promedio")
	private double calificacionPromedio;

	@Column( name = "curriculum_url")
	private String curriculumUrl;

	@Column(name = "fh_creacion")
	@Temporal(TemporalType.DATE)
	private DateTime fh_creacion;

}