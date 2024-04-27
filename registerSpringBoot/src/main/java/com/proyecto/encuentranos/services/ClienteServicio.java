package com.proyecto.encuentranos.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.models.ClienteModel;
import com.proyecto.encuentranos.models.DireccionModel;
import com.proyecto.encuentranos.models.PrestadorModel;
import com.proyecto.encuentranos.models.UsuarioModel;
import com.proyecto.encuentranos.repositories.IClienteRepositorio;
import com.proyecto.encuentranos.repositories.IDireccionRepositorio;
import com.proyecto.encuentranos.repositories.IUsuarioRepositorio;

@Service
public class ClienteServicio {
	@Autowired
	IClienteRepositorio clienteRepositorio;
	
    @Autowired
    IUsuarioRepositorio usuarioRepositorio;

    @Autowired
    IDireccionRepositorio direccionRepositorio;
	
	public ArrayList<ClienteModel> obtenerCliente(){
		return (ArrayList<ClienteModel>)clienteRepositorio.findAll();
	}
	
	public ClienteModel guardarCliente(ClienteModel cliente){
		return clienteRepositorio.save(cliente);
	}
	

    public ClienteModel actualizarCliente(Long id, ClienteModel clienteActualizado) {
        ClienteModel clienteExistente = clienteRepositorio.findById(id).orElse(null);

        if (clienteExistente != null) {
            // Actualizar datos del usuario
            clienteExistente.getUsuario().setNombre(clienteActualizado.getUsuario().getNombre());
            clienteExistente.getUsuario().setApellidoPaterno(clienteActualizado.getUsuario().getApellidoPaterno());
            clienteExistente.getUsuario().setApellidoMaterno(clienteActualizado.getUsuario().getApellidoMaterno());
            clienteExistente.getUsuario().setDni(clienteActualizado.getUsuario().getDni());
            clienteExistente.getUsuario().setCelular(clienteActualizado.getUsuario().getCelular());
            clienteExistente.getUsuario().setCorreoElectronico(clienteActualizado.getUsuario().getCorreoElectronico());
            clienteExistente.getUsuario().setContrasena(clienteActualizado.getUsuario().getContrasena());

            // Actualizar dirección del usuario
            clienteExistente.getUsuario().getDirecciones().get(0).setRegion(clienteActualizado.getUsuario().getDirecciones().get(0).getRegion());
            clienteExistente.getUsuario().getDirecciones().get(0).setProvincia(clienteActualizado.getUsuario().getDirecciones().get(0).getProvincia());
            clienteExistente.getUsuario().getDirecciones().get(0).setDistrito(clienteActualizado.getUsuario().getDirecciones().get(0).getDistrito());

            // Guardar el cliente actualizado
            clienteExistente = clienteRepositorio.save(clienteExistente);
        }
        return clienteExistente;
    }
    
    public Optional <ClienteModel> obtenerClientePorId(Long id) {
    	return clienteRepositorio.findById(id);
    }
}
