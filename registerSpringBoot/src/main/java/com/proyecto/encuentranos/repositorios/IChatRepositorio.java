package com.proyecto.encuentranos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.encuentranos.modelos.ChatModelo;
//CREANDO EL REPOSITORIO PARA ChatModelo
@Repository
public interface IChatRepositorio extends JpaRepository<ChatModelo, Integer> {
}
