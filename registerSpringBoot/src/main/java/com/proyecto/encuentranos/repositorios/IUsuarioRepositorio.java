package com.proyecto.encuentranos.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.encuentranos.modelos.UsuarioModelo;
//CREANDO EL REPOSITORIO PARA UsuarioModelo
@Repository
public interface IUsuarioRepositorio extends JpaRepository<UsuarioModelo, Integer>{
	
	//ESTAMOS OBTENIENDO EL CORREO DE UN USUARIO
	Optional <UsuarioModelo> findByCorreo(String correo);
	
	//ESTAMOS USANDO UN BOOLEANO PARA VERIFICAR SI EL CORREO DE UN USUARIO EXISTE O NO
    boolean existsByCorreo(String correo);
}
