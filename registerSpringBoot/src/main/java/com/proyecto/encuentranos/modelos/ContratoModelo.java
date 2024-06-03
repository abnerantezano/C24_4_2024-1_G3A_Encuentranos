package com.proyecto.encuentranos.modelos;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "contrato")
public class ContratoModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contrato")
    private int idContrato;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    private ClienteModelo idCliente;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_fin")
    private Date fechaFin;

    @Column(name = "estado")
    private String estado;

    @Column(name = "precio_final")
    private double precioFinal;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "inicio_servicio")
    private Date inicioServicio;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fin_servicio")
    private Date finServicio;

    // Getters and Setters

	public int getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(int idContrato) {
		this.idContrato = idContrato;
	}

	public ClienteModelo getidCliente() {
		return idCliente;
	}

	public void setidCliente(ClienteModelo idCliente) {
		this.idCliente = idCliente;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public double getPrecioFinal() {
		return precioFinal;
	}

	public void setPrecioFinal(double precioFinal) {
		this.precioFinal = precioFinal;
	}

	public Date getInicioServicio() {
		return inicioServicio;
	}

	public void setInicioServicio(Date inicioServicio) {
		this.inicioServicio = inicioServicio;
	}

	public Date getFinServicio() {
		return finServicio;
	}

	public void setFinServicio(Date finServicio) {
		this.finServicio = finServicio;
	}

}
