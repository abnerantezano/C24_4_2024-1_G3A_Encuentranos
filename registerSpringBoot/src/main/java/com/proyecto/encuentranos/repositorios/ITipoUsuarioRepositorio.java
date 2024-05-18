package com.proyecto.encuentranos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.encuentranos.modelos.TipoUsuarioModelo;
//CREANDO EL REPOSITORIO PARA TipoUsuarioModelo
@Repository
public interface ITipoUsuarioRepositorio extends JpaRepository<TipoUsuarioModelo, Integer>{
	
}
