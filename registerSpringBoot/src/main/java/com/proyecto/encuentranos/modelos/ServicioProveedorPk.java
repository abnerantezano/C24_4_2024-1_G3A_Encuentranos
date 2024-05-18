package com.proyecto.encuentranos.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
//ESTE ES EL ID EMBEBIDO DE SERVICIOPROVEEDORMODELO
@Embeddable
public class ServicioProveedorPk {

	//ATRIBUTOS
	
    @Column(name = "id_servicio")
    private Integer idServicio;
    
    @Column(name = "id_proveedor")
    private Integer idProveedor;

    //CONSTRUCTORES, GETTERS Y SETTERS
    
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
