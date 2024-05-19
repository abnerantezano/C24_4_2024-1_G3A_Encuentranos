package com.proyecto.encuentranos.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
//CREANDO EL MODELO servicio_proveedor
@Entity
@Table(name="servicio_proveedor")
public class ServicioProveedorModelo {
	
	//ATRIBUTOS
	
	//SE ESTA USANDO COMO ID UN ID EMBEBIDO
	//ESTE ID SE ESTA PONIENDO PORQUE NECESITAMOS QUE SEA UNA ENTIDAD LA TABLA 
	//servicio_proveedor
	
    @EmbeddedId
    private ServicioProveedorPk id;
        
    @ManyToOne
    @JoinColumn(name = "id_servicio")
    @MapsId("idServicio")
    private ServicioModelo idServicio;
    
    @ManyToOne
    @JoinColumn(name = "id_proveedor")
    @MapsId("idProveedor")
    private ProveedorModelo idProveedor;
    
    @NotNull
    @Column(name = "precio")
    private double precio;
    
    @Column(name = "negociable")
    private boolean negociable;

    //GETTERS Y SETTERS
    
    public ServicioProveedorPk getId() {
        return id;
    }

    public void setId(ServicioProveedorPk id) {
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

	public boolean isNegociable() {
		return negociable;
	}

	public void setNegociable(boolean negociable) {
		this.negociable = negociable;
	}
    
}
