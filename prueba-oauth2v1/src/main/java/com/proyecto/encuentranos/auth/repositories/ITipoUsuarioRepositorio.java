package com.proyecto.encuentranos.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.encuentranos.auth.models.TipoUsuarioModelo;


@Repository
public interface ITipoUsuarioRepositorio extends JpaRepository<TipoUsuarioModelo, Long>{

}
