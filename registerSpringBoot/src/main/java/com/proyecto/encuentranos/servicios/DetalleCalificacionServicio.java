package com.proyecto.encuentranos.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.DetalleCalificacionModelo;
import com.proyecto.encuentranos.modelos.DetalleCalificacionModeloId;
import com.proyecto.encuentranos.modelos.ProveedorModelo;
import com.proyecto.encuentranos.repositorios.IDetalleCalificacionRepositorio;
import com.proyecto.encuentranos.repositorios.IProveedorRepositorio;
//ESTAMOS CREANDO EL SERVICIO PARA CalificacionProveedor
@Service
public class DetalleCalificacionServicio {

    //INSTANCIAR LAS CLASES QUE USAREMOS
    private final IDetalleCalificacionRepositorio detalleCalificacionRepositorio;
    private final IProveedorRepositorio proveedorRepositorio;

    @Autowired
    public DetalleCalificacionServicio(IDetalleCalificacionRepositorio detalleCalificacionRepositorio, IProveedorRepositorio proveedorRepositorio) {
        this.detalleCalificacionRepositorio = detalleCalificacionRepositorio;
        this.proveedorRepositorio = proveedorRepositorio;
    }

    //CRUD
    
    //CREATE
    public DetalleCalificacionModelo guardarCalificacionProveedor(DetalleCalificacionModelo calificacionProveedor) {
        calificacionProveedor = detalleCalificacionRepositorio.save(calificacionProveedor);
        
        // RECALCULAR LA CALIFICACION DEL PROVEEDOR
        Integer idProveedor = calificacionProveedor.getIdProveedor().getIdProveedor();
        Double calificacionPromedio = calcularCalificacionPromedioProveedor(idProveedor);
        actualizarCalificacionPromedioProveedor(idProveedor, calificacionPromedio);
        
        return calificacionProveedor;
    }
    
    //READ
    public List<DetalleCalificacionModelo> obtenerCalificacionesProveedores(){
        return detalleCalificacionRepositorio.findAll();
    }
    //----------------------------------------
    
    //METODO PARA OBTENER LAS CALIFICACIONES DE UN PROVEEDOR POR SU ID
    public Optional<DetalleCalificacionModelo> obtenerCalificacionProveedorPorId(DetalleCalificacionModeloId id){
        return detalleCalificacionRepositorio.findById(id);
    }

    public List<DetalleCalificacionModelo> obtenerCalificacionesPorProveedorYServicio(Integer idProveedor, Integer idServicio) {
        return detalleCalificacionRepositorio.findByIdIdProveedorAndIdIdServicio(idProveedor, idServicio);
    }
    public List<DetalleCalificacionModelo> obtenerCalificacionesPorProveedor(Integer idProveedor) {
        return detalleCalificacionRepositorio.findByIdIdProveedor(idProveedor);
    }


    
    //CALCULAR PROMEDIO DEL PROVEEDOR
    public Double calcularCalificacionPromedioProveedor(Integer idProveedor) {
        List<DetalleCalificacionModelo> calificaciones = detalleCalificacionRepositorio.obtenerCalificacionesPorIdProveedor(idProveedor);
        
        if (calificaciones.isEmpty()) {
            return 0.0;
        }
        
        double sum = 0;
        for (DetalleCalificacionModelo calificacion : calificaciones) {
            sum += calificacion.getIdCalificacion().getNumero();
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
