package com.proyecto.encuentranos.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.encuentranos.modelos.MensajeModelo;
@Repository
public interface IMensajeRepositorio extends JpaRepository<MensajeModelo, Integer> {
	
    List<MensajeModelo> findByIdChatIdChat(Integer idChat);
    List<MensajeModelo> findByIdEmisorIdUsuario(Integer idEmisor);
    List<MensajeModelo> findByIdReceptorIdUsuario(Integer idReceptor);
    public Optional<MensajeModelo> findByIdEmisorIdUsuarioAndIdReceptorIdUsuario(Integer emisorId, Integer receptorId);
}
