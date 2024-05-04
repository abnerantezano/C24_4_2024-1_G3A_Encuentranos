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

@Entity
@Table(name="proveedor")
public class ProveedorModelo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column( name = "id_proveedor")
	private Long id;
	
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
	@Column(name = "dni")
	private String dni;
	
	@Column(name= "fecha_nacimiento")
	private LocalDate fechaNacimiento;
	
	@NotEmpty
	@Column(name = "celular")
	private String celular;
	
	@NotEmpty
	@Column(name = "imagen_url")
	private String imagenUrl;
	
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

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getImagenUrl() {
		return imagenUrl;
	}

	public void setImagenUrl(String imagenUrl) {
		this.imagenUrl = imagenUrl;
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
