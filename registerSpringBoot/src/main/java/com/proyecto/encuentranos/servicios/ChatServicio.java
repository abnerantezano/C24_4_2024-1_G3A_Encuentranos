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

    public List<ChatModelo> obtenerChatsPorCliente(int idCliente) {
        return chatRepositorio.findByIdClienteIdCliente(idCliente);
    }

    public List<ChatModelo> obtenerChatsPorProveedor(int idProveedor) {
        return chatRepositorio.findByIdProveedorIdProveedor(idProveedor);
    }

    public ChatModelo obtenerOcrearChat(int idCliente, int idProveedor) {
        ClienteModelo cliente = clienteRepositorio.findById(idCliente).orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        ProveedorModelo proveedor = proveedorRepositorio.findById(idProveedor).orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado"));

        return chatRepositorio.findByIdClienteIdClienteAndIdProveedorIdProveedor(idCliente, idProveedor)
                .orElseGet(() -> {
                    ChatModelo nuevoChat = new ChatModelo(cliente, proveedor);
                    return chatRepositorio.save(nuevoChat);
                });
    }

}
