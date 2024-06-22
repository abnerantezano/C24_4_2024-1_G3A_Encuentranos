package com.proyecto.encuentranos.modelos;

import com.google.type.DateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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
}
