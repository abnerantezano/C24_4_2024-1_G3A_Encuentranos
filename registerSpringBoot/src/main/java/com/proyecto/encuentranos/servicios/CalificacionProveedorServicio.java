package com.proyecto.encuentranos.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.CalificacionProveedorModelo;
import com.proyecto.encuentranos.modelos.CalificacionProveedorPk;
import com.proyecto.encuentranos.modelos.ProveedorModelo;
import com.proyecto.encuentranos.repositorios.ICalificacionProveedorRepositorio;
import com.proyecto.encuentranos.repositorios.IProveedorRepositorio;
//ESTAMOS CREANDO EL SERVICIO PARA CalificacionProveedor
@Service
public class CalificacionProveedorServicio {

	//INSTANCIAR LAS CLASES QUE USAREMOS
	
    @Autowired
    ICalificacionProveedorRepositorio calificacionProveedorRepositorio;
    
    @Autowired
    IProveedorRepositorio proveedorRepositorio;
    
    //CRUD
    
    //CREATE
    public CalificacionProveedorModelo guardarCalificacionProveedor(CalificacionProveedorModelo calificacionProveedor) {
        calificacionProveedor = calificacionProveedorRepositorio.save(calificacionProveedor);
        
        // RECALCULAR LA CALIFICACION DEL PROVEEDOR
        Integer idProveedor = calificacionProveedor.getIdProveedor().getId();
        Double calificacionPromedio = calcularCalificacionPromedioProveedor(idProveedor);
        actualizarCalificacionPromedioProveedor(idProveedor, calificacionPromedio);
        
        return calificacionProveedor;
    }
    
    //READ
    public ArrayList<CalificacionProveedorModelo> obtenerCalificacionesProveedores(){
        return (ArrayList<CalificacionProveedorModelo>)calificacionProveedorRepositorio.findAll();
    }
    //----------------------------------------
    
    //METODO PARA OBTENER LAS CALIFICACIONES DE UN PROVEEDOR POR SU ID
    public Optional<CalificacionProveedorModelo> obtenerCalificacionProveedorPorId(CalificacionProveedorPk id){
        return calificacionProveedorRepositorio.findById(id);
    }
    
    //CALCULAR PROMEDIO DEL PROVEEDOR
    public Double calcularCalificacionPromedioProveedor(Integer idProveedor) {
        List<CalificacionProveedorModelo> calificaciones = calificacionProveedorRepositorio.obtenerCalificacionesPorIdProveedor(idProveedor);
        
        if (calificaciones.isEmpty()) {
            return 0.0;
        }
        
        double sum = 0;
        for (CalificacionProveedorModelo calificacion : calificaciones) {
            sum += calificacion.getCalificacion();
        }
        
        return sum / calificaciones.size();
    }

    //ACTUALIZAR EL PROMEDIO 
    public void actualizarCalificacionPromedioProveedor(Integer idProveedor, Double calificacionPromedio) {
        ProveedorModelo proveedor = proveedorRepositorio.findById(idProveedor).orElse(null);
        
        if (proveedor != null) {
            proveedor.setCalificacionPromedio(calificacionPromedio);
            proveedorRepositorio.save(proveedor);
        }
    }
}
