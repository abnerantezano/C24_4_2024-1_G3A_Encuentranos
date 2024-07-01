package com.proyecto.encuentranos.servicios;

import com.amazonaws.services.dlm.model.ResourceNotFoundException;
import com.proyecto.encuentranos.modelos.ChatModelo;
import com.proyecto.encuentranos.modelos.ClienteModelo;
import com.proyecto.encuentranos.modelos.ProveedorModelo;
import com.proyecto.encuentranos.repositorios.IChatRepositorio;
import com.proyecto.encuentranos.repositorios.IClienteRepositorio;
import com.proyecto.encuentranos.repositorios.IProveedorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ChatServicio {

    private final IChatRepositorio chatRepositorio;
    private final IClienteRepositorio clienteRepositorio;
    private final IProveedorRepositorio proveedorRepositorio;

    @Autowired
    public ChatServicio(IChatRepositorio chatRepositorio,
                        IClienteRepositorio clienteRepositorio,
                        IProveedorRepositorio proveedorRepositorio) {
        this.chatRepositorio = chatRepositorio;
        this.clienteRepositorio = clienteRepositorio;
        this.proveedorRepositorio = proveedorRepositorio;
    }

    public List<ChatModelo> listarChats() {
        return chatRepositorio.findAll();
    }

    public Optional<ChatModelo> buscarChatPorId(Integer id) {
        return chatRepositorio.findById(id);
    }

    public ChatModelo agregarChat(ChatModelo chat) {
        return chatRepositorio.save(chat);
    }

    //BUSCAR CHAT POR EL ID DEL CLIENTE
    public List<ChatModelo> obtenerChatsPorCliente(int idCliente) {
        return chatRepositorio.findByIdClienteIdCliente(idCliente);
    }

    //BUSCAR CHAT POR EL ID DEL PROVEEDOR
    public List<ChatModelo> obtenerChatsPorProveedor(int idProveedor) {
        return chatRepositorio.findByIdProveedorIdProveedor(idProveedor);
    }

    public ChatModelo obtenerOcrearChat(int idCliente, int idProveedor) {
        // Obtener los modelos de cliente y proveedor desde los repositorios
        ClienteModelo cliente = clienteRepositorio.findById(idCliente)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        ProveedorModelo proveedor = proveedorRepositorio.findById(idProveedor)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado"));

        // Intentar encontrar un chat existente entre el cliente y el proveedor
        Optional<ChatModelo> chatExistente = chatRepositorio.findByIdClienteIdClienteAndIdProveedorIdProveedor(idCliente, idProveedor);

        if (chatExistente.isPresent()) {
            // Si el chat existe, devolverlo
            return chatExistente.get();
        } else {
            // Si el chat no existe, crear uno nuevo y guardarlo
            ChatModelo nuevoChat = new ChatModelo(cliente, proveedor);
            nuevoChat.setEstado("activo"); // Ajustar el estado según tus necesidades
            nuevoChat.setFhCreacion(new Date()); // Establecer la fecha de creación actual o según necesites
            return chatRepositorio.save(nuevoChat);
        }
    }


}
