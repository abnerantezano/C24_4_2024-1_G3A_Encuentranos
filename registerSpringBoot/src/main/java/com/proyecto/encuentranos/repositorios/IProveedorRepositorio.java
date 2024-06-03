package com.proyecto.encuentranos.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.encuentranos.modelos.ProveedorModelo;
//CREANDO EL REPOSITORIO PARA ProveedorModelo
@Repository
public interface IProveedorRepositorio extends JpaRepository<ProveedorModelo, Integer>{
	
	//ESTAMOS OBTENIENDO PROVEEDORES QUE PERTENEZCAN AL DISTRITO QUE SE ASIGNE
    List<ProveedorModelo> findByIdDistritoNombre(String nombreDistrito);
    
	//ESTAMOS USANDO ESTE BOOLEANDO PARA COMPROBAR SI EXISTE UN PROVEEDOR CON ESTE USUARIO 
	//SE ESTA BUSCANDO POR SU CORREO DEL USUARIO,
	//NOS SIRVE PARA REDIRECCIONAR DEPENDIENDO LO QUE RETORNE EL BOOLEANO
    boolean existsByIdUsuarioCorreo(String correo);
    
    //ESTAMOS CREANDO UNA OPCION DE BUSCAR EL ID DE UN USUARIO DESDE DEL PROVEEDOR
    Optional<ProveedorModelo> findByIdUsuarioIdUsuario(Integer usuarioId);

}
