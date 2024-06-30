package com.proyecto.encuentranos.controladores;

import com.proyecto.encuentranos.modelos.ChatModelo;
import com.proyecto.encuentranos.servicios.ChatServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public List<ChatModelo> listarChats() {
        return chatServicio.listarChats();
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ChatModelo> obtenerChatsPorId(@PathVariable Integer id) {
        Optional<ChatModelo> chat = chatServicio.buscarChatPorId(id);
        return chat.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<ChatModelo>> obtenerChatsPorCliente(@PathVariable Integer idCliente) {
        List<ChatModelo> chat = chatServicio.obtenerChatsPorCliente(idCliente);
        return new ResponseEntity<>(chat, HttpStatus.CREATED);
    }

    @GetMapping("/proveedor/{idProveedor}")
    public ResponseEntity<List<ChatModelo>> obtenerChatsPorProveedor(@PathVariable Integer idProveedor) {
        List<ChatModelo> chat = chatServicio.obtenerChatsPorProveedor(idProveedor);
        return new ResponseEntity<>(chat, HttpStatus.CREATED);
    }

    @PostMapping("/agregar")
    public ChatModelo agregarChat(@RequestBody ChatModelo chat) {
        return chatServicio.agregarChat(chat);
    }

    @PostMapping("/{idCliente}/{idProveedor}")
    public ResponseEntity<ChatModelo> obtenerOcrearChat(
            @PathVariable int idCliente,
            @PathVariable int idProveedor) {
        ChatModelo chat = chatServicio.obtenerOcrearChat(idCliente, idProveedor);
        return ResponseEntity.ok(chat);
    }
}
