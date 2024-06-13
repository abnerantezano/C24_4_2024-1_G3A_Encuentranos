package com.proyecto.encuentranos.controladores;

import com.proyecto.encuentranos.modelos.ChatModelo;
import com.proyecto.encuentranos.servicios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chat")
public class ChatControlador {

    private final ChatServicio chatServicio;

    @Autowired
    public ChatControlador(ChatServicio chatServicio) {
        this.chatServicio = chatServicio;
    }

    @GetMapping("/listar")
    public List<ChatModelo> getAllChats() {
        return chatServicio.getAllChats();
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ChatModelo> getChatById(@PathVariable Integer id) {
        Optional<ChatModelo> chat = chatServicio.getChatById(id);
        return chat.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/agregar")
    public ChatModelo createChat(@RequestBody ChatModelo chat) {
        return chatServicio.saveChat(chat);
    }
}
