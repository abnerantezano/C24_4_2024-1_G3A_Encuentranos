package com.proyecto.encuentranos.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.ProveedorModelo;
import com.proyecto.encuentranos.repositorios.IProveedorRepositorio;
@Service
public class ProveedorServicio {

	private final IProveedorRepositorio proveedorRepositorio;

	@Autowired
	public ProveedorServicio(IProveedorRepositorio proveedorRepositorio) {
		this.proveedorRepositorio = proveedorRepositorio;
	}

	//CRUD
    
    //CREATE
	public ProveedorModelo guardarProveedor(ProveedorModelo proveedor) {
		return proveedorRepositorio.save(proveedor);
	}
	
	//READ
	public List<ProveedorModelo> obtenerProveedores(){
		return proveedorRepositorio.findAll();
	}
	
	//UPDATE
	public ProveedorModelo actualizarProveedor(Integer id, ProveedorModelo proovedorActualizado) {
		ProveedorModelo proveedorExistente = proveedorRepositorio.findById(id).orElse(null);
        if (proveedorExistente != null) {
            // ACTUALIZAR DATOS DEL CLIENTE
        	proveedorExistente.setNombre(proovedorActualizado.getNombre());
        	proveedorExistente.setApellidoPaterno(proovedorActualizado.getApellidoPaterno());
        	proveedorExistente.setApellidoMaterno(proovedorActualizado.getApellidoMaterno());
        	proveedorExistente.setSexo(proovedorActualizado.getSexo());
        	proveedorExistente.setFechaNacimiento(proovedorActualizado.getFechaNacimiento());
        	proveedorExistente.setDni(proovedorActualizado.getDni());
        	proveedorExistente.setCelular(proovedorActualizado.getCelular());
        	proveedorExistente.setIdDistrito(proovedorActualizado.getIdDistrito());

        	proveedorExistente = proveedorRepositorio.save(proveedorExistente);
        }
        return proveedorExistente;
	}

    //----------------------------------------

	//BUSCAR UN PROVEEDOR POR SU ID
	public Optional<ProveedorModelo> encontrarProveedorPorId(Integer id){
		return proveedorRepositorio.findById(id);
	}

	//BUSCAR UN PROVEEDOR POR SU CORREO
    public Optional<ProveedorModelo> buscarProveedorPorCorreo(String correo) {
        return proveedorRepositorio.findByIdUsuarioCorreo(correo);
    }
	
	//BUSCAR UN PROVEEDOR POR EL ID DEL USUARIO
	public Optional<ProveedorModelo> buscarProveedorPorUsuarioId(Integer usuarioId) {
	    return proveedorRepositorio.findByIdUsuarioIdUsuario(usuarioId);
	}
	
}
