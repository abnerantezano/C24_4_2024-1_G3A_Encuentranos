package com.proyecto.encuentranos.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

// Clase para la clave compuesta
@Setter
@Getter
@Embeddable
public class ServicioProveedorModeloId implements Serializable {
    @Column(name = "id_servicio")
    private int idServicio;

    @Column(name = "id_proveedor")
    private int idProveedor;

    // Constructor sin argumentos
    public ServicioProveedorModeloId() {}
    
    public ServicioProveedorModeloId(int idServicio, int idProveedor) {
        this.idServicio = idServicio;
        this.idProveedor = idProveedor;
    }
    public ServicioProveedorModeloId(int idProveedor) {
        this.idProveedor = idProveedor;
    }

}
