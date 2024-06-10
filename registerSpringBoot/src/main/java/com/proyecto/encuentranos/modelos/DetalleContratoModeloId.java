package com.proyecto.encuentranos.modelos;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

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

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
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
