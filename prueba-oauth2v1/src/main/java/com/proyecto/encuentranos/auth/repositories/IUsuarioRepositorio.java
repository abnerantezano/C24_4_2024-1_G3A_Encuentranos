package com.proyecto.encuentranos.auth.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.encuentranos.auth.models.UsuarioModelo;

@Repository
public interface IUsuarioRepositorio extends JpaRepository<UsuarioModelo, Long>{
    Optional<UsuarioModelo> findByCorreo(String correo);
}
