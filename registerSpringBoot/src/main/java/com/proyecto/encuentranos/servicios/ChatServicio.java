package com.proyecto.encuentranos.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.ChatModelo;
import com.proyecto.encuentranos.repositorios.IChatRepositorio;


@Service
public class ChatServicio {

    @Autowired
    private IChatRepositorio chatRepositorio;

    public List<ChatModelo> getAllChats() {
        return chatRepositorio.findAll();
    }

    public Optional<ChatModelo> getChatById(Integer id) {
        return chatRepositorio.findById(id);
    }

    public ChatModelo saveChat(ChatModelo chat) {
        return chatRepositorio.save(chat);
    }
}