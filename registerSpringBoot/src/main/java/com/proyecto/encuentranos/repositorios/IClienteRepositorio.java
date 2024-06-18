package com.proyecto.encuentranos.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.encuentranos.modelos.ClienteModelo;
//CREANDO EL REPOSITORIO PARA ClienteModelo
@Repository
public interface IClienteRepositorio extends JpaRepository<ClienteModelo, Integer> {
	
	//ESTAMOS USANDO ESTE BOOLEANDO PARA COMPROBAR SI EXISTE UN CLIENTE CON ESTE USUARIO 
	//SE ESTA BUSCANDO POR SU CORREO DEL USUARIO,
	//NOS SIRVE PARA REDIRECCIONAR DEPENDIENDO LO QUE RETORNE EL BOOLEANO
    boolean existsByIdUsuarioCorreo(String correo);
    
    //ESTAMOS CREANDO UNA OPCION DE BUSCAR EL ID DE UN USUARIO DESDE DEL CLIENTE
    Optional<ClienteModelo> findByIdUsuarioIdUsuario(Integer usuarioId);
    
    Optional<ClienteModelo> findByIdUsuarioCorreo(String email);
}
