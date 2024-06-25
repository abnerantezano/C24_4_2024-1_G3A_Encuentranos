package com.proyecto.encuentranos.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.encuentranos.modelos.*;
@Repository
public interface IDetalleContratoRepositorio extends JpaRepository<DetalleContratoModelo, DetalleContratoModeloId> {
	List<DetalleContratoModelo> findByIdProveedor(ProveedorModelo proveedor);
    List<DetalleContratoModelo> findByIdContratoIdCliente(Optional<ClienteModelo> clienteId);
	List<DetalleContratoModelo> findByIdProveedorIdProveedor(Integer idProveedor);
	List<DetalleContratoModelo> findByIdProveedorAndIdContratoEstado(ProveedorModelo proveedor, String estado);
}