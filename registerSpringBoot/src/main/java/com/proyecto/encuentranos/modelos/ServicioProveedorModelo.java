package com.proyecto.encuentranos.modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "servicio_proveedor")
public class ServicioProveedorModelo {

    @EmbeddedId
    private ServicioProveedorModeloId id;
    
    @ManyToOne
    @JoinColumn(name = "id_servicio", referencedColumnName = "id_servicio", insertable = false, updatable = false)
    private ServicioModelo idServicio;

    @ManyToOne
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id_proveedor", insertable = false, updatable = false)
    private ProveedorModelo idProveedor;

    @Column(name = "precio")
    private double precio;
    
    @Column(name = "negociable")
    private boolean negociable;

    public ServicioProveedorModeloId getId() {
        return id;
    }

    public void setId(ServicioProveedorModeloId id) {
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
