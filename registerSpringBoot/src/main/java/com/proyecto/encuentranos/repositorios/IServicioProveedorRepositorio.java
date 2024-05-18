package com.proyecto.encuentranos.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.encuentranos.modelos.ServicioProveedorModelo;
import com.proyecto.encuentranos.modelos.ServicioProveedorPk;
//CREANDO EL REPOSITORIO PARA ServicioProveedorModelo
@Repository
public interface IServicioProveedorRepositorio extends JpaRepository<ServicioProveedorModelo, ServicioProveedorPk>{
    
	//ESTAMOS OBTENIENDO LOS SERVICIOS DE UN PROVEEDOR PERO QUE ESTEN EN EL DISTRITO 
	//QUE SE ASIGNE
	List<ServicioProveedorModelo> findByIdProveedorIdDistritoNombre(String nombreDistrito);
}
