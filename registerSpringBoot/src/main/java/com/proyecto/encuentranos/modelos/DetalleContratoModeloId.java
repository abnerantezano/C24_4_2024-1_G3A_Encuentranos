package com.proyecto.encuentranos.modelos;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
public class DetalleContratoModeloId implements Serializable {

    @Column(name = "id_proveedor")
    private int idProveedor;

    @Column(name = "id_servicio")
    private int idServicio;

    @Column(name = "id_contrato")
    private int idContrato;

    // No-argument constructor
    public DetalleContratoModeloId() {}

    public DetalleContratoModeloId(int idProveedor, int idServicio, int idContrato) {
        this.idProveedor = idProveedor;
        this.idServicio = idServicio;
        this.idContrato = idContrato;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetalleContratoModeloId that = (DetalleContratoModeloId) o;
        return idProveedor == that.idProveedor &&
               idServicio == that.idServicio &&
               idContrato == that.idContrato;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProveedor, idServicio, idContrato);
    }
}
