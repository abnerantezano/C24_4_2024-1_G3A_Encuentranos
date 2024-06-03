package com.proyecto.encuentranos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.encuentranos.modelos.ChatUsuarioModelo;
import com.proyecto.encuentranos.modelos.ChatUsuarioModeloId;
//CREANDO EL REPOSITORIO PARA ChatUsuarioModelo
@Repository
public interface IChatUsuarioRepositorio extends JpaRepository<ChatUsuarioModelo, ChatUsuarioModeloId> {
}
