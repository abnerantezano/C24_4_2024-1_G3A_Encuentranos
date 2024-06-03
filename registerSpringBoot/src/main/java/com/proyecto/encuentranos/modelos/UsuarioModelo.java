package com.proyecto.encuentranos.modelos;

<<<<<<< HEAD
import java.util.Date;

=======
>>>>>>> 93e8b4309b4c945e0945b7fdf2e64afa65e74b6c
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "usuario")
public class UsuarioModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column( name = "id_usuario")
    private int idUsuario;

<<<<<<< HEAD
    @ManyToOne
    @JoinColumn(name = "id_tipo", referencedColumnName = "id_tipo")
    private TipoUsuarioModelo idTipo;
=======
	@Column(name = "imagen_url")
	private String imagenUrl;
	
	@Column(name = "eliminada")
	private boolean eliminada;
>>>>>>> 93e8b4309b4c945e0945b7fdf2e64afa65e74b6c

    @Column(name = "correo", unique = true)
    private String correo;

    @Column(name = "contrasena")
    private String contrasena;

    @Column(name = "imagen_url")
    private String imagenUrl;

    @Column(name = "activo")
    private boolean activo;

    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    // Getters and Setters

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public TipoUsuarioModelo getidTipo() {
		return idTipo;
	}

	public void setidTipo(TipoUsuarioModelo idTipo) {
		this.idTipo = idTipo;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getImagenUrl() {
		return imagenUrl;
	}

	public void setImagenUrl(String imagenUrl) {
		this.imagenUrl = imagenUrl;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
<<<<<<< HEAD

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

=======
	
	
>>>>>>> 93e8b4309b4c945e0945b7fdf2e64afa65e74b6c
}
