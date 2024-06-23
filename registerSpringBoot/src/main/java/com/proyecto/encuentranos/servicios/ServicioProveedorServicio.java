package com.proyecto.encuentranos.servicios;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.ServicioModelo;
import com.proyecto.encuentranos.modelos.ServicioProveedorModelo;
import com.proyecto.encuentranos.modelos.ServicioProveedorModeloId;
import com.proyecto.encuentranos.repositorios.IServicioProveedorRepositorio;
import com.proyecto.encuentranos.repositorios.IServicioRepositorio;

// ESTAMOS CREANDO EL SERVICIO PARA ServicioProveedor
@Service
public class ServicioProveedorServicio {

    // INYECTAR LAS DEPENDENCIAS
    private final IServicioProveedorRepositorio servicioProveedorRepositorio;
    private final IServicioRepositorio servicioRepositorio;

    @Autowired
    public ServicioProveedorServicio(IServicioProveedorRepositorio servicioProveedorRepositorio, IServicioRepositorio servicioRepositorio) {
        this.servicioProveedorRepositorio = servicioProveedorRepositorio;
        this.servicioRepositorio = servicioRepositorio;
    }

    // CRUD

    // CREATE
    public ServicioProveedorModelo agregarServicioProveedor(ServicioProveedorModelo servicioProveedor) {
        return servicioProveedorRepositorio.save(servicioProveedor);
    }

    // READ
    public List<ServicioProveedorModelo> obtenerServiciosProveedores() {
        return servicioProveedorRepositorio.findAll();
    }

    // UPDATE
    public ServicioProveedorModelo actualizarServicioProveedor(ServicioProveedorModelo servicioProveedor) {
        return servicioProveedorRepositorio.save(servicioProveedor);
    }

