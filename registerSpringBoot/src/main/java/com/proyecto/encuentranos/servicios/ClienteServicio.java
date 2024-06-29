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
	
	private final IClienteRepositorio clienteRepositorio;
	private final IProveedorRepositorio proveedorRepositorio;
	private final IServicioProveedorRepositorio servicioRepositorio;

	@Autowired
	public ClienteServicio(IClienteRepositorio clienteRepositorio, IProveedorRepositorio proveedorRepositorio, IServicioProveedorRepositorio servicioRepositorio) {
		this.clienteRepositorio = clienteRepositorio;
		this.proveedorRepositorio = proveedorRepositorio;
		this.servicioRepositorio = servicioRepositorio;
	}

	//CRUD
	
	//CREATE
	public ClienteModelo guardarCliente(ClienteModelo cliente) {
		return clienteRepositorio.save(cliente);
	}
	
	//READ
	public List<ClienteModelo> obtenerClientes(){
		return clienteRepositorio.findAll();
	}
	
	//UPDATE
	public ClienteModelo actualizarCliente(Integer id, ClienteModelo clienteActualizado) {
		Optional<ClienteModelo> clienteExistenteOptional = clienteRepositorio.findById(id);
		if (clienteExistenteOptional.isPresent()) {
			ClienteModelo clienteExistente = clienteExistenteOptional.get();

			if (clienteActualizado.getNombre() != null) {
				clienteExistente.setNombre(clienteActualizado.getNombre());
			}
			if (clienteActualizado.getApellidoPaterno() != null) {
				clienteExistente.setApellidoPaterno(clienteActualizado.getApellidoPaterno());
			}
			if (clienteActualizado.getApellidoMaterno() != null) {
				clienteExistente.setApellidoMaterno(clienteActualizado.getApellidoMaterno());
			}
			if (clienteActualizado.getSexo() != null) {
				clienteExistente.setSexo(clienteActualizado.getSexo());
			}
			if (clienteActualizado.getDni() != null) {
				clienteExistente.setDni(clienteActualizado.getDni());
			}
			if (clienteActualizado.getCelular() != null) {
				clienteExistente.setCelular(clienteActualizado.getCelular());
			}
			if (clienteActualizado.getFechaNacimiento() != null) {
				clienteExistente.setFechaNacimiento(clienteActualizado.getFechaNacimiento());
			}
			if (clienteActualizado.getDescripcion() != null) {
				clienteExistente.setDescripcion(clienteActualizado.getDescripcion());
			}
			if (clienteActualizado.getIdDistrito() != null) {
				clienteExistente.setIdDistrito(clienteActualizado.getIdDistrito());
			}

			clienteExistente = clienteRepositorio.save(clienteExistente);
			return clienteExistente;
		}
		return null;
	}

    //----------------------------------------
	
	//BUSCAR CLIENTE POR ID
	public Optional<ClienteModelo> encontrarClientePorId(Integer id){
		return clienteRepositorio.findById(id);
	}

	//BUSCAR CLIENTE POR CORREO
    public Optional<ClienteModelo> buscarClientePorCorreo(String correo) {
        return clienteRepositorio.findByIdUsuarioCorreo(correo);
    }
	
	//OBTENER LOS PRESTADORES DEL MISMO DISTRITO DEL CLIENTE
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
	            if (proveedor.getIdUsuario().getEstado() != null) {
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
	            if (servicio.getIdProveedor().getIdUsuario().getEstado() != null) {
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
	    return clienteRepositorio.findByIdUsuarioIdUsuario(usuarioId);
	}
	
}
