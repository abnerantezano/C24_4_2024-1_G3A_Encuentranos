package com.proyecto.encuentranos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.encuentranos.modelos.ClienteModelo;
@Repository
public interface IClienteRepositorio extends JpaRepository<ClienteModelo, Long>{
    boolean existsByEmail(String email);
}
