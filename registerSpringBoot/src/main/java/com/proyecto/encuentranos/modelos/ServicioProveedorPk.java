package com.proyecto.encuentranos.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ServicioProveedorPk {

    @Column(name = "id_servicio")
    private Integer idServicio;
    
    @Column(name = "id_proveedor")
    private Integer idProveedor;

    public ServicioProveedorPk() {
    }

    public ServicioProveedorPk(Integer idServicio, Integer idProveedor) {
        this.idServicio = idServicio;
        this.idProveedor = idProveedor;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }
}
