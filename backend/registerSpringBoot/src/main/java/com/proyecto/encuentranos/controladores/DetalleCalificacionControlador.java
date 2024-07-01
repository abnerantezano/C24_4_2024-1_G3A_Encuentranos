package com.proyecto.encuentranos.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.encuentranos.modelos.DetalleCalificacionModelo;
import com.proyecto.encuentranos.modelos.DetalleCalificacionModeloId;
import com.proyecto.encuentranos.servicios.DetalleCalificacionServicio;

@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/detalle-calificacion")
public class DetalleCalificacionControlador {

    private final DetalleCalificacionServicio calificacionProveedorServicio;

    @Autowired
    public DetalleCalificacionControlador(DetalleCalificacionServicio calificacionProveedorServicio) {
        this.calificacionProveedorServicio = calificacionProveedorServicio;
    }

    // AGREGAR LA CALIFICACION DE UN PROVEEDOR
    @PostMapping("/agregar")
    public ResponseEntity<DetalleCalificacionModelo> guardarCalificacionProveedor(@RequestBody DetalleCalificacionModelo calificacionProveedor) {
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
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<DetalleCalificacionModelo>> obtenerCalificacionesProveedores() {
        List<DetalleCalificacionModelo> calificacionesProveedores = calificacionProveedorServicio.obtenerCalificacionesProveedores();
        return new ResponseEntity<>(calificacionesProveedores, HttpStatus.OK);
    }

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

    @GetMapping("/buscar/{idProveedor}/{idServicio}")
    public ResponseEntity<List<DetalleCalificacionModelo>> obtenerCalificacionesProveedorPorIdProveedor(
            @PathVariable("idProveedor") Integer idProveedor,
            @PathVariable("idServicio") Integer idServicio) {
        List<DetalleCalificacionModelo> calificacionesProveedor = calificacionProveedorServicio.obtenerCalificacionesPorProveedorYServicio(idProveedor, idServicio);
        return new ResponseEntity<>(calificacionesProveedor, HttpStatus.OK);
    }

    @GetMapping("/buscar/{idProveedor}")
    public ResponseEntity<List<DetalleCalificacionModelo>> obtenerCalificacionesProveedor(
            @PathVariable("idProveedor") Integer idProveedor) {
        List<DetalleCalificacionModelo> calificacionesProveedor = calificacionProveedorServicio.obtenerCalificacionesPorProveedor(idProveedor);
        return new ResponseEntity<>(calificacionesProveedor, HttpStatus.OK);
    }
}
