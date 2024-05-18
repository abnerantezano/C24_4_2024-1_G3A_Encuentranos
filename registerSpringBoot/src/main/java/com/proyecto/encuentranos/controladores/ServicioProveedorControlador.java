package com.proyecto.encuentranos.controladores;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.encuentranos.modelos.ServicioModelo;
import com.proyecto.encuentranos.modelos.ServicioProveedorModelo;
import com.proyecto.encuentranos.modelos.ServicioProveedorPk;
import com.proyecto.encuentranos.servicios.ServicioProveedorServicio;
//ESTAMOS CREANDO EL CONTROLADOR PARA ServicioProveedor
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/servicio-proveedor")
public class ServicioProveedorControlador {

	//INSTANCIAR LAS CLASES QUE USAREMOS

    @Autowired
    ServicioProveedorServicio servicioProveedorServicio;
    
    //AGREGAR A LA TABLA SERVICIO_PROVEEDOR
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
    
    //LISTAR LOS SERVICIOS PROVEEDORES
    @GetMapping("/listar")
    public ArrayList<ServicioProveedorModelo> obtenerServiciosProveedores(){
        return servicioProveedorServicio.obtenerServiciosProveedores();
    }
    
    //ACTUALIZAR SERVICIOS PROVEEDOR
    @PutMapping("/actualizar")
    public ServicioProveedorModelo actualizarServicioProveedor(@RequestBody ServicioProveedorModelo servicioProveedor){
        return servicioProveedorServicio.actualizarServicioProveedor(servicioProveedor);
    }
    
    //ELIMINAR SERVICIOS PROVEEDOR
    @DeleteMapping("/eliminar/{idServicio}/{idProveedor}")
    public String eliminarServicioProveedor(@PathVariable("idServicio") Integer idServicio, @PathVariable("idProveedor") Integer idProveedor) {
        ServicioProveedorPk id = new ServicioProveedorPk(idServicio, idProveedor);

    	boolean ok = this.servicioProveedorServicio.eliminarServicioProveedor(id);
    	
    	if(ok) {
    		return "Servicio Proveedor Eliminado" + id;
    	}else {
    		return "No eliminado";
    	}
    }
    
    //BUSCAR EN LA TABLA SERVICIOROVEEDOR INGRESANDO EL ID DEL SERVICIO Y EL ID DEL PROVEEDOR
    @GetMapping("/buscar/{idServicio}/{idProveedor}")
    public Optional<ServicioProveedorModelo> obtenerServicioProveedorPorId(@PathVariable("idServicio") Integer idServicio, @PathVariable("idProveedor") Integer idProveedor){
        ServicioProveedorPk id = new ServicioProveedorPk(idServicio, idProveedor);
        return servicioProveedorServicio.obtenerServicioProveedorPorId(id);
    }
    
    //OBTENER LOS SERVICIOS DE UN PROVEEDOR POR SU PRECIO
    @GetMapping("/filtrar/{precio}")
    public List<ServicioProveedorModelo> obtenerServicioProveedorPorPrecio(@PathVariable("precio") double precio){
    	return servicioProveedorServicio.obtenerServicioProveedorPorPrecio(precio);
    }
    
    //OBTENER LOS SERVICIOS A LOS QUE EL PROVEEDOR NO ESTA REGISTRADO
    @GetMapping("/servicios-no-registrados/{idProveedor}")
    public ArrayList<ServicioModelo> obtenerServiciosNoRegistrados(@PathVariable("idProveedor") Integer idProveedor){
    	return servicioProveedorServicio.obtenerServiciosNoRegistrados(idProveedor);
    }
    
    //OBTENER LOS SERVICIOS REGISTRADOS POR UN PROVEEDOR
    @GetMapping("/servicios-registrados/{idProveedor}")
    public List<ServicioModelo> obtenerServiciosDeProveedorPorId(@PathVariable("idProveedor") Integer idProveedor){
    	return servicioProveedorServicio.obtenerServiciosDeProveedorPorId(idProveedor);
    }

}
