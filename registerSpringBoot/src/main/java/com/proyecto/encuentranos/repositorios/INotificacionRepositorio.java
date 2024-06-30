package com.proyecto.encuentranos.repositorios;

import com.proyecto.encuentranos.modelos.NotificacionModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INotificacionRepositorio extends JpaRepository<NotificacionModelo, Integer> {
    List<NotificacionModelo> findByIdClienteIdCliente(int idCliente);
    List<NotificacionModelo> findByIdProveedorIdProveedor(int idProveedor);
    List<NotificacionModelo> findByEstado(String estado);
    List<NotificacionModelo> findByTipo(String tipo);
}
