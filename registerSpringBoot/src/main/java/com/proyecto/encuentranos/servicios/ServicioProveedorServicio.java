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

@Service
public class ServicioProveedorServicio {

    @Autowired
    IServicioProveedorRepositorio servicioProveedorRepositorio;

    @Autowired
    IServicioRepositorio servicioRepositorio;

    public ArrayList<ServicioProveedorModelo> obtenerServiciosProveedores() {
        return (ArrayList<ServicioProveedorModelo>) servicioProveedorRepositorio.findAll();
    }

    public Optional<ServicioProveedorModelo> obtenerServicioProveedorPorId(ServicioProveedorPk id) {
        return servicioProveedorRepositorio.findById(id);
    }

    public ServicioProveedorModelo agregarServicioProveedor(ServicioProveedorModelo servicioProveedor) {
        return servicioProveedorRepositorio.save(servicioProveedor);
    }

    public ServicioProveedorModelo actualizarServicioProveedor(ServicioProveedorModelo servicioProveedor) {
        return servicioProveedorRepositorio.save(servicioProveedor);
    }

    public List<ServicioProveedorModelo> obtenerServicioProveedorPorPrecio(double precio) {
        return servicioProveedorRepositorio.findAll().stream()
                .filter(servicio -> servicio.getPrecio() <= precio)
                .collect(Collectors.toList());
    }

    public ArrayList<ServicioModelo> obtenerServiciosNoRegistrados(Integer idProveedor) {
        // Obtener todos los servicios
        List<ServicioModelo> todosLosServicios = servicioRepositorio.findAll();

        // Obtener los servicios registrados por el proveedor específico
        List<ServicioProveedorModelo> serviciosProveedores = servicioProveedorRepositorio.findAll().stream()
                .filter(servicioProveedor -> servicioProveedor.getIdProveedor().getId().equals(idProveedor))
                .collect(Collectors.toList());

        // Extraer los IDs de los servicios que ya están registrados por el proveedor
        List<Integer> serviciosRegistradosIds = serviciosProveedores.stream()
                .map(servicioProveedor -> servicioProveedor.getIdServicio().getId())
                .distinct()
                .collect(Collectors.toList());

        // Filtrar los servicios no registrados por el proveedor
        List<ServicioModelo> serviciosNoRegistrados = todosLosServicios.stream()
                .filter(servicio -> !serviciosRegistradosIds.contains(servicio.getId()))
                .collect(Collectors.toList());

        return new ArrayList<>(serviciosNoRegistrados);
    }
    
    public List<ServicioModelo> obtenerServiciosDeProveedorPorId(Integer idProveedor) {
        // Obtener los IDs de los servicios registrados por el proveedor específico
        List<Integer> serviciosRegistradosIds = servicioProveedorRepositorio.findAll().stream()
                .filter(servicioProveedor -> servicioProveedor.getIdProveedor().getId().equals(idProveedor))
                .map(servicioProveedor -> servicioProveedor.getIdServicio().getId())
                .distinct()
                .collect(Collectors.toList());

        // Filtrar los servicios registrados por el proveedor
        return servicioRepositorio.findAll().stream()
                .filter(servicio -> serviciosRegistradosIds.contains(servicio.getId()))
                .collect(Collectors.toList());
    }

}
