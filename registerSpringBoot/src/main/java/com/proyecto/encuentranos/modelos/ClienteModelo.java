package com.proyecto.encuentranos.modelos;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
public class ClienteModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column( name = "id_cliente")
    private int idCliente;

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

	@Column( name = "sexo")
    private String sexo;

    @Column(name = "dni", length = 8, unique = true)
    private String dni;

    @Column(name = "celular", length = 9, unique = true)
    private String celular;

    @Temporal(TemporalType.DATE)
	@Column( name = "fecha_nacimiento")
    private Date fechaNacimiento;

    @Lob
	@Column( name = "descripcion")
    private String descripcion;

    @Temporal(TemporalType.DATE)
	@Column( name = "fecha_registro")
    private Date fechaRegistro;

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
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

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

}
