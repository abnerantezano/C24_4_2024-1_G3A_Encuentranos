package com.proyecto.encuentranos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.encuentranos.models.UsuarioModel;
@Repository

public interface IUsuarioRepositorio extends JpaRepository<UsuarioModel, Long>{

}
