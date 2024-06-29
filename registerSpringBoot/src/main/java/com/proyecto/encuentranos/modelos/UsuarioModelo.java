package com.proyecto.encuentranos.modelos;

import java.util.Date;

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
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "usuario")
public class UsuarioModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column( name = "id_usuario")
    private int idUsuario;

    @ManyToOne
    @JoinColumn(name = "id_tipo", referencedColumnName = "id_tipo")
    private TipoUsuarioModelo idTipo;
    
    @Column(name = "correo", unique = true)
    private String correo;

    @Column(name = "contrasena")
    private String contrasena;

    @Column(name = "imagen_url")
    private String imagenUrl;

    @Column(name = "estado")
    private String estado;

    @Temporal(TemporalType.DATE)
    @Column(name = "fh_creacion")
    private Date fechaRegistro;

}
