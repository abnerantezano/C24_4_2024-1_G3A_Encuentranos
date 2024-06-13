package com.proyecto.encuentranos.modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle_contrato")
public class DetalleContratoModelo {
	
    @EmbeddedId
    private DetalleContratoModeloId id;
    
    @ManyToOne
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id_proveedor", insertable = false, updatable = false)
    private ProveedorModelo idProveedor;

    @ManyToOne
    @JoinColumn(name = "id_servicio", referencedColumnName = "id_servicio", insertable = false, updatable = false)
    private ServicioModelo idServicio;

    @ManyToOne
    @JoinColumn(name = "id_contrato", referencedColumnName = "id_contrato", insertable = false, updatable = false)
    private ContratoModelo idContrato;

    @Column(name = "precio_actual")
    private double precioActual;

	public DetalleContratoModeloId getId() {
		return id;
	}

	public void setId(DetalleContratoModeloId id) {
		this.id = id;
	}

	public ProveedorModelo getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(ProveedorModelo idProveedor) {
		this.idProveedor = idProveedor;
	}

	public ServicioModelo getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(ServicioModelo idServicio) {
		this.idServicio = idServicio;
	}

	public ContratoModelo getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(ContratoModelo idContrato) {
		this.idContrato = idContrato;
	}

	public double getPrecioActual() {
		return precioActual;
	}

	public void setPrecioActual(double precioActual) {
		this.precioActual = precioActual;
	}

}
