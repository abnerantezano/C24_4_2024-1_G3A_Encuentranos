package com.proyecto.encuentranos.modelos;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="proveedor")
public class ProveedorModelo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column( name = "id_proveedor")
	private Integer id;
	
	@OneToOne
	@JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
	private UsuarioModelo idUsuario;
	
	@NotEmpty
	@Column(name = "nombre")
	private String nombre;
	
	@NotEmpty
	@Column(name = "apellido_paterno")
	private String apellidoPaterno;

	@NotEmpty
	@Column(name = "apellido_materno")
	private String apellidoMaterno;
	
	@NotEmpty
	@Column(name = "sexo")
	private String sexo;
	
	@NotEmpty
	@Column(name = "dni")
	private String dni;
	
	@NotEmpty
	@Column(name = "celular")
	private String celular;
	
	@Column(name= "fecha_nacimiento")
	private LocalDate fechaNacimiento;
	
	@OneToOne
	@JoinColumn(name = "id_distrito", referencedColumnName = "id_distrito")
	private DistritoModelo idDistrito;

	@Column(name = "disponible")
	private boolean disponible;
	
	@NotNull
	@Column(name = "calificacion_promedio")
	private double calificacionPromedio;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UsuarioModelo getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(UsuarioModelo idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public DistritoModelo getIdDistrito() {
		return idDistrito;
	}

	public void setIdDistrito(DistritoModelo idDistrito) {
		this.idDistrito = idDistrito;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public double getCalificacionPromedio() {
		return calificacionPromedio;
	}

	public void setCalificacionPromedio(double calificacionPromedio) {
		this.calificacionPromedio = calificacionPromedio;
	}
	
	
	
}
