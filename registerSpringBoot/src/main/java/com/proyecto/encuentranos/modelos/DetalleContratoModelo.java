package com.proyecto.encuentranos.modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle_contrato")
@IdClass(DetalleContratoModeloId.class)
public class DetalleContratoModelo {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id_proveedor")
    private ProveedorModelo idProveedor;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_servicio", referencedColumnName = "id_servicio")
    private ServicioModelo idServicio;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_contrato", referencedColumnName = "id_contrato")
    private ContratoModelo idContrato;

    @Column(name = "precio_actual")
    private double precioActual;
    
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
