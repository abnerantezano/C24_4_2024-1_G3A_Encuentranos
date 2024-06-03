package com.proyecto.encuentranos.modelos;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "calificacion")
public class CalificacionModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_calificacion")
    private int idCalificacion;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    private ClienteModelo cliente;

    @Column(name = "numero")
    private int numero;

    @Lob
    @Column(name = "comentario")
    private String comentario;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_calificacion")
    private Date fechaCalificacion;

    // Getters and Setters

	public int getIdCalificacion() {
		return idCalificacion;
	}

	public void setIdCalificacion(int idCalificacion) {
		this.idCalificacion = idCalificacion;
	}

	public ClienteModelo getCliente() {
		return cliente;
	}

	public void setCliente(ClienteModelo cliente) {
		this.cliente = cliente;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Date getFechaCalificacion() {
		return fechaCalificacion;
	}

	public void setFechaCalificacion(Date fechaCalificacion) {
		this.fechaCalificacion = fechaCalificacion;
	}

}
