package com.proyecto.encuentranos.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="servicio_prestador")
public class ServicioPrestadorModelo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_servicio", referencedColumnName = "id_servicio")
	private ServicioModelo idServicio;
	
	@ManyToOne
	@JoinColumn(name = "id_proveedor", referencedColumnName = "id_proveedor")
	private ProveedorModelo idProveedor;
	
	@NotNull
    @Column(name = "precio")
    private double precio;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ServicioModelo getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(ServicioModelo idServicio) {
		this.idServicio = idServicio;
	}

	public ProveedorModelo getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(ProveedorModelo idProveedor) {
		this.idProveedor = idProveedor;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}	
}
