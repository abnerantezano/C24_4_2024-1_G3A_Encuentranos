package com.proyecto.encuentranos.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.MensajeModelo;
import com.proyecto.encuentranos.repositorios.IMensajeRepositorio;

@Service
public class MensajeServicio {

    private final IMensajeRepositorio mensajeRepositorio;

    @Autowired
    public MensajeServicio(IMensajeRepositorio mensajeRepositorio) {
        this.mensajeRepositorio = mensajeRepositorio;
    }

    public List<MensajeModelo> getAllMensajes() {
        return mensajeRepositorio.findAll();
    }

    public Optional<MensajeModelo> getMensajeById(Integer id) {
        return mensajeRepositorio.findById(id);
    }

    public List<MensajeModelo> getMensajesByChatId(Integer chatId) {
        return mensajeRepositorio.findByIdChatIdChat(chatId);
    }

    public MensajeModelo saveMensaje(MensajeModelo mensaje) {
        return mensajeRepositorio.save(mensaje);
    }
    
    public List<MensajeModelo> getMensajesByEmisorId(Integer receptorId) {
        return mensajeRepositorio.findByIdEmisorIdUsuario(receptorId);
    }
    
    public List<MensajeModelo> getMensajesByReceptorId(Integer receptorId) {
        return mensajeRepositorio.findByIdReceptorIdUsuario(receptorId);
    }

    public Optional<MensajeModelo> getMensajeByEmisorIdAndReceptorId(Integer emisorId, Integer receptorId) {
        return mensajeRepositorio.findByIdEmisorIdUsuarioAndIdReceptorIdUsuario(emisorId, receptorId);
    }

}