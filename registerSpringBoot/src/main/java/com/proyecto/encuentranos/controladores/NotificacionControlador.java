package com.proyecto.encuentranos.controladores;

import com.proyecto.encuentranos.modelos.NotificacionModelo;
import com.proyecto.encuentranos.servicios.NotificacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notificaciones")
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

    @GetMapping("/proveedor/{idProveedor}")
    public List<NotificacionModelo> getNotificacionesByIdProveedor(@PathVariable int idProveedor) {
        return notificacionServicio.getNotificacionesByIdProveedor(idProveedor);
    }

    @GetMapping("/estado/{estado}")
    public List<NotificacionModelo> getNotificacionesByEstado(@PathVariable String estado) {
        return notificacionServicio.getNotificacionesByEstado(estado);
    }

    @GetMapping("/tipo/{tipo}")
    public List<NotificacionModelo> getNotificacionesByTipo(@PathVariable String tipo) {
        return notificacionServicio.getNotificacionesByTipo(tipo);
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
}
