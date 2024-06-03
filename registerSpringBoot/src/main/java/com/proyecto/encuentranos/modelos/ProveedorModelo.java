package com.proyecto.encuentranos.modelos;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "proveedor")
public class ProveedorModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column( name = "id_proveedor")
<<<<<<< HEAD
    private int idProveedor;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private UsuarioModelo idUsuario;
=======
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
	private String fechaNacimiento;
	
	@OneToOne
	@JoinColumn(name = "id_distrito", referencedColumnName = "id_distrito")
	private DistritoModelo idDistrito;
>>>>>>> 93e8b4309b4c945e0945b7fdf2e64afa65e74b6c

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

    @Temporal(TemporalType.TIMESTAMP)
	@Column( name = "fecha_registro")
    private Date fechaRegistro;

	public int getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}

	public UsuarioModelo getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(UsuarioModelo idUsuario) {
		this.idUsuario = idUsuario;
	}

	public DistritoModelo getIdDistrito() {
		return idDistrito;
	}

	public void setIdDistrito(DistritoModelo idDistrito) {
		this.idDistrito = idDistrito;
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

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getCalificacionPromedio() {
		return calificacionPromedio;
	}

	public void setCalificacionPromedio(double calificacionPromedio) {
		this.calificacionPromedio = calificacionPromedio;
	}

	public String getCurriculumUrl() {
		return curriculumUrl;
	}

	public void setCurriculumUrl(String curriculumUrl) {
		this.curriculumUrl = curriculumUrl;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	
}
