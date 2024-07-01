package com.proyecto.encuentranos.servicios;

import com.proyecto.encuentranos.modelos.ClienteModelo;
import com.proyecto.encuentranos.modelos.ContratoModelo;
import com.proyecto.encuentranos.modelos.NotificacionModelo;
import com.proyecto.encuentranos.modelos.ProveedorModelo;
import com.proyecto.encuentranos.repositorios.IClienteRepositorio;
import com.proyecto.encuentranos.repositorios.IContratoRepositorio;
import com.proyecto.encuentranos.repositorios.INotificacionRepositorio;
import com.proyecto.encuentranos.repositorios.IProveedorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotificacionServicio {

    private final INotificacionRepositorio notificacionRepositorio;
    @Autowired
    private IClienteRepositorio clienteRepositorio;

    @Autowired
    private IProveedorRepositorio proveedorRepositorio;

    @Autowired
    private IContratoRepositorio contratoRepositorio;

    @Autowired
    public NotificacionServicio(INotificacionRepositorio notificacionRepositorio) {
        this.notificacionRepositorio = notificacionRepositorio;
    }

    public NotificacionModelo crearNotificacion(int idCliente, int idProveedor, int idContrato, String titulo, String mensaje, String estado) {
        NotificacionModelo notificacion = new NotificacionModelo();

        // Obtener ClienteModelo
        ClienteModelo cliente = clienteRepositorio.findById(idCliente).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        notificacion.setIdCliente(cliente);

        // Obtener ProveedorModelo
        ProveedorModelo proveedor = proveedorRepositorio.findById(idProveedor).orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado"));
        notificacion.setIdProveedor(proveedor);

        // Obtener ContratoModelo
        ContratoModelo contrato = contratoRepositorio.findById(idContrato).orElseThrow(() -> new IllegalArgumentException("Contrato no encontrado"));
        notificacion.setIdContrato(contrato);

        notificacion.setTitulo(titulo);
        notificacion.setMensaje(mensaje);
        notificacion.setEstado(estado);

        return notificacionRepositorio.save(notificacion);
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

    public List<NotificacionModelo> buscarNotificacionesClienteContrato(int idCliente) {
        return notificacionRepositorio.findByIdClienteAndTituloRespuestaContrato(idCliente);
    }

    public List<NotificacionModelo> getNotificacionesByIdProveedor(int idProveedor) {
        return notificacionRepositorio.findByIdProveedorIdProveedor(idProveedor);
    }

    public List<NotificacionModelo> buscarNotificacionesProveedorContrato(int idProveedor) {
        return notificacionRepositorio.findByIdProveedorAndTituloNuevoContrato(idProveedor);
    }

    public List<NotificacionModelo> getNotificacionesByEstado(String estado) {
        return notificacionRepositorio.findByEstado(estado);
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
