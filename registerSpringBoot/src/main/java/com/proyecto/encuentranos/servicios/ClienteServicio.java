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
//ESTAMOS CREANDO EL SERVICIO PARA Cliente
@Service
public class ClienteServicio {
	
	//INSTANCIAR LAS CLASES QUE USAREMOS
	
	@Autowired
	IClienteRepositorio clienteRepositorio;
	
	@Autowired
	IProveedorRepositorio proveedorRepositorio;
	
	@Autowired
	IServicioProveedorRepositorio servicioRepositorio;
	
	//CRUD
	
	//CREATE
	public ClienteModelo guardarCliente(ClienteModelo cliente) {
		return clienteRepositorio.save(cliente);
	}
	
	//READ
	public ArrayList<ClienteModelo> obtenerClientes(){
		return (ArrayList<ClienteModelo>)clienteRepositorio.findAll();
	}
	
	//UPDATE
	public ClienteModelo actualizarCliente(Integer id, ClienteModelo clienteActualizado) {
		ClienteModelo clienteExistente = clienteRepositorio.findById(id).orElse(null);
        if (clienteExistente != null) {
            // ACTUALIZAR DATOS DEL USUARIO
        	clienteExistente.setNombre(clienteActualizado.getNombre());
        	clienteExistente.setApellidoPaterno(clienteActualizado.getApellidoPaterno());
        	clienteExistente.setApellidoMaterno(clienteActualizado.getApellidoMaterno());
        	clienteExistente.setFechaNacimiento(clienteActualizado.getFechaNacimiento());
        	clienteExistente.setDni(clienteActualizado.getDni());
        	clienteExistente.setSexo(clienteActualizado.getSexo());
        	clienteExistente.setCelular(clienteActualizado.getCelular());
        	clienteExistente.setIdDistrito(clienteActualizado.getIdDistrito());

            // ACTUALIZAR CORREO Y CONTRASEÑA DEL USUARIO
        	clienteExistente.getIdUsuario().setCorreo(clienteActualizado.getIdUsuario().getCorreo());
        	clienteExistente.getIdUsuario().setContrasena(clienteActualizado.getIdUsuario().getContrasena());

            // GUARDAR EL CLIENTE ACTUALIZADO
            clienteExistente = clienteRepositorio.save(clienteExistente);
        }
        return clienteExistente;
	}
	
	//DELETE
    //----------------------------------------
	
	//BUSCAR CLIENTE POR ID
	public Optional<ClienteModelo> encontrarClientePorId(Integer id){
		return clienteRepositorio.findById(id);
	}
	
	//OBTENER LOS PRESTADOR DEL MISMO DISTRITO DEL CLIENTE
	public List<ProveedorModelo> encontrarPrestadoresDeMiDistrito(Integer idCliente) {
		
	    Optional<ClienteModelo> clienteOptional = clienteRepositorio.findById(idCliente);
	    
	    if (clienteOptional.isPresent()) {
	        ClienteModelo cliente = clienteOptional.get();
	        String distrito = cliente.getIdDistrito().getNombre();
	        
	        //BUSCAR PROVEEDORES QUE TENGAN EL DISTRITO IGUAL AL CLIENTE
	        List<ProveedorModelo> proveedores = proveedorRepositorio.findByIdDistritoNombre(distrito);
	        List<ProveedorModelo> proveedoresDisponibles = new ArrayList<>();
	        
	        //AGERGAR LOS PROVEEDORES QUE ESTEN EN EL MISMO DISTRITO
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
	
	//OBTENER LOS SERVICIOS QUE OFRECE UN PROVEEDOR DEL MISMO DISTRITO QUE EL CLIENTE
	public List<ServicioProveedorModelo> encontrarServiciosDeMiDistrito(Integer idCliente) {
	    Optional<ClienteModelo> clienteOptional = clienteRepositorio.findById(idCliente);
	    if (clienteOptional.isPresent()) {
	        ClienteModelo cliente = clienteOptional.get();
	        String distrito = cliente.getIdDistrito().getNombre();
	        
	        //BUSCAR DISTRITOS QUE TENGAN EL DISTRITO IGUAL AL CLIENTE
	        List<ServicioProveedorModelo> servicios = servicioRepositorio.findByIdProveedorIdDistritoNombre(distrito);
	        List<ServicioProveedorModelo> serviciosDisponibles = new ArrayList<>();
	        
	        //AGERGAR LOS SERVICIOS QUE ESTEN EN EL MISMO DISTRITO
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

	//BUSCAR UN CLIENTE POR EL ID DEL USUARIO
	public Optional<ClienteModelo> buscarClientePorUsuarioId(Integer usuarioId) {
	    return clienteRepositorio.findByIdUsuarioId(usuarioId);
	}
	
}
