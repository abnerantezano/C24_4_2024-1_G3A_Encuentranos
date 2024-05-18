package com.proyecto.encuentranos.controladores;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.encuentranos.modelos.CalificacionProveedorModelo;
import com.proyecto.encuentranos.modelos.CalificacionProveedorPk;
import com.proyecto.encuentranos.servicios.CalificacionProveedorServicio;
//ESTAMOS CREANDO EL CONTROLADOR PARA CalificacionProveedor
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/calificacion-proveedor")
public class CalificacionProveedorControlador {

	//INSTANCIAR LAS CLASES QUE USAREMOS

	@Autowired
	CalificacionProveedorServicio calificacionProveedorServicio;

	//AGREGAR LA CALIFICACION DE UN PROVEEDOR
    @PostMapping("/agregar")
    public ResponseEntity<?> guardarCalificacionProveedor(@RequestBody CalificacionProveedorModelo calificacionProveedor) {
        try {
            calificacionProveedor.setId(new CalificacionProveedorPk(calificacionProveedor.getIdProveedor().getId(), calificacionProveedor.getIdCliente().getId()));
            calificacionProveedor = calificacionProveedorServicio.guardarCalificacionProveedor(calificacionProveedor);
            
            // RECALCULAR LA CALFICACION PROMEDIO DEL PROVEEDOR
            Integer idProveedor = calificacionProveedor.getIdProveedor().getId();
            Double calificacionPromedio = calificacionProveedorServicio.calcularCalificacionPromedioProveedor(idProveedor);
            calificacionProveedorServicio.actualizarCalificacionPromedioProveedor(idProveedor, calificacionPromedio);
            
            return new ResponseEntity<>(calificacionProveedor, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("No se pudo agregar la calificación del proveedor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //LISTADO LA TABLA CALIFICACION PROVEEDOR
    @GetMapping("/listar")
    public ArrayList<CalificacionProveedorModelo> obtenerCalificacionesProveedores(){
        return calificacionProveedorServicio.obtenerCalificacionesProveedores();
    }
    
    //OBTENER LA CALIFICACION PROVEEDOR POR EL ID DEL CLIENTE Y EL PROVEEDOR QUE INTERVIENEN
    @GetMapping("/buscar/{idCliente}/{idProveedor}")
    public Optional<CalificacionProveedorModelo> obtenerCalificacionProveedorPorId(@PathVariable("idProveedor") Integer idProveedor, @PathVariable("idCliente") Integer idCliente){
    	CalificacionProveedorPk id = new CalificacionProveedorPk(idProveedor, idCliente);
        return calificacionProveedorServicio.obtenerCalificacionProveedorPorId(id);
    }
}
