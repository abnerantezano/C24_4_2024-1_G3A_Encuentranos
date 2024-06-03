package com.proyecto.encuentranos.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.*;
import com.proyecto.encuentranos.repositorios.*;

@Service
public class ChatServicio {

    @Autowired
    private IChatRepositorio chatRepositorio;

    @Autowired
    private IChatUsuarioRepositorio chatUsuarioRepositorio;

    public ChatModelo crearChat() {
        ChatModelo chat = new ChatModelo();
        return chatRepositorio.save(chat);
    }
    
}