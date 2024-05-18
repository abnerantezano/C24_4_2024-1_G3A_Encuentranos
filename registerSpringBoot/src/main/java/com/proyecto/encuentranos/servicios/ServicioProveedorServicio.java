package com.proyecto.encuentranos.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.ServicioModelo;
import com.proyecto.encuentranos.modelos.ServicioProveedorModelo;
import com.proyecto.encuentranos.modelos.ServicioProveedorPk;
import com.proyecto.encuentranos.repositorios.IServicioProveedorRepositorio;
import com.proyecto.encuentranos.repositorios.IServicioRepositorio;
//ESTAMOS CREANDO EL SERVICIO PARA ServicioProveedor
@Service
public class ServicioProveedorServicio {

	//INSTANCIAR LAS CLASES QUE USAREMOS
	
    @Autowired
    IServicioProveedorRepositorio servicioProveedorRepositorio;

    @Autowired
    IServicioRepositorio servicioRepositorio;

    //CRUD
    
    //CREATE
    public ServicioProveedorModelo agregarServicioProveedor(ServicioProveedorModelo servicioProveedor) {
        return servicioProveedorRepositorio.save(servicioProveedor);
    }
    
    //READ
    public ArrayList<ServicioProveedorModelo> obtenerServiciosProveedores() {
        return (ArrayList<ServicioProveedorModelo>) servicioProveedorRepositorio.findAll();
    }
    
    //UPDATE
    public ServicioProveedorModelo actualizarServicioProveedor(ServicioProveedorModelo servicioProveedor) {
        return servicioProveedorRepositorio.save(servicioProveedor);
    }
    
    //DELETE
    public boolean eliminarServicioProveedor(ServicioProveedorPk id) {
        try {
            servicioProveedorRepositorio.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    //----------------------------------------

    //OBTENER EL SERVICIO_PROVEEDOR PRO EL ID
    public Optional<ServicioProveedorModelo> obtenerServicioProveedorPorId(ServicioProveedorPk id) {
        return servicioProveedorRepositorio.findById(id);
    }

    //OBTENER EL SERVICIO_PROVEEDOR POR PRECIO
    public List<ServicioProveedorModelo> obtenerServicioProveedorPorPrecio(double precio) {
        return servicioProveedorRepositorio.findAll().stream()
                .filter(servicio -> servicio.getPrecio() <= precio)
                .collect(Collectors.toList());
    }

    //OBTENER LOS SERVICIOS QUE NO ESTAN REGISTRADOS A UN PROVEEDOR ESPECIFICO
    public ArrayList<ServicioModelo> obtenerServiciosNoRegistrados(Integer idProveedor) {
        
        List<ServicioModelo> todosLosServicios = servicioRepositorio.findAll();

        // OBTENER LOS SERVICIOS REGISTRADOS POR EL PROVEEDOR
        List<ServicioProveedorModelo> serviciosProveedores = servicioProveedorRepositorio.findAll().stream()
                .filter(servicioProveedor -> servicioProveedor.getIdProveedor().getId().equals(idProveedor))
                .collect(Collectors.toList());

        // EXTRAEMOS LOS IDS DE LOS SERVICIOS YA REGISTRADOS
        List<Integer> serviciosRegistradosIds = serviciosProveedores.stream()
                .map(servicioProveedor -> servicioProveedor.getIdServicio().getId())
                .distinct()
                .collect(Collectors.toList());

        // FILTRAMOS LOS SERVICIOS NO REGISTRADOS POR EL PROVEEDOR
        List<ServicioModelo> serviciosNoRegistrados = todosLosServicios.stream()
                .filter(servicio -> !serviciosRegistradosIds.contains(servicio.getId()))
                .collect(Collectors.toList());

        return new ArrayList<>(serviciosNoRegistrados);
    }
    
    //OBTENER LOS SERVICIOS QUE ESTAN REGISTRADOS A UN PROVEEDOR ESPECIFICO
    public List<ServicioModelo> obtenerServiciosDeProveedorPorId(Integer idProveedor) {
    	
        // OBTENER EL ID DE LOS SERVICIOS REGISTRADSO
        List<Integer> serviciosRegistradosIds = servicioProveedorRepositorio.findAll().stream()
                .filter(servicioProveedor -> servicioProveedor.getIdProveedor().getId().equals(idProveedor))
                .map(servicioProveedor -> servicioProveedor.getIdServicio().getId())
                .distinct()
                .collect(Collectors.toList());

        // FILTRAMOS LOS SERVICIOS REGISTRADOS POR EL PROVEEDOR
        return servicioRepositorio.findAll().stream()
                .filter(servicio -> serviciosRegistradosIds.contains(servicio.getId()))
                .collect(Collectors.toList());
    }

}
