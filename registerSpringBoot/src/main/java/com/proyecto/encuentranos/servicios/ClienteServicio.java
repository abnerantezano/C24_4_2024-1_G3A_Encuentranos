package com.proyecto.encuentranos.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.ClienteModelo;
import com.proyecto.encuentranos.modelos.ProveedorModelo;
import com.proyecto.encuentranos.repositorios.IClienteRepositorio;
import com.proyecto.encuentranos.repositorios.IProveedorRepositorio;

@Service
public class ClienteServicio {
	@Autowired
	IClienteRepositorio clienteRepositorio;
	
	@Autowired
	IProveedorRepositorio proveedorRepositorio;
	
	public ArrayList<ClienteModelo> obtenerClientes(){
		return (ArrayList<ClienteModelo>)clienteRepositorio.findAll();
	}
	
	public ClienteModelo guardarCliente(ClienteModelo cliente) {
		return clienteRepositorio.save(cliente);
	}
	
	public ClienteModelo actualizarCliente(Long id, ClienteModelo clienteActualizado) {
		ClienteModelo clienteExistente = clienteRepositorio.findById(id).orElse(null);
        if (clienteExistente != null) {
            // Actualizar datos del usuario
        	clienteExistente.setNombre(clienteActualizado.getNombre());
        	clienteExistente.setApellidoPaterno(clienteActualizado.getApellidoPaterno());
        	clienteExistente.setApellidoMaterno(clienteActualizado.getApellidoMaterno());
        	clienteExistente.setDni(clienteActualizado.getDni());
        	clienteExistente.setCelular(clienteActualizado.getCelular());
        	clienteExistente.setRegion(clienteActualizado.getRegion());
        	clienteExistente.setProvincia(clienteActualizado.getProvincia());
        	clienteExistente.setDistrito(clienteActualizado.getDistrito());

            // Actualizar correo y contraseña del usuario
        	clienteExistente.getIdUsuario().setCorreo(clienteActualizado.getIdUsuario().getCorreo());
        	clienteExistente.getIdUsuario().setContraseña(clienteActualizado.getIdUsuario().getContraseña());

            // Guardar el cliente actualizado
            clienteExistente = clienteRepositorio.save(clienteExistente);
        }
        return clienteExistente;
	}
	
	public Optional<ClienteModelo> encontrarClientePorId(Long id){
		return clienteRepositorio.findById(id);
	}
	
	public List<ProveedorModelo> encontrarPrestadoresDeMiDistrito(Long idCliente) {
		Optional<ClienteModelo> clienteOptional = clienteRepositorio.findById(idCliente);
		if (clienteOptional.isPresent()) {
			ClienteModelo cliente = clienteOptional.get();
			String distrito = cliente.getDistrito();
			return proveedorRepositorio.findByDistrito(distrito);
		} else {
			return new ArrayList<>();
		}
	}
	
}
