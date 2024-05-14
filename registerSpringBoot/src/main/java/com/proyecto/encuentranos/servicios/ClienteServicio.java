package com.proyecto.encuentranos.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.ClienteModelo;
import com.proyecto.encuentranos.modelos.ProveedorModelo;
import com.proyecto.encuentranos.modelos.ServicioProveedorModelo;
import com.proyecto.encuentranos.repositorios.IClienteRepositorio;
import com.proyecto.encuentranos.repositorios.IProveedorRepositorio;
import com.proyecto.encuentranos.repositorios.IServicioProveedorRepositorio;

@Service
public class ClienteServicio {
	@Autowired
	IClienteRepositorio clienteRepositorio;
	
	@Autowired
	IProveedorRepositorio proveedorRepositorio;
	
	@Autowired
	IServicioProveedorRepositorio servicioRepositorio;
	
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
        	clienteExistente.setFechaNacimiento(clienteActualizado.getFechaNacimiento());
        	clienteExistente.setDni(clienteActualizado.getDni());
        	clienteExistente.setSexo(clienteActualizado.getSexo());
        	clienteExistente.setCelular(clienteActualizado.getCelular());
        	clienteExistente.setIdDistrito(clienteActualizado.getIdDistrito());

            // Actualizar correo y contraseña del usuario
        	clienteExistente.getIdUsuario().setCorreo(clienteActualizado.getIdUsuario().getCorreo());
        	clienteExistente.getIdUsuario().setContrasena(clienteActualizado.getIdUsuario().getContrasena());

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
	        String distrito = cliente.getIdDistrito().getNombre();
	        List<ProveedorModelo> proveedores = proveedorRepositorio.findByIdDistritoNombre(distrito);
	        List<ProveedorModelo> proveedoresDisponibles = new ArrayList<>();
	        for (ProveedorModelo proveedor : proveedores) {
	            if (proveedor.isDisponible()) {
	                proveedoresDisponibles.add(proveedor);
	            }
	        }
	        return proveedoresDisponibles;
	    } else {
	        return new ArrayList<>();
	    }
	}
	
	public List<ServicioProveedorModelo> encontrarServiciosDeMiDistrito(Long idCliente) {
	    Optional<ClienteModelo> clienteOptional = clienteRepositorio.findById(idCliente);
	    if (clienteOptional.isPresent()) {
	        ClienteModelo cliente = clienteOptional.get();
	        String distrito = cliente.getIdDistrito().getNombre();
	        List<ServicioProveedorModelo> servicios = servicioRepositorio.findByIdProveedorIdDistritoNombre(distrito);
	        List<ServicioProveedorModelo> serviciosDisponibles = new ArrayList<>();
	        for (ServicioProveedorModelo servicio : servicios) {
	            if (servicio.getIdProveedor().isDisponible()) {
	            	serviciosDisponibles.add(servicio);
	            }
	        }
	        return serviciosDisponibles;
	    } else {
	        return new ArrayList<>();
	    }
	}

	
}
