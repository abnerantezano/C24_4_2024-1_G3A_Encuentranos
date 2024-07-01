package com.proyecto.encuentranos.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.encuentranos.modelos.ClienteModelo;
@Repository
public interface IClienteRepositorio extends JpaRepository<ClienteModelo, Integer> {

    boolean existsByIdUsuarioCorreo(String correo);
    
    Optional<ClienteModelo> findByIdUsuarioIdUsuario(Integer usuarioId);
    
    Optional<ClienteModelo> findByIdUsuarioCorreo(String email);
}
