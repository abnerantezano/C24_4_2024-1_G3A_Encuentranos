package com.proyecto.encuentranos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.encuentranos.modelos.ChatModelo;

import java.util.List;
import java.util.Optional;

@Repository
public interface IChatRepositorio extends JpaRepository<ChatModelo, Integer> {
    List<ChatModelo> findByIdClienteIdCliente(int idCliente);
    List<ChatModelo> findByIdProveedorIdProveedor(int idProveedor);
    Optional<ChatModelo> findByIdClienteIdClienteAndIdProveedorIdProveedor(int idCliente, int idProveedor);
}
