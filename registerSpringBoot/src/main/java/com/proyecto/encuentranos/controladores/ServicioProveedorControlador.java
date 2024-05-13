package com.proyecto.encuentranos.controladores;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.encuentranos.modelos.ServicioProveedorModelo;
import com.proyecto.encuentranos.modelos.ServicioProveedorPk;
import com.proyecto.encuentranos.servicios.ServicioProveedorServicio;

@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/servicio-proveedor")
public class ServicioProveedorControlador {

    @Autowired
    ServicioProveedorServicio servicioProveedorServicio;
    
    @GetMapping("/listar")
    public ArrayList<ServicioProveedorModelo> obtenerServiciosProveedores(){
        return servicioProveedorServicio.obtenerServiciosProveedores();
    }
    
    @GetMapping("/buscar/{idServicio}/{idProveedor}")
    public Optional<ServicioProveedorModelo> obtenerServicioProveedorPorId(@PathVariable("idServicio") Integer idServicio, @PathVariable("idProveedor") Integer idProveedor){
        ServicioProveedorPk id = new ServicioProveedorPk(idServicio, idProveedor);
        return servicioProveedorServicio.obtenerServicioProveedorPorId(id);
    }
    
    @PostMapping("/agregar")
    public ResponseEntity<?> agregarServicioProveedor(@RequestBody List<ServicioProveedorModelo> serviciosProveedor) {
        try {
            if (serviciosProveedor.size() > 5) {
                return new ResponseEntity<>("Solo se pueden agregar como máximo 5 servicios proveedores a la vez", HttpStatus.BAD_REQUEST);
            }
            
            for (ServicioProveedorModelo servicioProveedor : serviciosProveedor) {
                servicioProveedor.setId(new ServicioProveedorPk(servicioProveedor.getIdServicio().getId(), servicioProveedor.getIdProveedor().getId()));
                servicioProveedorServicio.agregarServicioProveedor(servicioProveedor);
            }
            
            ArrayList<ServicioProveedorModelo> serviciosProveedorActualizados = servicioProveedorServicio.obtenerServiciosProveedores();
            return new ResponseEntity<>(serviciosProveedorActualizados, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("No se pudo agregar el servicio proveedor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @PutMapping("/actualizar")
    public ServicioProveedorModelo actualizarServicioProveedor(@RequestBody ServicioProveedorModelo servicioProveedor){
        return servicioProveedorServicio.actualizarServicioProveedor(servicioProveedor);
    }
    
    @GetMapping("/filtrar/{precio}")
    public List<ServicioProveedorModelo> obtenerServicioProveedorPorPrecio(@PathVariable("precio") double precio){
    	return servicioProveedorServicio.obtenerServicioProveedorPorPrecio(precio);
    }

}
