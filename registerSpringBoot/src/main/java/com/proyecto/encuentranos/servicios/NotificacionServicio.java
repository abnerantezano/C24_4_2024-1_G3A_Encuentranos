package com.proyecto.encuentranos.servicios;

import com.proyecto.encuentranos.modelos.NotificacionModelo;
import com.proyecto.encuentranos.repositorios.INotificacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificacionServicio {

    private final INotificacionRepositorio notificacionRepositorio;

    @Autowired
    public NotificacionServicio(INotificacionRepositorio notificacionRepositorio) {
        this.notificacionRepositorio = notificacionRepositorio;
    }

    public List<NotificacionModelo> getAllNotificaciones() {
        return notificacionRepositorio.findAll();
    }

    public Optional<NotificacionModelo> getNotificacionById(int id) {
        return notificacionRepositorio.findById(id);
    }

    public List<NotificacionModelo> getNotificacionesByIdCliente(int idCliente) {
        return notificacionRepositorio.findByIdClienteIdCliente(idCliente);
    }

    public List<NotificacionModelo> getNotificacionesByIdProveedor(int idProveedor) {
        return notificacionRepositorio.findByIdProveedorIdProveedor(idProveedor);
    }

    public List<NotificacionModelo> getNotificacionesByEstado(String estado) {
        return notificacionRepositorio.findByEstado(estado);
    }

    public List<NotificacionModelo> getNotificacionesByTipo(String tipo) {
        return notificacionRepositorio.findByTipo(tipo);
    }

    public NotificacionModelo crearNotificacion(NotificacionModelo notificacion) {
        return notificacionRepositorio.save(notificacion);
    }

    public void eliminarNotificacion(int id) {
        notificacionRepositorio.deleteById(id);
    }

    public void actualizarEstado(int id, String estado) {
        Optional<NotificacionModelo> notificacion = notificacionRepositorio.findById(id);
        notificacion.ifPresent(n -> {
            n.setEstado(estado);
            notificacionRepositorio.save(n);
        });
    }
}