    // DELETE
    public boolean eliminarServicioProveedor(ServicioProveedorModeloId id) {
        try {
            servicioProveedorRepositorio.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // OBTENER EL SERVICIO_PROVEEDOR POR EL ID
    public Optional<ServicioProveedorModelo> obtenerServicioProveedorPorId(ServicioProveedorModeloId id) {
        return servicioProveedorRepositorio.findById(id);
    }

    // OBTENER EL SERVICIO_PROVEEDOR POR PRECIO
    public List<ServicioProveedorModelo> obtenerServicioProveedorPorPrecio(double precio) {
        return servicioProveedorRepositorio.findAll().stream()
                .filter(servicio -> servicio.getPrecio() <= precio)
                .toList();
    }

    // OBTENER EL PRECIO MÁS ALTO DE LA TABLA SERVICIO_PROVEEDOR
    public Optional<ServicioProveedorModelo> obtenerServicioProveedorAlto() {
        return servicioProveedorRepositorio.findAll().stream()
                .max(Comparator.comparing(ServicioProveedorModelo::getPrecio));
    }

    // OBTENER EL PRECIO MÁS BAJO DE LA TABLA SERVICIO_PROVEEDOR
    public Optional<ServicioProveedorModelo> obtenerServicioProveedorBajo() {
        return servicioProveedorRepositorio.findAll().stream()
                .min(Comparator.comparing(ServicioProveedorModelo::getPrecio));
    }

    // OBTENER EL PRECIO MÁS ALTO Y MÁS BAJO DE LA TABLA SERVICIO_PROVEEDOR
    public Map<String, List<ServicioProveedorModelo>> obtenerServiciosProveedorExtremos() {

        List<ServicioProveedorModelo> servicios = servicioProveedorRepositorio.findAll();

        // OBTENIENDO EL PRECIO MÁS ALTO
        double maxPrecio = servicios.stream()
                .mapToDouble(ServicioProveedorModelo::getPrecio)
                .max()
                .orElse(Double.MIN_VALUE);
        List<ServicioProveedorModelo> serviciosMasCaros = servicios.stream()
                .filter(servicio -> servicio.getPrecio() == maxPrecio)
                .toList();

        // OBTENIENDO EL PRECIO MÁS BAJO
        double minPrecio = servicios.stream()
                .mapToDouble(ServicioProveedorModelo::getPrecio)
                .min()
                .orElse(Double.MAX_VALUE);

        List<ServicioProveedorModelo> serviciosMasBaratos = servicios.stream()
                .filter(servicio -> servicio.getPrecio() == minPrecio)
                .toList();

        Map<String, List<ServicioProveedorModelo>> extremos = new HashMap<>();
        extremos.put("serviciosMasCaros", serviciosMasCaros);
        extremos.put("serviciosMasBaratos", serviciosMasBaratos);

        return extremos;
    }

    // OBTENER LOS SERVICIOS QUE NO ESTÁN REGISTRADOS A UN PROVEEDOR ESPECÍFICO
    public List<ServicioModelo> obtenerServiciosNoRegistrados(Integer idProveedor) {

        List<ServicioModelo> todosLosServicios = servicioRepositorio.findAll();

        // OBTENER LOS SERVICIOS REGISTRADOS POR EL PROVEEDOR
        List<ServicioProveedorModelo> serviciosProveedores = servicioProveedorRepositorio.findAll().stream()
                .filter(servicioProveedor -> servicioProveedor.getIdProveedor().getIdProveedor() == idProveedor)
                .toList();

        // EXTRAEMOS LOS IDS DE LOS SERVICIOS YA REGISTRADOS
        List<Integer> serviciosRegistradosIds = serviciosProveedores.stream()
                .map(servicioProveedor -> servicioProveedor.getIdServicio().getIdServicio())
                .distinct()
                .toList();

        // FILTRAMOS LOS SERVICIOS NO REGISTRADOS POR EL PROVEEDOR
        List<ServicioModelo> serviciosNoRegistrados = todosLosServicios.stream()
                .filter(servicio -> !serviciosRegistradosIds.contains(servicio.getIdServicio()))
                .toList();

        return new ArrayList<>(serviciosNoRegistrados);
    }

    // OBTENER LOS SERVICIOS QUE ESTÁN REGISTRADOS A UN PROVEEDOR ESPECÍFICO
    public List<ServicioModelo> obtenerServiciosDeProveedorPorId(Integer idProveedor) {

        // OBTENER EL ID DE LOS SERVICIOS REGISTRADOS
        List<Integer> serviciosRegistradosIds = servicioProveedorRepositorio.findAll().stream()
                .filter(servicioProveedor -> servicioProveedor.getIdProveedor().getIdProveedor() == idProveedor)
                .map(servicioProveedor -> servicioProveedor.getIdServicio().getIdServicio())
                .distinct()
                .toList();

        // FILTRAMOS LOS SERVICIOS REGISTRADOS POR EL PROVEEDOR
        return servicioRepositorio.findAll().stream()
                .filter(servicio -> serviciosRegistradosIds.contains(servicio.getIdServicio()))
                .toList();
    }
    
    //OBTENER SERVICIO PROVEEDOR POR EL ID DEL PROVEEDOR
    public List<ServicioProveedorModelo> obtenerServicioProveedorPorIdProveedor(int idProvedor) {
        return servicioProveedorRepositorio.findByIdIdProveedor(idProvedor);
    }

    public List<ServicioProveedorModelo> obtenerServiciosProveedorNegociablePorIdProveedor(int idProveedor) {
        List<ServicioProveedorModelo> serviciosProveedor = servicioProveedorRepositorio.findByIdIdProveedor(idProveedor);
        return serviciosProveedor.stream()
                .filter(ServicioProveedorModelo::isNegociable)
                .toList(); // Usar Stream.toList() para lista inmutable
    }

    public List<ServicioProveedorModelo> obtenerServiciosProveedorNoNegociablePorIdProveedor(int idProveedor) {
        List<ServicioProveedorModelo> serviciosProveedor = servicioProveedorRepositorio.findByIdIdProveedor(idProveedor);
        return serviciosProveedor.stream()
                .filter(servicio -> !servicio.isNegociable())
                .toList(); // Usar Stream.toList() para lista inmutable
    }

}
