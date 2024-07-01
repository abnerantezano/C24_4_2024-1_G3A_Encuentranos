package com.proyecto.encuentranos.repositorios;

import com.proyecto.encuentranos.modelos.NotificacionModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INotificacionRepositorio extends JpaRepository<NotificacionModelo, Integer> {
    List<NotificacionModelo> findByIdClienteIdCliente(int idCliente);
    List<NotificacionModelo> findByIdProveedorIdProveedor(int idProveedor);
    List<NotificacionModelo> findByEstado(String estado);
    @Query("SELECT n FROM NotificacionModelo n WHERE n.idProveedor.idProveedor = :idProveedor AND n.titulo = 'Nuevo contrato'")
    List<NotificacionModelo> findByIdProveedorAndTituloNuevoContrato(int idProveedor);
    @Query("SELECT n FROM NotificacionModelo n WHERE n.idCliente.idCliente = :idCliente AND n.titulo = 'Respuesta contrato'")
    List<NotificacionModelo> findByIdClienteAndTituloRespuestaContrato(int idCliente);
}
