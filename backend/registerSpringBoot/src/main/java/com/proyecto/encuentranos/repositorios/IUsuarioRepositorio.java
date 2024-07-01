package com.proyecto.encuentranos.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.encuentranos.modelos.UsuarioModelo;
@Repository
public interface IUsuarioRepositorio extends JpaRepository<UsuarioModelo, Integer>{
	
	Optional <UsuarioModelo> findByCorreo(String correo);
	
    boolean existsByCorreo(String correo);
}
