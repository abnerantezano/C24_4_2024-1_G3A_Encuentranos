package com.proyecto.encuentranos.controladores;

import com.proyecto.encuentranos.modelos.NotificacionModelo;
import com.proyecto.encuentranos.servicios.NotificacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notificacion")
public class NotificacionControlador {

    private final NotificacionServicio notificacionServicio;

    @Autowired
    public NotificacionControlador(NotificacionServicio notificacionServicio) {
        this.notificacionServicio = notificacionServicio;
    }

    @GetMapping("/listar")
    public List<NotificacionModelo> getAllNotificaciones() {
        return notificacionServicio.getAllNotificaciones();
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<NotificacionModelo> getNotificacionById(@PathVariable int id) {
        Optional<NotificacionModelo> notificacion = notificacionServicio.getNotificacionById(id);
        return notificacion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{idCliente}")
    public List<NotificacionModelo> getNotificacionesByIdCliente(@PathVariable int idCliente) {
        return notificacionServicio.getNotificacionesByIdCliente(idCliente);
    }

    //ESTE ENDPOINT LISTA LAS NOTIFICACIONES SOLO QUE TENGAN COMO TITULO Respuesta contrato
    @GetMapping("/cliente-respuesta-contrato/{idCliente}")
    public List<NotificacionModelo> buscarNotificacionesClienteContrato(@PathVariable int idCliente) {
        return notificacionServicio.buscarNotificacionesClienteContrato(idCliente);
    }

    @GetMapping("/proveedor/{idProveedor}")
    public List<NotificacionModelo> getNotificacionesByIdProveedor(@PathVariable int idProveedor) {
        return notificacionServicio.getNotificacionesByIdProveedor(idProveedor);
    }

    //ESTE ENDPOINT LISTA LAS NOTIFICACIONES SOLO QUE TENGAN COMO TITULO Nuevo contrato
    @GetMapping("/proveedor-nuevo-contrato/{idProveedor}")
    public List<NotificacionModelo> buscarNotificacionesProveedorContrato(@PathVariable int idProveedor) {
        return notificacionServicio.buscarNotificacionesProveedorContrato(idProveedor);
    }

    @GetMapping("/estado/{estado}")
    public List<NotificacionModelo> getNotificacionesByEstado(@PathVariable String estado) {
        return notificacionServicio.getNotificacionesByEstado(estado);
    }

    @PostMapping("/agregar")
    public NotificacionModelo crearNotificacion(@RequestBody NotificacionModelo notificacion) {
        return notificacionServicio.crearNotificacion(notificacion);
    }

    @PutMapping("/actualizar-estado/{id}")
    public ResponseEntity<Void> actualizarEstado(@PathVariable int id) {
        notificacionServicio.actualizarEstado(id, "visto");
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarNotificacion(@PathVariable int id) {
        notificacionServicio.eliminarNotificacion(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para agregar una notificación
    @PostMapping("/agregar/{idCliente}/{idProveedor}/{idContrato}")
    public ResponseEntity<NotificacionModelo> agregarNotificacion(
            @PathVariable int idCliente,
            @PathVariable int idProveedor,
            @PathVariable int idContrato,
            @RequestBody NotificacionModelo notificacion) {

        NotificacionModelo nuevaNotificacion = notificacionServicio.crearNotificacion(idCliente, idProveedor, idContrato, notificacion.getTitulo(), notificacion.getMensaje(), notificacion.getEstado());
        return ResponseEntity.ok(nuevaNotificacion);
    }
}
