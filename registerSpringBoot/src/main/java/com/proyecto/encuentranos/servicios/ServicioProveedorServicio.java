package com.proyecto.encuentranos.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.ServicioProveedorModelo;
import com.proyecto.encuentranos.modelos.ServicioProveedorPk;
import com.proyecto.encuentranos.repositorios.IServicioProveedorRepositorio;

@Service
public class ServicioProveedorServicio {
    
    @Autowired
    IServicioProveedorRepositorio servicioProveedorRepositorio;
    
    public ArrayList<ServicioProveedorModelo> obtenerServiciosProveedores(){
        return (ArrayList<ServicioProveedorModelo>)servicioProveedorRepositorio.findAll();
    }
    
    public Optional<ServicioProveedorModelo> obtenerServicioProveedorPorId(ServicioProveedorPk id){
        return servicioProveedorRepositorio.findById(id);
    }
    
    public ServicioProveedorModelo agregarServicioProveedor(ServicioProveedorModelo servicioProveedor){
        return servicioProveedorRepositorio.save(servicioProveedor);
    }
    
    public ServicioProveedorModelo actualizarServicioProveedor(ServicioProveedorModelo servicioProveedor){
        return servicioProveedorRepositorio.save(servicioProveedor);
    }
    
    public List<ServicioProveedorModelo> obtenerServicioProveedorPorPrecio(double precio) {
        return servicioProveedorRepositorio.findAll().stream()
                .filter(servicio -> servicio.getPrecio() <= precio)
                .collect(Collectors.toList());
    }
}
