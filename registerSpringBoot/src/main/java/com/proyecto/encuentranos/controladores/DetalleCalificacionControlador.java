package com.proyecto.encuentranos.controladores;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.encuentranos.modelos.DetalleCalificacionModelo;
import com.proyecto.encuentranos.modelos.DetalleCalificacionModeloId;
import com.proyecto.encuentranos.servicios.DetalleCalificacionServicio;

// ESTAMOS CREANDO EL CONTROLADOR PARA CalificacionProveedor
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/calificacion-proveedor")
public class DetalleCalificacionControlador {

    // INSTANCIAR LAS CLASES QUE USAREMOS
    @Autowired
    DetalleCalificacionServicio calificacionProveedorServicio;

    // AGREGAR LA CALIFICACION DE UN PROVEEDOR
    @PostMapping("/agregar")
    public ResponseEntity<?> guardarCalificacionProveedor(@RequestBody DetalleCalificacionModelo calificacionProveedor) {
        try {
            calificacionProveedor.setId(new DetalleCalificacionModeloId(
                calificacionProveedor.getIdProveedor().getIdProveedor(),
                calificacionProveedor.getIdServicio().getIdServicio(),
                calificacionProveedor.getIdCalificacion().getIdCalificacion()
            ));
            calificacionProveedor = calificacionProveedorServicio.guardarCalificacionProveedor(calificacionProveedor);

            // RECALCULAR LA CALFICACION PROMEDIO DEL PROVEEDOR
            Integer idProveedor = calificacionProveedor.getIdProveedor().getIdProveedor();
            Double calificacionPromedio = calificacionProveedorServicio.calcularCalificacionPromedioProveedor(idProveedor);
            calificacionProveedorServicio.actualizarCalificacionPromedioProveedor(idProveedor, calificacionPromedio);

            return new ResponseEntity<>(calificacionProveedor, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("No se pudo agregar la calificación del proveedor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // LISTADO LA TABLA CALIFICACION PROVEEDOR
    @GetMapping("/listar")
    public ResponseEntity<ArrayList<DetalleCalificacionModelo>> obtenerCalificacionesProveedores() {
        ArrayList<DetalleCalificacionModelo> calificacionesProveedores = calificacionProveedorServicio.obtenerCalificacionesProveedores();
        return new ResponseEntity<>(calificacionesProveedores, HttpStatus.OK);
    }

    // OBTENER LA CALIFICACION PROVEEDOR POR EL ID DEL CLIENTE Y EL PROVEEDOR QUE INTERVIENEN
    @GetMapping("/buscar/{idProveedor}/{idServicio}/{idCalificacion}")
    public ResponseEntity<Optional<DetalleCalificacionModelo>> obtenerCalificacionProveedorPorId(
            @PathVariable("idProveedor") Integer idProveedor,
            @PathVariable("idServicio") Integer idServicio,
            @PathVariable("idCalificacion") Integer idCalificacion) {
        DetalleCalificacionModeloId id = new DetalleCalificacionModeloId(idProveedor, idServicio, idCalificacion);
        Optional<DetalleCalificacionModelo> calificacionProveedor = calificacionProveedorServicio.obtenerCalificacionProveedorPorId(id);
        if (calificacionProveedor.isPresent()) {
            return new ResponseEntity<>(calificacionProveedor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
