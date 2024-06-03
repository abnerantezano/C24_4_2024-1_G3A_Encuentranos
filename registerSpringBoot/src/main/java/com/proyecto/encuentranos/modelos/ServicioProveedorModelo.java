package com.proyecto.encuentranos.modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "servicio_proveedor")
@IdClass(ServicioProveedorModeloId.class)
public class ServicioProveedorModelo {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_servicio", referencedColumnName = "id_servicio")
    private ServicioModelo idServicio;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id_proveedor")
    private ProveedorModelo idProveedor;

    @Column(name = "precio")
    private double precio;
    
    @Column(name = "negociable")
    private boolean negociable;

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

	public boolean isNegociable() {
		return negociable;
	}

	public void setNegociable(boolean negociable) {
		this.negociable = negociable;
	}

    public void setId(ServicioProveedorModeloId id) {
        this.idServicio = new ServicioModelo();
        this.idServicio.setIdServicio(id.getIdServicio());
        this.idProveedor = new ProveedorModelo();
        this.idProveedor.setIdProveedor(id.getIdProveedor());
    }
	
	

}
