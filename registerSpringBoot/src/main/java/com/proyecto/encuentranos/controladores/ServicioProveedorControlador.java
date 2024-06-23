package com.proyecto.encuentranos.controladores;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.encuentranos.modelos.ServicioModelo;
import com.proyecto.encuentranos.modelos.ServicioProveedorModelo;
import com.proyecto.encuentranos.modelos.ServicioProveedorModeloId;
import com.proyecto.encuentranos.servicios.ServicioProveedorServicio;

// ESTAMOS CREANDO EL CONTROLADOR PARA ServicioProveedor
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/servicio-proveedor")
public class ServicioProveedorControlador {

    // INSTANCIAR LAS CLASES QUE USAREMOS
    private final ServicioProveedorServicio servicioProveedorServicio;

    @Autowired
    public ServicioProveedorControlador(ServicioProveedorServicio servicioProveedorServicio) {
        this.servicioProveedorServicio = servicioProveedorServicio;
    }

    // AGREGAR A LA TABLA SERVICIO_PROVEEDOR
    @PostMapping("/agregar")
    public ResponseEntity<ServicioProveedorModelo> agregarServicioProveedor(@RequestBody List<ServicioProveedorModelo> serviciosProveedor) {
        try {
            if (serviciosProveedor.size() > 5) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            for (ServicioProveedorModelo servicioProveedor : serviciosProveedor) {
                ServicioProveedorModeloId id = new ServicioProveedorModeloId(servicioProveedor.getIdServicio().getIdServicio(), servicioProveedor.getIdProveedor().getIdProveedor());
                servicioProveedor.setId(id);
                servicioProveedorServicio.agregarServicioProveedor(servicioProveedor);
            }

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // LISTAR LOS SERVICIOS PROVEEDORES
    @GetMapping("/listar")
    public ResponseEntity<List<ServicioProveedorModelo>> obtenerServiciosProveedores() {
        List<ServicioProveedorModelo> servicios = servicioProveedorServicio.obtenerServiciosProveedores();
        return new ResponseEntity<>(servicios, HttpStatus.OK);
    }

    // ACTUALIZAR SERVICIOS PROVEEDOR
    @PutMapping("/actualizar")
    public ResponseEntity<ServicioProveedorModelo> actualizarServicioProveedor(@RequestBody ServicioProveedorModelo servicioProveedor) {
        ServicioProveedorModelo servicioActualizado = servicioProveedorServicio.actualizarServicioProveedor(servicioProveedor);
        return new ResponseEntity<>(servicioActualizado, HttpStatus.OK);
    }

    // ELIMINAR SERVICIOS PROVEEDOR
    @DeleteMapping("/eliminar/{idServicio}/{idProveedor}")
    public ResponseEntity<String> eliminarServicioProveedor(@PathVariable("idServicio") Integer idServicio, @PathVariable("idProveedor") Integer idProveedor) {
        ServicioProveedorModeloId id = new ServicioProveedorModeloId(idServicio, idProveedor);
        boolean eliminado = servicioProveedorServicio.eliminarServicioProveedor(id);
        if (eliminado) {
            return new ResponseEntity<>("Servicio Proveedor Eliminado" + id, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se pudo eliminar el servicio proveedor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // BUSCAR EN LA TABLA SERVICIOROVEEDOR INGRESANDO EL ID DEL SERVICIO Y EL ID DEL PROVEEDOR
    @GetMapping("/buscar/{idServicio}/{idProveedor}")
    public ResponseEntity<Optional<ServicioProveedorModelo>> obtenerServicioProveedorPorId(@PathVariable("idServicio") Integer idServicio, @PathVariable("idProveedor") Integer idProveedor) {
        ServicioProveedorModeloId id = new ServicioProveedorModeloId(idServicio, idProveedor);
        Optional<ServicioProveedorModelo> servicioEncontrado = servicioProveedorServicio.obtenerServicioProveedorPorId(id);
        if (servicioEncontrado.isPresent()) {
            return new ResponseEntity<>(servicioEncontrado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // OBTENER LOS SERVICIOS DE UN PROVEEDOR POR SU PRECIO
    @GetMapping("/filtrar/{precio}")
    public ResponseEntity<List<ServicioProveedorModelo>> obtenerServicioProveedorPorPrecio(@PathVariable("precio") double precio) {
        List<ServicioProveedorModelo> serviciosFiltrados = servicioProveedorServicio.obtenerServicioProveedorPorPrecio(precio);
        return new ResponseEntity<>(serviciosFiltrados, HttpStatus.OK);
    }

    // OBTENER EL PRECIO MAS ALTO DE LA TABLA SERVICIO_PROVEEDOR
    @GetMapping("/filtrar/alto")
    public ResponseEntity<Optional<ServicioProveedorModelo>> obtenerServicioProveedorAlto() {
        Optional<ServicioProveedorModelo> servicioAlto = servicioProveedorServicio.obtenerServicioProveedorAlto();
        if (servicioAlto.isPresent()) {
            return new ResponseEntity<>(servicioAlto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // OBTENER EL PRECIO MAS BAJO DE LA TABLA SERVICIO_PROVEEDOR
    @GetMapping("/filtrar/bajo")
    public ResponseEntity<Optional<ServicioProveedorModelo>> obtenerServicioProveedorBajo() {
        Optional<ServicioProveedorModelo> servicioBajo = servicioProveedorServicio.obtenerServicioProveedorBajo();
        if (servicioBajo.isPresent()) {
            return new ResponseEntity<>(servicioBajo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // OBTENER LOS SERVICIOS MAS ALTOS Y MAS BAJOS QUE HAY EN EL MOMENTO
    @GetMapping("/filtrar/extremos")
    public ResponseEntity<Map<String, List<ServicioProveedorModelo>>> obtenerServiciosProveedorExtremos() {
        Map<String, List<ServicioProveedorModelo>> extremos = servicioProveedorServicio.obtenerServiciosProveedorExtremos();
        return new ResponseEntity<>(extremos, HttpStatus.OK);
    }

    // OBTENER LOS SERVICIOS A LOS QUE EL PROVEEDOR NO ESTA REGISTRADO
    @GetMapping("/servicios-no-registrados/{idProveedor}")
    public ResponseEntity<List<ServicioModelo>> obtenerServiciosNoRegistrados(@PathVariable("idProveedor") Integer idProveedor) {
        List<ServicioModelo> serviciosNoRegistrados = servicioProveedorServicio.obtenerServiciosNoRegistrados(idProveedor);
        return new ResponseEntity<>(serviciosNoRegistrados, HttpStatus.OK);
    }

    // OBTENER LOS SERVICIOS REGISTRADOS POR UN PROVEEDOR
    @GetMapping("/servicios-registrados/{idProveedor}")
    public ResponseEntity<List<ServicioModelo>> obtenerServiciosDeProveedorPorId(@PathVariable("idProveedor") Integer idProveedor) {
        List<ServicioModelo> serviciosRegistrados = servicioProveedorServicio.obtenerServiciosDeProveedorPorId(idProveedor);
        return new ResponseEntity<>(serviciosRegistrados, HttpStatus.OK);
    }
    
    // OBTENER LOS SERVICIOS PROVEEDOR POR ID DEL PROVEEDOR
    @GetMapping("/buscar/{idProveedor}")
    public ResponseEntity<List<ServicioProveedorModelo>> obtenerServicioProveedorPorIdProveedor(@PathVariable("idProveedor") int idProveedor) {
        List<ServicioProveedorModelo> serviciosProveedor = servicioProveedorServicio.obtenerServicioProveedorPorIdProveedor(idProveedor);
        if (!serviciosProveedor.isEmpty()) {
            return new ResponseEntity<>(serviciosProveedor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/negociables/{idProveedor}")
    public ResponseEntity<List<ServicioProveedorModelo>> obtenerServiciosNegociables(@PathVariable int idProveedor) {
        List<ServicioProveedorModelo> serviciosNegociables = servicioProveedorServicio.obtenerServiciosProveedorNegociablePorIdProveedor(idProveedor);
        return ResponseEntity.ok(serviciosNegociables);
    }

    @GetMapping("/no-negociables/{idProveedor}")
    public ResponseEntity<List<ServicioProveedorModelo>> obtenerServiciosNoNegociables(@PathVariable int idProveedor) {
        List<ServicioProveedorModelo> serviciosNoNegociables = servicioProveedorServicio.obtenerServiciosProveedorNoNegociablePorIdProveedor(idProveedor);
        return ResponseEntity.ok(serviciosNoNegociables);
    }

}
