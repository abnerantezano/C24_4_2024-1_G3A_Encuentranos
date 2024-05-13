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

@Service
public class CalificacionProveedorServicio {

    @Autowired
    ICalificacionProveedorRepositorio calificacionProveedorRepositorio;
    
    @Autowired
    IProveedorRepositorio proveedorRepositorio;
    
    public ArrayList<CalificacionProveedorModelo> obtenerCalificacionesProveedores(){
        return (ArrayList<CalificacionProveedorModelo>)calificacionProveedorRepositorio.findAll();
    }
    
    public Optional<CalificacionProveedorModelo> obtenerCalificacionProveedorPorId(CalificacionProveedorPk id){
        return calificacionProveedorRepositorio.findById(id);
    }
    
    public CalificacionProveedorModelo guardarCalificacionProveedor(CalificacionProveedorModelo calificacionProveedor) {
        calificacionProveedor = calificacionProveedorRepositorio.save(calificacionProveedor);
        
        // Recalcular la calificación promedio del proveedor
        Integer idProveedor = calificacionProveedor.getIdProveedor().getId();
        Double calificacionPromedio = calcularCalificacionPromedioProveedor(idProveedor);
        actualizarCalificacionPromedioProveedor(idProveedor, calificacionPromedio);
        
        return calificacionProveedor;
    }
    
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

    public void actualizarCalificacionPromedioProveedor(Integer idProveedor, Double calificacionPromedio) {
        ProveedorModelo proveedor = proveedorRepositorio.findById(idProveedor).orElse(null);
        
        if (proveedor != null) {
            proveedor.setCalificacionPromedio(calificacionPromedio);
            proveedorRepositorio.save(proveedor);
        }
    }

}
