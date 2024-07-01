package com.proyecto.encuentranos.controladores;

import com.amazonaws.services.migrationhubstrategyrecommendations.model.ResourceNotFoundException;
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
    public ResponseEntity<?> obtenerOcrearChat(
            @PathVariable int idCliente,
            @PathVariable int idProveedor) {
        try {
            ChatModelo chat = chatServicio.obtenerOcrearChat(idCliente, idProveedor);
            HttpStatus status = chat.getIdChat() > 0 ? HttpStatus.OK : HttpStatus.CREATED;
            return ResponseEntity.status(status).body(chat);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            ex.printStackTrace(); // Imprimir la traza de la excepción en la consola para debug
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
