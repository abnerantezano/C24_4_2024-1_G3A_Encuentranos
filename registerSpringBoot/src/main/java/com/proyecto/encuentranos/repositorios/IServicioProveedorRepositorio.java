package com.proyecto.encuentranos.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.encuentranos.modelos.ServicioProveedorModelo;
import com.proyecto.encuentranos.modelos.ServicioProveedorModeloId;

@Repository
public interface IServicioProveedorRepositorio extends JpaRepository<ServicioProveedorModelo, ServicioProveedorModeloId>{

	List<ServicioProveedorModelo> findByIdProveedorIdDistritoNombre(String nombreDistrito);
	
	List<ServicioProveedorModelo> findByIdIdProveedor(int idProveedor);
}
