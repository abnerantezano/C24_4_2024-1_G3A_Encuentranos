package com.proyecto.encuentranos.servicios;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.ServicioModelo;
import com.proyecto.encuentranos.modelos.ServicioProveedorModelo;
import com.proyecto.encuentranos.modelos.ServicioProveedorModeloId;
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
    public boolean eliminarServicioProveedor(ServicioProveedorModeloId id) {
        try {
            servicioProveedorRepositorio.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    //----------------------------------------

    //OBTENER EL SERVICIO_PROVEEDOR PRO EL ID
    public Optional<ServicioProveedorModelo> obtenerServicioProveedorPorId(ServicioProveedorModeloId id) {
        return servicioProveedorRepositorio.findById(id);
    }

    //OBTENER EL SERVICIO_PROVEEDOR POR PRECIO
    public List<ServicioProveedorModelo> obtenerServicioProveedorPorPrecio(double precio) {
        return servicioProveedorRepositorio.findAll().stream()
                .filter(servicio -> servicio.getPrecio() <= precio)
                .collect(Collectors.toList());
    }
    
    //OBTENER EL PRECIO MAS ALTO DE LA TABLA SERVICIO_PROVEEDOR
    public Optional<ServicioProveedorModelo> obtenerServicioProveedorAlto(){
    	return servicioProveedorRepositorio.findAll().stream()
    			.max(Comparator.comparing(ServicioProveedorModelo::getPrecio));
    }
    
    //OBTENER EL PRECIO MAS BAJO DE LA TABLA SERVICIO_PROVEEDOR
    public Optional<ServicioProveedorModelo> obtenerServicioProveedorBajo(){
    	return servicioProveedorRepositorio.findAll().stream()
    			.min(Comparator.comparing(ServicioProveedorModelo::getPrecio));
    }
    
    //OBTENER EL PRECIO DE MAS ALTO Y MAS BAJO DE LA TABLA SERVICIO_PROVEEDOR
    public Map<String, List<ServicioProveedorModelo>> obtenerServiciosProveedorExtremos() {
    	
        List<ServicioProveedorModelo> servicios = servicioProveedorRepositorio.findAll();
        
        // OBTENIENDO EL PRECIO MAS ALTO
        double maxPrecio = servicios.stream()
                                    .mapToDouble(ServicioProveedorModelo::getPrecio)
                                    .max()
                                    .orElse(Double.MIN_VALUE);
        //FILTRAMOS LOS SERVICIOS MAS ALTOS
        List<ServicioProveedorModelo> serviciosMasCaros = servicios.stream()
                                                                    .filter(servicio -> servicio.getPrecio() == maxPrecio)
                                                                    .collect(Collectors.toList());
        
        // OBTENIENDO EL PRECIO MAS BAJO
        double minPrecio = servicios.stream()
                                    .mapToDouble(ServicioProveedorModelo::getPrecio)
                                    .min()
                                    .orElse(Double.MAX_VALUE);
        
        //FILTRAMOS LOS SERVICIOS MAS BAJOS
        List<ServicioProveedorModelo> serviciosMasBaratos = servicios.stream()
                                                                      .filter(servicio -> servicio.getPrecio() == minPrecio)
                                                                      .collect(Collectors.toList());
        
        // CREAMOS UN MAP AL QUE AGREGAREMOS LA LISTA DEPENDIENDO DE QUE SEA
        Map<String, List<ServicioProveedorModelo>> extremos = new HashMap<>();
        extremos.put("serviciosMasCaros", serviciosMasCaros);
        extremos.put("serviciosMasBaratos", serviciosMasBaratos);
        
        return extremos;
    }



  //OBTENER LOS SERVICIOS QUE NO ESTAN REGISTRADOS A UN PROVEEDOR ESPECIFICO
    public ArrayList<ServicioModelo> obtenerServiciosNoRegistrados(Integer idProveedor) {

        List<ServicioModelo> todosLosServicios = servicioRepositorio.findAll();

        // OBTENER LOS SERVICIOS REGISTRADOS POR EL PROVEEDOR
        List<ServicioProveedorModelo> serviciosProveedores = servicioProveedorRepositorio.findAll().stream()
                .filter(servicioProveedor -> servicioProveedor.getIdProveedor().getIdProveedor() == idProveedor)
                .collect(Collectors.toList());

        // EXTRAEMOS LOS IDS DE LOS SERVICIOS YA REGISTRADOS
        List<Integer> serviciosRegistradosIds = serviciosProveedores.stream()
                .map(servicioProveedor -> servicioProveedor.getIdServicio().getIdServicio())
                .distinct()
                .collect(Collectors.toList());

        // FILTRAMOS LOS SERVICIOS NO REGISTRADOS POR EL PROVEEDOR
        List<ServicioModelo> serviciosNoRegistrados = todosLosServicios.stream()
                .filter(servicio -> !serviciosRegistradosIds.contains(servicio.getIdServicio()))
                .collect(Collectors.toList());

        return new ArrayList<>(serviciosNoRegistrados);
    }

    
    //OBTENER LOS SERVICIOS QUE ESTAN REGISTRADOS A UN PROVEEDOR ESPECIFICO
    public List<ServicioModelo> obtenerServiciosDeProveedorPorId(Integer idProveedor) {
    	
        // OBTENER EL ID DE LOS SERVICIOS REGISTRADSO
        List<Integer> serviciosRegistradosIds = servicioProveedorRepositorio.findAll().stream()
                .filter(servicioProveedor -> servicioProveedor.getIdProveedor().getIdProveedor() == idProveedor)
                .map(servicioProveedor -> servicioProveedor.getIdServicio().getIdServicio())
                .distinct()
                .collect(Collectors.toList());

        // FILTRAMOS LOS SERVICIOS REGISTRADOS POR EL PROVEEDOR
        return servicioRepositorio.findAll().stream()
                .filter(servicio -> serviciosRegistradosIds.contains(servicio.getIdServicio()))
                .collect(Collectors.toList());
    }

}
