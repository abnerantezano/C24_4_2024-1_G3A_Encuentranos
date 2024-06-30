package com.proyecto.encuentranos.controladores;

import com.proyecto.encuentranos.modelos.ChatModelo;
import com.proyecto.encuentranos.modelos.MensajeModelo;
import com.proyecto.encuentranos.servicios.MensajeServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mensaje")
public class MensajeControlador {

    private final MensajeServicio mensajeServicio;

    @Autowired
    public MensajeControlador(MensajeServicio mensajeServicio) {
        this.mensajeServicio = mensajeServicio;
    }

    @GetMapping("/listar")
    public List<MensajeModelo> getAllMensajes() {
        return mensajeServicio.getAllMensajes();
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<MensajeModelo> getMensajeById(@PathVariable Integer id) {
        Optional<MensajeModelo> mensaje = mensajeServicio.getMensajeById(id);
        return mensaje.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/chat/{idChat}")
    public List<MensajeModelo> getMensajesByChatId(@PathVariable Integer idChat) {
        return mensajeServicio.getMensajesByChatId(idChat);
    }

    @PostMapping("/agregar/{idChat}")
    public MensajeModelo createMensaje(@RequestBody MensajeModelo mensaje, @PathVariable Integer idChat) {
        ChatModelo chat = new ChatModelo();
        chat.setIdChat(idChat);
        mensaje.setIdChat(chat);
        return mensajeServicio.crearMensaje(mensaje);
    }


    @GetMapping("/mensajes-por-emisor/{idEmisor}")
    public List<MensajeModelo> getMensajesByEmisorId(@PathVariable Integer idEmisor) {
        return mensajeServicio.getMensajesByEmisorId(idEmisor);
    }

    @GetMapping("/mensajes-por-receptor/{idReceptor}")
    public List<MensajeModelo> getMensajesByReceptorId(@PathVariable Integer idReceptor) {
        return mensajeServicio.getMensajesByReceptorId(idReceptor);
    }
}
